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
    public ArrayList <DataOutputStream> getDs(){
        return ds;
    }
    public String getUser(){
        return user;
    }

    public void afegeixEspectador(DataOutputStream d) {
        ds.add(d);
        if (ds.size() > maxEspec) {
            maxEspec = ds.size();
        }
    }
    public int getMaxEspec(){
            return maxEspec;
    }

    public void eliminaEspectador(DataOutputStream d) {
        ds.remove(d);
    }


    public void afegeixMoviment(String s){
        historial.add(s);

    }
    public ArrayList<String> getHistorial (){
        return historial;
    }
}

