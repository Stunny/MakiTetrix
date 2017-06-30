package Network;

import Model.GestioDades;
import model.utils.Encrypter;
import utils.GameDataManager;
import utils.ObserveManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

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

    private GameDataManager gdm;
    private ObserveManager observeManager;

    public ThreadServidorDedicat(Socket sClient, GestioDades gestioDades){
        this.sClient = sClient;
        this.gestioDades = gestioDades;
    }

    @Override
    public void run(){
        try {

            doStream = new DataOutputStream(sClient.getOutputStream());
            diStream = new DataInputStream(sClient.getInputStream());

            while (true){
                String request = diStream.readUTF();
                readRequest(request);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Analyses the message recieved from the client
     * @param request
     */
    public void readRequest(String request) throws IOException {
        String [] reqData = request.split("-");

        switch(reqData[0]){
            case "L": //Login Request
                Encrypter encrypter = new Encrypter();
                try {
                    String aux = encrypter.decrypt(reqData[1]);
                    System.out.println("Decripted string: " + aux);
                    loginStatus = gestioDades.gestionaLogin(aux);
                    enviaResposta(loginStatus);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "R": //Register Request
                System.out.println("detecto un registre en el server");
                registerStatus = gestioDades.gestionaRegistre(reqData[1]);
                System.out.println("gestiona dades em retorna un " + registerStatus);
                enviaResposta(registerStatus);
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
                break;

            case "ReplaySelect"://Selected user to see replays
                // observeManager.beginObserve();
                System.out.println("I want to see: " + reqData[1] + "user's replays");
                // TODO: enviar las diferentes partidas a escoger para observar
                break;

            case "OBSelect": //Selected user to observe
                System.out.println("I want to spectate: " + reqData[1]);
                // TODO: establecer observador a la partida seleccionada
                break;
        }

    }

    /**
     * Returns server's answer to client
     * @throws IOException
     */
    public void enviaResposta(int error) throws IOException {
        System.out.println("envio aquesta resposta del servidor (bbdd) al client: " + error);
        switch(error){
            case 0:
                doStream.writeUTF("OK");
                break;
            case 1:
                doStream.writeUTF("KO-Usuari/email no existeix");
                break;
            case 2:
                doStream.writeUTF("KO-La contrasenya no es correcta");
                break;
            case 3:
                doStream.writeUTF("KO-loginStatus'usuari ja existeix");
                break;
            case 4:
                doStream.writeUTF("KO-El email ja existeix");
                break;
            case 5:
                doStream.writeUTF("KO-El email i l'usuari ja existeixen");
                break;
        }
    }
}