package Model.exceptions;

import java.sql.SQLException;

/**
 * Created by Alex on 09/07/2017.
 */
public class BadAccessToDatabaseException extends SQLException {

    private String db_user;

    private String db_password;

    public BadAccessToDatabaseException(String user, String db_password){
        this.db_password = db_password;
        this.db_user = user;
    }

    public void printMessage(){
        System.err.println("Error al intentar acceder a la base de datos.");
        System.err.println("Usuario usado: "+db_user);
        System.err.println("Contraseña usada: "+db_password.replaceAll(".", "*"));
        System.out.println();
        super.printStackTrace();
    }

    public String getDb_user() {
        return db_user;
    }

    public void setDb_user(String db_user) {
        this.db_user = db_user;
    }

    public String getDb_password() {
        return db_password;
    }

    public void setDb_password(String db_password) {
        this.db_password = db_password;
    }

}
