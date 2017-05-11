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

    public void startGame () {
        gamestarted = true;
        Pieza startpiece = new Pieza(generateRandom());
        actualpiece = startpiece;
        addNewPieceToMatrix(actualpiece);
        nextPiece(new Pieza (generateRandom()));
    }

    private int generateRandom (){
        return 1;
                //new Random().nextInt(7);
    }

    private void addNewPieceToMatrix (Pieza piece){
        piece.setPosicion(0,4);
        for (int i = 0; i < piece.getPieza().length; i++){
            for (int j = 0; j < piece.getPieza()[0].length; j++){
                interfaz[piece.getPosicionx()+i][piece.getPosiciony()+j] = piece.getPieza()[i][j];
            }
        }
    }

    private void addPieceToMatrix (Pieza piece) {
        for (int i = 0; i < piece.getPieza().length; i++){
            for (int j = 0; j < piece.getPieza()[0].length; j++){
                interfaz[piece.getPosicionx()+i][piece.getPosiciony()+j] = piece.getPieza()[i][j];
            }
        }
    }

    public void downABox (){
        clearPiece(actualpiece);
        actualpiece.setPosicion(actualpiece.getPosicionx()+1,actualpiece.getPosiciony());
        addPieceToMatrix(actualpiece);

    }

    public void leftBox (){
        if (!actualpiece.getParada()) {
            clearPiece(actualpiece);
            actualpiece.setPosicion(actualpiece.getPosicionx(), actualpiece.getPosiciony() - 1);
            addPieceToMatrix(actualpiece);
        }

    }

    public void rigthBox (){
        if (!actualpiece.getParada()) {
            clearPiece(actualpiece);
            actualpiece.setPosicion(actualpiece.getPosicionx() + 1, actualpiece.getPosiciony() + 1);
            addPieceToMatrix(actualpiece);
        }
    }

    public void rotateRigth () {
        if (!actualpiece.getParada()) {
            clearPiece(actualpiece);
            actualpiece.rotateRight();
            addPieceToMatrix(actualpiece);
        }
    }

    public void rotateLeft () {
        if (!actualpiece.getParada()) {

            clearPiece(actualpiece);
            actualpiece.rotateLeft();
            addPieceToMatrix(actualpiece);
        }
    }

    private void clearPiece (Pieza piece){
        for (int i = 0; i < piece.getPieza().length; i++){
            for (int j = 0; j < piece.getPieza()[0].length; j++){
                interfaz[piece.getPosicionx()+i][piece.getPosiciony()+j] = -1;
            }
        }
    }

    private void nextPiece (Pieza piece){
        nextpiece = piece;
    }

    public int[][] getInterfaz (){
        return interfaz;
    }

    public int[][] getNextpiece () {
        return nextpiece.getPieza();
    }

}
