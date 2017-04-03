package Controller;

import Model.GestioDades;
import Network.ThreadSocket;
import View.View;

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
        startThread();
    }

    public void startThread() {
        if (ts == null || !ts.isAlive()) {
            //aqui comence thread
            ts = new ThreadSocket();
            ts.start();
        }
    }
}