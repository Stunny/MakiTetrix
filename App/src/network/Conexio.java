package network;

import Vista.GameView;
import controller.EspectatorController;
import controller.GameController;
import model.Move;
import model.User;
import model.utils.Encrypter;

import java.io.*;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Admin on 16/05/2017.
 */
public class Conexio extends Thread {

    private DataInputStream diStream;
    private DataOutputStream doStream;
    private Socket sServidor;

    private String KOMessage;
    private boolean ResponseSuccess = true;
    private int time;
    private User currentUser;
    private EspectatorController espectatorController;

    /**
     * Sets necesary functionalities for client-server communication
     */
    private void connect(){
        // Averiguem quina direccio IP hem d'utilitzar
        InetAddress iAddress;
        try {
            iAddress = InetAddress.getLocalHost();
            String IP = iAddress.getHostAddress();

            //Socket sServidor = new Socket("172.20.31.90", 33333);
            sServidor = new Socket (String.valueOf(IP), 33333);
            doStream = new DataOutputStream(sServidor.getOutputStream());
            diStream = new DataInputStream(sServidor.getInputStream());
        } catch (ConnectException c){
            System.err.println("Error! El servidor no esta disponible!");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes necesary functionalities for client-server communication
     */
    public void disconnect()	{
        try {
            doStream.close();
        }	catch (IOException	|	NullPointerException	e)	{
            System.err.println("[DISCONNECT]	" + e.getMessage());
        }
        try {
            diStream.close();
        }	catch (IOException	|	NullPointerException	e)	{
            System.err.println("[DISCONNECT]	" + e.getMessage());
        }
        try {
            sServidor.close();
        }	catch (IOException	|	NullPointerException	e)	{
            System.err.println("[DISCONNECT]	" + e.getMessage());
        }
    }

    /**
     * Manages starting client user request (login or register) for a server communication
     * @param user User that desires to login or register
     */
    public void startingLoginRegister(User user){
        connect();

        if (user.getEmail() == null){
            login(user);
        }else{
            register(user);
        }

        disconnect();
    }

    /**
     * Comunicates with server when a login request is done
     * @param user User to login
     */
    public void login(User user) {
        try {
            Encrypter encrypter = new Encrypter();
            //el usuario ha logueado usando el email
            if (user.getUserName().contains("@")){
                String emailAux = encrypter.encrypt(user.getUserName());
                String passwordAux = encrypter.encrypt(user.getPassword());

                doStream.writeUTF("L");
                doStream.writeUTF(emailAux);
                doStream.writeUTF(passwordAux);

            }else{
                //el usuario ha logueado usando el nombre de usuario
                String nameAux = encrypter.encrypt(user.getUserName());
                String passwordAux = encrypter.encrypt(user.getPassword());

                doStream.writeUTF("L");
                doStream.writeUTF(nameAux);
                doStream.writeUTF(passwordAux);
            }
            tractaResposta(diStream.readUTF());
        } catch (Exception a){
            a.printStackTrace();
        }
    }

    /**
     * obtiene los valores de las teclas por defecto
     * @param user usuario a quien va dirigido
     * @return conjunto de valores de las teclas
     */
    public ArrayList<Integer> getTeclesUser(String user) {
        ArrayList<Integer>result = new ArrayList<>();

        try {
            connect();
            doStream.writeUTF("getTecles");
            doStream.writeUTF(user);
            if (diStream.readBoolean()) {
                result.add(diStream.readInt());
                result.add(diStream.readInt());
                result.add(diStream.readInt());
                result.add(diStream.readInt());
                result.add(diStream.readInt());
                result.add(diStream.readInt());

                disconnect();
                return result;

            }else {

                disconnect();
                return result;
            }
        } catch (Exception exc){
            exc.printStackTrace();
        }
        return result;
    }

    /**
     * envia al servidor los valores de las teclas por defecto
     * @param user usuario a quien se le ponen los valores
     * @param d valor derecha
     * @param i valor izquierda
     * @param a valor abajo
     * @param rd valor rotar derecha
     * @param ri valor rotar izquierda
     * @param p valor pausa
     */
    public void setTeclesUser(String user, int d, int i, int a, int rd, int ri, int p) {
        try {
            connect();
            Encrypter encrypter = new Encrypter();
            doStream.writeUTF("setTecles");
            doStream.writeUTF(user);
            doStream.writeInt(d);
            doStream.writeInt(i);
            doStream.writeInt(a);
            doStream.writeInt(rd);
            doStream.writeInt(ri);
            doStream.writeInt(p);
            disconnect();
        } catch (Exception exc){
            exc.printStackTrace();
        }
    }

    public void register(User user) {
        try {
            Encrypter encrypter = new Encrypter();
            String nameAux = encrypter.encrypt(user.getUserName());
            String emailAux = encrypter.encrypt(user.getEmail());
            String passwordAux = encrypter.encrypt(user.getPassword());

            doStream.writeUTF("R");
            doStream.writeUTF(nameAux);
            doStream.writeUTF(emailAux);
            doStream.writeUTF(passwordAux);
        } catch (Exception a){
            a.printStackTrace();
        }
    }

    /**
     * Sets the specified user's status to "Offline"
     */
    public void disconnectUser(User user) {
        connect();

        try {
            doStream.writeUTF("DISCONNECT");
            doStream.writeUTF(user.getUserName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        disconnect();
    }


    /**
     * Checks server's answer to client requests
     * @param s String indicating the server response
     * @throws IOException
     */
    public void tractaResposta(String s) throws IOException {
        if (s.equals("OK")){
            ResponseSuccess = true;
        }else{
            KOMessage = diStream.readUTF();
            ResponseSuccess = false;
        }
    }
    public synchronized void començaActualitza(){
        connect();
        System.out.println("comença actualitza");

    }


    public synchronized int getPartidesOnline(){
        connect();
        int act = 1;
        try {
            doStream.writeUTF("GIVEPARTIDES");
            act = diStream.readInt();
        }catch (IOException e){
            e.printStackTrace();
        }
        return act;
    }

        public synchronized boolean actualitza(int nPartidesActuals){
        connect();
        try{

            doStream.writeUTF("GIVEPARTIDES");
            int act = diStream.readInt();
            System.out.println("rebo int ="+act);
            if(nPartidesActuals!=act){
                System.out.println("diferent");
                return true;
            }else{
                disconnect();
                return false;
            }

        } catch (IOException e){
            e.printStackTrace();
        }
        disconnect();
        return false;
    }

    /**
     * Sends the desired user to espectate
     * @param userNameToEspectate User to spectate name
     */


    public void sendUserToEspectate(String userNameToEspectate, GameController gc, GameView gv) {
        System.out.println("Empiezo a espectar");

        connect();
        try {
            doStream.writeUTF("ESPECTATE");
            doStream.writeUTF(userNameToEspectate);
            gc.setPGDirect(true);
            String missatge;
            ArrayList<String> historial = new ArrayList<>();
            missatge = diStream.readUTF();
            if (missatge.equals("found")) {
                System.out.println("found");
                missatge = diStream.readUTF();
                while (!missatge.equals("END_HISTORIAL")) {
                    System.out.println("PARTE HISTORIAL MOVIMIENTOS: " + missatge);
                    historial.add(missatge);
                    missatge = diStream.readUTF();
                }
                gc.readyReplay(historial);
                gc.startReplay();
            }else{
                System.out.println("repe");
            }
            } catch(IOException e){
                e.printStackTrace();
            }

    }

    /**
     * Sends the desired user
     * @param replayID
     */
    public ArrayList<String> getDesiredUserReplay(int replayID) {
        connect();
        ArrayList<String> movements = new ArrayList<>();
        try {
            doStream.writeUTF("GET_REPLAY");
            doStream.writeInt(replayID);
            doStream.writeUTF(currentUser.getUserName());
            System.out.println("getDesiredUserReplay");
            String aux = null;
            do {
                aux = diStream.readUTF();
                if (!aux.equals("END")){
                    movements.add(aux);
                }
            }while (!aux.equals("END"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        disconnect();
        return movements;
    }
    /**
     *lee un movimiento
     * @return missatge movimiento
     */

    public String readMove (){

        String missatge = "";
        try {
            missatge = diStream.readUTF();
            System.out.println("MOVIMIENTOS: " + missatge);
        } catch (IOException ioe){
            ioe.printStackTrace();
        }

        return missatge;
    }

    @Deprecated
    /**
     * Makes a petition for a list of all online users
     * @return ArrayList<String> containing the names of the current online users
     */
    public ArrayList<String> getOnlineUsers() {
        connect();
        ArrayList<String> onlineUsers = new ArrayList<>();
        try {
            doStream.writeUTF("LIVE_USERS");

            int size = diStream.readInt();
            for (int i = 0; i < size; i++){
                onlineUsers.add(diStream.readUTF());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        disconnect();
        return onlineUsers;
    }

    /**
     * Asks the server for user's replay list
     * @param userName User we want replays from
     * @return String[] containing all the replays info
     */
    public String[] getReplays(String userName) {
        connect();
        String aux = null;
        try {
            doStream.writeUTF("REPLAY_LIST");
            doStream.writeUTF(currentUser.getUserName());
            String data = null;
            int replay_number = diStream.readInt();
            String[] total = new String[replay_number];
            for (int i = 0; i < replay_number; i++){
                data = diStream.readUTF();
                total[i] = data;
            }
            /*
            do {
                aux = diStream.readUTF();
            }while (aux.equals("KO"));
*/


            return total;
        } catch (IOException e) {
            e.printStackTrace();
        }

        disconnect();

        return null;
    }

    /**
     * Asks for a list of current game's time
     * @throws IOException
     * @return String array containing all user's current play time and name
     */
    public String[] getGamingUsers() {
        connect();

        String data[] = null;
        try {
            doStream.writeUTF("GAMING_USERS");
            doStream.writeUTF(currentUser.getUserName());
            int aux = diStream.readInt();
            data = new String[aux];

            for (int i = 0 ; i < aux; i++){
                data[i] = diStream.readUTF();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        disconnect();

        return data;
    }

    /**
     * Notifies the server of a game start
     * @throws IOException
     */
    public void sendStartGame (){
        connect();
        try{
            doStream.writeUTF("GAME_START");
            doStream.writeUTF(currentUser.getUserName());

        } catch (IOException e){
            e.printStackTrace();
        }
        disconnect();
    }

    /**
     * Asks the server for the number of spectators that are watching the game the current user is playing
     * @return espectadors numero de espectadores
     */

    public int pideEspectadores (){
        connect();
        int espectadors = 0;

        try{
            doStream.writeUTF("GIVESPEC");
            doStream.writeUTF(currentUser.getUserName());
            espectadors = diStream.readInt();
        } catch (IOException e){
            e.printStackTrace();
        }
        disconnect();
        return espectadors;
    }
    /**
     * Notifies the server of a new move nad sends it
     * @param m Move we send to the server
     */

    public void sendMove (Move m){
        connect();
        try{
            doStream.writeUTF("MOVE");
            doStream.writeUTF(currentUser.getUserName());
            doStream.writeUTF(m.toString());
        } catch (IOException e){
            e.printStackTrace();
        }

        disconnect();
    }

    /**
     * Notifies the server that the user's game has ended
     */
    public void sendEndGame (){
        connect();
        try{
            doStream.writeUTF("GAME_STOP");
            doStream.writeUTF(currentUser.getUserName());
        } catch (IOException e){
            e.printStackTrace();
        }
        disconnect();
    }

    /**
     * Sends to the server the user replay.
     * @param path Path of the replay
     */
    public void sendReplay (String path){
        connect();

        try {
            doStream.writeUTF("NEW_REPLAY");
            doStream.writeUTF(currentUser.getUserName());
            doStream.writeUTF(path);
            BufferedReader br = new BufferedReader(new FileReader(path));
            String aux;
            while((aux = br.readLine()) != null) {
                doStream.writeUTF(aux);
            }
            doStream.writeUTF("END");
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        disconnect();
    }

    /**
     * Notifies the server that the user desires to save the game data
     * @param score Score of the user's game
     * @param tiempo Time of the user's game
     * @param replay_path Path to the user's replay
     */
    public void saveGameData(int score, int tiempo, String replay_path){
        connect();

        try {
            doStream.writeUTF("END_GAME_DATA");
            doStream.writeUTF(currentUser.getUserName());
            doStream.writeInt(score);
            doStream.writeInt(tiempo);
            doStream.writeUTF(replay_path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        disconnect();
    }

    public void eliminaEspectador(String user){
        connect();

        try {
            doStream.writeUTF("DELETESPEC");

        } catch (IOException e) {
            e.printStackTrace();
        }

        disconnect();
    }


    /**
     * Sets players' key configuration and modifies BBDD gaming and startingGameTime
     * @param userName Player we want to modify and set parameters for
     * @param status BBDD gaming's status
     * @return Array containing player's key configuration
     */
    public ArrayList<Integer> gameStartParameters(String userName, boolean status) {
        ArrayList<Integer>result = new ArrayList<>();
        try {
            connect();
            doStream.writeUTF("START_PARAMETERS");
            doStream.writeUTF(userName);
            if (diStream.readBoolean()) {
                result.add(diStream.readInt());
                result.add(diStream.readInt());
                result.add(diStream.readInt());
                result.add(diStream.readInt());
                result.add(diStream.readInt());
                result.add(diStream.readInt());
            }

            doStream.writeBoolean(status);
            disconnect();
            return result;
        } catch (Exception exc){
            exc.printStackTrace();
        }
        return result;
    }

    public String getResponse() {
        return KOMessage;
    }

    public boolean isResponseSuccess() {
        return ResponseSuccess;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public int getTime() {
        return time;
    }


    /**
     * Afegeix un nou usuari que està al "lobby" (pantalla d'espectadors)
     * @param espectatorController controlador de la finestra
     * @throws IOException
     */
    public void addEspectatorController(EspectatorController espectatorController) {
        this.espectatorController = espectatorController;
        connect();
        try{
            doStream.writeUTF("NEW_LOBBY");



        } catch (IOException e){
            e.printStackTrace();
        }
        disconnect();
    }

}