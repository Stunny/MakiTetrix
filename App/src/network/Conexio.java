package network;

import model.Move;
import model.User;
import model.utils.Encrypter;

import java.io.*;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes necesary functionalities for client-server communication
     */
    private void disconnect()	{
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

    /**
     * Sends the desired user to espectate
     * @param userNameToEspectate User to spectate name
     */
    public void sendUserToEspectate(String userNameToEspectate) {
        connect();
        try {
            doStream.writeUTF("ESPECTATE");
            doStream.writeUTF(userNameToEspectate);
            String missatge = diStream.readUTF();
            System.out.println("Comencem espectadoria");
            while(!missatge.equals("end")){
                System.out.println("missatge rebut: "+missatge);
                missatge = diStream.readUTF();
            }
            System.out.println("s'acaba la espectadoria");
        } catch (IOException e) {
            e.printStackTrace();
        }

        disconnect();
    }

    /**
     * Sends the desired user
     * @param userNameReplays
     */
    public ArrayList<String> sendDesiredUserReplay(int userNameReplays) {
        connect();
        ArrayList<String> movements = new ArrayList<>();
        try {
            doStream.writeUTF("REPLAY");
            doStream.writeInt(userNameReplays);

            String aux = null;
            do {
                aux = diStream.readUTF();
                if (!aux.equals("END")){
                    movements.add(aux);
                }
            }while (!aux.equals("END"));

            for (int i = 0; i < movements.size(); i++){
                System.out.println("Rebo aquests moviments: " + movements.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        disconnect();
        return movements;
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

        try {
            doStream.writeUTF("REPLAY_LIST");
            doStream.writeUTF(userName);
            String data = null;
            int replay_number = diStream.readInt();
            String[] total = new String[replay_number];
            for (int i = 0; i < replay_number; i++){
                data = diStream.readUTF();
                total[i] = data;
            }

            return total;
        } catch (IOException e) {
            e.printStackTrace();
        }

        disconnect();

        return null;
    }

    /**
     * Asks for a list of current game's time
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
            doStream.writeUTF("GAME_END");
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
            BufferedReader br = new BufferedReader(new FileReader(path));
            String aux;
            while((aux = br.readLine()) != null) {
                doStream.writeUTF(aux);
            }
            doStream.writeUTF("END");
            br.close();
            //doStream.writeUTF("END_NEW_REPLAY");
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
            System.out.println("size "+result.size());

            doStream.writeBoolean(status);
            disconnect();
            return result;
        } catch (Exception exc){
            exc.printStackTrace();
        }
        return result;
    }

    /**
     * Notifies the server that the user desires to save the game data
     * @param score Score of the user's game
     * @param tiempo Time of the user's game
     * @param max_espectators Maximum number of spectators during the user's game
     * @param replay_path Path to the user's replay
     */
    public void saveGameData(int score, int tiempo, int max_espectators, String replay_path){
        connect();

        try {
            doStream.writeUTF("END_GAME_DATA");
            doStream.writeUTF(currentUser.getUserName());
            doStream.writeInt(score);
            doStream.writeInt(tiempo);
            doStream.writeInt(max_espectators);
            doStream.writeUTF(replay_path);
            sendReplay(replay_path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        disconnect();
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

}
