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
 * Administrators, kurš pārvalda testus un lietotāju piekļuvi.
 */
public class Admin extends User {
    
    public Admin(String name, String login, String password) {
        super(name, login, password);
    }

    /**
     * Izveido jaunu testa objektu.
     * @return izveidotais tests
     */
    public Test createTest() {
        return new Test();
    }

    /**
     * Aktivizē testu izpildei.
     * @param test testa objekts
     */
    public void startTest(Test test) {
        // Testu aktivizācijas loģika
    }

    /**
     * Ierobežo testa atkārtotas kārtošanas iespēju.
     * @param test testa objekts
     */
    public void restrictRetake(Test test) {
        // Ierobežošanas loģika
    }
}
