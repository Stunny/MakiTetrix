package controller;

import Vista.GameView;
import Vista.KeySelectMenu;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import model.Partida;
import model.User;
import model.utils.Encrypter;
import network.Conexio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by natal on 20/5/2017.
 */
public class KeySelectMenuController implements ActionListener {
    private KeySelectMenu view;
    private Conexio conexio;
    private User u;

    private String derecha;
    private String izquierda;
    private String abajo;
    private String rotarDerecha;
    private String rotarIzquierda;
    private String pause;


    public KeySelectMenuController(Conexio c, KeySelectMenu view, User currentUser) {
        //TODO: actulizar al tener nuevas letras, cambiar view a los nuevos valores.
        /*
        Falta añadir controlador game para coger los valores nuevos.
         */
        u = currentUser;
        this.view = view;
        this.conexio = c;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getActionCommand().equals("save")){
            derecha = view.getTextDerecha().getText();
            izquierda = view.getTextIzquierda().getText();
            abajo = view.getTextAbajo().getText();
            rotarDerecha = view.getTextRotarDerecha().getText();
            rotarIzquierda = view.getTextRotarIzquierda().getText();
            pause = view.getTextPause().getText();

            if (checkLenght(derecha, izquierda, abajo, rotarDerecha, rotarIzquierda, pause)){
                int asciiDerecha = getAscii(derecha);
                int asciiIzquierda = getAscii(izquierda);
                int asciiAbajo = getAscii(abajo);
                int asciiRotarDerecha = getAscii(rotarDerecha);
                int asciiRotarIzquierda = getAscii(rotarIzquierda);
                int asciiPause = getAscii(pause);

                if (checkValues(asciiDerecha, asciiIzquierda, asciiAbajo, asciiRotarDerecha, asciiRotarIzquierda, asciiPause)) {
                    derecha = derecha.toUpperCase();
                    izquierda = izquierda.toUpperCase();
                    abajo = abajo.toUpperCase();
                    rotarDerecha = rotarDerecha.toUpperCase();
                    rotarIzquierda = rotarIzquierda.toUpperCase();
                    pause = pause.toUpperCase();

                    if (checkRepetitions()) {
                        view.setVisible(false);


                        conexio.setTeclesUser(u.getUserName(),asciiDerecha, asciiIzquierda, asciiAbajo, asciiRotarDerecha, asciiRotarIzquierda, asciiPause);
                    }else{
                        view.showKeySelectError("No se puede asignar una misma tecla para dos acciones!");
                    }
                }
            }else{
                view.showKeySelectError("Los movimientos deben tener una sola tecla asociada!");
            }
        }
    }


    /**
     * Comprueba que no haya ninguna tecla asignada a dos acciones distintas
     * @return True si ho hay ninguna tecla asignada a dos acciones distintas
     */
    private boolean checkRepetitions() {
        return checkItemRepetition(derecha) && checkItemRepetition(izquierda) && checkItemRepetition(abajo)
                && checkItemRepetition(rotarDerecha) && checkItemRepetition(rotarIzquierda) && checkItemRepetition(pause);

    }

    /**
     * Comprueba que una sola tecla no se halle repetida en mas de un solo campo a la vez
     * @param item tecla a comprobar que no se halle repetida
     * @return True si la tecla no esta repetida en mas de un campo
     */
    private boolean checkItemRepetition(String item) {
        int result = getResult(item.equals(derecha), item.equals(izquierda), item.equals(abajo) ,
                item.equals(rotarDerecha), item.equals(rotarIzquierda), item.equals(pause));

        return result == 1;
    }

    /**
     * Cuenta y devuelve el numero de True que existen en los parametros
     * @param vars Todas aquellos parametros booleanos a contabilizar
     * @return El numero de True que existen en los parametros
     */
    private int getResult(boolean... vars) {
        int count = 0;
        for (boolean var : vars) {
            count += (var ? 1 : 0);
        }
        return count;
    }

    /**
     * Identifica si todos los valores de las tesclas que ha introducido el usuario contienen un solo elemento.
     * @param derecha Tecla que el usuario ha asignado para mover a la derecha
     * @param izquierda Tecla que el usuario ha asignado para mover a la izquierda
     * @param abajo Tecla que el usuario ha asignado para mover abajo
     * @param rotarDerecha Tecla que el usuario ha asignado para rotar a la derecha
     * @param rotarIzquierda Tecla que el usuario ha asignado para rotar a la izquierda
     * @param pause Tecla que el usuario ha asignado para pausar
     * @return retorna cierto si el tamaño de todos los campos es igual a 1, de lo contrario retorna falso
     */
    private boolean checkLenght(String derecha, String izquierda, String abajo, String rotarDerecha, String rotarIzquierda, String pause) {
        if (derecha.length() == 1 && izquierda.length()== 1 && abajo.length() == 1 && rotarDerecha.length() == 1
            && rotarIzquierda.length() == 1 && pause.length() == 1){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Transforma el char de la tecla a su valor en ascii (integer).
     * @param tecla Parametro a convertir a entero
     * @return devuelve el valor entero del parametro
     */
    private int getAscii(String tecla) {
        if (tecla.codePointAt(0) < 65 || tecla.codePointAt(0) > 90){
            return tecla.toUpperCase().codePointAt(0);
        }else{
            return tecla.codePointAt(0);
        }
    }

    /**
     * Comprueva que los valores introducidos por el usuario son validos
     * @param asciiDerecha Valor ascii de la tecla asiganada para moverse a la derecha
     * @param asciiIzquierda Valor ascii de la tecla asiganada para moverse a la izquierda
     * @param asciiAbajo Valor ascii de la tecla asiganada para moverse anajo
     * @param asciiRotarDerecha Valor ascii de la tecla asiganada para rotar a la derecha
     * @param asciiRotarIzquierda Valor ascii de la tecla asiganada para rotar a la izquierda
     * @param asciiPause Valor ascii de la tecla asiganada para pausar
     * @return Retorna cierto si todas las teclas devuelven cierto en la funcion checkKey
     */
    private boolean checkValues(int asciiDerecha, int asciiIzquierda, int asciiAbajo, int asciiRotarDerecha, int asciiRotarIzquierda, int asciiPause) {
        if (checkKey(asciiDerecha) && checkKey(asciiIzquierda) && checkKey(asciiAbajo) && checkKey(asciiRotarDerecha)
                && checkKey(asciiRotarIzquierda) && checkKey(asciiPause)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Comprueva si la tecla se encuentra entre a A y la Z
     * @param key valor ascii de una tecla
     * @return devuelve cierto si la tecla se encuentra entre a A y la Z
     */
    private boolean checkKey(int key){
        //si la letra es minuscula la convertimos a mayusucla
        if (key < 65 || key > 90){
            view.showKeySelectError("EL valor " + Character.toString((char)key) + " debe estar comprendido entre la A y la Z.");
            return false;
        }else{
            return true;
        }
    }
}
