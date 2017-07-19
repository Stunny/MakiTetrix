package model;

/**
 * Crea un movimiento para guardar la información en la repetición o enviarla al server.
 * Created by avoge on 27/04/2017.
 */
public class Move {
    public static final int PIECE = 0;
    public static final int MOVE = 1;

    private int move;
    private Pieza piece;
    private int time;
    private int option;

    //Constructores

    /**
     * Crea un movimiento a partir de un movimiento y del tiempo en el que se hace.
     * @param move      Tipo de movimiento.
     * @param time      Tiempo en el que sucede dicho movimiento.
     * @see Partida     Estan definidos los tipos de movimientos.
     */
    public Move(int move, int time){
        this.move = move;
        this.time = time;
        option = MOVE;
    }

    /**
     * Crea un movimiento a partir de la nueva pieza generada.
     * @param piece     Pieza que se asigna al nuevo movimiento.
     */
    public Move (Pieza piece) {
        this.piece = piece;
        time = 0;
        option = PIECE;
    }

    //Getters && Setters

    public Pieza getPiece (){
        return piece;
    }
    public int getOption (){
        return option;
    }
    public int getMove (){
        return move;
    }
    public int getTime (){
        return time;
    }

    @Override
    public String toString (){
        switch (option){
            case MOVE:
                return (option + "," + move + "," + time);
            case PIECE:
                return (option + "," + piece.toString());
        }
        return "";
    }

}
