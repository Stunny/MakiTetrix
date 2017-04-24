package Vista;

import controller.LoginController;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal de login del cliente
 * Created by jorti on 30/03/2017.
 */
public class LoginView extends JFrame{

    public static String LOG_EMPTY_UNAME = "EUN";
    public static String LOG_EMPTY_PSSWD = "EPW";

    private JTextField jtfUserName;
    private JPasswordField jpfPassword;

    private JButton jbLogIn;
    private JButton jbRegister;

    /**
     * Builds a new Login screen
     */
    public LoginView(){
        setTitle("MakiTetrix - Login");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(420,500);

        JPanel jpGrid = new JPanel (new GridLayout(6,1));

        JLabel jlGameTitle = new JLabel("MakiTetrix", SwingConstants.CENTER);
        jlGameTitle.setFont(new Font("TituloTM", Font.BOLD, 40));
        jpGrid.add(jlGameTitle);

        JPanel jpAux = new JPanel(new FlowLayout());
        JLabel jlUserName = new JLabel("Nombre d'usuario: ");
        jtfUserName = new JTextField();
        jtfUserName.setEditable(true);
        jtfUserName.setPreferredSize(new Dimension(200,24));

        jpAux.add(jlUserName);
        jpAux.add(jtfUserName);
        jpGrid.add(jpAux);

        jpAux = new JPanel (new FlowLayout());
        JLabel jlPassword = new JLabel("Contraseña: ");
        jpfPassword = new JPasswordField();
        jpfPassword.setEditable(true);
        jpfPassword.setPreferredSize(new Dimension(200,24));

        jpAux.add(jlPassword);
        jpAux.add(jpfPassword);

        jpGrid.add(jpAux);

        JPanel jpLogIn = new JPanel ();
        jbLogIn = new JButton("Entrar");

        jpLogIn.add(jbLogIn);
        jpGrid.add(jpLogIn);

        JLabel jlSeparator = new JLabel("____________________________", SwingConstants.CENTER);
        jlSeparator.setFont(new Font("SeparacionTM", Font.BOLD, 20));
        jpGrid.add(jlSeparator);

        JPanel jpRegister = new JPanel();
        jbRegister = new JButton("Registro");

        jpRegister.add(jbRegister);
        jpGrid.add(jpRegister);

        getContentPane().add(jpGrid, BorderLayout.CENTER);
    }

    /**
     * Links the login window buttons to the corresponding logic controller
     * @param controller an instance of LoginController
     */
    public void registerController(LoginController controller){
        jbLogIn.setActionCommand(LoginController.LOGIN_ACTION_LOG);
        jbRegister.setActionCommand(LoginController.LOGIN_ACTION_REG);

        jbLogIn.addActionListener(controller);
        jbRegister.addActionListener(controller);
    }

    /**
     * @return The entered user name
     */
    public String getUserName(){
        String uname = jtfUserName.getText();
        if(uname.equals(""))
            uname = LOG_EMPTY_UNAME;
        return uname;
    }

    /**
     * @return The entered user password
     */
    public String getPassword(){
        String pw = new String(jpfPassword.getPassword());
        if(pw.equals("")){
           pw = LOG_EMPTY_PSSWD;
        }
        return pw;
    }

    /**
     * Sets the login error message below the credential fields.
     */
    public void setLoginError(){
        //TODO: hacer que debajo de los campos de texto aparezca un mensaje en rojo que diga "Nombre de usuario o contraseña incorrectos"
        String[] options = { "OK" };
        JOptionPane.showOptionDialog(this, "Nombre de usuario o contraseña incorrecto",
                "ERROR AL INICIAR SESSIÓn", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                null, options, options[0]);
        jtfUserName.setText("");
        jpfPassword.setText("");
    }
}
