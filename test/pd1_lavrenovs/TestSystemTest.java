package pd1_lavrenovs;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import java.util.Arrays;
import java.util.List;

public class TestSystemTest {

    private Question question;
    private Student student;
    private pd1_lavrenovs.Test testObj;

    @Before
    public void setUp() {
        List<String> opts = Arrays.asList("Rīga", "Tallina", "Viļņa");
        question = new Question("Kāda ir Latvijas galvaspilsēta?", "Rīga", opts);
        student = new Student("Jānis", "janis", "parole123");
        testObj = new pd1_lavrenovs.Test();
    }

    @Test
    public void testQuestionIsCorrect() {
        Assert.assertTrue(question.isCorrect("Rīga"));
        Assert.assertTrue(question.isCorrect("rīga"));
        Assert.assertFalse(question.isCorrect("Tallina"));
        Assert.assertFalse(question.isCorrect(""));
    }

    @Test
    public void testResultCalculateGrade() {
        Result result = new Result(student, testObj);
        result.addCorrect();
        result.addCorrect();
        result.addWrong();
        Assert.assertEquals(6.67, result.calculateGrade(), 0.01);
        Assert.assertEquals(2, result.getCorrectAnswers());
        Assert.assertEquals(1, result.getWrongAnswers());
    }

    @Test
    public void testResultCalculateGradeEmpty() {
        Result result = new Result(student, testObj);
        Assert.assertEquals(0.0, result.calculateGrade(), 0.001);
    }

    @Test
    public void testStudentGetAnswersAndClear() {
        student.getAnswers(question, "Rīga");
        student.getAnswers(question, "Tallina");
        Assert.assertEquals(2, student.getQuestionsCount());
        Assert.assertEquals(1, student.getRightAnswers());
        student.clear();
        Assert.assertEquals(0, student.getQuestionsCount());
        Assert.assertEquals(0, student.getRightAnswers());
    }

    @Test
    public void testAddAndGetQuestion() {
        List<String> opts = Arrays.asList("A", "B", "C");
        testObj.addQuestion("Testa jautājums?", "A", opts);
        Assert.assertEquals(1, testObj.getQuestionCount());
        Question q = testObj.getQuestion(0);
        Assert.assertNotNull(q);
        Assert.assertEquals("Testa jautājums?", q.getText());
        Assert.assertTrue(q.isCorrect("A"));
        Assert.assertNull(testObj.getQuestion(5));
    }

    @Test
    public void testAddUserValidation() {
        testObj.addUser("Pēteris", "peteris", "abc123", "abc123");
        Assert.assertEquals(1, testObj.getUsers().size());
        try {
            testObj.addUser("Anna", "anna", "pass1", "pass2");
            Assert.fail("Bija jāmet IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(e.getMessage().contains("Paroles"));
        }
        try {
            testObj.addUser("Pēteris2", "peteris", "abc123", "abc123");
            Assert.fail("Bija jāmet IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(e.getMessage().contains("eksistē"));
        }
        Assert.assertEquals(1, testObj.getUsers().size());
    }
}