/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pd1_lavrenovs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vadims Lavrenovs
 */

/**
 * User klase ir bāzes klase visiem sistēmas lietotājiem.
 * Tā satur kopīgus datus un metodes gan Studentam, gan Administratoram.
 */
public class User {

    protected String name;
    protected String login;
    protected String password;

    /**
     * Lietotāja konstruktors.
     *
     * @param name lietotāja vārds
     * @param login pieteikšanās vārds
     * @param password parole
     */
    public User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }

    /**
     * Metode autorizācijai sistēmā.
     *
     * @param login ievadītais pieteikšanās vārds
     * @return true, ja pieteikšanās vārds ir pareizs
     */
    static public String enter(String login, String password) throws Exception {

        String sql = "SELECT role FROM users WHERE login = ? AND password = ?";

        try (Connection con = DataBase.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, login);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("role"); // "student" / "admin"
                }
                return null;

            }

        }
    }

    public static boolean registerStudent(String name, String surname, String login, String password) {

    String checkSql = "SELECT 1 FROM USERS WHERE LOGIN = ?";
    String insertSql = "INSERT INTO USERS (NAME, LOGIN, PASSWORD, ROLE) VALUES (?, ?, ?, ?)";

    try (Connection con = DataBase.getConnection()) {

        try (PreparedStatement ps = con.prepareStatement(checkSql)) {
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return false;
                }
            }
        }

        try (PreparedStatement ps = con.prepareStatement(insertSql)) {
            String fullName = name + " " + surname;

            ps.setString(1, fullName);
            ps.setString(2, login);
            ps.setString(3, password);
            ps.setString(4, "student");

            ps.executeUpdate();
            return true;
        }

    } catch (Exception ex) {
        Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
    }

    return false;
}
    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}