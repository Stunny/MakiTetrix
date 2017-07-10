package Vista;

import controller.MenuController;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jorti on 17/05/2017.
 *
 * Vista del menú principal del MakiTetrix.
 */
public class MainMenuView extends JFrame{
    private JLabel usuario;
    private JButton teclas;
    private JButton jugarpartida;
    private JButton verpartida;
    private JButton partidaanterior;
    private JButton salir;

    //Contructor

    public MainMenuView () {
        //Parametros básicos de la aplicación
        setSize(420,500);
        setTitle("MakiTetrix - Menú Principal");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //North
        JPanel north = new JPanel(new FlowLayout());
        usuario = new JLabel("RandomName");
        usuario.setHorizontalAlignment(SwingConstants.CENTER);
        usuario.setFont(new Font("Prueba", Font.BOLD,35));


        north.add(usuario);

        getContentPane().add(usuario,BorderLayout.NORTH);

        //Center
        JPanel center = new JPanel (new GridLayout(6,1));
        JPanel auxteclas = new JPanel ();
        JPanel auxjugarpartida = new JPanel ();
        JPanel auxverpartida = new JPanel ();
        JPanel auxpartidaanterior = new JPanel ();
        JPanel auxsalir = new JPanel ();
        JPanel voidPanel = new JPanel();

        teclas = new JButton ("Configuración Teclas");
        auxteclas.add(teclas);
        jugarpartida = new JButton("Iniciar Partida");
        auxjugarpartida.add(jugarpartida);
        verpartida = new JButton("Ver Partida En Vivo");
        auxverpartida.add(verpartida);
        partidaanterior = new JButton ("Reproducir Partida Anterior");
        auxpartidaanterior.add(partidaanterior);
        salir = new JButton("Salir");
        auxsalir.add(salir);

        center.add(voidPanel);
        center.add(auxteclas);
        center.add(auxjugarpartida);
        center.add(auxverpartida);
        center.add(auxpartidaanterior);
        center.add(auxsalir);

        getContentPane().add(center, BorderLayout.CENTER);
    }

    public void registerActions (MenuController mc){

        addWindowListener(mc);

        teclas.addActionListener(mc);
        teclas.setActionCommand("teclas");

        jugarpartida.addActionListener(mc);
        jugarpartida.setActionCommand("jugar");

        verpartida.addActionListener(mc);
        verpartida.setActionCommand("ver");

        partidaanterior.addActionListener(mc);
        partidaanterior.setActionCommand("anterior");

        salir.addActionListener(mc);
        salir.setActionCommand("salir");
    }
}
