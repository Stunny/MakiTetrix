package Model;

import Model.exceptions.BadAccessToDatabaseException;

import java.security.NoSuchAlgorithmException;
import java.sql.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;
import java.security.MessageDigest;

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
        System.out.println("User: " + serverConfig.getDb_user());

        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://" + serverConfig.getDb_ip() + ":" + serverConfig.getDb_port() + "/" + serverConfig.getDb_name() + "?autoReconnect=true&useSSL=false",
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
            c = DriverManager.getConnection("jdbc:mysql://" + serverConfig.getDb_ip() + ":" + serverConfig.getDb_port() + "/" + serverConfig.getDb_name() + "?autoReconnect=true&useSSL=false",
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
            c = DriverManager.getConnection("jdbc:mysql://" + serverConfig.getDb_ip() + ":" + serverConfig.getDb_port() + "/" + serverConfig.getDb_name() + "?autoReconnect=true&useSSL=false",
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
            c = DriverManager.getConnection("jdbc:mysql://" + serverConfig.getDb_ip() + ":" + serverConfig.getDb_port() + "/" + serverConfig.getDb_name() + "?autoReconnect=true&useSSL=false",
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
            c = DriverManager.getConnection("jdbc:mysql://" + serverConfig.getDb_ip() + ":" + serverConfig.getDb_port() + "/" + serverConfig.getDb_name() + "?autoReconnect=true&useSSL=false",
                    serverConfig.getDb_user(), serverConfig.getDb_pass());

            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("select user, mail from Login");
            while (r.next()) {
                if(r.getString("user").equals(u.getUserName())){
                    nameExists = true;
                }
                if(r.getString("mail").equals(u.getEmail())){
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

        return (nameExists || mailExists);
    }

    /**
     * Comprueba si un usuario esta conectado
     *
     * @param username Nombre de usuario a comprobar
     * @return true si el usuario esta conectado en el instante
     * @throws BadAccessToDatabaseException Se lanza si algo impide el acceso a la base de datos o si no se ha podido realizar la query
     */
    public boolean userIsOnline(String username) throws BadAccessToDatabaseException{
        if(!userExists(new User(username, "", "")))
            return false;

        boolean isOnline = false;
        String user = username.replace("(Online)", "");

        try {
            // create a mysql database connection
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://"+serverConfig.getDb_ip()+":"+serverConfig.getDb_port()+"/"+serverConfig.getDb_name()+"?autoReconnect=true&useSSL=false",
                    serverConfig.getDb_user(), serverConfig.getDb_pass());

            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("select connected from Login WHERE user = \"" + username + "\"");
            while (r.next()) {
                if(r.getInt("connected") == 1)
                    isOnline = true;
            }

            c.close();
        }catch (ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new BadAccessToDatabaseException(serverConfig.getDb_user(), serverConfig.getDb_pass());
        }

        return isOnline;
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

        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        messageDigest.update(contra.getBytes());
        contra = new String(messageDigest.digest());
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

                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                messageDigest.update(u.getPassword().getBytes());
                String encryptedString = new String(messageDigest.digest());

                preparedStmt.setString(3, encryptedString);
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
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return userExists? 1 : 0;
    }

    /**
     * Actualiza el estado de conexion de un usuario a desconectad
     * @param username Nombre de usuario a desconectar
     *
     * @throws BadAccessToDatabaseException Se lanza si algo impide el acceso a la base de datos o si no se ha podido realizar la query
     */
    public void disconnectUser(String username) throws BadAccessToDatabaseException {
        if(!userExists(new User(username, "", ""))){
            return;
        }

        try {
            // create a mysql database connection
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://"+serverConfig.getDb_ip()+":"+serverConfig.getDb_port()+"/"+serverConfig.getDb_name()+"?autoReconnect=true&useSSL=false",
                    serverConfig.getDb_user(), serverConfig.getDb_pass());

            String query = "UPDATE Login SET CONNECTED = 0 WHERE user = ?;";

            PreparedStatement stmt = c.prepareStatement(query);
            stmt.setString(1, username);

            stmt.execute();
            c.close();
        }catch (ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new BadAccessToDatabaseException(serverConfig.getDb_user(), serverConfig.getDb_pass());
        }
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
            c = DriverManager.getConnection("jdbc:mysql://" + serverConfig.getDb_ip() + ":" + serverConfig.getDb_port() + "/" + serverConfig.getDb_name() + "?autoReconnect=true&useSSL=false",
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

    /**
     * Devuelve toda la informacion de todas las partidas de un usuario
     * @param user Usuario de qual queremos saber la informacion de sus partidas
     * @return Devuelve un ArrayList<String[]> donde cada casilla de array del string contiene la informacion de una partida
     */
    public ArrayList<String[]> getGameData(String user){
        ArrayList<String[]> totalData = new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://" + serverConfig.getDb_ip() + ":" + serverConfig.getDb_port() + "/" + serverConfig.getDb_name() + "?autoReconnect=true&useSSL=false",
                    serverConfig.getDb_user(), serverConfig.getDb_pass());

            Statement s = c.createStatement ();
            s.executeQuery ("SELECT score, time, game_date, max_espectators FROM Partida, Login WHERE Login.user = '" + user + "';");
            ResultSet r = s.getResultSet ();
            while (r.next()){
                String[] aux = new String[]{String.valueOf(r.getInt("score")), String.valueOf(r.getInt("time")),
                        r.getString("game_date"), String.valueOf(r.getInt("max_espectators"))};
                totalData.add(aux);
            }

            c.close();
            return totalData;

        }catch (ClassNotFoundException | SQLException cnfe){
            cnfe.printStackTrace();
        }
        return totalData;
    }

    @Deprecated
    /**
     * Devuelve el numero de replays de un usuario
     * @param user Usuario del qual queremos saber su numero de replays
     * @return Devuelve un entero indicando el numero de replays
     */
    public int getNumberReplays(String user) {
        int i = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://" + serverConfig.getDb_ip() + ":" + serverConfig.getDb_port() + "/" + serverConfig.getDb_name() + "?autoReconnect=true&useSSL=false",
                    serverConfig.getDb_user(), serverConfig.getDb_pass());

            Statement s = c.createStatement ();
            s.executeQuery ("SELECT score, time, game_date, max_espectators FROM Partida, Login WHERE Login.user = '" + user + "';");
            ResultSet r = s.getResultSet ();
            while (r.next()){
                i++;
            }
            c.close();

            return i;
        }catch (ClassNotFoundException | SQLException cnfe){
            cnfe.printStackTrace();
        }
        return i;
    }
}