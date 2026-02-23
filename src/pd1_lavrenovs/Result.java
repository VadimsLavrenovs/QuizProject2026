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
 * Result klase glabā studenta testa rezultātus.
 * Tā satur informāciju par pareizām, nepareizām un neatbildētām atbildēm.
 */
public class Result {
    /**
     * Lietotājs, kuram pieder rezultāts
     */
    private User user;

    /**
     * Tests, kuram pieder rezultāts
     */
    private Test test;

    /**
     * Pareizi atbildēto jautājumu skaits
     */
    private int correctAnswers;

    /**
     * Nepareizi atbildēto jautājumu skaits
     */
    private int wrongAnswers;

    /**
     * Neatbildēto jautājumu skaits
     */
    private int unanswered;

    /**
     * Aprēķinātais gala vērtējums
     */
    private double grade;

    /**
     * Komentārs par testa rezultātu
     */
    private String comment;

    /**
     * Izveido testa rezultāta objektu
     * @param user lietotājs, kuram pieder rezultāts
     * @param test tests, par kuru tiek saglabāts rezultāts
     */
    public Result(User user, Test test) {
        this.user = user;
        this.test = test;
    }

    /**
     * Aprēķina gala vērtējumu
     * @return aprēķinātais vērtējums
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
     * Atgriež komentāru par testa rezultātu
     * @return komentārs
     */
    public String getComment() {
        return comment;
    }
}