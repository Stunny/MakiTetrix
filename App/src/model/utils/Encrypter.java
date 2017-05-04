package model.utils;

import java.nio.charset.StandardCharsets;

/**
 * Created by pedroriera on 6/4/17.
 */
public class Encrypter {
    /**
     * Encripta los datos introducidos al hacer un login
     * @param mail string que contiene el mail
     * @param pass string que contiene el pass
     */
    public void encryptLogin(String mail, String pass) {
        encryptMail(mail);
        encryptPass(pass);
    }

    /**
     * Encripta los datos introducidos al hacer el registro (mail, userName y pass)
     * @param mail string que contiene el mail
     * @param pass string  que contine el pass
     * @param user string que contiene el user
     */
    public void encryptSingUp(String mail, String pass, String user) {
        encryptMail(mail);
        encryptPass(pass);
        encryptUserName(user);
    }

    /**
     * encripta el nombre de usuario
     * @param user string que contiene el nombre de usuario
     * @return contiene un array de bits con el nombre de usuario encriptado
     */
    private byte[] encryptUserName(String user) {
        return user.getBytes(StandardCharsets.UTF_8);

    }

    /**
     * Encripta el mail
     * @param mail string que contiene el mail
     * @return contiene un array de bits con el mail encriptado
     */
    private byte[] encryptMail(String mail) {
        return mail.getBytes(StandardCharsets.UTF_8);

    }

    /**
     * Encripta la contrsena
     * @param pass string que contiene la contrasena
     * @return contiene un array de bits con la contrasena encriptada
     */
    private byte[] encryptPass(String pass) {
        byte[] b = pass.getBytes(StandardCharsets.UTF_8);
        //hacer una opearacion maxi chunga y secreta para encriptar
        //b = b;
       return b;

    }

}
