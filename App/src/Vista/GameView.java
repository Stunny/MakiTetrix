package Vista;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal del juego.
 * Created by jorti on 31/03/2017.
 */
public class GameView extends JFrame{

    private JPanel[][]caselles;
    private JPanel centre;
    private JLabel temps;
    private JLabel nivel;
    private JLabel puntuacion;
    private JPanel siguientePieza;
    private JLabel observador;

    public GameView(){
        //Datos de la ventana
        setTitle("MakiTetrix - Game");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(432,800);
        getContentPane().setLayout(new BorderLayout());

        //Información necesaria en la parte de arriba del nivel

        temps = new JLabel ("XX:XX");
        nivel = new JLabel ("Nivel X");
        puntuacion = new JLabel ("0000 - eres inútil");

        JPanel north = new JPanel (new FlowLayout());
        north.add(temps);
        north.add(nivel);
        north.add(puntuacion);

        getContentPane().add(north, BorderLayout.NORTH);

        //Panel del juego centra, que incluye matriz del juego y siguiente pieza

        centre = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        //c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0;

        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 25; j++){
                JPanel aux = new JPanel();
                aux.setBackground(Color.ORANGE);
                aux.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                c.fill = GridBagConstraints.BOTH;
                c.weightx = c.weighty = 0.5;
                c.gridx = i;
                c.gridy = j;
                centre.add(aux, c);
            }
        }

        GridBagConstraints c2 = new GridBagConstraints();
        c2.fill = GridBagConstraints.BOTH;

        getContentPane().add(centre, BorderLayout.CENTER);
        //Creación panel de abajo para que se vean tus observadores

        JPanel observadores = new JPanel (new FlowLayout());
        observador = new JLabel ("Observador 1");
        observadores.add(observador);

        getContentPane().add(observadores,BorderLayout.SOUTH);

    }

}
