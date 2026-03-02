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
 * User klase ir bāzes klase visiem sistēmas lietotājiem.
 * Tā satur kopīgus datus un metodes gan Studentam, gan Administratoram.
 * 
 * Klase nodrošina:
 *   Lietotāja autorizāciju sistēmā
 *   Jauna studenta reģistrāciju
 * 
 * @author Vadims Lavrenovs
 */
public class User {

    /** Lietotāja pilnais vārds */
    protected String name;

    /** Lietotāja pieteikšanās vārds */
    protected String login;

    /** Lietotāja parole */
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

        String sql = "SELECT role FROM APP.users WHERE login = ? AND password = ?";

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

     /**
     * Reģistrē jaunu studentu sistēmā.
     * 
     * Metode pārbauda, vai login jau eksistē.
     * Ja neeksistē, tiek izveidots jauns lietotājs ar lomu "student".
     *
     * @param name studenta vārds
     * @param surname studenta uzvārds
     * @param login izvēlētais pieteikšanās vārds
     * @param password izvēlētā parole
     * @return true, ja reģistrācija veiksmīga;
     *         false, ja login jau eksistē vai radās kļūda
     */
    public static boolean registerStudent(String name, String surname, String login, String password) {

    String checkSql = "SELECT 1 FROM APP.USERS WHERE LOGIN = ?";
    String insertSql = "INSERT INTO APP.USERS (NAME, LOGIN, PASSWORD, ROLE) VALUES (?, ?, ?, ?)";

    try (Connection con = DataBase.getConnection()) {
        // Pārbaude, vai login jau eksistē
        try (PreparedStatement ps = con.prepareStatement(checkSql)) {
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return false;
                }
            }
        }
        
        // Jauna lietotāja pievienošana
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
    /**
     * @return lietotāja pieteikšanās vārds
     */
    public String getLogin() {
        return login;
    }

    /**
     * @return lietotāja parole
     */
    public String getPassword() {
        return password;
    }
}