package controller;

import Vista.GameView;
import Vista.KeySelectMenu;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import model.Partida;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by natal on 20/5/2017.
 */
public class KeySelectMenuController implements ActionListener {
    private KeySelectMenu view;

    public KeySelectMenuController(KeySelectMenu view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getActionCommand().equals("save")){
            String derecha = view.getTextDerecha().getText();
            String izquierda = view.getTextIzquierda().getText();
            String abajo = view.getTextAbajo().getText();
            String rotarDerecha = view.getTextRotarDerecha().getText();
            String rotarIzquierda = view.getTextRotarIzquierda().getText();
            String pause = view.getTextPause().getText();

            if (checkLenght(derecha, izquierda, abajo, rotarDerecha, rotarIzquierda, pause)){
                int asciiDerecha = getAscii(derecha);
                int asciiIzquierda = getAscii(izquierda);
                int asciiAbajo = getAscii(abajo);
                int asciiRotarDerecha = getAscii(rotarDerecha);
                int asciiRotarIzquierda = getAscii(rotarIzquierda);
                int asciiPause = getAscii(pause);

                if (checkValues(asciiDerecha, asciiIzquierda, asciiAbajo, asciiRotarDerecha, asciiRotarIzquierda, asciiPause)){
                    view.setVisible(false);
                    GameController.setTeclas(asciiDerecha, asciiIzquierda, asciiAbajo, asciiRotarIzquierda, asciiIzquierda, asciiPause);
                }
            }else{
                view.showKeySelectError("Los movimientos solo pueden tener una tecla asociada!");
            }
        }
    }

    /**
     * Identifica si todos los valores de las tesclas que ha introducido el usuario contienen un solo elemento.
     *
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
     *
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
     *
     *
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
     *
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
