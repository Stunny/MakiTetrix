package model;

/**
 * Created by miquelator on 24/4/17.
 */
public class Pieza {
    private int posicionx;
    private int posiciony;
    private int tipo;
    private int[][] pieza;

    public Pieza(int tipo) {
        this.tipo = tipo;

        switch (tipo) {
            case 0:
                this.pieza = new int[2][2];
                for (int i = 0; i < pieza.length; i++){
                    for(int j = 0; j < pieza[0].length; j++){
                        pieza[i][j] = -1;
                    }
                }
                setCasilla(0, 0);
                setCasilla(0, 1);
                setCasilla(1, 0);
                setCasilla(1, 1);
                break;

            case 1:
                this.pieza = new int[4][4];
                for (int i = 0; i < pieza.length; i++){
                    for(int j = 0; j < pieza[0].length; j++){
                        pieza[i][j] = -1;
                    }
                }
                setCasilla(0, 1);
                setCasilla(1, 1);
                setCasilla(2, 1);
                setCasilla(3, 1);
                break;

            case 2:
                this.pieza = new int[3][3];
                for (int i = 0; i < pieza.length; i++){
                    for(int j = 0; j < pieza[0].length; j++){
                        pieza[i][j] = -1;
                    }
                }
                setCasilla(0, 1);
                setCasilla(0, 2);
                setCasilla(1, 0);
                setCasilla(1, 1);
                break;

            case 3:
                this.pieza = new int[3][3];
                for (int i = 0; i < pieza.length; i++){
                    for(int j = 0; j < pieza[0].length; j++){
                        pieza[i][j] = -1;
                    }
                }
                setCasilla(0, 0);
                setCasilla(0, 1);
                setCasilla(1, 1);
                setCasilla(1, 2);
                break;

            case 4:
                this.pieza = new int[3][3];
                for (int i = 0; i < pieza.length; i++){
                    for(int j = 0; j < pieza[0].length; j++){
                        pieza[i][j] = -1;
                    }
                }
                setCasilla(0, 1);
                setCasilla(1, 1);
                setCasilla(2, 1);
                setCasilla(2, 2);
                break;

            case 5:
                this.pieza = new int[3][3];
                for (int i = 0; i < pieza.length; i++){
                    for(int j = 0; j < pieza[0].length; j++){
                        pieza[i][j] = -1;
                    }
                }
                setCasilla(0, 1);
                setCasilla(1, 1);
                setCasilla(2, 1);
                setCasilla(2, 0);
                break;

            case 6:
                this.pieza = new int[3][3];
                for (int i = 0; i < pieza.length; i++){
                    for(int j = 0; j < pieza[0].length; j++){
                        pieza[i][j] = -1;
                    }
                }
                setCasilla(0, 1);
                setCasilla(1, 0);
                setCasilla(1, 1);
                setCasilla(1, 2);
                break;
        }
    }

    private void setCasilla (int x, int y){
        pieza[x][y] = tipo;
    }

    public void setPosicion (int x, int y){
        this.posicionx = x;
        this.posiciony = y;
    }

    public int[][] getPieza(){
        return pieza;
    }

    public void rotateRight (){
        int[][] aux = new int[pieza.length][pieza[0].length];
        for (int i = 0; i < pieza.length; i++){
            for (int j = 0; j < pieza[i].length; j++){
                aux[i][j] = pieza[pieza.length-j-1][i];
            }
        }
        pieza = aux;
    }

    public void rotateLeft (){
        int[][] aux = new int[pieza.length][pieza[0].length];
        for (int i = 0; i < pieza.length; i++){
            for (int j = 0; j < pieza[i].length; j++){
                aux[i][j] = pieza[j][pieza.length-i-1];
            }
        }
        pieza = aux;
    }

    public int getPosicionx (){
        return posicionx;
    }
    public int getPosiciony (){
        return posiciony;
    }
}
