package model;

/**
 * Created by jorti on 14/05/2017.
 */
public class Pieza {
    private int posx;
    private int posy;
    private int tipo;
    private int posicion;

    //Constructors

    public Pieza(int tipo){
        this.tipo = tipo;
        switch (this.tipo){
            case 0:
                posx = 0;
                posy = 4;
                break;
            case 1:
                posx = 1;
                posy = 4;
                break;
            case 2:
                posx = 1;
                posy = 4;
                break;
            case 3:
                posx = 1;
                posy = 4;
                break;
            case 4:
                posx = 1;
                posy = 4;
                break;
            case 5:
                posx = 1;
                posy = 4;
            case 6:
                posx = 1;
                posy = 4;
        }
        posicion = 0;
    }

    //Public Methods

    public void rotateRight (){
        if (posicion == 3){
            posicion = 0;
            return;
        }
        posicion++;
    }

    public void rotateLeft (){
        if (posicion == 0){
            posicion = 3;
            return;
        }
        posicion--;
    }

    // Getters && Setters

    public int getTipo (){return tipo;}

    public void setPosx (int posx) {this.posx = posx;}

    public void setPosy (int posy) {this.posy = posy;}

    public void setPosicion(int posicion){this.posicion = posicion;}

    public int getPosx () {return posx;}

    public int getPosy() {return posy;}

    public int getPosicion (){return posicion;}


    @Override
    public Pieza clone (){
        Pieza aux = new Pieza (this.tipo);
        aux.setPosicion(this.posicion);
        aux.setPosy(this.posy);
        aux.setPosx(this.posx);
        return aux;
    }
}
