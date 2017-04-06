package Model;

import Controller.ServerController;
import java.io.DataInputStream;
import java.util.ArrayList;


/**
 * Created by Admin on 24/03/2017.
 */
public class GestioDades {
    //tractament de dades --> info del server a la BBDD (Miquel)
    public GestioDades(){
        plenaUsuaris();
    }

    void conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/MakiTetris", "root", "root");
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("select user from Login");
            while (r.next()) {
                System.out.println(r.getString("user"));
            }

        } catch (Exception var2) {
            var2.printStackTrace();
        }
    }

    public String[] plenaUsuaris(){
        String[] usuaris = {"1", "2", "3", "4"};
        //retornar la llista dels usuaris de la BBDD

        return usuaris;
    }

    void addUser(User u) {
        try {
            // create a mysql database connection
            Class.forName("com.mysql.jdbc.Driver");
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/MakiTetris", "root", "root");

            // the mysql insert statement
            String query = " insert into users (user, mail, password)" + " values (?, ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = c.prepareStatement(query);
            preparedStmt.setString(1, u.getUserName());
            preparedStmt.setString(2, u.getEmail());
            preparedStmt.setString(3, u.getPassword());


            // execute the preparedstatement
            preparedStmt.execute();

            c.close();
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }
}
