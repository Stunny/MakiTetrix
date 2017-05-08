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

        switch (tipo){
            case 0:

                setCasilla(1,1);
                setCasilla(2,1);
                setCasilla(2,2);
                setCasilla(1,2);
                break;

            case 1:
                setCasilla(0,1);
                setCasilla(1,1);
                setCasilla(2,1);
                setCasilla(3,1);
                break;

            case 2:
                setCasilla(1,1);
                setCasilla(1,2);
                setCasilla(2,0);
                setCasilla(2,1);
                break;

            case 3:
                setCasilla(1,0);
                setCasilla(1,1);
                setCasilla(2,1);
                setCasilla(2,2);
                break;

            case 4:
                setCasilla(0,1);
                setCasilla(1,1);
                setCasilla(2,1);
                setCasilla(2,2);
                break;

            case 5:
                setCasilla(0,2);
                setCasilla(1,2);
                setCasilla(2,2);
                setCasilla(2,1);
                break;

            case 6:

                setCasilla(1,1);
                setCasilla(2,1);
                setCasilla(2,0);
                setCasilla(3,1);
                break;
        }

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
