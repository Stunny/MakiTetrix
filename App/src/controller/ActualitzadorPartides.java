package controller;


import network.Conexio;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;

/**
 * Created by miquelator on 24/7/17.
 */
public class ActualitzadorPartides extends Thread{

    private boolean keepGoing;
    private Timer timer;

    private int nPartides;
    private Conexio c;
    private EspectatorController ec;
    public ActualitzadorPartides (Conexio c, EspectatorController ec){
        this.c = c;
        this.ec = ec;
        this.nPartides = c.getPartidesOnline();
        keepGoing = true;
    }
    @Override
    public synchronized void run(){
            c.començaActualitza();
        System.out.println("començo a run");
        timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (keepGoing){
                    if(c.actualitza(nPartides)){
                        c.disconnect();
                        System.out.println("actualitza");
                        nPartides = c.getPartidesOnline();
                        try {
                            ec.actualitzaLlistaUsuaris();
                        } catch (ParseException pe) {
                            pe.printStackTrace();
                        }
                    }
                    System.out.println("keep going = "+keepGoing);
                } else {
                    System.out.println("surto");
                    c.disconnect();
                    timer.stop();
                }
            }
        });
        timer.start();


    }

    /**
     *
     * @param keepGoing
     */
    public synchronized void setGoing(boolean keepGoing){
        keepGoing = false;
    }
}
