package model;

/**
 * Created by miquelator on 24/4/17.
 */
public class Pieza {
    private int poscionx;
    private int posciony;
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
                setCasilla(1, 1);
                setCasilla(1, 2);
                setCasilla(2, 0);
                setCasilla(2, 1);
                break;

            case 3:
                this.pieza = new int[3][3];
                for (int i = 0; i < pieza.length; i++){
                    for(int j = 0; j < pieza[0].length; j++){
                        pieza[i][j] = -1;
                    }
                }
                setCasilla(1, 0);
                setCasilla(1, 1);
                setCasilla(2, 1);
                setCasilla(2, 2);
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
        this.poscionx = x;
        this.posciony = y;
    }
}
