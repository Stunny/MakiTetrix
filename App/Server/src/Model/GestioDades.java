package Model;

import Model.exceptions.BadAccessToDatabaseException;

import java.sql.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

/**
 * Clase encargada de manejar los datos del servidor contra la base de datos local
 */
public class GestioDades {

    /**
     * Objeto de configuraciones del servidor
     */
    private Configuration serverConfig;

    /**
     * Objeto de conexion con la base de datos
     */
    private Connection c;


    /**
     * Construye un nuevo objeto gestor de base de datos preguntandole la contraseña al usuario
     *
     * @deprecated
     */
    public GestioDades(){
        System.out.println("Introdueix la contraseña de la teva base de dades local:");
        Scanner keyboard = new Scanner(System.in);
        //db_pass = keyboard.nextLine();
    }

    /**
     * Constructor que utiliza la configuracion externalizada del servidor
     *
     * @param serverConfig Objeto de configuracion del servidor
     * @throws BadAccessToDatabaseException Se lanza si algo impide el acceso a la base de datos o si no se ha podido realizar la query
     */
    public GestioDades(Configuration serverConfig) throws BadAccessToDatabaseException {
        this.serverConfig = serverConfig;

        System.out.println("Accessing database...");
        System.out.println("User: "+serverConfig.getDb_user());

        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://"+serverConfig.getDb_ip()+":"+serverConfig.getDb_port()+"/"+serverConfig.getDb_name()+"?autoReconnect=true&useSSL=false",
                    serverConfig.getDb_user(), serverConfig.getDb_pass());
        }catch (ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }
        catch (SQLException e) {
            throw new BadAccessToDatabaseException(serverConfig.getDb_user(), serverConfig.getDb_pass());
        }
    }

    /**
     *  LLena un lista con todos los usuarios existentes en la BBDD
     *
     * @return Devuelve un ArrayList con todos los usuarios existentes en la BBDD
     * @unused
     */
    public ArrayList<String> plenaUsuaris() throws BadAccessToDatabaseException {
        ArrayList<String> usuaris = new ArrayList<>();
        Encrypter encrypter = new Encrypter();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/MakiTetris?autoReconnect=true&useSSL=false",
                    serverConfig.getDb_user(), serverConfig.getDb_pass());
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT user FROM Login");
            while (r.next()) {
                String userName = encrypter.decrypt(r.getString("user"));
                usuaris.add(userName);
                //usuaris.add(r.getString("user"));
            }
            c.close();

        }catch (ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }
        catch (SQLException e) {
            throw new BadAccessToDatabaseException(serverConfig.getDb_user(), serverConfig.getDb_pass());
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return usuaris;
    }

    /**
     * Devuelve la informacion del usuario que hayamos seleccionado
     *
     * @param nom Nombre del usuario del cual queremos obtener toda la información
     * @return Devuelve un ArrayList<String>
     */
    public ArrayList<String> mostraDades (String nom) throws BadAccessToDatabaseException {
        ArrayList<String> info = new ArrayList<>();
        try {

            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://"+serverConfig.getDb_ip()+":"+serverConfig.getDb_port()+"/"+serverConfig.getDb_name()+"?autoReconnect=true&useSSL=false",
                    serverConfig.getDb_user(), serverConfig.getDb_pass());

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

        }catch (ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }
        catch (SQLException e) {
            throw new BadAccessToDatabaseException(serverConfig.getDb_user(), serverConfig.getDb_pass());
        }
        return info;
    }

    /**
     * Devuelve una instancia del modelo User correspondiente al nombre recibido
     *
     * @param nom Nombre del usuario del cual queremos obtener toda la información
     * @return Dvuelve una instancia de User
     */
    public User getUser(String nom) throws BadAccessToDatabaseException {
        User u = new User(null, null, null);
        try {

            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://"+serverConfig.getDb_ip()+":"+serverConfig.getDb_port()+"/"+serverConfig.getDb_name()+"?autoReconnect=true&useSSL=false",
                    serverConfig.getDb_user(), serverConfig.getDb_pass());

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

        }catch (ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }
        catch (SQLException e) {
            throw new BadAccessToDatabaseException(serverConfig.getDb_user(), serverConfig.getDb_pass());
        }
        return u;
    }

    /**
     * Busca usaurios en la BBDD segun el nombre introducido
     *
     * @param userName Nombre del usuario/s que queremos buscar
     * @return Devuelve un ArrayList con todos aquellos usuarios de la BBDD que coincidan con el nombre de busqueda
     */
    public ArrayList<String> fetch(String userName) throws BadAccessToDatabaseException {
        ArrayList<String> trobats = new ArrayList<>();
        try {
            // create a mysql database connection
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://"+serverConfig.getDb_ip()+":"+serverConfig.getDb_port()+"/"+serverConfig.getDb_name()+"?autoReconnect=true&useSSL=false",
                    serverConfig.getDb_user(), serverConfig.getDb_pass());

            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT user FROM Login WHERE user REGEXP '" + userName + "';");
            while (r.next()) {
                trobats.add(r.getString("user"));
            }

            c.close();

        }catch (ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }
        catch (SQLException e) {
            throw new BadAccessToDatabaseException(serverConfig.getDb_user(), serverConfig.getDb_pass());
        }

        return trobats;
    }

    /**
     * Comprueba la existencia de un usuario en la abse de datos
     *
     * @param u Usuario
     * @return true if user exists in database
     * @throws BadAccessToDatabaseException Se lanza si algo impide el acceso a la base de datos o si no se ha podido realizar la query
     */
    private boolean userExists(User u) throws BadAccessToDatabaseException {
        Boolean nameExists = false;
        Boolean mailExists = false;
        try {
            // create a mysql database connection
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://"+serverConfig.getDb_ip()+":"+serverConfig.getDb_port()+"/"+serverConfig.getDb_name()+"?autoReconnect=true&useSSL=false",
                    serverConfig.getDb_user(), serverConfig.getDb_pass());

            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("select user, mail from Login");
            while (r.next()) {
                if(r.getString("user").equals(u.getUserName())){
                    nameExists = true;
                    System.out.println("user repe");
                }
                if(r.getString("mail").equals(u.getEmail())){
                    System.out.println("email de la base de dades: " + r.getString("mail"));
                    System.out.println("email amb el que ens volem registrar: " + u.getEmail());
                    System.out.println("email repe");
                    mailExists = true;
                }
            }

            c.close();
        }catch (ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }
        catch (SQLException e) {
            throw new BadAccessToDatabaseException(serverConfig.getDb_user(), serverConfig.getDb_pass());
        }

        return (!nameExists && !mailExists);
    }

    /**
     * Realiza el inicio de sesion de un usuario en la base de datos
     *
     * @param nom Nombre de usuario
     * @param contra Contraseña de usuario
     * @return  0 si OK. 2 si contraseña incorrecta. 1 otro error.
     * @throws BadAccessToDatabaseException Se lanza si algo impide el acceso a la base de datos o si no se ha podido realizar la query
     */
    public int loginUser(String nom, String contra) throws BadAccessToDatabaseException {
        boolean ok = false;
        boolean passko = false;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://"+serverConfig.getDb_ip()+":"+serverConfig.getDb_port()+"/"+serverConfig.getDb_name()+"?autoReconnect=true&useSSL=false",
                    serverConfig.getDb_user(), serverConfig.getDb_pass());

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
                }
            }

            c.close();
        }catch (ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }
        catch (SQLException e) {
            throw new BadAccessToDatabaseException(serverConfig.getDb_user(), serverConfig.getDb_pass());
        }

        if(ok){
            return 0;
        }else if(passko) {
            return 2;
        }else{
            return 1;
        }
    }

    /**
     * Añade un usuario a la base de datos si no existe ya en ella.
     *
     * @param u Usuario
     * @return 0 si se registra con éxito. 1 si algun dato ya estaba en la base de datos
     * @throws BadAccessToDatabaseException Se lanza si algo impide el acceso a la base de datos o si no se ha podido realizar la query
     */
    private int addUser(User u) throws BadAccessToDatabaseException {

        boolean userExists = userExists(u);

        try {

            if (!userExists) {

                Class.forName("com.mysql.jdbc.Driver");
                c = DriverManager.getConnection("jdbc:mysql://"+serverConfig.getDb_ip()+":"+serverConfig.getDb_port()+"/"+serverConfig.getDb_name()+"?autoReconnect=true&useSSL=false",
                        serverConfig.getDb_user(), serverConfig.getDb_pass());

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
            }

        }catch (ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }
        catch (SQLException e) {
            throw new BadAccessToDatabaseException(serverConfig.getDb_user(), serverConfig.getDb_pass());
        }

        return userExists? 1 : 0;
    }

    /**
     * Borra un usuario de la base de datos
     *
     * @param userName Nombre de usuario
     * @throws BadAccessToDatabaseException Se lanza si algo impide el acceso a la base de datos o si no se ha podido realizar la query
     */
    public void deleteUser(String userName) throws BadAccessToDatabaseException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://"+serverConfig.getDb_ip()+":"+serverConfig.getDb_port()+"/"+serverConfig.getDb_name()+"?autoReconnect=true&useSSL=false",
                    serverConfig.getDb_user(), serverConfig.getDb_pass());
        }catch (ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }
        catch (SQLException e) {
            throw new BadAccessToDatabaseException(serverConfig.getDb_user(), serverConfig.getDb_pass());
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

    /**
     * Deja en constancia el cambio de estado de conexion de un usuario (online/offline)
     *
     * @param user Usuario
     * @param status Estado de conexion (true: online, false: offline)
     * @throws BadAccessToDatabaseException Se lanza si algo impide el acceso a la base de datos o si no se ha podido realizar la query
     */
    public void setConnectionStatus(String user, boolean status) throws BadAccessToDatabaseException {
        try {
            // create a mysql database connection
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://"+serverConfig.getDb_ip()+":"+serverConfig.getDb_port()+"/"+serverConfig.getDb_name()+"?autoReconnect=true&useSSL=false",
                    serverConfig.getDb_user(), serverConfig.getDb_pass());

            String query = "UPDATE Login SET connected = "+ String.valueOf(status)+ " WHERE Login.user = '" + user + "';";
            PreparedStatement preparedStmt = c.prepareStatement(query);
            preparedStmt.execute();

            c.close();
        }catch (ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }
        catch (SQLException e) {
            throw new BadAccessToDatabaseException(serverConfig.getDb_user(), serverConfig.getDb_pass());
        }
    }

    /**
     * Gestiona el acceso de usuario a la aplicación
     *
     * @param userNameOREmail Nombre o email del usuario ??
     * @param password Contraseña del usuario
     * @return 0 si login OK. 1 si el usuario o email no existe. 2 si contraseña incorrecta
     */
    public int handleLogin(String userNameOREmail, String password) {

        int error = 0;
        try {
            error = loginUser(userNameOREmail, password);
        } catch (BadAccessToDatabaseException e) {
            e.printMessage();
        }
        return error;
    }

    /**
     * Gestiona el registro de un usuario en la base de datos
     *
     * @param usuari Nombre de usuario
     * @param password Contraseña de usuario
     * @param email Email de usuario
     * @return 0 si OK. 3 si el nombre de usuario ya existe. 4 si el mail ya existe. 5 Si ambos existen.
     */
    public int handleRegister(String usuari, String password, String email) {
        User u = new User(usuari, password, email);

        int error = 0;
        try {
            error = addUser(u);
        } catch (BadAccessToDatabaseException e) {
            e.printMessage();
        }

        return error;
    }
}