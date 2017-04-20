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


    public ArrayList<String> plenaUsuaris(){
        ArrayList<String> usuaris = new ArrayList<>();
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
        User u = new User(null, null, null);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/MakiTetris", "root", "root");
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("select * from Login");
            while (r.next()) {
                if(r.getString("user").equals(nom)){
                    u.setUserName(r.getString("user"));
                    u.setPassword(r.getString("password"));
                    u.setEmail(r.getString("mail"));
                    return u;
                }
            }
            c.close();

        } catch (Exception var2) {
            var2.printStackTrace();
        }
        return u;
    }

    public ArrayList<String> busca(String b){
        ArrayList<String>trobats = new ArrayList<>();
        try {
            // create a mysql database connection
            Class.forName("com.mysql.jdbc.Driver");
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/MakiTetris", "root", "root");

            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("select user,mail, password from Login");
            while (r.next()) {
                if(r.getString("user").contains(b)){
                    trobats.add(r.getString("user"));
                    System.out.println(r.getString("user"));
                }
            }

            c.close();

        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }

        return trobats;
    }

    public int checkExisteix (User u){
        Boolean nomrepe = false;
        Boolean mailrepe = false;
        try {
            // create a mysql database connection
            Class.forName("com.mysql.jdbc.Driver");
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/MakiTetris", "root", "root");

            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("select user,mail from Login");
            while (r.next()) {
                if(r.getString("user").equals(u.getUserName())){
                    nomrepe = true;
                }
                if(r.getString("mail").equals(u.getEmail())){
                    mailrepe = true;
                }
            }

            c.close();
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
       // System.out.println("nomrepe ="+nomrepe+" mailrepe ="+mailrepe);
        if(nomrepe||mailrepe){
            if (nomrepe&&mailrepe){
                return 4;
            }else{
                if (nomrepe){
                    return 2;
                }else{
                    return 3;
                }
            }
        }else{
            return 1;
        }
    }

    public int loginUser(String nom, String contra){
        try {
            // create a mysql database connection
            Class.forName("com.mysql.jdbc.Driver");
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/MakiTetris", "root", "root");

            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("select user,mail, password from Login");
            while (r.next()) {
                if(r.getString("user").equals(nom)||r.getString("mail").equals(nom)){
                    if(r.getString("password").equals(contra)){
                        return 1;
                    }
                    else {
                        return 3;
                    }
                }
            }

            c.close();
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
        return 2;
    }

    public int addUser(User u) {
        int answer = checkExisteix(u);
        try {
            if (answer == 1) {
                // create a mysql database connection
                Class.forName("com.mysql.jdbc.Driver");
                Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/MakiTetris", "root", "root");

                String query = " insert into Login (user, mail, password)" + " values (?, ?, ?)";

                PreparedStatement preparedStmt = c.prepareStatement(query);
                preparedStmt.setString(1, u.getUserName());
                preparedStmt.setString(2, u.getEmail());
                preparedStmt.setString(3, u.getPassword());
                preparedStmt.execute();

                c.close();
                return answer;
            }else{
                return answer;
            }
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
       return answer;
    }

    public int gestionaResposta() {
        int error = 0;

        //retornar el integer dels errors de login/registre
        //nota @miquel: he canviat els numeros a retornar. no esta be si retorne el mateix numero per a dos errors diferents

        return error;
    }
}
