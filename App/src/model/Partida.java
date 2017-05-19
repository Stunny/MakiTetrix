package model;

import java.io.File;
import java.util.Queue;
import java.util.Random;

/**
 * Created by jorti on 14/05/2017.
 *
 * Esta clase gestiona toda la información de la partida.
 * Por lo que se encarga de printar las pieces, comprovar
 * las colisiones, la puntuación, etc.
 *
 * @see Pieza
 * @see PlayGame
 * @see controller.GameController
 */
public class Partida {

    //Valor cuando no hay pieza, en la interfaz
    private final static int VOID_VALUE = -1;
    //Tamaño de la interfaz
    private final static int MAXY = 10;
    private final static int MAXX = 25;
    //Posibles movimientos
    public final static int MOVE_RIGHT = 0;
    public final static int MOVE_LEFT = 1;
    public final static int MOVE_DOWN = 2;
    public final static int ROTATE_RIGHT = 3;
    public final static int ROTATE_LEFT = 4;
    public final static int END = 5;

    private int[][] interfaz;
    private Pieza actualpiece;
    private Pieza nextpiece;
    private int floortime;
    private boolean end;
    private int level;
    private int lineas;
    private int points;
    private PlayGame master;
    private Queue<Move> savegame;

    //Constructor

    public Partida(){
        interfaz = new int[MAXX][MAXY];
        for (int i = 0; i < interfaz.length; i++){
            for (int j = 0; j < interfaz[i].length; j++){
                interfaz[i][j] = -1;
            }
        }
        end = false;
        level = 1;
        lineas = 0;
        floortime = 2;
    }

    public Partida (Queue<Move> savedgame){
        this.savegame = savedgame;
        interfaz = new int[MAXX][MAXY];
        for (int i = 0; i < interfaz.length; i++){
            for (int j = 0; j < interfaz[i].length; j++){
                interfaz[i][j] = -1;
            }
        }
        end = false;
        level = 1;
        lineas = 0;
        floortime = 2;
    }

    //Public Methods

    /**
     * Genera una nueva partida, asigando los valor de las nuevas
     * piezas.
     * @see Pieza
     * @see #generateRandom()
     */
    public void newGame (){
        actualpiece = new Pieza(generateRandom());
        savegame.add(new Move(actualpiece));
        nextpiece = new Pieza(generateRandom());
        savegame.add(new Move (nextpiece));
        end = false;
        updateInterfaz(actualpiece);
    }

    public void newGame (Pieza actualpiece, Pieza nextpiece){
        this.actualpiece = actualpiece.clone();
        this.nextpiece = nextpiece.clone();
        end = false;
        updateInterfaz(actualpiece);
    }

    /**
     * Permite girar una pieza hacia la derecha.
     *
     * @see Pieza
     * @see #collision(Pieza, int)
     * @see #clear(Pieza)
     * @see #updateInterfaz(Pieza)
     */
    public void rotateRight (int time){
        savegame.add(new Move(ROTATE_RIGHT, time));
        if (!(collision(actualpiece, ROTATE_RIGHT))) {
            clear(actualpiece);
            actualpiece.rotateRight();
            updateInterfaz(actualpiece);
            return;
        }
        updateInterfaz(actualpiece);

    }

    /**
     * Permite girar una pieza hacia la izquierda.
     *
     * @see Pieza
     * @see #collision(Pieza, int)
     * @see #clear(Pieza)
     * @see #updateInterfaz(Pieza)
     */
    public void rotateLeft (int time) {
        savegame.add(new Move(ROTATE_LEFT, time));
        if (!(collision(actualpiece, ROTATE_LEFT))) {
            clear(actualpiece);
            actualpiece.rotateLeft();
            updateInterfaz(actualpiece);
            return;
        }
        updateInterfaz(actualpiece);
    }

    /**
     * Mueva la pieza una posicion hacia la derecha.
     *
     * @see #collision(Pieza, int)
     * @see #clear(Pieza)
     * @see #updateInterfaz(Pieza)
     */
    public void goRight (int time){
        savegame.add(new Move(MOVE_RIGHT, time));
        if (!(collision(actualpiece, MOVE_RIGHT))) {
            clear(actualpiece);
            actualpiece.setPosy(actualpiece.getPosy() + 1);
            updateInterfaz(actualpiece);
            return;
        }
        updateInterfaz(actualpiece);
    }

    /**
     * Mueva la pieza una posicion hacia la izquierda.
     *
     * @see #collision(Pieza, int)
     * @see #clear(Pieza)
     * @see #updateInterfaz(Pieza)
     */
    public void goLeft (int time){
        savegame.add(new Move(MOVE_LEFT, time));
        if (!(collision(actualpiece, MOVE_LEFT))){
            clear(actualpiece);
            actualpiece.setPosy(actualpiece.getPosy() - 1);
            updateInterfaz(actualpiece);
            return;
        }
        updateInterfaz(actualpiece);
    }

