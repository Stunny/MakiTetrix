package model;

import java.util.Random;

/**
 * Created by jorti on 14/05/2017.
 */
public class Partida {

    //Valor cuando no hay pieza, en la interfaz
    private final static int VOID_VALUE = -1;
    //Tamaño de la interfaz
    private final static int MAXY = 10;
    private final static int MAXX = 25;
    //Posibles movimientos
    private final static int MOVE_RIGHT = 0;
    private final static int MOVE_LEFT = 1;
    private final static int MOVE_DOWN = 2;
    private final static int ROTATE_RIGHT = 3;
    private final static int ROTATE_LEFT = 4;

    private int[][] interfaz;
    private Pieza actuallpiece;
    private Pieza nextpiece;
    private int floortime;

    private Timer master;

    //Constructor

    public Partida(){
        interfaz = new int[MAXX][MAXY];
        for (int i = 0; i < interfaz.length; i++){
            for (int j = 0; j < interfaz[i].length; j++){
                interfaz[i][j] = -1;
            }
        }
    }

    //Public Methods

    public void newGame (){
        floortime = 2;
        actuallpiece = new Pieza(generateRandom());
        nextpiece = new Pieza(generateRandom());
        updateInterfaz(actuallpiece);
    }

    public void rotateRight (){
        if (!(collision(actuallpiece, ROTATE_RIGHT))) {
            clear(actuallpiece);
            actuallpiece.rotateRight();
            updateInterfaz(actuallpiece);
            return;
        }
        updateInterfaz(actuallpiece);

    }

    public void rotateLeft () {
        if (!(collision(actuallpiece, ROTATE_LEFT))) {
            clear(actuallpiece);
            actuallpiece.rotateLeft();
            updateInterfaz(actuallpiece);
            return;
        }
        updateInterfaz(actuallpiece);
    }

    public void goRight (){
        if (!(collision(actuallpiece, MOVE_RIGHT))) {
            clear(actuallpiece);
            actuallpiece.setPosy(actuallpiece.getPosy() + 1);
            updateInterfaz(actuallpiece);
            return;
        }
        updateInterfaz(actuallpiece);
    }

    public void goLeft (){
        if (!(collision(actuallpiece, MOVE_LEFT))){
            clear(actuallpiece);
            actuallpiece.setPosy(actuallpiece.getPosy() - 1);
            updateInterfaz(actuallpiece);
            return;
        }
        updateInterfaz(actuallpiece);
    }

    public void goDown (){
        if (!(collision(actuallpiece, MOVE_DOWN))) {
            clear(actuallpiece);
            actuallpiece.setPosx(actuallpiece.getPosx() + 1);
            updateInterfaz(actuallpiece);
            return;
        }
        updateInterfaz(actuallpiece);
    }

    public boolean hadFloor (){
        if (collision(actuallpiece, MOVE_DOWN)){
            floortime--;
            return true;
        }
        return false;
    }

    public void checkLine (){
        int lines = 0;
        boolean haveline = false;
       for (int i = MAXX-1; i >= 0; i--){
           if (hadCompleteLine(i)){
               lines++;
           }
       }
       while (lines > 0){
           for (int i = MAXX-1; i >= 0; i--){
               if (hadCompleteLine(i)){
                   haveline = true;
               }
               if (haveline == true){
                   try {
                       interfaz[i] = interfaz[i-1];
                   } catch (ArrayIndexOutOfBoundsException aioobe){
                       interfaz[i] = new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
                   }
               } else {
                   interfaz[i] = interfaz[i];
               }
           }
           haveline = false;
           lines--;
       }

    }


    public void chargeNextPiece () {
        actuallpiece = nextpiece.clone();
        nextpiece = new Pieza(generateRandom());
        floortime = 2;
    }


    //Private Methods

