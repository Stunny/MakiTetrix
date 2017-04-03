package Model;

import Controller.ServerController;
import java.io.DataInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by Admin on 24/03/2017.
 */
public class GestioDades {

        public GestioDades() {
        }

        void conectar() {
            try {
                Connection c = DriverManager.getConnection("jdbc:mysql://localhost:8080/MakiTetris", "root", "root");
                Statement s = c.createStatement();
                ResultSet r = s.executeQuery("select * from Login");
                while (r.next()){
                    System.out.println(r.getString("mail"));
                }

            } catch (Exception var2) {
                var2.printStackTrace();
            }

        }
}