    /**
     * Mueva la pieza una posicion hacia la abajo.
     *
     * @see #collision(Pieza, int)
     * @see #clear(Pieza)
     * @see #updateInterfaz(Pieza)
     */
    public void goDown (int time){
        savegame.add(new Move(MOVE_DOWN, time));
        if (!(collision(actualpiece, MOVE_DOWN))) {
            clear(actualpiece);
            actualpiece.setPosx(actualpiece.getPosx() + 1);
            updateInterfaz(actualpiece);
            return;
        }
        updateInterfaz(actualpiece);
    }

    public void goDown (){
        if (!(collision(actualpiece, MOVE_DOWN))) {
            clear(actualpiece);
            actualpiece.setPosx(actualpiece.getPosx() + 1);
            updateInterfaz(actualpiece);
            return;
        }
        updateInterfaz(actualpiece);
    }

    /**
     * Comprueba si la pieza actual esta tocando alguna
     * cosa por la parte de abajo.
     *
     * @see #collision(Pieza, int)
     */
    public boolean hadFloor (){
        if (collision(actualpiece, MOVE_DOWN)){
            floortime--;
            return true;
        }
        return false;
    }

    /**
     * Mira si hay alguna fila completa. La elimina del
     * interfaz y la baja su fila superior. Además de añadar
     * las lineas encontradas al total y subir el level.
     *
     * @see #hadCompleteLine(int)
     */
    public void checkLine (){
        int lines = 0;
        boolean haveline = false;
       for (int i = MAXX-1; i >= 0; i--){
           if (hadCompleteLine(i)){
               lines++;
           }
       }
       lineas = lineas + lines;
        this.updatePoints(lines);
       while (lines > 0){
           for (int i = MAXX-1; i >= 0; i--){
               if (hadCompleteLine(i)){
                   haveline = true;
               }
               if (haveline){
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
       if (lineas == 10){
           lineas = 0;
           level++;
       }

    }

    /**
     * Comprueba si la posicion de salia esta ocupada.
     *
     * @see #collision(Pieza, int)
     */
    public void checkEnd (){
        if (collision(nextpiece, END)){
            end = true;
        }
    }

    /**
     * Pone como pieza actual la siguiente, y genera
     * una nueva nextpiece. Además de reiniciar el tiempo
     * de suelo.
     *
     * @see Pieza
     * @see #generateRandom()
     */
    public void chargeNextPiece () {
        actualpiece = nextpiece.clone();
        nextpiece = new Pieza(generateRandom());
        floortime = 2;
    }

    public void chargeNextPiece (Pieza piece) {
        actualpiece = nextpiece.clone();
        nextpiece = piece.clone();
        floortime = 2;
    }

    /*public void saveGame () {
        String nombre;
        nombre =
        File f = new File ();
        while (!(savegame.isEmpty()){

        }
    */}

    //Private Methods

    /**
     * Actualiza la interfaz para introducir una pieza.
     *
     * @param piece     Pieza a introduccir en la interfaz.
     */
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

    /**
     * Elimina una pieza de la interfaz.
     *
     * @param piece     Pieza a eliminar de la interfaz.
     */
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

    /**
     * Comprueba si hay alguna colisio, tanto por
     * los laterales como por dejaba y también si ya no se
     * pueden generar nuevas piezas.
     *
     * @see #checkRotateRight(Pieza)
     * @see #checkRotateLeft(Pieza)
     * @see #checkRight(Pieza)
     * @see #checkLeft(Pieza)
     * @see #checkDown(Pieza)
     * @see #checkEnd()
     *
     * @param piece     Pieza que va a colisionar.
     * @param move      Movimiento que va hacer la pieza.
     * @return          Devuelve cierto si la pieza va a colisionar y por
     *                  lo tanto no puede hacer el movimiento que queria hacer.
     */
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
            case END:
                return checkInitPos(piece);
        }
        return false;
    }

    /**
     * Comprueba todas las posibles combinaciones para que no haya
     * una colision por la zona de la derecha.
     *
     * @param piece     Pieza que podría colisionar.
     * @return          Devuelve cierto si la pieza colisionará al
     *                  ir a la derecha.
     */
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

    /**
     * Comprueba todas las posibles combinaciones para que no haya
     * una colision por la zona de la izquierda.
     *
     * @param piece     Pieza que podría colisionar.
     * @return          Devuelve cierto si la pieza colisionará al
     *                  ir a la izquierda.
     */
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

    /**
     * Comprueba todas las posibles combinaciones para que no haya
     * una colision por la parte de abajo.
     *
     * @param piece     Pieza que podría colisionar.
     * @return          Devuelve cierto si la pieza colisionará al bajar.
     */
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

    /**
     * Comprueba todas las posibles rotaciones hacia la derecha
     * para si hay colisiones al rotar la pieza.
     *
     * @param piece     Pieza que podría colisionar.
     * @return          Devuelve cierto si la pieza colisionará al rotar.
     */
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

