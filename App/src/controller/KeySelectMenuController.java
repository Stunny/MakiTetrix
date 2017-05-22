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

    private boolean checkLenght(String derecha, String izquierda, String abajo, String rotarDerecha, String rotarIzquierda, String pause) {
        if (derecha.length() == 1 && izquierda.length()== 1 && abajo.length() == 1 && rotarDerecha.length() == 1
            && rotarIzquierda.length() == 1 && pause.length() == 1){
            return true;
        }else{
            return false;
        }
    }

    private int getAscii(String tecla) {
        if (tecla.codePointAt(0) < 65 || tecla.codePointAt(0) > 90){
            return tecla.toUpperCase().codePointAt(0);
        }else{
            return tecla.codePointAt(0);
        }
    }

    private boolean checkValues(int asciiDerecha, int asciiIzquierda, int asciiAbajo, int asciiRotarDerecha, int asciiRotarIzquierda, int asciiPause) {
        if (checkKey(asciiDerecha) && checkKey(asciiIzquierda) && checkKey(asciiAbajo) && checkKey(asciiRotarDerecha)
                && checkKey(asciiRotarIzquierda) && checkKey(asciiPause)){
            return true;
        }else{
            return false;
        }
    }

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
