package Network;

import java.io.DataOutputStream;
import java.util.ArrayList;

/**
 * Created by miquelator on 21/7/17.
 */
public class LlistaEspectadors {
    private String user;
    private ArrayList<DataOutputStream> ds;
    private ArrayList<String>historial;
    private int maxEspec;



    public LlistaEspectadors(String u){
        maxEspec = 0;
        user=u;
        ds = new ArrayList<>();
        historial = new ArrayList<>();
    }
    /**
     * Retorna tota la llista d'espectadors en una partida
     * @return ds
     */
    public ArrayList <DataOutputStream> getDs(){
        return ds;
    }

    /**
     * Retorna l'usuari que està jugant una partida
     * @return user
     */
    public String getUser(){
        return user;
    }

    /**
     * Inclou una espectador a llista d'espectadors de la partida
     * @param d el DataOutputStream de l'espectador
     */
    public void afegeixEspectador(DataOutputStream d) {
        ds.add(d);
        if (ds.size() > maxEspec) {
            maxEspec = ds.size();
        }
    }

    /**
     * Retorna el maxim valor d'espectadors que hi ha hagut mirant la partida
     * @return maxEspec valor d'espectadors
     */
    public int getMaxEspec(){
            return maxEspec;
    }

    /**
     * Elimina l'espectador especificat de la llista
     * @param d el DataOutputStream de l'espectador
     */
    public void eliminaEspectador(DataOutputStream d) {
        ds.remove(d);
    }

    /**
     * Afegeix un moviment en l'historial dels moviments que s'han fet en una partida
     * @param s String del moviment
     */
    public void afegeixMoviment(String s){
        historial.add(s);

    }
    /**
     * Retorna tots els moviments que s'han fet al llarg de la partida
     * @return historial
     */
    public ArrayList<String> getHistorial (){
        return historial;
    }
}

