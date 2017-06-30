package Model;
import java.sql.*;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;


public class GestioDades {
    private Connection c;
    private String pass;
    //tractament de dades --> info del server a la BBDD (Miquel)
    public GestioDades(){
        plenaUsuaris();
    }

    /**
     *  LLena un lista con todos los usuarios existentes en la BBDD
     *
     * @return Devuelve un ArrayList con todos los usuarios existentes en la BBDD
     */
    public ArrayList<String> plenaUsuaris(){
        ArrayList<String> usuaris = new ArrayList<>();
        try {
            System.out.println("Introdueix la contraseña de la teva base de dades local:");
            Scanner keyboard = new Scanner(System.in);
            pass = keyboard.next();

            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/MakiTetris?autoReconnect=true&useSSL=false", "root", pass);
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

    /**
     * Devuelve una instancia del modelo User, con toda la informacion del usuario que hayamos seleccionado
     *
     * @param nom Nombre del usuario del cual queremos obtener toda la información
     * @return Dvuelve una instancia de User
     */
    public User mostraDades (String nom){
        User u = new User(null, null, null);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/MakiTetris?autoReconnect=true&useSSL=false", "root", pass);
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

    /**
     * Busca usaurios en la BBDD segun el nombre introducido
     *
     * @param b Nombre del usuario/s que queremos buscar
     * @return Devuelve un ArrayList con todos aquellos usuarios de la BBDD que coincidan con el nombre de busqueda
     */
    public ArrayList<String> busca(String b){
        ArrayList<String>trobats = new ArrayList<>();
        try {
            // create a mysql database connection
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/MakiTetris?autoReconnect=true&useSSL=false", "root", pass);
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

    private int checkExisteix(User u){
        Boolean nomrepe = false;
        Boolean mailrepe = false;
        try {
            // create a mysql database connection
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/MakiTetris?autoReconnect=true&useSSL=false", "root", pass);
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("select user, mail from Login");
            while (r.next()) {
                if(r.getString("user").equals(u.getUserName())){
                    nomrepe = true;
                    System.out.println("user repe");
                }
                if(r.getString("mail").equals(u.getEmail())){
                    System.out.println("email repe");
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
            if (nomrepe && mailrepe){
                return 5;
            }else{
                if (nomrepe){
                    return 3;
                }else{
                    return 4;
                }
            }
        }else{
            return 1;
        }
    }

    public int loginUser(String nom, String contra){
        boolean ok = false;
        boolean passko = false;
        boolean userko = false;
        try {
            // create a mysql database connection
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/MakiTetris?autoReconnect=true&useSSL=false", "root", pass);
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("select user, mail, password from Login");
            while (r.next()) {
                if(r.getString("user").equals(nom)||r.getString("mail").equals(nom)){
                    if(r.getString("password").equals(contra)){
                        ok = true;
                    }
                    else {
                        passko = true;
                    }
                }else {
                    userko = true;
                }
            }

            c.close();
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }

        if(ok){
            return 0;
        }else if(passko) {
            return 2;
        }else{
            return 1;
        }

    }


    private int addUser(User u) {
        int answer = checkExisteix(u);
        try {
            if (answer == 1) {
                // create a mysql database connection
                Class.forName("com.mysql.jdbc.Driver");
                c = DriverManager.getConnection("jdbc:mysql://localhost:3306/MakiTetris?autoReconnect=true&useSSL=false", "root", pass);
                String query = " insert into Login (user, mail, password)" + " values (?, ?, ?)";

                PreparedStatement preparedStmt = c.prepareStatement(query);
                preparedStmt.setString(1, u.getUserName());
                preparedStmt.setString(2, u.getEmail());
                preparedStmt.setString(3, u.getPassword());
                preparedStmt.execute();

                c.close();

                return 0;
            }else{
                return answer;
            }
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
       return answer;
    }

    public int gestionaLogin(String missatge) {
        //0:ok, 1:usuari/mail no existeix 2:contra no
        String [] dades = missatge.split("#");
        String usuari_password = dades[0];
        String email = dades[1];
        int error = loginUser(usuari_password, email);
        return error;
    }

    public int gestionaRegistre(String missatge) {
        //0:ok, 3:usuari existeix 4:mail existeix 5:both
        String [] dades = missatge.split("#");
        String usuari = dades[0];
        String password = dades[1];
        String email = dades[2];
        //extreure el usuari, password i email i crear instancia de usuari i afegirlo
        User u = new User(usuari, password, email);
        int error = addUser(u);
        System.out.println("al gestio dades, addUser em retorna un " + error);
        return error;
    }
}