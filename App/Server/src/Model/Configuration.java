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

    public int getPort_server() {
        return port_server;
    }

    public void setPort_server(int port_server) {
        this.port_server = port_server;
    }

    public String getDb_name() {
        return db_name;
    }

    public void setDb_name(String db_name) {
        this.db_name = db_name;
    }

    public String getDb_user() {
        return db_user;
    }

    public void setDb_user(String db_user) {
        this.db_user = db_user;
    }

    public String getDb_pass() {
        return db_pass;
    }

    public void setDb_pass(String db_pass) {
        this.db_pass = db_pass;
    }

    public String getDb_ip() {
        return db_ip;
    }

    public void setDb_ip(String db_ip) {
        this.db_ip = db_ip;
    }

    public int getDb_port() {
        return db_port;
    }

    public void setDb_port(int db_port) {
        this.db_port = db_port;
    }
}
