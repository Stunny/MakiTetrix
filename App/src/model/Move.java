package model;

/**
 * Created by avoge on 27/04/2017.
 */
public class Move {

    public static final int MOVE_RIGHT = 0;
    public static final int MOVE_LEFT = 1;
    public static final int MOVE_DOWN = 2;
    public static final int ROTATE_RIGHT = 3;
    public static final int ROTATE_LEFT = 4;

    /**
     * Time from last move in millis
     */
    private int timeFromLastMove;

    /**
     * Move to be performed
     */
    private int move;

    public Move(int move){
        this.move = move;
    }

    @Override
    public String toString(){
        return "MOVE" + "-" + Integer.toString(timeFromLastMove) + "-" + Integer.toString(move);
    }

}
