package Network;

import Controller.ServerController;
import Model.Encrypter;
import Model.GestioDades;
import Model.exceptions.BadAccessToDatabaseException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by Admin on 24/03/2017.
 */
public class ThreadServidorDedicat extends Thread{

    private DataInputStream diStream;
    private DataOutputStream doStream;
    private Socket sClient;

    private int loginStatus;
    private int registerStatus;
    private GestioDades gestioDades;
    private ServerController sController;
    private String currentUser;



    public ThreadServidorDedicat(Socket sClient, GestioDades gestioDades, ServerController sController){
        this.sClient = sClient;
        this.gestioDades = gestioDades;
        this.sController = sController;
    }

    @Override
    public void run(){
        try {
            doStream = new DataOutputStream(sClient.getOutputStream());
            diStream = new DataInputStream(sClient.getInputStream());
            String request = diStream.readUTF();
            readRequest(request);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Analyses the message recieved from the client
     * @param request
     */
    public void readRequest(String request) throws IOException {
        switch(request){
            case "setTecles": {
               String u = diStream.readUTF();
               int d = diStream.readInt();
               int i = diStream.readInt();
               int a = diStream.readInt();
               int rd = diStream.readInt();
               int ri = diStream.readInt();
               int p = diStream.readInt();

               try {

                   gestioDades.setTecles(u, d, i, a, rd, ri, p);

               }catch(BadAccessToDatabaseException e){
                   e.printMessage();
               }
            }
            break;

            case "getTecles": {
                String u = diStream.readUTF();
                try {
                    ArrayList<Integer>result = gestioDades.getTecles(u);

                    if (result.size()==0){
                        doStream.writeBoolean(false);
                    }else{
                        doStream.writeBoolean(true);
                        doStream.writeInt(result.get(0));
                        doStream.writeInt(result.get(1));
                        doStream.writeInt(result.get(2));
                        doStream.writeInt(result.get(3));
                        doStream.writeInt(result.get(4));
                        doStream.writeInt(result.get(5));
                    }
                }catch(BadAccessToDatabaseException e){
                    e.printMessage();
                }
            }
            break;

            case "L": //Login Request
                try {
                    String userNameOREmail = diStream.readUTF();
                    String password = diStream.readUTF();

                    String decryptedUser = Encrypter.decrypt(userNameOREmail);
                    String decryptedPsswd = Encrypter.decrypt(password);


                    loginStatus = gestioDades.handleLogin(decryptedUser, decryptedPsswd);
                    enviaResposta(loginStatus);

                    if(loginStatus == 0){
                        //Login successful: keep username
                        sController.updateUserList();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "R": //Register Request
                try {
                    String usuari = diStream.readUTF();
                    String email = diStream.readUTF();
                    String password = diStream.readUTF();

                    String decryptedUserName = Encrypter.decrypt(usuari);
                    String decryptedPassword = Encrypter.decrypt(password);
                    String decryptedMail = Encrypter.decrypt(email);

                    registerStatus = gestioDades.handleRegister(decryptedUserName, decryptedPassword, decryptedMail);
                    enviaResposta(registerStatus);

                    if(registerStatus == 0){
                        sController.updateUserList();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case "DISCONNECT":
                try {
                    gestioDades.disconnectUser(diStream.readUTF());
                } catch (BadAccessToDatabaseException e) {
                    e.printMessage();
                }
                sController.updateUserList();
                break;



            case "MV": //New Move on Game
                // TODO: almacenar nuevo movimiento en el manager
                break;

            case "GAME_END": //final de partida
                currentUser = diStream.readUTF();
                gestioDades.setGamingStatus(currentUser, false, null);

                // TODO: gdm.setFinishTime(reqData[1]);
                // TODO: observeManager.notifyEndOfGame();
                // TODO: ACTUALIZAR BASE DE DATOS CON LA NUEVA INFORMACION
                break;

            case "LIVE_USERS"://List of online players
                ArrayList<String> onlineUsers = sController.onlineUsers();
                doStream.writeInt(onlineUsers.size());
                for (int i = 0; i < onlineUsers.size(); i++){
                    doStream.writeUTF(onlineUsers.get(i));
                }

                break;

            case "GAMING_USERS"://List of players currently playing
                currentUser = diStream.readUTF();
                ArrayList<String> gamingUsers = gestioDades.gamingUsers(currentUser);
                doStream.writeInt(gamingUsers.size());
                for (int i = 0; i < gamingUsers.size(); i++){
                    doStream.writeUTF(gamingUsers.get(i));
                }

                break;

            case "NEW_LOBBY" :

                System.out.println("new lobby a server");
                sController.afegeixLobby(doStream);

                break;
            case "REPLAY_LIST"://List of player's games
                currentUser = diStream.readUTF();
                ArrayList<String[]> gameInfo = gestioDades.getGameData(currentUser);
                doStream.writeInt(gameInfo.size());
                System.out.println("size: " + gameInfo.size());
                for (int i = 0; i < gameInfo.size(); i++){
                    String[] aux = gameInfo.get(i);
                    String data = aux[0] + "#" + aux[1] + "#" + aux[2] + "#" + aux[3];
                    System.out.println("data: " + data);
                    doStream.writeUTF(data);
                }

                break;

            case "GET_REPLAY"://Selected user's replay
                int replayID = diStream.readInt();
                currentUser = diStream.readUTF();
                ArrayList<String> replay = gestioDades.getDesiredReplay(replayID, currentUser);
                for (int i = 0; i < replay.size(); i++){
                    doStream.writeUTF(replay.get(i));
                }

                doStream.writeUTF("END");
                break;

            case "ESPECTATE": //Selected user to observe
                String selectedUser = diStream.readUTF();
                    //Ens afegim com a espectador de la partida del jugador user
                    sController.afegeixEspectador(selectedUser,doStream);

                break;

            case "START_PARAMETERS"://Sets player's default keys and notifies server a change in gaming status
                currentUser = diStream.readUTF();
                try {
                    ArrayList<Integer>result = gestioDades.getTecles(currentUser);

                    if (result.size()==0){
                        doStream.writeBoolean(false);
                    }else{
                        doStream.writeBoolean(true);
                        doStream.writeInt(result.get(0));
                        doStream.writeInt(result.get(1));
                        doStream.writeInt(result.get(2));
                        doStream.writeInt(result.get(3));
                        doStream.writeInt(result.get(4));
                        doStream.writeInt(result.get(5));
                    }
                    boolean status = diStream.readBoolean();
                    java.util.Date dt = new java.util.Date();
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String startingGameTime = sdf.format(dt);
                    gestioDades.setGamingStatus(currentUser, status, startingGameTime);
                }catch(BadAccessToDatabaseException e){
                    e.printMessage();
                }
                break;

            case "GAME_STOP":
                //avisem a tots els espectadors que es para la transmissio i es buida l'arraylist espectadors
                String u = diStream.readUTF();
                LlistaEspectadors espectadors= sController.getEspectadors(u);
                ArrayList<DataOutputStream> ds= espectadors.getDs();
                for (int i=0; i<ds.size();i++){
                        ds.get(i).writeUTF("end");
                }
                espectadors.eliminaEspectador(doStream);
                sController.eliminaPartida(u);
                break;

            case "GAME_START":
                currentUser = diStream.readUTF();
                sController.addPartida(currentUser);
                //sController.actualitzaLlistesEspectadors();

                break;

            case "GIVESPEC":
                currentUser = diStream.readUTF();
                LlistaEspectadors aux = sController.getEspectadors(currentUser);


                if (aux == null){
                    doStream.writeInt(0);

                }else {
                    doStream.writeInt(aux.getDs().size());
                }

                break;
            
            case "MOVE":
                String user = diStream.readUTF();
                String s = diStream.readUTF();

                //System.out.println("moviment: " + s);
                espectadors= sController.getEspectadors(user);

                //afegim moviment a l'historial de la partida
                espectadors.afegeixMoviment(s);

                //Busquem tots els espectadors als que s'han d'enviar missatges
                ds = espectadors.getDs();
                for (int i = 0; i < ds.size(); i++){
                    ds.get(i).writeUTF(s);
                }

                break;

            case "DELETESPEC":
                System.out.println("deletespec");
                System.out.println("retrans size = "+sController.getRetrans().size());
                ArrayList<LlistaEspectadors> retrans = sController.getRetrans();
                for(int i = 0; i< retrans.size();i++){
                    ArrayList<DataOutputStream> llaux = retrans.get(i).getDs();
                    for(int x = 0; x<llaux.size();x++){
                        if (llaux.get(x).toString().equals(doStream.toString())){
                            System.out.println("llego");
                            llaux.remove(x);
                        }

                    }
                }
                break;

            case "GIVEPARTIDES":
                doStream.writeInt(sController.getRetrans().size());
                break;

            case "END_GAME_DATA":
                currentUser = diStream.readUTF();
                int score = diStream.readInt();
                int millis = diStream.readInt();
                String replay_path = diStream.readUTF();

                gestioDades.saveGameData(currentUser, score, millis, sController.getEspectadors(currentUser).getMaxEspec(), replay_path);
                gestioDades.setGamingStatus(currentUser, false, null);
                break;

            case "NEW_REPLAY":
                String move = null;
                String userName = diStream.readUTF();
                String path = diStream.readUTF();
                int order = 0;
                int numGame = gestioDades.gestNumGames(userName);
                Connection c = gestioDades.connect();
                do {
                    move = diStream.readUTF();
                    if (!move.equals("END")){
                        gestioDades.addMove(userName, move, order, numGame, c, path);
                        order++;
                    }
                }while (!move.equals("END"));
                gestioDades.close(c);
                break;
        }
    }


    /**
     * Returns server's answer to client
     * @throws IOException
     */
    public void enviaResposta(int error) throws IOException {
        switch(error){
            case 0:
                doStream.writeUTF("OK");
                //es correcte cridar a una funcio que actulitzi la vista desde aqui?
                break;
            case 1:
                doStream.writeUTF("KO");
                doStream.writeUTF("Usuari/email no existeix");
                break;
            case 2:
                doStream.writeUTF("KO");
                doStream.writeUTF("La contraseña no es correcta");
                break;
            case 3:
                doStream.writeUTF("KO");
                doStream.writeUTF("El usuari ja existeix");
                break;
            case 4:
                doStream.writeUTF("KO");
                doStream.writeUTF("El email ja existeix");
                break;
            case 5:
                doStream.writeUTF("KO");
                doStream.writeUTF("El email i l'usuari ja existeixen");
                break;
            case 6:
                doStream.writeUTF("KO");
                doStream.writeUTF("Aquest usuari ja està connectat");
        }
    }


}