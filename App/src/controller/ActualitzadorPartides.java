package controller;


import network.Conexio;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getGoing()){
                    if(c.actualitza(nPartides)){
                        c.disconnect();
                        nPartides = c.getPartidesOnline();
                        try {
                            ec.actualitzaLlistaUsuaris();

                        } catch (ParseException pe) {
                            pe.printStackTrace();
                        }
                    }
                } else {
                    System.out.println("surto");

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
        this.keepGoing = keepGoing;
    }
    public synchronized boolean getGoing(){
        return this.keepGoing;
    }
}
