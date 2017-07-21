package Network;

import java.io.DataOutputStream;
import java.util.ArrayList;

/**
 * Created by miquelator on 21/7/17.
 */
public class LlistaEspectadors {
    private String user;
    private ArrayList<DataOutputStream> ds;

    public LlistaEspectadors(String u){
        user=u;
        ds = new ArrayList<>();
    }
    public ArrayList <DataOutputStream>getDs(){
        return ds;
    }
    public String getUser(){
        return user;
    }
    public void afegeixEspectador(DataOutputStream d){
        ds.add(d);
        System.out.println("tamany espectadors: "+ds.size());

    }
}

