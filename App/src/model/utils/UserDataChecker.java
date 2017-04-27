package model.utils;

/**
 * Created by pedroriera on 6/4/17.
 */
public class UserDataChecker {

    public static final String EMAIL_REGEX = "/^([a-z\\d!#$%&'*+\\-\\/=?^_`{|}~\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]+(\\.[a-z\\d!#$%&'*+\\-\\/=?^_`{|}~\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]+)*|\"((([ \\t]*\\r\\n)?[ \\t]+)?([\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x7f\\x21\\x23-\\x5b\\x5d-\\x7e\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0d-\\x7f\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]))*(([ \\t]*\\r\\n)?[ \\t]+)?\")@(([a-z\\d\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]|[a-z\\d\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF][a-z\\d\\-._~\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]*[a-z\\d\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])\\.)+([a-z\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]|[a-z\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF][a-z\\d\\-._~\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]*[a-z\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])\\.?$/i;\n" +
            "   ";
    public static final String PASSWORD_REGEX = "^(?=[\040-\176]*?[A-Z])(?=[\040-\176]*?[a-z])(?=[\040-\176]*\u200C?[0-9])(?=[\040-\176\u200C ]*?[#?!@$%^&*-])[\04\u200C0-\176]{8,72}$\n";
    /**
     * Comprueba que el nombre de usuario contenga un minimo de 4 caracteres, y sean caracteres UTF-8
     * @param user_name cadena de caracteres que se recibe desde la vista y contiene el nombre de usuario
     * @return true: si 'user_name' es un nombre de usuario valido -- false: si 'user_name' no es un nombre de usuario valido
     */
    public boolean checkUserName(String user_name) {
        if (user_name.length() < 4) return false;
        return true;
    }

    /**
     * Comprueba que la contrasena contenga un minimo de 8 caracteres que sean caracteres ASCII,
     * una mayuscula, una minuscula, un digito y un caracter especial (#?!@$%^&*-)
     * @param password
     * @return true: si la contrasena es valida -- false: si la contrasena no es valida
     */
    private boolean checkPassword(String password) {
        if (!password.matches(PASSWORD_REGEX)) return false;
        return true;
    }

    /**
     * Comprueba que el mail sea valido
     * @param email
     * @return true: si el mail es valido -- false: si el mail no es valido
     */
    private boolean checkEMail(String email) {
        if (!email.matches(EMAIL_REGEX)) return false;
        return true;
    }

    /**
     * Comprueba que el campo de repetir password coincida con el de password
     * @param pass
     * @param pass2
     * @return true: si pass == pass2 -- false: si pass != pass2
     */
    private boolean checkPassRepeat (String pass, String pass2) {
        return pass.equals(pass2);
    }
}
