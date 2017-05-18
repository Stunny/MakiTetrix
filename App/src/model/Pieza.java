package model;

/**
 * Created by jorti on 14/05/2017.
 *
 * Contiene la información de la pieza:
 *      Posx, posy: posicion dentro de la interfaz.
 *      Tipo: del 0 al 6 según el tipo de pieza que sea.
 *          0: Cuadrado
 *          1: Forma "I"
 *          2: Forma "Z"
 *          3: Forma "S"
 *          4: Forma "L"
 *          5: Forma "L" invertida
 *          6: Forma "T"
 *      Posicion: posicion de la rotacion el valor va de 0 a 3.
 * La clase Partida se encarga de interpretar todos estos datos y
 * representarlos en la matriz.
 * @see Partida
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

    /**
     * Suma uno a la posicion, refieriendose que va hacia la derecha.
     * En caso de llegar a 3, su ultima posicion, vuelva al valor 0.
     */
    public void rotateRight (){
        if (posicion == 3){
            posicion = 0;
            return;
        }
        posicion++;
    }

    /**
     * Suma uno a la posicion, refieriendose que va hacia la derecha.
     * En caso de llegar a 0, su ultima posicion, vuelva al valor 3.
     */
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
