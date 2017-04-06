package model.utils;

/**
 * Created by pedroriera on 6/4/17.
 */
public class UserDataChecker {

    /**
     * Comprueba que el nombre de usuario contenga un minimo de 4 caracteres, y sean caracteres UTF-8
     * @param user_name cadena de caracteres que se recibe desde la vista y contiene el nombre de usuario
     * @return true: si 'user_name' es un nombre de usuario valido -- false: si 'user_name' no es un nombre de usuario valido
     */
    private boolean checkUserName(String user_name) {
        if (user_name.length() < 4) return false;
        return true;
    }

    /**
     * Comprueba que la contrasena contenga un minimo de 6 caracteres, y sean caracteres UTF-8
     * @param password
     * @return
     */
    private boolean checkPassword(String password) {
        if (password.length() < 6 /*&& !password.matches()*/) return false;
        return true;
    }
}
