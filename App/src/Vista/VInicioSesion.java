package Vista;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal de login del cliente
 * Created by jorti on 30/03/2017.
 */
public class VInicioSesion extends JFrame{
    private JLabel jltitulojuego;
    private JTextField jtfnombreusuario;
    private JPasswordField jpfcontrasenya;
    private JButton jbentrar;
    private JButton jbregistro;

    public VInicioSesion (){
        //Parametros básicos de la ventana
        setTitle("LSTetrixMaki");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(420,500);

        JPanel esquema = new JPanel (new GridLayout(6,1));

        jltitulojuego = new JLabel("TetrixMaki", SwingConstants.CENTER);
        jltitulojuego.setFont(new Font("TituloTM", Font.BOLD, 40));
        esquema.add(jltitulojuego);

        JPanel aux = new JPanel(new FlowLayout());
        JLabel jlnombreusuario = new JLabel("Nombre d'usuario: ");
        jtfnombreusuario = new JTextField();
        jtfnombreusuario.setEditable(true);
        jtfnombreusuario.setPreferredSize(new Dimension(200,24));

        aux.add(jlnombreusuario);
        aux.add(jtfnombreusuario);
        esquema.add(aux);

        aux = new JPanel (new FlowLayout());
        JLabel jlcontrasenya = new JLabel("Contraseña: ");
        jpfcontrasenya = new JPasswordField();
        jpfcontrasenya.setEditable(true);
        jpfcontrasenya.setPreferredSize(new Dimension(200,24));

        aux.add(jlcontrasenya);
        aux.add(jpfcontrasenya);

        esquema.add(aux);

        JPanel jpentrar = new JPanel ();
        jbentrar = new JButton("Entrar");

        jpentrar.add(jbentrar);
        esquema.add(jpentrar);

        JLabel separacion = new JLabel("____________________________", SwingConstants.CENTER);
        separacion.setFont(new Font("SeparacionTM", Font.BOLD, 20));
        esquema.add(separacion);

        JPanel jpregistro = new JPanel();
        jbregistro = new JButton("Registro");

        jpregistro.add(jbregistro);
        esquema.add(jpregistro);

        getContentPane().add(esquema, BorderLayout.CENTER);
    }


}
