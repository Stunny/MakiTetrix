package model.utils;

import org.apache.commons.crypto.cipher.CryptoCipher;
import org.apache.commons.crypto.cipher.CryptoCipherFactory;
import org.apache.commons.crypto.utils.Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * Created by pedroriera on 6/4/17.
 */
public class Encrypter {

    /**
     * encripta el nombre de usuario
     * @param user string que contiene el nombre de usuario
     * @return contiene un array de bits con el nombre de usuario encriptado
     *//*
    private String encryptUserName(String user) throws Exception {
        byte[] input = user.getBytes(StandardCharsets.UTF_8);
        return new String(encyptBytes(input), StandardCharsets.UTF_8);

    }*/

    /**
     * Encripta el mail
     * @param mail string que contiene el mail
     * @return contiene un array de bits con el mail encriptado
     *//*
    private String encryptMail(String mail) throws Exception {
        byte[] input = mail.getBytes(StandardCharsets.UTF_8);
        return new String(encyptBytes(input), StandardCharsets.UTF_8);

    }
*/
    /**
     * Encripta la contrsena
     * @param pass string que contiene la contrasena
     * @return contiene un array de bits con la contrasena encriptada
     *//*
    private String encryptPass(String pass) throws Exception {
        byte[] input = pass.getBytes(StandardCharsets.UTF_8);
        return new String(encyptBytes(input), StandardCharsets.UTF_8);

    }
*/
    /**
     * Encripta una cadena de bytes
     * @param input cadena de bytes a encriptar
     * @return cadena de bytes encriptada
     * @throws Exception Excepcion de seguridad
     */
    private byte[] encyptBytes(byte[] input) throws Exception {
        final SecretKeySpec key = new SecretKeySpec(getUTF8Bytes("1234567890123456"), "AES");
        final IvParameterSpec iv = new IvParameterSpec(getUTF8Bytes("1234567890123456"));

        Properties properties = new Properties();
        properties.setProperty(CryptoCipherFactory.CLASSES_KEY, CryptoCipherFactory.CipherProvider.OPENSSL.getClassName());

        final String transform = "AES/CBC/PKCS5Padding";
        CryptoCipher encipher = Utils.getCipherInstance(transform, properties);

        byte[] output = new byte[32];
        //Initializes the cipher with ENCRYPT_MODE, key and iv.
        encipher.init(Cipher.ENCRYPT_MODE, key, iv);
        //Continues a multiple-part encryption/decryption operation for byte array.
        int updateBytes = encipher.update(input, 0, input.length, output, 0);
        System.out.println(updateBytes);
        //We must call doFinal at the end of encryption/decryption.
        int finalBytes = encipher.doFinal(input, 0, 0, output, updateBytes);
        System.out.println(finalBytes);
        //Closes the cipher.
        encipher.close();

        return output;

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
