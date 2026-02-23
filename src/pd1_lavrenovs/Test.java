/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pd1_lavrenovs;

/**
 *
 * @author Vadims Lavrenovs
 */

import java.util.ArrayList;
import java.util.List;

/**
 * Test klase pārvalda visu testēšanas procesu.
 * Tā glabā lietotājus, jautājumus un rezultātus.
 */
public class Test {
    private List<User> users = new ArrayList<>();
    private List<Question> questions = new ArrayList<>();
    private User currentUser;
    private boolean allowRetake;
    private boolean isActive;

    /**
     * Pievieno jaunu lietotāju sistēmai.
     * @param name vārds
     * @param login pieteikšanās vārds
     * @param password parole
     * @param repetition paroles atkārtojums
     */
    public void addUser(String name, String login, String password, String repetition) {
        // Reģistrācijas loģika
        if (!password.equals(repetition)) {
            throw new IllegalArgumentException("Paroles nesakrīt");
        }

        for (User u : users) {
            if (u.getLogin().equals(login)) {
                throw new IllegalArgumentException("Lietotājs ar šādu lietotājvārdu jau eksistē");
            }
        }

        User newUser = new Student(name, login, password);
        users.add(newUser);
    }

    public void addQuestion(String text, String answer ,List<String> answerOptions){
        Question newQuestion = new Question(text, answer, answerOptions);
        questions.add(newQuestion);
    }

    /**
     * Meklē lietotāju autorizācijai.
     * @param login pieteikšanās vārds
     * @param password parole
     * @return lietotāja objekts vai null
     */
    private User findUser(String login, String password) {
        return null; 
    }

    /**
     * Aprēķina vidējo vērtējumu.
     * @return vidējais vērtējums
     */
    private float showAverageScore() {
        return 0.0f;
    }

    /**
     * Atgriež rezultātu sarakstu.
     * @return rezultātu saraksts
     */
    private List<String> getResults() {
        return new ArrayList<>();
    }

    /** Saglabā datus failā. */
    private void save() { }

    /** Ielādē datus no faila. */
    private void load() { }
}