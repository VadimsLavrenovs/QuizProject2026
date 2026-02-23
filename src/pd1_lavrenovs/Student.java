/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pd1_lavrenovs;

/**
 *
 * @author Vadims Lavrenovs
 */

/**
 * Students, kurš aizpilda testus un saglabā savus rezultātus.
 */
public class Student extends User {
    private int questionsCount = 0;
    private int rightAnswers = 0;

    public Student(String name, String login, String password) {
        super(name, login, password);
    }

    /**
     * Saņem studenta atbildi uz jautājumu.
     * @param question jautājuma objekts
     * @param answer atbildes teksts
     */
    public void getAnswers(Question question, String answer) {

    }

    /**
     * Uzsāk testa kārtošanu.
     * @param test testa objekts
     */
    public void beginTest(Test test) {
        // Testa uzsākšanas loģika
    }

    /**
     * Notīra studenta pašreizējos rezultātus.
     */
    public void clear() {

    }
}
