package Model;

import Controller.ServerController;
import java.io.DataInputStream;
import java.util.ArrayList;

/**
 * Created by Admin on 24/03/2017.
 */
public class GestioDades {
    //tractament de dades --> info del server a la BBDD (Miquel)
    public GestioDades(){
        plenaUsuaris();
    }

    public static void main(String[] args) {

    }

    public String[] plenaUsuaris(){
        String[] usuaris = {"1", "2", "3", "4"};
        //retornar la llista dels usuaris de la BBDD

        return usuaris;
    }

    public void borraUsuari(String deleteUser) {
        //borrar el usuari que ens pasen
        System.out.println("Volem borrar el usuari: " + deleteUser);

    }

    public void mostraDades(String usuari) {
        System.out.println("he de mostrar les dades de l'usuari: " + usuari);
    }
}