/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pd1_lavrenovs;

/**
 * Result klase glabā studenta testa rezultātus.
 * Tā satur informāciju par pareizām, nepareizām un neatbildētām atbildēm,
 * kā arī aprēķina gala atzīmi 10 ballu skalā.
 *
 * @author Vadims Lavrenovs
 * @version 1.1
 */
public class Result {

    /** Lietotājs, kuram pieder rezultāts */
    private User user;

    /** Tests, kuram pieder rezultāts */
    private Test test;

    /** Pareizi atbildēto jautājumu skaits */
    private int correctAnswers;

    /** Nepareizi atbildēto jautājumu skaits */
    private int wrongAnswers;

    /** Neatbildēto jautājumu skaits */
    private int unanswered;

    /** Aprēķinātais gala vērtējums */
    private double grade;

    /** Komentārs par testa rezultātu */
    private String comment;

    /**
     * Izveido testa rezultāta objektu.
     *
     * @param user lietotājs, kuram pieder rezultāts
     * @param test tests, par kuru tiek saglabāts rezultāts
     */
    public Result(User user, Test test) {
        this.user = user;
        this.test = test;
        this.correctAnswers = 0;
        this.wrongAnswers = 0;
        this.unanswered = 0;
    }

    /**
     * Reģistrē pareizas atbildes faktu.
     */
    public void addCorrect() {
        correctAnswers++;
    }

    /**
     * Reģistrē nepareizas atbildes faktu.
     */
    public void addWrong() {
        wrongAnswers++;
    }

    /**
     * Reģistrē neatbildētu jautājumu.
     */
    public void addUnanswered() {
        unanswered++;
    }

    /**
     * Aprēķina gala vērtējumu 10 ballu skalā.
     * Formula: (pareizās / kopā) × 10
     *
     * @return aprēķinātais vērtējums; 0, ja nav jautājumu
     */
    public double calculateGrade() {
        int total = correctAnswers + wrongAnswers + unanswered;
        if (total == 0) {
            return 0;
        }
        grade = (double) correctAnswers / total * 10;
        return grade;
    }

    /**
     * Atgriež pareizi atbildēto jautājumu skaitu.
     *
     * @return pareizo atbilžu skaits
     */
    public int getCorrectAnswers() {
        return correctAnswers;
    }

    /**
     * Atgriež nepareizi atbildēto jautājumu skaitu.
     *
     * @return nepareizo atbilžu skaits
     */
    public int getWrongAnswers() {
        return wrongAnswers;
    }

    /**
     * Atgriež neatbildēto jautājumu skaitu.
     *
     * @return neatbildēto jautājumu skaits
     */
    public int getUnanswered() {
        return unanswered;
    }

    /**
     * Atgriež aprēķināto atzīmi (jāizsauc pēc {@link #calculateGrade()}).
     *
     * @return atzīme
     */
    public double getGrade() {
        return grade;
    }

    /**
     * Atgriež komentāru par testa rezultātu.
     *
     * @return komentārs
     */
    public String getComment() {
        return comment;
    }

    /**
     * Iestata komentāru par testa rezultātu.
     *
     * @param comment komentāra teksts
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    /**
     * Saglabā testa rezultātu datubāzē.
     *
     * @param login lietotāja login
     * @param testId testa ID
     * @return true ja saglabāšana veiksmīga
     */
    public static boolean saveResultToDB(String login, int testId, 
        int correct, int wrong, int unanswered, double grade) {
        String sql = "INSERT INTO APP.RESULTS (USER_LOGIN, TEST_ID, "
                   + "CORRECT_ANSWERS, WRONG_ANSWERS, UNANSWERED, GRADE) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        try (java.sql.Connection con = DataBase.getConnection();
             java.sql.PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, login);
            ps.setInt(2, testId);
            ps.setInt(3, correct);
            ps.setInt(4, wrong);
            ps.setInt(5, unanswered);
            ps.setDouble(6, grade);
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(Result.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
            return false;
        }
        }

        /**
         * Ielādē vidējo atzīmi no datubāzes pēc lietotāja login.
         *
         * @param login lietotāja login
         * @return vidējā atzīme vai 0.0 ja nav rezultātu
         */
        public static double loadAverageGrade(String login) {
            String sql = "SELECT AVG(GRADE) FROM APP.RESULTS WHERE USER_LOGIN = ?";
            try (java.sql.Connection con = DataBase.getConnection();
                 java.sql.PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, login);
                try (java.sql.ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) return rs.getDouble(1);
                }
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(Result.class.getName())
                        .log(java.util.logging.Level.SEVERE, null, ex);
            }
            return 0.0;
        }
}