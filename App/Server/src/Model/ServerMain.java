package Model;

import javax.swing.*;
import Controller.ServerController;
import Network.ThreadSocketServer;
import View.*;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by Admin on 20/03/2017.
 */
public class ServerMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            //creem la vista
            ServerAdminView serverAdminView = new ServerAdminView();

            Configuration serverConfig = null;
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            try {
                File configFile = new File(classLoader.getResource("config.json").getFile());
                serverConfig = new Gson().fromJson(new FileReader(configFile), Configuration.class);
            }catch (FileNotFoundException|NullPointerException npe){
                System.err.println("Error: archivo \"config.json\" no encontrado.");
                System.err.println("Cree el archivo en el directorio \'resources\' con el siguiente formato:");
                System.err.println("{\n\t\"port_server\":\"\",\n\t\"db_name\":\"\",\n\t\"db_user\":\"\",\n\t\"db_pass\":\"\",\n\t\"db_ip\":\"\",\n\t\"db_port\":\"\"\n}");
                System.exit(404);
            }

            if(serverConfig != null) {
                //creem el model
                GestioDades gestioDades = new GestioDades(serverConfig);

                //creem el controlador
                ServerController sController = new ServerController(serverAdminView, gestioDades);

                //creem el socket servidor
                ThreadSocketServer.PORT = serverConfig.getPort_server();
                ThreadSocketServer threadSocketServer = new ThreadSocketServer(gestioDades, sController);
                threadSocketServer.start();

                //printa el numero segons si es pot fer un adduser: 1:ok 2:usuari existeix 3:mail existeix 4:both
                serverAdminView.controladorBoto(sController);
                serverAdminView.setVisible(true);

                //añadimos el servidor al thread
                //threadSocketServer.controller(sController);
            }
        });
    }
}
