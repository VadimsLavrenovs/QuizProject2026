/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pd1_lavrenovs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Test klase pārvalda visu testēšanas procesu.
 * Nodrošina jautājumu ielādi no datubāzes, jaunu jautājumu saglabāšanu,
 * kā arī lietotāju reģistrāciju un pārvaldību.
 *
 * @author Vadims Lavrenovs
 */
public class Test {

    /** Sistēmas lietotāji */
    private List<User> users = new ArrayList<>();

    /** Šī testa jautājumi (ielādēti no DB) */
    private List<Question> questions = new ArrayList<>();

    /** Pašreizējais aktīvais lietotājs */
    private User currentUser;

    /** Vai atkārtota kārtošana ir atļauta */
    private boolean allowRetake;

    /** Vai tests ir aktīvs */
    private boolean isActive;

    /** Pašreizējā testa ID datubāzē */
    private int testId = 1;

    /**
     * Ielādē jautājumus no datubāzes pēc norādītā testa ID.
     * Katrs jautājums tiek pārveidots par {@link Question} objektu
     * ar trim atbilžu variantiem.
     *
     * @param testId testa identifikators datubāzē
     * @return jautājumu saraksts; tukšs saraksts kļūdas gadījumā
     */
    public List<Question> loadQuestionsFromDB(int testId) {
        this.testId = testId;
        questions.clear();
        String sql = "SELECT TEXT, ANSWER, OPTION1, OPTION2, OPTION3 "
                   + "FROM APP.QUESTIONS WHERE TEST_ID = ?";
        try (Connection con = DataBase.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, testId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String text    = rs.getString("TEXT");
                    String answer  = rs.getString("ANSWER");
                    String opt1    = rs.getString("OPTION1");
                    String opt2    = rs.getString("OPTION2");
                    String opt3    = rs.getString("OPTION3");

                    List<String> opts = new ArrayList<>();
                    opts.add(opt1);
                    opts.add(opt2);
                    opts.add(opt3);

                    questions.add(new Question(text, answer, opts));
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, "DB kļūda ielādējot jautājumus", ex);
        }
        return questions;
    }

    /**
     * Saglabā jaunu jautājumu datubāzē norādītajam testam.
     *
     * @param questionText jautājuma teksts
     * @param correctAnswer pareizā atbilde (jābūt vienai no option1/2/3)
     * @param option1 pirmais atbilžu variants
     * @param option2 otrais atbilžu variants
     * @param option3 trešais atbilžu variants
     * @param testId testa ID, kuram pieder jautājums
     * @return {@code true} ja saglabāšana veiksmīga; {@code false} citādi
     */
    public static boolean saveQuestionToDB(String questionText, String correctAnswer,
            String option1, String option2, String option3, int testId) {
        String sql = "INSERT INTO APP.QUESTIONS (TEXT, ANSWER, OPTION1, OPTION2, OPTION3, TEST_ID) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DataBase.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, questionText);
            ps.setString(2, correctAnswer);
            ps.setString(3, option1);
            ps.setString(4, option2);
            ps.setString(5, option3);
            ps.setInt(6, testId);
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, "DB kļūda saglabājot jautājumu", ex);
            return false;
        }
    }

    /**
     * Izveido jaunu testu datubāzē ar norādīto nosaukumu.
     *
     * @param testName testa nosaukums
     * @return jaunā testa ID vai -1 kļūdas gadījumā
     */
    public static int createTestInDB(String testName) {
        String sql = "INSERT INTO APP.TESTS (NAME) VALUES (?)";
        try (Connection con = DataBase.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, testName);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, "DB kļūda veidojot testu", ex);
        }
        return -1;
    }

    /**
     * Ielādē pieejamo testu sarakstu no datubāzes.
     *
     * @return karte ID → nosaukums; tukša karte kļūdas gadījumā
     */
    public static java.util.Map<Integer, String> loadTestListFromDB() {
        java.util.LinkedHashMap<Integer, String> map = new java.util.LinkedHashMap<>();
        String sql = "SELECT ID, NAME FROM APP.TESTS ORDER BY ID";
        try (Connection con = DataBase.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                map.put(rs.getInt("ID"), rs.getString("NAME"));
            }
        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, "DB kļūda ielādējot testus", ex);
        }
        return map;
    }

    /**
     * Pievieno jaunu lietotāju sarakstam (atmiņā).
     *
     * @param name       vārds
     * @param login      pieteikšanās vārds
     * @param password   parole
     * @param repetition paroles atkārtojums validācijai
     * @throws IllegalArgumentException ja paroles nesakrīt vai login jau eksistē
     */
    public void addUser(String name, String login, String password, String repetition) {
        if (!password.equals(repetition)) {
            throw new IllegalArgumentException("Paroles nesakrīt");
        }
        for (User u : users) {
            if (u.getLogin().equals(login)) {
                throw new IllegalArgumentException("Lietotājs ar šādu lietotājvārdu jau eksistē");
            }
        }
        users.add(new Student(name, login, password));
    }

    /**
     * Pievieno jautājumu atmiņas sarakstam (bez DB saglabāšanas).
     *
     * @param text          jautājuma teksts
     * @param answer        pareizā atbilde
     * @param answerOptions atbilžu variantu saraksts
     */
    public void addQuestion(String text, String answer, List<String> answerOptions) {
        questions.add(new Question(text, answer, answerOptions));
    }

    /**
     * Atgriež lietotāju sarakstu (atmiņā).
     *
     * @return lietotāju saraksts
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Atgriež jautājumu sarakstu.
     *
     * @return jautājumu saraksts
     */
    public List<Question> getQuestions() {
        return questions;
    }

    /**
     * Atgriež jautājumu pēc indeksa.
     *
     * @param index jautājuma indekss (no 0)
     * @return {@link Question} objekts vai {@code null}, ja indekss ārpus diapazona
     */
    public Question getQuestion(int index) {
        if (index < 0 || index >= questions.size()) return null;
        return questions.get(index);
    }

    /**
     * Atgriež jautājumu kopējo skaitu.
     *
     * @return jautājumu skaits
     */
    public int getQuestionCount() {
        return questions.size();
    }
    
    public static int getTestIdByName(String name) {
    String sql = "SELECT ID FROM APP.TESTS WHERE NAME = ?";
    try (Connection con = DataBase.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, name);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt("ID");
        }
    } catch (Exception ex) {
        Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
    }
    return -1;
    }
    
    public static void setRetakeLimit(int testId, int limit) {
        String sql = "UPDATE APP.TESTS SET MAX_RETAKES = ? WHERE ID = ?";
        try (Connection con = DataBase.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ps.setInt(2, testId);
            ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
    }
    }

    public static int getRetakeLimit(int testId) {
        String sql = "SELECT MAX_RETAKES FROM APP.TESTS WHERE ID = ?";
        try (Connection con = DataBase.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, testId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("MAX_RETAKES");
            }
        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public int getCurrentTestId() {
        return testId;
    }
    
    public static void setTestActive(int testId, boolean active) {
        String sql = "UPDATE APP.TESTS SET IS_ACTIVE = ? WHERE ID = ?";
        try (Connection con = DataBase.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, active ? 1 : 0);
            ps.setInt(2, testId);
            ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean isTestActive(int testId) {
        String sql = "SELECT IS_ACTIVE FROM APP.TESTS WHERE ID = ?";
        try (Connection con = DataBase.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, testId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("IS_ACTIVE") == 1;
            }
        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
}