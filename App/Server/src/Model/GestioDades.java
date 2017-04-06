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
            Class.forName("com.mysql.jdbc.Driver");
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/MakiTetris", "root", "root");
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("select * from Login");
            while (r.next()) {
                System.out.println(r.getString("mail"));
            }

        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    void addUser(User u) {
        try {
            // create a mysql database connection
            String myDriver = "org.gjt.mm.mysql.Driver";
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/MakiTetris", "root", "root");
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "root", "");


            // the mysql insert statement
            String query = " insert into users (first_name, last_name, date_created, is_admin, num_points)"
                    + " values (?, ?, ?, ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, "Barney");
            preparedStmt.setString(2, "Rubble");
            preparedStmt.setDate(3, startDate);
            preparedStmt.setBoolean(4, false);
            preparedStmt.setInt(5, 5000);

            // execute the preparedstatement
            preparedStmt.execute();

            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }
}
}