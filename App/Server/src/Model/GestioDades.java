package Model;

import java.sql.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;


public class GestioDades {
    private Connection c;
    private String pass;
    //tractament de dades --> info del server a la BBDD (Miquel)

    public GestioDades(){
        System.out.println("Introdueix la contraseña de la teva base de dades local:");
        Scanner keyboard = new Scanner(System.in);
        pass = keyboard.next();
    }

    /**
     *  LLena un lista con todos los usuarios existentes en la BBDD
     *
     * @return Devuelve un ArrayList con todos los usuarios existentes en la BBDD
     */
    public ArrayList<String> plenaUsuaris(){
        ArrayList<String> usuaris = new ArrayList<>();
        Encrypter encrypter = new Encrypter();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/MakiTetris?autoReconnect=true&useSSL=false", "root", pass);
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT user FROM Login");
            while (r.next()) {
                String userName = encrypter.decrypt(r.getString("user"));
                usuaris.add(userName);
                //usuaris.add(r.getString("user"));
            }
            c.close();

        } catch (Exception var2) {
            var2.printStackTrace();
        }

        return usuaris;
    }

    /**
     * Devuelve la informacion del usuario que hayamos seleccionado
     *
     * @param nom Nombre del usuario del cual queremos obtener toda la información
     * @return Devuelve un ArrayList<String>
     */
    public ArrayList<String> mostraDades (String nom){
        ArrayList<String> info = new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/MakiTetris?autoReconnect=true&useSSL=false", "root", pass);
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM Login");
            while (r.next()) {
                if(r.getString("user").equals(nom)){
                    info.add(r.getString("connected"));
                    info.add(r.getString("register_date"));
                    info.add(r.getString("last_login"));
                    info.add(r.getString("number_games"));
                    info.add(r.getString("total_points"));
                    return info;
                }
            }
            c.close();

        } catch (Exception var2) {
            var2.printStackTrace();
        }
        return info;
    }

    /**
     * Devuelve una instancia del modelo User correspondiente al nombre recibido
     *
     * @param nom Nombre del usuario del cual queremos obtener toda la información
     * @return Dvuelve una instancia de User
     */
    public User retornaUser (String nom){
        User u = new User(null, null, null);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/MakiTetris?autoReconnect=true&useSSL=false", "root", pass);
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM Login");
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
     * @param userName Nombre del usuario/s que queremos buscar
     * @return Devuelve un ArrayList con todos aquellos usuarios de la BBDD que coincidan con el nombre de busqueda
     */
    public ArrayList<String> busca(String userName){
        ArrayList<String> trobats = new ArrayList<>();
        try {
            // create a mysql database connection
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/MakiTetris?autoReconnect=true&useSSL=false", "root", pass);
            Statement s = c.createStatement();

            ResultSet r = s.executeQuery("SELECT user FROM Login WHERE user REGEXP '" + userName + "';");
            while (r.next()) {
                trobats.add(r.getString("user"));
            }

            c.close();

        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
            e.printStackTrace();
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
                    System.out.println("email de la base de dades: " + r.getString("mail"));
                    System.out.println("email amb el que ens volem registrar: " + u.getEmail());
                    System.out.println("email repe");
                    mailrepe = true;
                }
            }

            c.close();
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
            e.printStackTrace();
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
            Statement s = c.createStatement ();
            s.executeQuery ("SELECT user, mail, password FROM Login");
            ResultSet r = s.getResultSet ();
            while (r.next ()) {
                if(r.getString("user").equals(nom)||r.getString("mail").equals(nom)){
                    if(r.getString("password").equals(contra)){
                        String query = "UPDATE Login SET connected = true WHERE Login.user = '" + nom + "';";
                        PreparedStatement preparedStmt = c.prepareStatement(query);
                        preparedStmt.execute();
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
            e.printStackTrace();
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
                String query = "INSERT INTO Login (user, mail, password, connected, register_date, last_login, number_games, total_points)"
                        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

                PreparedStatement preparedStmt = c.prepareStatement(query);
                preparedStmt.setString(1, u.getUserName());
                preparedStmt.setString(2, u.getEmail());
                preparedStmt.setString(3, u.getPassword());
                preparedStmt.setBoolean(4, true);
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                Date date = new Date(System.currentTimeMillis());
                preparedStmt.setString(5, dateFormat.format(date));
                preparedStmt.setString(6, dateFormat.format(date));
                preparedStmt.setInt(7, 0);
                preparedStmt.setInt(8, 0);
                preparedStmt.execute();

                c.close();

                return 0;
            }else{
                return answer;
            }
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return answer;
    }


    public int gestionaLogin(String userNameOREmail, String password) {
        //0:ok, 1:usuari/mail no existeix 2:contra no
        int error = loginUser(userNameOREmail, password);
        return error;
    }

    public int gestionaRegistre(String usuari, String password, String email) {
        //0:ok, 3:usuari existeix 4:mail existeix 5:both
        User u = new User(usuari, password, email);
        int error = addUser(u);
        return error;
    }

    public void borraUsuari(String userName){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/MakiTetris?autoReconnect=true&useSSL=false", "root", pass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String query = "DELETE FROM Login WHERE user = '" + userName + "';";
        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = c.prepareStatement(query);
            preparedStmt.execute();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setDisconnected(String disconnectingUser){
        try {
            // create a mysql database connection
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/MakiTetris?autoReconnect=true&useSSL=false", "root", pass);
            String query = "UPDATE Login SET connected = false WHERE Login.user = '" + disconnectingUser + "';";
            PreparedStatement preparedStmt = c.prepareStatement(query);
            preparedStmt.execute();

            c.close();
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void connect(){

    }
}