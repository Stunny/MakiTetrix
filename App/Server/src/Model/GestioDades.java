package Model;
import java.sql.*;
import Controller.ServerController;
import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.StringJoiner;


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

    public ArrayList<String> plenaUsuaris(){
        ArrayList<String> usuaris = new ArrayList<String>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/MakiTetris", "root", "root");
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("select user from Login");
            while (r.next()) {
               usuaris.add(r.getString("user"));
            }
            c.close();

        } catch (Exception var2) {
            var2.printStackTrace();
        }

        return usuaris;
    }

    public User mostraDades (String nom){
        User u = new User();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/MakiTetris", "root", "root");
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("select * from Login");
            while (r.next()) {
                if(r.getString("user").equals(nom)){
                    u.setUserName(r.getString("user"));
                    u.setPassword(r.getString("password"));
                    u.setEmail(r.getString("email"));
                    return u;
                }
            }
            c.close();

        } catch (Exception var2) {
            var2.printStackTrace();
        }
        return u;
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