    private void updateInterfaz (Pieza piece){
        switch (piece.getTipo()){
            case 0:
                interfaz[piece.getPosx()][piece.getPosy()] = piece.getTipo();
                interfaz[piece.getPosx()][piece.getPosy()+1] = piece.getTipo();
                interfaz[piece.getPosx()+1][piece.getPosy()] = piece.getTipo();
                interfaz[piece.getPosx()+1][piece.getPosy()+1] = piece.getTipo();
                break;
            case 1:
                switch(piece.getPosicion()){
                    case 0:
                        interfaz[piece.getPosx()-1][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()+1][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()+2][piece.getPosy()] = piece.getTipo();
                        break;
                    case 1:
                        interfaz[piece.getPosx()][piece.getPosy()-1] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()+1] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()+2] = piece.getTipo();
                        break;
                    case 2:
                        interfaz[piece.getPosx()-2][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()-1][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()+1][piece.getPosy()] = piece.getTipo();
                        break;
                    case 3:
                        interfaz[piece.getPosx()][piece.getPosy()-2] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()-1] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()+1] = piece.getTipo();
                        break;
                }
                break;
            case 2:
                switch(piece.getPosicion()){
                    case 0:
                        interfaz[piece.getPosx()-1][piece.getPosy()-1] = piece.getTipo();
                        interfaz[piece.getPosx()-1][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()+1] = piece.getTipo();
                        break;
                    case 1:
                        interfaz[piece.getPosx()][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()+1][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()+1] = piece.getTipo();
                        interfaz[piece.getPosx()-1][piece.getPosy()+1] = piece.getTipo();
                        break;
                    case 2:
                        interfaz[piece.getPosx()][piece.getPosy()-1] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()+1][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()+1][piece.getPosy()+1] = piece.getTipo();
                        break;
                    case 3:
                        interfaz[piece.getPosx()][piece.getPosy()-1] = piece.getTipo();
                        interfaz[piece.getPosx()+1][piece.getPosy()-1] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()-1][piece.getPosy()] = piece.getTipo();
                        break;
                }
                break;
            case 3:
                switch(piece.getPosicion()){
                    case 0:
                        interfaz[piece.getPosx()][piece.getPosy()-1] = piece.getTipo();
                        interfaz[piece.getPosx()-1][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()-1][piece.getPosy()+1] = piece.getTipo();
                        break;
                    case 1:
                        interfaz[piece.getPosx()-1][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()+1] = piece.getTipo();
                        interfaz[piece.getPosx()+1][piece.getPosy()+1] = piece.getTipo();
                        break;
                    case 2:
                        interfaz[piece.getPosx()+1][piece.getPosy()-1] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()+1][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()+1] = piece.getTipo();
                        break;
                    case 3:
                        interfaz[piece.getPosx()-1][piece.getPosy()-1] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()-1] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()+1][piece.getPosy()] = piece.getTipo();
                        break;
                }
                break;
            case 4:
                switch(piece.getPosicion()){
                    case 0:
                        interfaz[piece.getPosx()][piece.getPosy()-1] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()+1] = piece.getTipo();
                        interfaz[piece.getPosx()-1][piece.getPosy()+1] = piece.getTipo();
                        break;
                    case 1:
                        interfaz[piece.getPosx()-1][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()+1][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()+1][piece.getPosy()+1] = piece.getTipo();
                        break;
                    case 2:
                        interfaz[piece.getPosx()+1][piece.getPosy()-1] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()-1] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()+1] = piece.getTipo();
                        break;
                    case 3:
                        interfaz[piece.getPosx()-1][piece.getPosy()-1] = piece.getTipo();
                        interfaz[piece.getPosx()-1][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()+1][piece.getPosy()] = piece.getTipo();
                        break;
                }
                break;
            case 5:
                switch(piece.getPosicion()){
                    case 0:
                        interfaz[piece.getPosx()-1][piece.getPosy()-1] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()-1] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()+1] = piece.getTipo();
                        break;
                    case 1:
                        interfaz[piece.getPosx()-1][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()+1][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()-1][piece.getPosy()+1] = piece.getTipo();
                        break;
                    case 2:
                        interfaz[piece.getPosx()][piece.getPosy()-1] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()+1] = piece.getTipo();
                        interfaz[piece.getPosx()+1][piece.getPosy()+1] = piece.getTipo();
                        break;
                    case 3:
                        interfaz[piece.getPosx()-1][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()+1][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()+1][piece.getPosy()-1] = piece.getTipo();
                        break;
                }
                break;
            case 6:
                switch(piece.getPosicion()){
                    case 0:
                        interfaz[piece.getPosx()][piece.getPosy()-1] = piece.getTipo();
                        interfaz[piece.getPosx()-1][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()+1] = piece.getTipo();
                        break;
                    case 1:
                        interfaz[piece.getPosx()-1][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()+1][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()+1] = piece.getTipo();
                        break;
                    case 2:
                        interfaz[piece.getPosx()][piece.getPosy()-1] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()+1][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()+1] = piece.getTipo();
                        break;
                    case 3:
                        interfaz[piece.getPosx()][piece.getPosy()-1] = piece.getTipo();
                        interfaz[piece.getPosx()-1][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()][piece.getPosy()] = piece.getTipo();
                        interfaz[piece.getPosx()+1][piece.getPosy()] = piece.getTipo();
                        break;
                }
                break;

        }
    }

    private void clear(Pieza piece){
        switch (piece.getTipo()){
            case 0:
                interfaz[piece.getPosx()][piece.getPosy()] = VOID_VALUE;
                interfaz[piece.getPosx()][piece.getPosy()+1] = VOID_VALUE;
                interfaz[piece.getPosx()+1][piece.getPosy()] = VOID_VALUE;
                interfaz[piece.getPosx()+1][piece.getPosy()+1] = VOID_VALUE;
                break;
            case 1:
                switch(piece.getPosicion()){
                    case 0:
                        interfaz[piece.getPosx()-1][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()+1][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()+2][piece.getPosy()] = VOID_VALUE;
                        break;
                    case 1:
                        interfaz[piece.getPosx()][piece.getPosy()-1] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()+1] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()+2] = VOID_VALUE;
                        break;
                    case 2:
                        interfaz[piece.getPosx()-2][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()-1][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()+1][piece.getPosy()] = VOID_VALUE;
                        break;
                    case 3:
                        interfaz[piece.getPosx()][piece.getPosy()-2] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()-1] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()+1] = VOID_VALUE;
                        break;
                }
                break;
            case 2:
                switch(piece.getPosicion()){
                    case 0:
                        interfaz[piece.getPosx()-1][piece.getPosy()-1] = VOID_VALUE;
                        interfaz[piece.getPosx()-1][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()+1] = VOID_VALUE;
                        break;
                    case 1:
                        interfaz[piece.getPosx()][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()+1][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()+1] = VOID_VALUE;
                        interfaz[piece.getPosx()-1][piece.getPosy()+1] = VOID_VALUE;
                        break;
                    case 2:
                        interfaz[piece.getPosx()][piece.getPosy()-1] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()+1][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()+1][piece.getPosy()+1] = VOID_VALUE;
                        break;
                    case 3:
                        interfaz[piece.getPosx()][piece.getPosy()-1] = VOID_VALUE;
                        interfaz[piece.getPosx()+1][piece.getPosy()-1] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()-1][piece.getPosy()] = VOID_VALUE;
                        break;
                }
                break;
            case 3:
                switch(piece.getPosicion()){
                    case 0:
                        interfaz[piece.getPosx()][piece.getPosy()-1] = VOID_VALUE;
                        interfaz[piece.getPosx()-1][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()-1][piece.getPosy()+1] = VOID_VALUE;
                        break;
                    case 1:
                        interfaz[piece.getPosx()-1][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()+1] = VOID_VALUE;
                        interfaz[piece.getPosx()+1][piece.getPosy()+1] = VOID_VALUE;
                        break;
                    case 2:
                        interfaz[piece.getPosx()+1][piece.getPosy()-1] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()+1][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()+1] = VOID_VALUE;
                        break;
                    case 3:
                        interfaz[piece.getPosx()-1][piece.getPosy()-1] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()-1] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()+1][piece.getPosy()] = VOID_VALUE;
                        break;
                }
                break;
            case 4:
                switch(piece.getPosicion()){
                    case 0:
                        interfaz[piece.getPosx()][piece.getPosy()-1] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()+1] = VOID_VALUE;
                        interfaz[piece.getPosx()-1][piece.getPosy()+1] = VOID_VALUE;
                        break;
                    case 1:
                        interfaz[piece.getPosx()-1][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()+1][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()+1][piece.getPosy()+1] = VOID_VALUE;
                        break;
                    case 2:
                        interfaz[piece.getPosx()+1][piece.getPosy()-1] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()-1] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()+1] = VOID_VALUE;
                        break;
                    case 3:
                        interfaz[piece.getPosx()-1][piece.getPosy()-1] = VOID_VALUE;
                        interfaz[piece.getPosx()-1][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()+1][piece.getPosy()] = VOID_VALUE;
                        break;
                }
                break;
            case 5:
                switch(piece.getPosicion()){
                    case 0:
                        interfaz[piece.getPosx()-1][piece.getPosy()-1] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()-1] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()+1] = VOID_VALUE;
                        break;
                    case 1:
                        interfaz[piece.getPosx()-1][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()+1][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()-1][piece.getPosy()+1] = VOID_VALUE;
                        break;
                    case 2:
                        interfaz[piece.getPosx()][piece.getPosy()-1] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()+1] = VOID_VALUE;
                        interfaz[piece.getPosx()+1][piece.getPosy()+1] = VOID_VALUE;
                        break;
                    case 3:
                        interfaz[piece.getPosx()-1][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()+1][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()+1][piece.getPosy()-1] = VOID_VALUE;
                        break;
                }
                break;
            case 6:
                switch(piece.getPosicion()){
                    case 0:
                        interfaz[piece.getPosx()][piece.getPosy()-1] = VOID_VALUE;
                        interfaz[piece.getPosx()-1][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()+1] = VOID_VALUE;
                        break;
                    case 1:
                        interfaz[piece.getPosx()-1][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()+1][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()+1] = VOID_VALUE;
                        break;
                    case 2:
                        interfaz[piece.getPosx()][piece.getPosy()-1] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()+1][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()+1] = VOID_VALUE;
                        break;
                    case 3:
                        interfaz[piece.getPosx()][piece.getPosy()-1] = VOID_VALUE;
                        interfaz[piece.getPosx()-1][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()][piece.getPosy()] = VOID_VALUE;
                        interfaz[piece.getPosx()+1][piece.getPosy()] = VOID_VALUE;
                        break;
                }
                break;

        }
    }

    private boolean collision (Pieza piece, int move){
        switch (move){
            case MOVE_RIGHT:
                return checkRight(piece);
            case MOVE_LEFT:
                return checkLeft(piece);
            case MOVE_DOWN:
                return checkDown(piece);
            case ROTATE_LEFT:
                return checkRotateLeft(piece);
            case ROTATE_RIGHT:
                return checkRotateRight(piece);
        }
        return false;
    }

    private boolean checkRight (Pieza piece){
        switch (piece.getTipo()){
            case 0:
                try {
                    if (interfaz[piece.getPosx()][piece.getPosy() + 2] != VOID_VALUE ||
                            interfaz[piece.getPosx() + 1][piece.getPosy() + 2] != VOID_VALUE) {
                        return true;
                    }
                } catch (ArrayIndexOutOfBoundsException aioobe){
                    return true;
                }
                break;
            case 1:
                switch (piece.getPosicion()){
                    case 0:
                        try {
                            if (interfaz[piece.getPosx()-1][piece.getPosy() + 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx()][piece.getPosy() + 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx()+1][piece.getPosy() + 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx()+2][piece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 1:
                        try {
                            if (interfaz[piece.getPosx()][piece.getPosy() + 3] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 2:
                        try {
                            if (interfaz[piece.getPosx()-2][piece.getPosy() + 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx()-1][piece.getPosy() + 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx()][piece.getPosy() + 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx()+1][piece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 3:
                        try {
                            if (interfaz[piece.getPosx()][piece.getPosy() + 2] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                }
                break;
            case 2:
                switch (piece.getPosicion()){
                    case 0:
                        try {
                            if (interfaz[piece.getPosx()][piece.getPosy() + 2] != VOID_VALUE ||
                                    interfaz[piece.getPosx() - 1][piece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 1:
                        try {
                            if (interfaz[piece.getPosx()][piece.getPosy() + 2] != VOID_VALUE ||
                                    interfaz[piece.getPosx() - 1][piece.getPosy() + 2] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 2:
                        try {
                            if (interfaz[piece.getPosx()][piece.getPosy() + 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() + 2] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 3:
                        try {
                            if (interfaz[piece.getPosx()][piece.getPosy() + 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() - 1][piece.getPosy() + 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy()] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                }
                break;
            case 3:
                switch (piece.getPosicion()){
                    case 0:
                        try {
                            if (interfaz[piece.getPosx() -1 ][piece.getPosy() + 2] != VOID_VALUE ||
                                    interfaz[piece.getPosx()][piece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 1:
                        try {
                            if (interfaz[piece.getPosx()][piece.getPosy() + 2] != VOID_VALUE ||
                                    interfaz[piece.getPosx() - 1][piece.getPosy() + 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() + 2] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 2:
                        try {
                            if (interfaz[piece.getPosx()][piece.getPosy() + 2] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 3:
                        try {
                            if (interfaz[piece.getPosx()][piece.getPosy() + 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() - 1][piece.getPosy()] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                }
                break;
            case 4:
                switch (piece.getPosicion()){
                    case 0:
                        try {
                            if (interfaz[piece.getPosx() -1 ][piece.getPosy() + 2] != VOID_VALUE ||
                                    interfaz[piece.getPosx()][piece.getPosy() + 2] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 1:
                        try {
                            if (interfaz[piece.getPosx()-1][piece.getPosy() + 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx()][piece.getPosy() + 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() + 2] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 2:
                        try {
                            if (interfaz[piece.getPosx()][piece.getPosy() + 2] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy()] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 3:
                        try {
                            if (interfaz[piece.getPosx()][piece.getPosy() + 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() - 1][piece.getPosy()+1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                }
                break;
            case 5:
                switch (piece.getPosicion()){
                    case 0:
                        try {
                            if (interfaz[piece.getPosx() - 1][piece.getPosy()] != VOID_VALUE ||
                                    interfaz[piece.getPosx()][piece.getPosy() + 2] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 1:
                        try {
                            if (interfaz[piece.getPosx()-1][piece.getPosy() + 2] != VOID_VALUE ||
                                    interfaz[piece.getPosx()][piece.getPosy() + 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 2:
                        try {
                            if (interfaz[piece.getPosx()][piece.getPosy() + 2] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() + 2] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 3:
                        try {
                            if (interfaz[piece.getPosx()][piece.getPosy() + 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() - 1][piece.getPosy()+1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                }
                break;
            case 6:
                switch (piece.getPosicion()){
                    case 0:
                        try {
                            if (interfaz[piece.getPosx() -1 ][piece.getPosy() + 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx()][piece.getPosy() + 2] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 1:
                        try {
                            if (interfaz[piece.getPosx()-1][piece.getPosy() + 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx()][piece.getPosy() + 2] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 2:
                        try {
                            if (interfaz[piece.getPosx()][piece.getPosy() + 2] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 3:
                        try {
                            if (interfaz[piece.getPosx()][piece.getPosy() + 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() - 1][piece.getPosy()+1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                }
                break;
        }
        return false;
    }

    private boolean checkLeft (Pieza piece) {
        switch (piece.getTipo()) {
            case 0:
                try {
                    if (interfaz[piece.getPosx()][piece.getPosy() - 1] != VOID_VALUE ||
                            interfaz[piece.getPosx() + 1][piece.getPosy() - 1] != VOID_VALUE) {
                        return true;
                    }
                } catch (ArrayIndexOutOfBoundsException aioobe){
                    return true;
                }
                break;
            case 1:
                switch (piece.getPosicion()) {
                    case 0:
                        try {
                            if (interfaz[piece.getPosx()-1][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx()][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx()+1][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx()+2][piece.getPosy() - 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                    case 1:
                        try {
                            if (interfaz[piece.getPosx()][piece.getPosy() - 2] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                    case 2:
                        try {
                            if (interfaz[piece.getPosx()-2][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx()-1][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx()][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx()+1][piece.getPosy() - 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                    case 3:
                        try {
                            if (interfaz[piece.getPosx()][piece.getPosy() - 3] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                }
                break;
            case 2:
                switch (piece.getPosicion()) {
                    case 0:
                        try {
                            if (interfaz[piece.getPosx()][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() - 1][piece.getPosy() - 2] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                    case 1:
                        try {
                            if (interfaz[piece.getPosx()][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() - 1][piece.getPosy()] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() - 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                    case 2:
                        try {
                            if (interfaz[piece.getPosx()][piece.getPosy() - 2] != VOID_VALUE ||
                                    interfaz[piece.getPosx() - 1][piece.getPosy() - 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                    case 3:
                        try {
                            if (interfaz[piece.getPosx()][piece.getPosy() - 2] != VOID_VALUE ||
                                    interfaz[piece.getPosx() - 1][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() - 2] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                }
                break;
            case 3:
                switch (piece.getPosicion()) {
                    case 0:
                        try {
                            if (interfaz[piece.getPosx() - 1][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx()][piece.getPosy() - 2] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                    case 1:
                        try {
                            if (interfaz[piece.getPosx() - 1][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx()][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy()] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                    case 2:
                        try {
                            if (interfaz[piece.getPosx()][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() - 2] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                    case 3:
                        try {
                            if (interfaz[piece.getPosx() - 1][piece.getPosy() - 2] != VOID_VALUE ||
                                    interfaz[piece.getPosx()][piece.getPosy() - 2] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() - 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                }
                break;
            case 4:
                switch (piece.getPosicion()) {
                    case 0:
                        try {
                            if (interfaz[piece.getPosx() - 1][piece.getPosy()] != VOID_VALUE ||
                                    interfaz[piece.getPosx()][piece.getPosy() - 2] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                    case 1:
                        try {
                            if (interfaz[piece.getPosx() - 1][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx()][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() - 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                    case 2:
                        try {
                            if (interfaz[piece.getPosx()][piece.getPosy() - 2] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() - 2] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                    case 3:
                        try {
                            if (interfaz[piece.getPosx() - 1][piece.getPosy() - 2] != VOID_VALUE ||
                                    interfaz[piece.getPosx()][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() - 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                }
                break;
            case 5:
                switch (piece.getPosicion()) {
                    case 0:
                        try {
                            if (interfaz[piece.getPosx() - 1][piece.getPosy() - 2] != VOID_VALUE ||
                                    interfaz[piece.getPosx()][piece.getPosy() - 2] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                    case 1:
                        try {
                            if (interfaz[piece.getPosx() - 1][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx()][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() - 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                    case 2:
                        try {
                            if (interfaz[piece.getPosx()][piece.getPosy() - 2] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy()] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                    case 3:
                        try {
                            if (interfaz[piece.getPosx() - 1][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx()][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() - 2] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                }
                break;
            case 6:
                switch (piece.getPosicion()) {
                    case 0:
                        try {
                            if (interfaz[piece.getPosx() - 1][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx()][piece.getPosy() - 2] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                    case 1:
                        try {
                            if (interfaz[piece.getPosx() - 1][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx()][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() - 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                    case 2:
                        try {
                            if (interfaz[piece.getPosx()][piece.getPosy() - 2] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() - 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                    case 3:
                        try {
                            if (interfaz[piece.getPosx() - 1][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx()][piece.getPosy() - 2] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() - 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                }
                break;
        }
        return false;
    }

    private boolean checkDown (Pieza piece) {
        switch (piece.getTipo()) {
            case 0:
                try {
                    if (interfaz[piece.getPosx() + 2][piece.getPosy()] != VOID_VALUE ||
                            interfaz[piece.getPosx() + 2][piece.getPosy() + 1] != VOID_VALUE) {
                        return true;
                    }
                } catch (ArrayIndexOutOfBoundsException aioobe) {
                    return true;
                }
                break;
            case 1:
                switch (piece.getPosicion()) {
                    case 0:
                        try {
                            if (interfaz[piece.getPosx() + 3][piece.getPosy()] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                    case 1:
                        try {
                            if (interfaz[piece.getPosx() + 1][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy()] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() + 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() + 2] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                    case 2:
                        try {
                            if (interfaz[piece.getPosx() + 2][piece.getPosy()] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                    case 3:
                        try {
                            if (interfaz[piece.getPosx() + 1][piece.getPosy() - 2] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy()] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                }
                break;
            case 2:
                switch (piece.getPosicion()) {
                    case 0:
                        try {
                            if (interfaz[piece.getPosx()][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy()] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                    case 1:
                        try {
                            if (interfaz[piece.getPosx() + 2][piece.getPosy()] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                    case 2:
                        try {
                            if (interfaz[piece.getPosx() + 1][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 2][piece.getPosy()] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 2][piece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                    case 3:
                        try {
                            if (interfaz[piece.getPosx() + 2][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy()] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                }
                break;
            case 3:
                switch (piece.getPosicion()) {
                    case 0:
                        try {
                            if (interfaz[piece.getPosx() + 1][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy()] != VOID_VALUE ||
                                    interfaz[piece.getPosx()][piece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                    case 1:
                        try {
                            if (interfaz[piece.getPosx() + 1][piece.getPosy()] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 2][piece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                    case 2:
                        try {
                            if (interfaz[piece.getPosx() + 2][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 2][piece.getPosy()] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                    case 3:
                        try {
                            if (interfaz[piece.getPosx() + 1][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 2][piece.getPosy()] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                }
                break;
            case 4:
                switch (piece.getPosicion()) {
                    case 0:
                        try {
                            if (interfaz[piece.getPosx() + 1][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy()] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                    case 1:
                        try {
                            if (interfaz[piece.getPosx() + 2][piece.getPosy()] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 2][piece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                    case 2:
                        try {
                            if (interfaz[piece.getPosx() + 2][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy()] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                    case 3:
                        try {
                            if (interfaz[piece.getPosx()][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 2][piece.getPosy()] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                }
                break;
            case 5:
                switch (piece.getPosicion()) {
                    case 0:
                        try {
                            if (interfaz[piece.getPosx() + 1][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy()] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                    case 1:
                        try {
                            if (interfaz[piece.getPosx() + 2][piece.getPosy()] != VOID_VALUE ||
                                    interfaz[piece.getPosx()][piece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                    case 2:
                        try {
                            if (interfaz[piece.getPosx() + 1][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy()] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 2][piece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                    case 3:
                        try {
                            if (interfaz[piece.getPosx() + 2][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 2][piece.getPosy()] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                }
                break;
            case 6:
                switch (piece.getPosicion()) {
                    case 0:
                        try {
                            if (interfaz[piece.getPosx() + 1][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy()] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                    case 1:
                        try {
                            if (interfaz[piece.getPosx() + 2][piece.getPosy()] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                    case 2:
                        try {
                            if (interfaz[piece.getPosx() + 1][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 2][piece.getPosy()] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 1][piece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                    case 3:
                        try {
                            if (interfaz[piece.getPosx() + 1][piece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[piece.getPosx() + 2][piece.getPosy()] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            return true;
                        }
                        break;
                }
                break;
        }
        return false;
    }

    private boolean checkRotateRight (Pieza piece){
        Pieza rotatedpiece = piece.clone();
        rotatedpiece.rotateRight();
        switch (rotatedpiece.getTipo()) {
            case 1:
                switch (rotatedpiece.getPosicion()){
                    case 0:
                        try {
                            if (interfaz[rotatedpiece.getPosx() - 1][rotatedpiece.getPosy()] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() + 1][rotatedpiece.getPosy()] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() + 2][rotatedpiece.getPosy()] != VOID_VALUE ) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 1:
                        try {
                            if (interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() + 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() + 2] != VOID_VALUE ) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 2:
                        try {
                            if (interfaz[rotatedpiece.getPosx() - 2][rotatedpiece.getPosy()] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() - 1][rotatedpiece.getPosy()] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() + 1 ][rotatedpiece.getPosy()] != VOID_VALUE ) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 3:
                        try {
                            if (interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() - 2] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() + 1] != VOID_VALUE ) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                }
                break;
            case 2:
                switch (rotatedpiece.getPosicion()){
                    case 0:
                        try {
                            if (interfaz[rotatedpiece.getPosx() - 1][rotatedpiece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 1:
                        try {
                            if (interfaz[rotatedpiece.getPosx() - 1][rotatedpiece.getPosy() + 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() + 1][rotatedpiece.getPosy()] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 2:
                        try {
                            if (interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() + 1][rotatedpiece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 3:
                        try {
                            if (interfaz[rotatedpiece.getPosx() - 1][rotatedpiece.getPosy()] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() + 1][rotatedpiece.getPosy() - 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                }
                break;
            case 3:
                switch (rotatedpiece.getPosicion()){
                    case 0:
                        try {
                            if (interfaz[rotatedpiece.getPosx() - 1][rotatedpiece.getPosy()] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() - 1][rotatedpiece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 1:
                        try {
                            if (interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() + 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() + 1][rotatedpiece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 2:
                        try {
                            if (interfaz[rotatedpiece.getPosx() + 1][rotatedpiece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() + 1][rotatedpiece.getPosy()] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 3:
                        try {
                            if (interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() - 1][rotatedpiece.getPosy() - 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                }
                break;
            case 4:
                switch (rotatedpiece.getPosicion()){
                    case 0:
                        try {
                            if (interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() + 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() - 1][rotatedpiece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 1:
                        try {
                            if (interfaz[rotatedpiece.getPosx() - 1][rotatedpiece.getPosy()] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() + 1][rotatedpiece.getPosy()] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() + 1][rotatedpiece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 2:
                        try {
                            if (interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() + 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() + 1][rotatedpiece.getPosy() - 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 3:
                        try {
                            if (interfaz[rotatedpiece.getPosx() - 1][rotatedpiece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() - 1][rotatedpiece.getPosy()] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() + 1][rotatedpiece.getPosy()] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                }
                break;
            case 5:
                switch (rotatedpiece.getPosicion()){
                    case 0:
                        try {
                            if (interfaz[rotatedpiece.getPosx() - 1][rotatedpiece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 1:
                        try {
                            if (interfaz[rotatedpiece.getPosx() - 1][rotatedpiece.getPosy()] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() - 1][rotatedpiece.getPosy() + 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() + 1][rotatedpiece.getPosy()] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 2:
                        try {
                            if (interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() + 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() + 1][rotatedpiece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 3:
                        try {
                            if (interfaz[rotatedpiece.getPosx() + 1][rotatedpiece.getPosy()] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() - 1][rotatedpiece.getPosy()] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() + 1][rotatedpiece.getPosy() - 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                }
                break;
            case 6:
                switch (rotatedpiece.getPosicion()){
                    case 0:
                        try {
                            if (interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 1:
                        try {
                            if (interfaz[rotatedpiece.getPosx() + 1][rotatedpiece.getPosy()] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 2:
                        try {
                            if (interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() - 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 3:
                        try {
                            if (interfaz[rotatedpiece.getPosx() - 1][rotatedpiece.getPosy()] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                }
                break;
        }
        return false;
    }

    private boolean checkRotateLeft (Pieza piece){
        Pieza rotatedpiece = piece.clone();
        rotatedpiece.rotateLeft();
        switch (rotatedpiece.getTipo()) {
            case 1:
                switch (rotatedpiece.getPosicion()){
                    case 0:
                        try {
                            if (interfaz[rotatedpiece.getPosx() - 1][rotatedpiece.getPosy()] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() + 1][rotatedpiece.getPosy()] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() + 2][rotatedpiece.getPosy()] != VOID_VALUE ) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 1:
                        try {
                            if (interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() + 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() + 2] != VOID_VALUE ) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 2:
                        try {
                            if (interfaz[rotatedpiece.getPosx() - 2][rotatedpiece.getPosy()] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() - 1][rotatedpiece.getPosy()] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() + 1 ][rotatedpiece.getPosy()] != VOID_VALUE ) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 3:
                        try {
                            if (interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() - 2] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() + 1] != VOID_VALUE ) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                }
                break;
            case 2:
                switch (rotatedpiece.getPosicion()){
                    case 0:
                        try {
                            if (interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() - 1][rotatedpiece.getPosy() - 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 1:
                        try {
                            if (interfaz[rotatedpiece.getPosx() - 1][rotatedpiece.getPosy() + 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 2:
                        try {
                            if (interfaz[rotatedpiece.getPosx() + 1][rotatedpiece.getPosy()] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() + 1][rotatedpiece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 3:
                        try {
                            if (interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() + 1][rotatedpiece.getPosy() - 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                }
                break;
            case 3:
                switch (rotatedpiece.getPosicion()){
                    case 0:
                        try {
                            if (interfaz[rotatedpiece.getPosx() - 1][rotatedpiece.getPosy() + 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() - 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 1:
                        try {
                            if (interfaz[rotatedpiece.getPosx() - 1][rotatedpiece.getPosy()] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() + 1][rotatedpiece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 2:
                        try {
                            if (interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() + 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() + 1][rotatedpiece.getPosy() - 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 3:
                        try {
                            if (interfaz[rotatedpiece.getPosx() + 1][rotatedpiece.getPosy()] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() - 1][rotatedpiece.getPosy() - 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                }
                break;
            case 4:
                switch (rotatedpiece.getPosicion()){
                    case 0:
                        try {
                            if (interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() + 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() - 1][rotatedpiece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 1:
                        try {
                            if (interfaz[rotatedpiece.getPosx() - 1][rotatedpiece.getPosy()] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() + 1][rotatedpiece.getPosy()] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() + 1][rotatedpiece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 2:
                        try {
                            if (interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() + 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() + 1][rotatedpiece.getPosy() - 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 3:
                        try {
                            if (interfaz[rotatedpiece.getPosx() - 1][rotatedpiece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() - 1][rotatedpiece.getPosy()] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() + 1][rotatedpiece.getPosy()] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                }
                break;
            case 5:
                switch (rotatedpiece.getPosicion()){
                    case 0:
                        try {
                            if (interfaz[rotatedpiece.getPosx() - 1][rotatedpiece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 1:
                        try {
                            if (interfaz[rotatedpiece.getPosx() - 1][rotatedpiece.getPosy()] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() - 1][rotatedpiece.getPosy() + 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() + 1][rotatedpiece.getPosy()] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 2:
                        try {
                            if (interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() + 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() + 1][rotatedpiece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 3:
                        try {
                            if (interfaz[rotatedpiece.getPosx() +1][rotatedpiece.getPosy() - 1] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() - 1][rotatedpiece.getPosy()] != VOID_VALUE ||
                                    interfaz[rotatedpiece.getPosx() + 1][rotatedpiece.getPosy()] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                }
                break;
            case 6:
                switch (rotatedpiece.getPosicion()){
                    case 0:
                        try {
                            if (interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() - 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 1:
                        try {
                            if (interfaz[rotatedpiece.getPosx() - 1][rotatedpiece.getPosy()] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 2:
                        try {
                            if (interfaz[rotatedpiece.getPosx()][rotatedpiece.getPosy() + 1] != VOID_VALUE) {
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                    case 3:
                        if (interfaz[rotatedpiece.getPosx() + 1][rotatedpiece.getPosy()] != VOID_VALUE) {
                            return true;
                        }
                        try {

                        } catch (ArrayIndexOutOfBoundsException aioobe){
                            return true;
                        }
                        break;
                }
                break;
        }
        return false;
    }

    private boolean hadCompleteLine (int linea){
        for (int i = 0; i < MAXY; i++){
            if (interfaz[linea][i] == VOID_VALUE){
                return false;
            }
        }
        return true;
    }


    private int generateRandom (){
        return new Random().nextInt(7);
    }

    //Getters && Setters

    public int[][] getInterfaz (){
        return interfaz;
    }

    public int getFloortime () {return floortime;}
    public Pieza getNextpiece () {return nextpiece;}

    public void setMasterTimer(Timer t){
        master = t;
    }
    public void reduceVelocidad(){
        master.setVelocidad(1000);
    }
    public void subeVelocidad(){
        master.setVelocidad(300);
    }
}
