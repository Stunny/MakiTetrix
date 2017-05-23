package model.utils;

import org.apache.commons.crypto.cipher.CryptoCipher;
import org.apache.commons.crypto.cipher.CryptoCipherFactory;
import org.apache.commons.crypto.utils.Utils;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.crypto.Data;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Properties;

/**
 * Created by pedroriera on 6/4/17.
 */
public class Encrypter {

    private static final String ALG = "AES";
    private static final byte[] key =  getUTF8Bytes("1234567890123456");

    /**
     * encripta el nombre de usuario
     * @param user string que contiene el nombre de usuario
     * @return contiene un array de bits con el nombre de usuario encriptado
     */
    public String encryptUserName(String user) throws Exception {
        byte[] input = user.getBytes(StandardCharsets.UTF_8);
        return new String(encyptBytes(input), StandardCharsets.UTF_8);

    }

    /**
     * Encripta el mail
     * @param mail string que contiene el mail
     * @return contiene un array de bits con el mail encriptado
     */
    public String encryptMail(String mail) throws Exception {
        byte[] input = mail.getBytes(StandardCharsets.UTF_8);
        return new String(encyptBytes(input), StandardCharsets.UTF_8);

    }

    /**
     * Encripta la contrsena
     * @param pass string que contiene la contrasena
     * @return contiene un array de bits con la contrasena encriptada
     */
    public String encryptPass(String pass) throws Exception {
        byte[] input = pass.getBytes(StandardCharsets.UTF_8);
        return new String(encyptBytes(input), StandardCharsets.UTF_8);

    }

    /**
     * Encripta una cadena de bytes
     * @param input cadena de bytes a encriptar
     * @return cadena de bytes encriptada
     * @throws Exception Excepcion de seguridad
     */
    private byte[] encyptBytes(byte[] input) throws Exception {

        Key _key = generateKey();
        Cipher c = Cipher.getInstance(ALG);
        c.init(Cipher.ENCRYPT_MODE, _key);
        return c.doFinal(input);

    }

    public static String decrypt(String encrypted_input) throws Exception {
        Key _key = generateKey();
        Cipher c = Cipher.getInstance(ALG);
        c.init(Cipher.DECRYPT_MODE, _key);
        byte[] decoded_value = new BASE64Decoder().decodeBuffer(encrypted_input);
        byte[] dec_value_bytes = c.doFinal(decoded_value);
        return new String(dec_value_bytes);

    }

    private static Key generateKey() throws Exception {
        return new SecretKeySpec(key, ALG);
    }

    /**
     * Convierte un string a cadena de bytes
     * @param input String a convertir
     * @return Cadena de bytes que contiene los datos del input en forma de bytes
     */
    private static byte[] getUTF8Bytes(String input) {
        return input.getBytes(StandardCharsets.UTF_8);
    }
}
