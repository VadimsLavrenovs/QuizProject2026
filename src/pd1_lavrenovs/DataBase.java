/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pd1_lavrenovs;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
/**
 * Klase DataBase nodrošina savienojuma izveidi ar datubāzi,
 * izmantojot konfigurācijas parametrus no faila config.properties.
 * 
 * Konfigurācijas failā jābūt šādiem parametriem:
 *   db.url - datubāzes adrese
 *   db.user - lietotājvārds
 *   db.password - parole
 * 
 * @author Vadims Lavrenovs
 */
public class DataBase {
    /**
     * Izveido un atgriež savienojumu ar datubāzi.
     * 
     * Metode nolasa konfigurācijas failu no resursu mapes
     * (/pd1_lavrenovs/config.properties) un izmanto tajā
     * definētos parametrus savienojuma izveidei.
     *
     * @return Connection objekts, kas reprezentē aktīvu savienojumu ar datubāzi
     * @throws Exception ja konfigurācijas fails nav atrasts vai savienojumu neizdodas izveidot
     */
    public static Connection getConnection() throws Exception {
        Properties p = new Properties();
        try (InputStream in = DataBase.class.getResourceAsStream("/pd1_lavrenovs/config.properties")) {
            if (in == null) throw new RuntimeException("config.properties not found");
            p.load(in);
        }
        return DriverManager.getConnection(
            p.getProperty("db.url"),
            p.getProperty("db.user"),
            p.getProperty("db.password")
        );
    }
}
