package Network;

import Controller.ServerController;
import Model.Encrypter;
import Model.GestioDades;
import Model.exceptions.BadAccessToDatabaseException;
import utils.GameDataManager;
import utils.ObserveManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Admin on 24/03/2017.
 */
public class ThreadServidorDedicat extends Thread {

    private DataInputStream diStream;
    private DataOutputStream doStream;
    private Socket sClient;

    private int loginStatus;
    private int registerStatus;
    private GestioDades gestioDades;
    private ServerController sController;

    private GameDataManager gdm;
    private ObserveManager observeManager;

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

            case "NG": //New Game Start Request
                gdm = new GameDataManager();
                break;

            case "MV": //New Move on Game
                // TODO: almacenar nuevo movimiento en el manager
                break;

            case "EoG": //End of Game: final de partida
                // TODO: gdm.setFinishTime(reqData[1]);
                // TODO: observeManager.notifyEndOfGame();
                // TODO: ACTUALIZAR BASE DE DATOS CON LA NUEVA INFORMACION
                break;

            case "LIVE_USERS":

                ArrayList<String> onlineUsers = sController.onlineUsers();
                doStream.writeInt(onlineUsers.size());
                for (int i = 0; i < onlineUsers.size(); i++){
                    doStream.writeUTF(onlineUsers.get(i));
                }

                break;

            case "REPLAY_LIST":
                String userName = diStream.readUTF();

                ArrayList<String[]> gameInfo = gestioDades.getGameData(userName);
                //doStream.writeUTF(gameInfo);

                doStream.writeInt(gameInfo.size());
                for (int i = 0; i < gameInfo.size(); i++){
                    String[] aux = gameInfo.get(i);
                    String data = aux[0] + "-" + aux[1] + "-" + aux[2] + "-" + aux[3];
                    doStream.writeUTF(data);
                }
                break;

            case "REPLAY"://Selected user's replay
                // observeManager.beginObserve();
                System.out.println("Quiero ver la replay numero: " + diStream.readInt());
                // TODO: peticion de la replay seleccionada
                break;

            case "ESPECTATE": //Selected user to observe
                System.out.println("I want to spectate: " + diStream.readUTF());
                // TODO: establecer observador a la partida seleccionada
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
        }
    }

}