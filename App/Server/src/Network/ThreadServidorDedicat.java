package Network;

import Model.GestioDades;
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
    private GestioDades gestioDades = new GestioDades();
    private int L;
    private int R;

    public ThreadServidorDedicat(Socket sClient){
        this.sClient = sClient;
    }

    @Override
    public void run(){
        try {
            //creem les instancies necesaries per rebre i enviar dades
            doStream = new DataOutputStream(sClient.getOutputStream());
            diStream = new DataInputStream(sClient.getInputStream());
            //llegiexo les trames del client i les analitzo
            while (true){
                String aux = diStream.readUTF();
                tractaResposta(aux);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Analyses the message recieved from the client
     * @param resposta
     */
    public void tractaResposta(String resposta) throws IOException {

        String [] aux = resposta.split("-");
        if(aux[0].equals("L")){
            L = gestioDades.gestionaLogin(aux[1]);
            enviaResposta(L);
            //0:ok, 1:usuari/mail no existeix 2:contra no

        }else if (aux[0].equals("R")){
            R = gestioDades.gestionaRegistre(aux[1]);
            enviaResposta(R);
            //0:ok, 3:usuari existeix 4:mail existeix 5:both
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
                break;
            case 1:
                doStream.writeUTF("KO-Usuari/email no existeix");
                break;
            case 2:
                doStream.writeUTF("KO-La contrasenya no es correcta");
                break;
            case 3:
                doStream.writeUTF("KO-L'usuari ja existeix");
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