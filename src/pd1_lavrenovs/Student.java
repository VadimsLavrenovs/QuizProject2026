/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pd1_lavrenovs;

/**
 * Student klase pārstāv studentu, kurš aizpilda testus.
 * Paplašina {@link User} klasi un satur loģiku atbilžu apstrādei
 * un rezultātu uzkrāšanai.
 *
 * @author Vadims Lavrenovs
 * @version 1.1
 */
public class Student extends User {

    /** Kopējais atbildēto jautājumu skaits šajā sesijā */
    private int questionsCount = 0;

    /** Pareizo atbilžu skaits šajā sesijā */
    private int rightAnswers = 0;

    /**
     * Izveido jaunu studenta objektu.
     *
     * @param name     studenta vārds
     * @param login    pieteikšanās vārds
     * @param password parole
     */
    public Student(String name, String login, String password) {
        super(name, login, password);
    }

    /**
     * Apstrādā studenta atbildi uz konkrētu jautājumu.
     * Palielina {@code questionsCount} par 1, un ja atbilde ir pareiza —
     * arī {@code rightAnswers} par 1.
     *
     * @param question jautājuma objekts
     * @param answer   studenta izvēlētās atbildes teksts
     */
    public void getAnswers(Question question, String answer) {
        questionsCount++;
        if (question.isCorrect(answer)) {
            rightAnswers++;
        }
    }

    /**
     * Uzsāk testa kārtošanu — atjauno skaitītājus.
     *
     * @param test testa objekts, kuru students sāk kārtot
     */
    public void beginTest(Test test) {
        clear();
    }

    /**
     * Notīra studenta pašreizējos rezultātus (jauna testa sākumam).
     */
    public void clear() {
        questionsCount = 0;
        rightAnswers = 0;
    }

    /**
     * Atgriež atbildēto jautājumu kopējo skaitu.
     *
     * @return jautājumu skaits
     */
    public int getQuestionsCount() {
        return questionsCount;
    }

    /**
     * Atgriež pareizo atbilžu skaitu.
     *
     * @return pareizo atbilžu skaits
     */
    public int getRightAnswers() {
        return rightAnswers;
    }
}