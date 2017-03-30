package Vista;

import javax.swing.*;
import java.awt.*;

/**
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
        setSize(420,600);

        JPanel esquema = new JPanel (new GridLayout(6,1));

        jltitulojuego = new JLabel("TetrixMaki");
        esquema.add(jltitulojuego);

        JPanel aux = new JPanel(new FlowLayout());
        JLabel jlnombreusuario = new JLabel("Nombre d'usuario: ");
        jtfnombreusuario = new JTextField();

        aux.add(jlnombreusuario);
        aux.add(jtfnombreusuario);
        esquema.add(aux);

        aux = new JPanel (new FlowLayout());
        JLabel jlcontrasenya = new JLabel("Contraseña: ");
        jpfcontrasenya = new JPasswordField();

        aux.add(jlcontrasenya);
        aux.add(jpfcontrasenya);

        esquema.add(aux);

        jbentrar = new JButton("Entrar");

        esquema.add(jbentrar);

        JLabel separacion = new JLabel("----- o -----");
        esquema.add(separacion);

        jbregistro = new JButton("Registro");

        esquema.add(jbregistro);

        getContentPane().add(esquema, BorderLayout.CENTER);
    }


}
