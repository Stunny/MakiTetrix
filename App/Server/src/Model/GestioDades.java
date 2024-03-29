package Model;

import Model.exceptions.BadAccessToDatabaseException;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

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
    private String startingGameTime;


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
    public ArrayList <Integer> getTecles(String u)throws BadAccessToDatabaseException{
        ArrayList<Integer>result = new ArrayList<Integer>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://" + serverConfig.getDb_ip() + ":" + serverConfig.getDb_port() + "/" + serverConfig.getDb_name() + "?autoReconnect=true&useSSL=false",
                    serverConfig.getDb_user(), serverConfig.getDb_pass());

            Statement s = c.createStatement();

            s.executeQuery("SELECT user FROM DefaultKeys");
            ResultSet rs = s.getResultSet();

            //Comprobamos si el usuario ya tiene teclas guardadas
            boolean exists = false;
            while (rs.next()) {
                String name = rs.getString("user");
                if (name.equals(u)) {
                    exists = true;
                }
            }
            rs.close();
            s.close();

            if (exists){
                String query = "SELECT * FROM DefaultKeys WHERE user = ?";
                PreparedStatement preparedStmt = c.prepareStatement(query);
                preparedStmt.setString(1, u);
                preparedStmt.execute();
                ResultSet rs2 = preparedStmt.getResultSet();
                while (rs2.next()) {
                    result.add(rs2.getInt("derecha"));
                    result.add(rs2.getInt("izquierda"));
                    result.add(rs2.getInt("abajo"));
                    result.add(rs2.getInt("rderecha"));
                    result.add(rs2.getInt("rizquierda"));
                    result.add(rs2.getInt("pause"));
                }

                preparedStmt.close();
                c.close();
                return result;

            }else{
                c.close();
                return result;
            }

        }catch (ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
            throw new BadAccessToDatabaseException(serverConfig.getDb_user(), serverConfig.getDb_pass());
        }
        return result;
    }

    public void setTecles (String u, int d, int i, int a, int rd, int ri, int p)throws BadAccessToDatabaseException {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://" + serverConfig.getDb_ip() + ":" + serverConfig.getDb_port() + "/" + serverConfig.getDb_name() + "?autoReconnect=true&useSSL=false",
                    serverConfig.getDb_user(), serverConfig.getDb_pass());


            Statement s = c.createStatement ();
            s.executeQuery ("SELECT user FROM DefaultKeys");
            ResultSet rs = s.getResultSet ();

            //Comprobamos si el usuario ya tiene teclas guardadas
            boolean exists = false;
            while (rs.next ())
            {
                String name = rs.getString ("user");
                if(name.equals(u)){
                    exists = true;
                }
            }
            rs.close ();
            s.close ();

            //Insertamos fila con teclas nueva o actualizamos la vieja dependiendo de exists
            if (exists){
                s = c.createStatement();

                String query = "update DefaultKeys set derecha = ?, izquierda = ?, abajo = ?, rderecha = ?, rizquierda = ?, pause = ? where user = ?";
                PreparedStatement preparedStmt = c.prepareStatement(query);
                preparedStmt.setInt(1, d);
                preparedStmt.setInt(2, i);
                preparedStmt.setInt(3, a);
                preparedStmt.setInt(4, rd);
                preparedStmt.setInt(5, ri);
                preparedStmt.setInt(6, p);
                preparedStmt.setString(7, u);

                preparedStmt.execute();
                c.close();

            }else {
                s = c.createStatement();

                String query = " insert into DefaultKeys (user, derecha, izquierda, abajo, rderecha, rizquierda, pause)"
                        + " values (?, ?, ?, ?, ?, ?, ?)";

                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = c.prepareStatement(query);
                preparedStmt.setString(1, u);
                preparedStmt.setInt(2, d);
                preparedStmt.setInt(3, i);
                preparedStmt.setInt(4, a);
                preparedStmt.setInt(5, rd);
                preparedStmt.setInt(6, ri);
                preparedStmt.setInt(7, p);


                // execute the preparedstatement
                preparedStmt.execute();
                c.close();

            }

        }catch (ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }catch (SQLException e){

            throw new BadAccessToDatabaseException(serverConfig.getDb_user(), serverConfig.getDb_pass());

        }
    }

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
            ResultSet r = s.executeQuery("SELECT connected FROM Login WHERE user = \"" + username + "\"");
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
        boolean connected = false;

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
            s.executeQuery ("SELECT user, mail, password, connected FROM Login");
            ResultSet r = s.getResultSet ();
            while (r.next ()) {
                if(r.getString("user").equals(nom)||r.getString("mail").equals(nom)){
                    if(r.getString("password").equals(contra)){
                        if(r.getBoolean("connected")){
                            connected = true;
                        }else{



                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        Date date = new Date(System.currentTimeMillis());

                        String query = "UPDATE Login SET connected = true, last_login = '" + dateFormat.format(date) + "' WHERE Login.user = '" + nom + "';";
                        PreparedStatement preparedStmt = c.prepareStatement(query);
                        preparedStmt.execute();
                        ok = true;
                        }
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
        }if(connected) {
            return 6;
        } else {
            if (passko) {
                return 2;
            } else {
                return 1;
            }
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
                c = DriverManager.getConnection("jdbc:mysql://" + serverConfig.getDb_ip() + ":" + serverConfig.getDb_port() + "/"
                                + serverConfig.getDb_name() + "?autoReconnect=true&useSSL=false", serverConfig.getDb_user(), serverConfig.getDb_pass());

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

            String query = "UPDATE Login SET connected = 0 WHERE user = ?;";

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

        String query = "DELETE FROM Partida WHERE user = '" + userName + "';";
        String query2 = "DELETE FROM Replay WHERE user = '" + userName + "';";
        String query3 = "DELETE FROM Login WHERE user = '" + userName + "';";
        String query4 = "DELETE FROM Login WHERE user = '" + userName + "';";

        PreparedStatement preparedStmt = null;
        PreparedStatement preparedStmt2 = null;
        PreparedStatement preparedStmt3 = null;
        PreparedStatement preparedStmt4 = null;

        try {
            preparedStmt = c.prepareStatement(query);
            preparedStmt.execute();
            preparedStmt2 = c.prepareStatement(query2);
            preparedStmt2.execute();
            preparedStmt3 = c.prepareStatement(query3);
            preparedStmt3.execute();
            preparedStmt4 = c.prepareStatement(query4);
            preparedStmt4.execute();
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
     * Devuelve la informacion de todas las partidas de un usuario
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
            s.executeQuery ("SELECT score, time, game_date, max_espectators FROM Partida WHERE Partida.user = '" + user + "';");
            ResultSet r = s.getResultSet ();
            while (r.next()){
                String[] aux = new String[]{String.valueOf(r.getInt("score")), r.getString("time"),
                        r.getString("game_date"), String.valueOf(r.getInt("max_espectators"))};
                totalData.add(aux);
            }

            c.close();
/*            for (int i = 0; i < totalData.size(); i++){
                System.out.println("llegeixo aquestes replays: " + totalData.get(i));
            }
  */          return totalData;

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

    /**
     * Recupera de la BBDD la puntuacion maxima de las 10 partidas con mas puntos
     * @return La puntuacion maxima
     */
    public int[] getTopTenScores() {
        int[] score = new int[10];
        int i = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://" + serverConfig.getDb_ip() + ":" + serverConfig.getDb_port() + "/" + serverConfig.getDb_name() + "?autoReconnect=true&useSSL=false",
                    serverConfig.getDb_user(), serverConfig.getDb_pass());

            Statement s = c.createStatement ();
            s.executeQuery ("SELECT user, score FROM Partida ORDER BY score DESC;");
            ResultSet r = s.getResultSet ();
            while (r.next() && i < 10){
                score[i] = r.getInt("score");
                i++;
            }
            c.close();

            return score;
        }catch (ClassNotFoundException | SQLException cnfe){
            cnfe.printStackTrace();
        }
        return score;
    }

    /**
     * Recupera de la BBDD el nombre de los 10 usuarios con la puntuacion mas alta
     * @return El nombre de los 10 usuarios con la puntuacion mas alta
     */
    public String[] getTopTenScoreUsers() {
        String[] userNames = new String [10];
        int i = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://" + serverConfig.getDb_ip() + ":" + serverConfig.getDb_port() + "/" + serverConfig.getDb_name() + "?autoReconnect=true&useSSL=false",
                    serverConfig.getDb_user(), serverConfig.getDb_pass());

            Statement s = c.createStatement ();
            s.executeQuery ("SELECT user, score FROM Partida ORDER BY score DESC;");
            ResultSet r = s.getResultSet ();
            while (r.next() && i < 10){
                userNames[i] = r.getString("user");
                i++;
            }
            c.close();

            return userNames;
        }catch (ClassNotFoundException | SQLException cnfe){
            cnfe.printStackTrace();
        }
        return userNames;
    }

    /**
     * Recupera de la BBDD el numero de espectadores maximos de las 5 partidas con mas espectadores
     * @return El numero de especatores maximos
     */
    public int[] getTopViewers() {
        int[] views = new int[5];
        int i = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://" + serverConfig.getDb_ip() + ":" + serverConfig.getDb_port() + "/" + serverConfig.getDb_name() + "?autoReconnect=true&useSSL=false",
                    serverConfig.getDb_user(), serverConfig.getDb_pass());

            Statement s = c.createStatement ();
            s.executeQuery ("SELECT user, max_espectators FROM Partida ORDER BY max_espectators DESC;");
            ResultSet r = s.getResultSet ();
            while (r.next() && i < 5){
                views[i] = r.getInt("max_espectators");
                i++;
            }
            c.close();

            return views;
        }catch (ClassNotFoundException | SQLException cnfe){
            cnfe.printStackTrace();
        }
        return views;
    }

    /**
     * Recupera de la BBDD el nombre de los 5 usuarios con la cantidad de viewers mas alta
     * @return El nombre de los 5 usuarios con la cantidad de viewers mas alta
     */
    public String[] getTopViewersUsers() {
        String[] userNames = new String [5];
        int i = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://" + serverConfig.getDb_ip() + ":" + serverConfig.getDb_port() + "/" + serverConfig.getDb_name() + "?autoReconnect=true&useSSL=false",
                    serverConfig.getDb_user(), serverConfig.getDb_pass());

            Statement s = c.createStatement ();
            s.executeQuery ("SELECT user, max_espectators FROM Partida ORDER BY max_espectators DESC;");
            ResultSet r = s.getResultSet ();
            while (r.next() && i < 5){
                userNames[i] = r.getString("user");
                i++;
            }
            c.close();

            return userNames;
        }catch (ClassNotFoundException | SQLException cnfe){
            cnfe.printStackTrace();
        }
        return userNames;
    }

    /**
     * Recupera de la BBDD todos aquellos jugadores que se hallen actualmente en partida
     * @return Devuelve un ArrayList<String> con los nombres de los jugadores que se hallen en partida
     * @param currentUser Jugador que hace la peticion y que no debe ser añadido a la lista
     */
    public ArrayList<String> gamingUsers(String currentUser) {
        ArrayList<String> userNames = new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://" + serverConfig.getDb_ip() + ":" + serverConfig.getDb_port() + "/" + serverConfig.getDb_name() + "?autoReconnect=true&useSSL=false",
                    serverConfig.getDb_user(), serverConfig.getDb_pass());

            Statement s = c.createStatement ();
            s.executeQuery ("SELECT user, gaming, startingGameTime FROM Login WHERE gaming = true AND user != '" + currentUser + "' ORDER BY user DESC;");
            ResultSet r = s.getResultSet ();
            while (r.next()){
                userNames.add(r.getString("user"));
                userNames.add(r.getString("startingGameTime"));
            }
            c.close();

            return userNames;
        }catch (ClassNotFoundException | SQLException cnfe){
            cnfe.printStackTrace();
        }
        return userNames;
    }

    /**
     * Changes the user's gaming status depending on the specified parameters
     * @param userName User name we want to modify the gaming status on
     * @param status The status we want to set to the specified user
     */
    public void setGamingStatus(String userName, boolean status, String startingGameTime) {
        this.startingGameTime = startingGameTime;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://" + serverConfig.getDb_ip() + ":" + serverConfig.getDb_port() + "/" + serverConfig.getDb_name() + "?autoReconnect=true&useSSL=false",
                    serverConfig.getDb_user(), serverConfig.getDb_pass());

            String query = "UPDATE Login SET gaming = ?, startingGameTime = ? WHERE user = ?;";
            PreparedStatement stmt = c.prepareStatement(query);
            stmt.setBoolean(1, status);
            stmt.setString(2, startingGameTime);
            stmt.setString(3, userName);

            stmt.execute();
            c.close();

        }catch (ClassNotFoundException | SQLException cnfe){
            cnfe.printStackTrace();
        }
    }

    /**
     * Saves gama data into database when game is finished
     * @param userName user who played the game
     * @param score final score of the game
     * @param max_espectators number of maximum spectators during the game
     * @param replayPath path of the replay
     */
    public void saveGameData(String userName, int score, int millis, int max_espectators, String replayPath){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://" + serverConfig.getDb_ip() + ":" + serverConfig.getDb_port() + "/" + serverConfig.getDb_name() + "?autoReconnect=true&useSSL=false",
                    serverConfig.getDb_user(), serverConfig.getDb_pass());
            String query = "INSERT INTO Partida(user, score, time, game_date, max_espectators, replay_path) VALUES (?, ?, ?, ?, ?, ?);";
            String tiempo = String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://" + serverConfig.getDb_ip() + ":" + serverConfig.getDb_port() + "/" + serverConfig.getDb_name() + "?autoReconnect=true&useSSL=false",
                    serverConfig.getDb_user(), serverConfig.getDb_pass());
            PreparedStatement stmt = c.prepareStatement(query);
            stmt.setString(1, userName);
            stmt.setInt(2, score);
            stmt.setString(3, tiempo);
            stmt.setString(4, this.startingGameTime);
            stmt.setInt(5, max_espectators);
            stmt.setString(6, replayPath);
            stmt.execute();
            //c.close();

        }catch (ClassNotFoundException | SQLException cnfe){
            cnfe.printStackTrace();
        }

        updateMainServerViewInfo(userName);
    }

    @Deprecated
    /**
     * Gets selected user's total score
     * @param userName User we want to know the total points of
     * @return Total points
     */
    private int getTotalScore(String userName) {
        int totalScore = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://" + serverConfig.getDb_ip() + ":" + serverConfig.getDb_port() + "/" + serverConfig.getDb_name() + "?autoReconnect=true&useSSL=false",
                    serverConfig.getDb_user(), serverConfig.getDb_pass());

            Statement s = c.createStatement ();
            s.executeQuery ("SELECT total_points FROM Login WHERE user = '" + userName + "';");
            ResultSet r = s.getResultSet ();
            if (r.next()){
                totalScore = r.findColumn("total_points");
            }
            c.close();

            return totalScore;
        }catch (ClassNotFoundException | SQLException cnfe){
            cnfe.printStackTrace();
        }
        return totalScore;
    }

    /**
     * Updates server main view with new saved game data
     * @param userName
     */
    private void updateMainServerViewInfo(String userName) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://" + serverConfig.getDb_ip() + ":" + serverConfig.getDb_port() + "/" + serverConfig.getDb_name() + "?autoReconnect=true&useSSL=false",
                    serverConfig.getDb_user(), serverConfig.getDb_pass());

            String query = "UPDATE Login SET total_points = (SELECT SUM(score) FROM Partida AS p WHERE p.user = ?), number_games = (SELECT COUNT(score) FROM Partida AS p WHERE p.user = ?) WHERE user = ?;";
            PreparedStatement stmt = c.prepareStatement(query);
            stmt.setString(1, userName);
            stmt.setString(2, userName);
            stmt.setString(3, userName);

            stmt.execute();
            c.close();

        }catch (ClassNotFoundException | SQLException cnfe){
            cnfe.printStackTrace();
        }
    }

    /**
     * Adds a move that will be part of the replay to the database
     * @param move Move we want to save into the database
     * @param order ID that specifies the order in which the moves must be read
     * @param numGame The replay to which the moves belong to
     * @param c
     * @param path
     */
    //todo: añadir path a la query
    public void addMove(String currentUser, String move, int order, int numGame, Connection c, String path) {
        try {
            String query = "INSERT INTO Replay(user, move, ID, Order_, path) VALUES (?, ?, ?, ?, ?);";

            PreparedStatement stmt = c.prepareStatement(query);
            stmt.setString(1, currentUser);
            stmt.setString(2, move);
            stmt.setInt(3, numGame + 1);
            stmt.setInt(4, order);
            stmt.setString(5, path);
            stmt.execute();

        }catch (SQLException cnfe){
            cnfe.printStackTrace();
        }
    }

    /**
     * Retrieves the number of replays a user has saved
     * @param currentUser User from which we want to read the number of replays from
     * @return The number of replays
     */
    public int gestNumGames(String currentUser) {
        int numReplays = 0;
        try{
            c = DriverManager.getConnection("jdbc:mysql://" + serverConfig.getDb_ip() + ":" + serverConfig.getDb_port() + "/" + serverConfig.getDb_name() + "?autoReconnect=true&useSSL=false",
                    serverConfig.getDb_user(), serverConfig.getDb_pass());
            Class.forName("com.mysql.jdbc.Driver");
            Statement s = c.createStatement ();
            s.executeQuery ("SELECT ID FROM Replay WHERE user = '" + currentUser + "' ORDER BY ID DESC LIMIT 1;");
            ResultSet r = s.getResultSet ();
            if (r.next()){
                numReplays = r.findColumn("ID");
            }
            c.close();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return numReplays;
    }

    /**
     * Enables connection to database
     * @return Connection to database
     */
    public Connection connect(){
        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:mysql://" + serverConfig.getDb_ip() + ":" + serverConfig.getDb_port() + "/" + serverConfig.getDb_name() + "?autoReconnect=true&useSSL=false",
                    serverConfig.getDb_user(), serverConfig.getDb_pass());
            Class.forName("com.mysql.jdbc.Driver");
            return c;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return c;
    }

    /**
     * Closes connection to database
     * @param c Connection to database
     */
    public void close(Connection c){
        try {
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves desired replay from database
     * @param replayID ID of the replay we want to retrieve
     * @param currentUser User to whom the retrieved replay belongs to
     * @return ArrayList<String> containing all movements of the replay
     */
    public ArrayList<String> getDesiredReplay(int replayID, String currentUser) {
        ArrayList<String> replay = new ArrayList<>();
        try{
            c = DriverManager.getConnection("jdbc:mysql://" + serverConfig.getDb_ip() + ":" + serverConfig.getDb_port() + "/" + serverConfig.getDb_name() + "?autoReconnect=true&useSSL=false",
                    serverConfig.getDb_user(), serverConfig.getDb_pass());
            Class.forName("com.mysql.jdbc.Driver");
            Statement s = c.createStatement ();
            s.executeQuery ("SELECT move FROM Replay WHERE user = '" + currentUser + "' AND ID = " + replayID + " ORDER BY Order_ ASC;");
            ResultSet r = s.getResultSet ();
            while (r.next()){
                replay.add(r.getString("move"));
            }
            c.close();
            return replay;
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return replay;
    }

    /**
     * @param selectedUser usuario del que se desean recuperar las repeticiones
     * @return lista de rutas de los archivos de repeticion del usuario
     */
    public ArrayList<String> getReplays(String selectedUser) {
        ArrayList<String> paths;
        try{
            c = DriverManager.getConnection("jdbc:mysql://" + serverConfig.getDb_ip() + ":" + serverConfig.getDb_port() + "/" + serverConfig.getDb_name() + "?autoReconnect=true&useSSL=false",
                    serverConfig.getDb_user(), serverConfig.getDb_pass());
            Class.forName("com.mysql.jdbc.Driver");
            Statement s = c.createStatement ();

            s.executeQuery ("SELECT DISTINCT path FROM Replay WHERE user = '" + selectedUser + "';");
            ResultSet r = s.getResultSet ();

            paths = new ArrayList<>();
            while (r.next()){
                paths.add(r.getString("path"));
            }
            c.close();

            return paths;
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Elimina una replay de la base de datos, asi como su archivo de informacion
     * @param replayPath Ruta del archivo a eliminar
     */
    public void deleteReplay(String replayPath) throws BadAccessToDatabaseException {
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

        String query = "DELETE FROM Replay WHERE path = '" + replayPath + "';";
        PreparedStatement preparedStmt = null;

        try {
            preparedStmt = c.prepareStatement(query);
            preparedStmt.execute();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        File replayFile = new File(replayPath);

        replayFile.delete();
    }
}