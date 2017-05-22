package utils;

import model.utils.Encrypter;
import org.apache.commons.crypto.cipher.CryptoCipher;
import org.apache.commons.crypto.cipher.CryptoCipherFactory;
import org.apache.commons.crypto.utils.Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * Created by pedroriera on 15/5/17.
 */
public class Decrypter extends Encrypter {

   /* private byte[] decryptBytes(byte[] input) throws Exception {
        final SecretKeySpec key = new SecretKeySpec(getUTF8Bytes("1234567890123456"), "AES");
        final IvParameterSpec iv = new IvParameterSpec(getUTF8Bytes("1234567890123456"));

        Properties properties = new Properties();
        properties.setProperty(CryptoCipherFactory.CLASSES_KEY, CryptoCipherFactory.CipherProvider.OPENSSL.getClassName());

        final String transform = "AES/CBC/PKCS5Padding";
        CryptoCipher encipher = Utils.getCipherInstance(transform, properties);
        byte[] output = new byte[32];


        properties.setProperty(CryptoCipherFactory.CLASSES_KEY, CryptoCipherFactory.CipherProvider.JCE.getClassName());
        CryptoCipher decipher = Utils.getCipherInstance(transform, properties);
        decipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte [] decoded = new byte[32];
        decipher.doFinal(output, 0, updateBytes + input, decoded, 0);

        return output;

    }

    /**
     * Convierte un string a cadena de bytes
     * @param input String a convertir
     * @return Cadena de bytes que contiene los datos del input en forma de bytes
     */
    /*private static byte[] getUTF8Bytes(String input) {
        return input.getBytes(StandardCharsets.UTF_8);
    }*/
}
