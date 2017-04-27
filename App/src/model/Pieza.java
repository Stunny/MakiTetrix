package model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by miquelator on 24/4/17.
 */
public class Pieza {
    int x;
    int y;
    private int tipo;
    private ArrayList<int[]> matriz;

    public Pieza (int tipo){
        this.tipo = tipo;
        this.matriz = new ArrayList<int []>();

        }

    public ArrayList<int []>  getMatriz (){
        return this.matriz;
    }
    public int  getX (){
        return x;
    }
    public int  getY (){
        return y;
    }

    public void  setPos (int x, int y){
        this.x = x;
        this.y = y;

    }

    public void  setCasilla (int x, int y){
        int[] aux = new int [2];
        aux[0] = x;
        aux[1] = y;
        this.matriz.add(aux);

    }




}
