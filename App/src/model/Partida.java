package model;

import java.util.Random;

/**
 * Created by miquelator on 27/4/17.
 */
public class Partida {
    private int[][] interfaz;
    private Pieza nextpiece;
    private Pieza actualpiece;
    private int puntuacion;
    private int tiempo;
    private boolean gamestarted;
    private boolean gamefinished;

    public Partida (){
        interfaz = new int[25][10];
        for (int i =0; i < interfaz.length; i++){
            for (int j = 0; j < interfaz[0].length; j++){
                interfaz[i][j] = -1;
            }
        }
        puntuacion = 0;
        tiempo = 0;
    }

    /**
     * Inicia la Partida. Genera una pieza inicial, la siguiente y las coloca en su posición.
     */
    public void startGame () {
        gamestarted = true;
        Pieza startpiece = new Pieza(generateRandom());
        actualpiece = startpiece;
        addNewPieceToMatrix(actualpiece);
        setnextPiece(new Pieza (generateRandom()));
    }

    /**
     * Genera un número aleatorio, del 0 al 6.
     * @return el número aleatorio.
     */
    private int generateRandom (){
        return new Random().nextInt(7);
    }

    /**
     * Añadé una nueva pieza a la interfaz del juego. La diferencia con #addPieceToMatrix(Pieza piece) es
     * que la pieza que se añadé en este caso se la situa en la posición inicial.
     * @param piece
     */
    private void addNewPieceToMatrix (Pieza piece){
        piece.setPosicion(0,4);
        for (int i = 0; i < piece.getPieza().length; i++){
            for (int j = 0; j < piece.getPieza()[0].length; j++){
                interfaz[piece.getPosicionx()+i][piece.getPosiciony()+j] = piece.getPieza()[i][j];
            }
        }
    }

    /**
     * Añade una pieza a la interfaz del juego. La diferencia con #addNewPieceToMatrix es que la posicion
     * donde se coloca esta pieza viene dada por la información de la pieza.
     * @param piece
     */
    private void addPieceToMatrix (Pieza piece) {
        for (int i = 0; i < piece.getPieza().length; i++){
            for (int j = 0; j < piece.getPieza()[0].length; j++){
                interfaz[piece.getPosicionx()+i][piece.getPosiciony()+j] = piece.getPieza()[i][j];
            }
        }
    }

    /**
     * Hace descender la pieza una casilla hacía abajo.
     */
    public void downABox (){
        clearPiece(actualpiece);
        actualpiece.setPosicion(actualpiece.getPosicionx()+1,actualpiece.getPosiciony());
        addPieceToMatrix(actualpiece);

    }

    /**
     * Hace mover la pieza una casilla hacia la izquierda.
     */
    public void leftBox (){
        clearPiece(actualpiece);
        actualpiece.setPosicion(actualpiece.getPosicionx(),actualpiece.getPosiciony()-1);
        addPieceToMatrix(actualpiece);

    }

    /**
     * Mueve una casilla hacia la derecha la pieza.
     */
    public void rigthBox (){
        clearPiece(actualpiece);
        actualpiece.setPosicion(actualpiece.getPosicionx(),actualpiece.getPosiciony()+1);
        addPieceToMatrix(actualpiece);

    }

    /**
     * Hace rotar la pieza hacía la derecha.
     * @see Pieza#rotateRight()
     */
    public void rotateRigth () {
        clearPiece(actualpiece);
        actualpiece.rotateRight();
        addPieceToMatrix(actualpiece);
    }

    /**
     * Hace rotar la pieza hacía la izquierda.
     * @see Pieza#rotateLeft()
     */
    public void rotateLeft () {
        clearPiece(actualpiece);
        actualpiece.rotateLeft();
        addPieceToMatrix(actualpiece);
    }

    /**
     * Borra una pieza de la matriz interfaz.
     * @param piece
     */
    private void clearPiece (Pieza piece){
        for (int i = 0; i < piece.getPieza().length; i++){
            for (int j = 0; j < piece.getPieza()[0].length; j++){
                interfaz[piece.getPosicionx()+i][piece.getPosiciony()+j] = -1;
            }
        }
    }

    //Setter & Getters

    private void setnextPiece(Pieza piece){
        nextpiece = piece;
    }

    public int[][] getInterfaz (){
        return interfaz;
    }

    public int[][] getNextpiece () {
        return nextpiece.getPieza();
    }

}
