/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pd1_lavrenovs;

import java.util.List;

/**
 *
 * @author Vadims Lavrenovs
 */

/**
 * Question klase apraksta vienu testa jautājumu.
 * Tā satur jautājuma tekstu un pareizo atbildi.
 */
public class Question {

    private String text;
    private String answer;
    private List<String> answerOptions;

    /**
     * Jautājuma konstruktors.
     *
     * @param text jautājuma teksts
     * @param answer pareizā atbilde
     * @param answerOptions atbilžu variantu saraksts
     */
    public Question(String text, String answer, List<String> answerOptions) {
        this.text = text;
        this.answer = answer;
        this.answerOptions = answerOptions;
    }

    public List<String> getAnswerOptions() {
        return answerOptions;
    }

    /**
     * Atgriež jautājuma tekstu.
     *
     * @return jautājuma teksts
     */
    public String getText() {
        return text;
    }

    /**
     * Pārbauda atbildes pareizību.
     *
     * @param answer lietotāja atbilde
     * @return true, ja atbilde ir pareiza
     */
    public boolean isCorrect(String answer) {
        return this.answer.equalsIgnoreCase(answer);
    }
}
