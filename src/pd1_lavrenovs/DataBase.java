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
 *
 * @author Vadims Lavrenovs
 */
public class DataBase {    
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
