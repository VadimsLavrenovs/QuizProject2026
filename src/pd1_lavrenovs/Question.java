/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pd1_lavrenovs;

import java.util.List;

/**
 * Question klase apraksta vienu testa jautājumu.
 * Tā satur jautājuma tekstu, pareizo atbildi un atbilžu variantus.
 *
 * @author Vadims Lavrenovs
 * @version 1.1
 */
public class Question {

    /** Jautājuma teksts */
    private String text;

    /** Pareizās atbildes teksts */
    private String answer;

    /** Atbilžu varianti (A, B, C) */
    private List<String> answerOptions;

    /**
     * Jautājuma konstruktors.
     *
     * @param text          jautājuma teksts
     * @param answer        pareizā atbilde
     * @param answerOptions atbilžu variantu saraksts
     */
    public Question(String text, String answer, List<String> answerOptions) {
        this.text = text;
        this.answer = answer;
        this.answerOptions = answerOptions;
    }

    /**
     * Atgriež atbilžu variantu sarakstu.
     *
     * @return atbilžu saraksts
     */
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
     * Pārbauda, vai lietotāja atbilde ir pareiza.
     * Salīdzināšana notiek neatkarīgi no reģistra.
     *
     * @param userAnswer lietotāja ievadītā atbilde
     * @return {@code true}, ja atbilde ir pareiza; {@code false} citādi
     */
    public boolean isCorrect(String userAnswer) {
        return this.answer.equalsIgnoreCase(userAnswer);
    }

    /**
     * Atgriež pareizās atbildes tekstu.
     *
     * @return pareizā atbilde
     */
    public String getAnswer() {
        return answer;
    }
}