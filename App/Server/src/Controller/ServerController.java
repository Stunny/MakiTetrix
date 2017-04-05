package Controller;

import Model.GestioDades;
import Network.ThreadSocket;
import View.View;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.stream.Stream;

/**
 * Created by Admin on 21/03/2017.
 */

public class ServerController {
    private static View view;
    private GestioDades gestioDades;
    private ThreadSocket ts;

    public ServerController(View view, GestioDades gestioDades, ThreadSocket threadSocket) {
        this.view = view;
        this.gestioDades = gestioDades;
        this.ts = threadSocket;
        ompleUsuaris();
        startThread();
    }

    public void ompleUsuaris(){
        String [] usuaris = gestioDades.plenaUsuaris();
        for (int i = 0; i < usuaris.length; i++){
            view.getModel().addElement(usuaris[i]);

        }
    }

    public void startThread() {
        if (ts == null || !ts.isAlive()) {
            //aqui comence thread
            ts = new ThreadSocket();
            ts.start();
        }
    }
}