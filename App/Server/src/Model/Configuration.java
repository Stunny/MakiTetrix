package Model;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Clase que representa y almacena la configuracion del servidor, extraida del fichero de configuracion
 * Created by Alex on 08/07/2017.
 */
public class Configuration {

    private int port_server;

    private String db_name;
    private String db_user;
    private String db_pass;

    private String db_ip;
    private int db_port;

    public Configuration() {

    }

    int getPort_server() {
        return port_server;
    }


    String getDb_name() {
        return db_name;
    }


    String getDb_user() {
        return db_user;
    }


    String getDb_pass() {
        return db_pass;
    }


    String getDb_ip() {
        return db_ip;
    }


    int getDb_port() {
        return db_port;
    }

}