    /**
     * Comprueba todas las posibles rotaciones hacia la izquierda
     * para si hay colisiones al rotar la pieza.
     *
     * @param piece     Pieza que podría colisionar.
     * @return          Devuelve cierto si la pieza colisionará al rotar.
     */
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
                        try {
                            if (interfaz[rotatedpiece.getPosx() + 1][rotatedpiece.getPosy()] != VOID_VALUE) {
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

    /**
     * Mira si la próxima pieza se sobreescribirá en alguna anterior.
     *
     * @param piece     Pieza que podría estar por encima de alguna anterior.
     * @return          Devuelve cierto si en alguna posicion de salida hay
     *                  alguna pieza situada.
     */
    private boolean checkInitPos (Pieza piece){
        switch (piece.getTipo()){
            case 0:
                if (interfaz[piece.getPosx()][piece.getPosy()] != VOID_VALUE ||
                        interfaz[piece.getPosx()][piece.getPosy()+1] != VOID_VALUE ||
                        interfaz[piece.getPosx()+1][piece.getPosy()] != VOID_VALUE ||
                        interfaz[piece.getPosx()+1][piece.getPosy()+1] != VOID_VALUE){
                    return true;
                }
                break;
            case 1:
                if (interfaz[piece.getPosx()-1][piece.getPosy()] != VOID_VALUE ||
                        interfaz[piece.getPosx()][piece.getPosy()] != VOID_VALUE ||
                        interfaz[piece.getPosx()+1][piece.getPosy()] != VOID_VALUE ||
                        interfaz[piece.getPosx()+2][piece.getPosy()] != VOID_VALUE){
                    return true;
                }
                break;
            case 2:
                if (interfaz[piece.getPosx()-1][piece.getPosy()-1] != VOID_VALUE ||
                        interfaz[piece.getPosx()-1][piece.getPosy()] != VOID_VALUE ||
                        interfaz[piece.getPosx()][piece.getPosy()] != VOID_VALUE ||
                        interfaz[piece.getPosx()][piece.getPosy()+1] != VOID_VALUE){
                    return true;
                }
                break;
            case 3:
                if (interfaz[piece.getPosx()][piece.getPosy()-1] != VOID_VALUE ||
                        interfaz[piece.getPosx()-1][piece.getPosy()] != VOID_VALUE ||
                        interfaz[piece.getPosx()+1][piece.getPosy()] != VOID_VALUE ||
                        interfaz[piece.getPosx()-1][piece.getPosy()+1] != VOID_VALUE){
                    return true;
                }
                break;
            case 4:
                if (interfaz[piece.getPosx()][piece.getPosy()-1] != VOID_VALUE ||
                        interfaz[piece.getPosx()][piece.getPosy()] != VOID_VALUE ||
                        interfaz[piece.getPosx()][piece.getPosy()+1] != VOID_VALUE ||
                        interfaz[piece.getPosx()-1][piece.getPosy()+1] != VOID_VALUE){
                    return true;
                }
                break;
            case 5:
                if (interfaz[piece.getPosx()-1][piece.getPosy()-1] != VOID_VALUE ||
                        interfaz[piece.getPosx()][piece.getPosy()-1] != VOID_VALUE ||
                        interfaz[piece.getPosx()][piece.getPosy()] != VOID_VALUE ||
                        interfaz[piece.getPosx()][piece.getPosy()+1] != VOID_VALUE){
                    return true;
                }
                break;
            case 6:
                if (interfaz[piece.getPosx()][piece.getPosy()-1] != VOID_VALUE ||
                        interfaz[piece.getPosx()-1][piece.getPosy()] != VOID_VALUE ||
                        interfaz[piece.getPosx()][piece.getPosy()] != VOID_VALUE ||
                        interfaz[piece.getPosx()][piece.getPosy()+1] != VOID_VALUE){
                    return true;
                }
                break;
        }
        return false;
    }

    /**
     * Comprueba si una linea de la interfaz esta completa. Es decir que
     * ninguno de sus valores es igual a VOID_VALUE.
     *
     * @param linea     Linea a comprovar si esta completa.
     * @return          Devuelve cierto si la linea esta completa.
     */
    private boolean hadCompleteLine (int linea){
        for (int i = 0; i < MAXY; i++){
            if (interfaz[linea][i] == VOID_VALUE){
                return false;
            }
        }
        return true;
    }

    /**
     * Acatualiza la puntuación actual.
     *
     * @param lineas    Lineas que sean eliminado.
     */
    private void updatePoints (int lineas){
        switch (lineas){
            case 1:
                points = points + (40*level);
                break;
            case 2:
                points = points + (100*level);
                break;
            case 3:
                points = points + (300*level);
                break;
            case 4:
                points = points + (1200*level);
                break;
        }
    }

    /**
     * Genera un número aleatoria del 0 al 6.
     *
     * @return Devuelve el número generado.
     */
    private int generateRandom (){
        return new Random().nextInt(7);
    }

    //Getters && Setters

    public int[][] getInterfaz (){
        return interfaz;
    }

    public int getFloortime () {return floortime;}
    public Pieza getNextpiece () {return nextpiece;}
    public boolean isEnded(){
        return end;
    }
    public int getLevel() {return level;}
    public int getPoints() {return points;}

    public void setMasterTimer(PlayGame t){
        master = t;
    }
    public void reduceVelocidad(){
        master.setVelocidad(1000);
    }
    public void subeVelocidad(){
        master.setVelocidad(300);
    }
}
