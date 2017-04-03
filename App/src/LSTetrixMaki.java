import Vista.VInicioSesion;

import javax.swing.*;

/**
 * Created by jorti on 30/03/2017.
 */

public class LSTetrixMaki {
    public static void main (String[] args){
        SwingUtilities.invokeLater(new Runnable (){

            @Override
            public void run(){
                VInicioSesion vis = new VInicioSesion();
                vis.setVisible(true);
            }

        });
    }
}
