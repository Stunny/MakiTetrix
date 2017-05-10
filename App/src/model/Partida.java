package model;

import java.util.Random;

/**
 * Created by miquelator on 27/4/17.
 */
public class Partida {
    private int[][] interfaz;
    private int puntuacion;
    private int tiempo;
    private boolean gameStarted;
    private boolean gameFinished;

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
        gameStarted = true;
        for (int i = 0; i< 7; i++){
            Pieza startPiece = new Pieza(i);
        }
        //addPieceToMatrix(startPiece);
    }

    private int generateRandom (){
        return new Random().nextInt(7);
    }

    private void addPieceToMatrix (Pieza piece){

    }

}
