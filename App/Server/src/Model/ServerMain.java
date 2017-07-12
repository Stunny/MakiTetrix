package Model;

import javax.swing.*;
import Controller.ServerController;
import Model.exceptions.BadAccessToDatabaseException;
import Network.ThreadSocketServer;
import View.*;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Admin on 20/03/2017.
 */
public class ServerMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            //creem la vista
            ServerAdminView serverAdminView = new ServerAdminView();

            Configuration serverConfig = null;
            try {
                File configFile = new File("./App/Server/resources/config.json");
                serverConfig = new Gson().fromJson(new FileReader(configFile), Configuration.class);
            }catch (FileNotFoundException|NullPointerException|JsonIOException e){
                System.err.println("Error: archivo \"config.json\" no encontrado.");
                System.err.println("Cree el archivo en el directorio \'resources\' con el siguiente formato:");
                System.err.println("{\n\t\"port_server\":\"\",\n\t\"db_name\":\"\",\n\t\"db_user\":\"\",\n\t\"db_pass\":\"\",\n\t\"db_ip\":\"\",\n\t\"db_port\":\"\"\n}");
                e.printStackTrace();
            }catch (JsonSyntaxException jse){
                System.out.println("Error: formato incorrecto en el archivo de configuracion.");
                System.err.println("Cree el archivo en el directorio \'resources\' con el siguiente formato:");
                System.err.println("{\n\t\"port_server\":\"\",\n\t\"db_name\":\"\",\n\t\"db_user\":\"\",\n\t\"db_pass\":\"\",\n\t\"db_ip\":\"\",\n\t\"db_port\":\"\"\n}");
                jse.printStackTrace();
            }

            if(serverConfig != null) {
                //creem el model
                GestioDades gestioDades = null;
                try {
                    gestioDades = new GestioDades(serverConfig);
                } catch (BadAccessToDatabaseException e) {
                    e.printMessage();
                }

                if(gestioDades != null) {
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
            }
        });
    }
}
