package Vista;

import controller.RegisterController;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal de registro del cliente
 * Created by jorti on 31/03/2017.
 */
public class RegisterView extends JFrame{

    private JTextField jtfUsername;
    private JTextField jtfEmail;
    private JPasswordField jpfPassword;
    private JPasswordField jpfConfirmPassword;

    private JCheckBox jcbAcceptTerms;

    private JButton jbRegister;

    /**
     *
     */
    public RegisterView(){
        configFrame();

        //TODO: Interficie grafica de la ventana de registro

        jbRegister = new JButton("Register");
        jbRegister.setPreferredSize(new Dimension(200, 30));
        add(jbRegister, BorderLayout.SOUTH);
        pack();
    }

    /**
     *
     * @param controller
     */
    public void registerController(RegisterController controller){
        jbRegister.setActionCommand(RegisterController.ACTION_REG);
        jbRegister.addActionListener(controller);
    }

    /**
     *
     */
    private void configFrame() {
        setTitle("MakiTetrix - Register");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(420, 500);
        setLayout(new BorderLayout());
    }

    public String getUserName(){
        return jtfUsername.getText();
    }

    public String getUserEmail(){
        return jtfEmail.getText();
    }

    public String getUserPassword(){
        return new String(jpfPassword.getPassword());
    }

    public String getConfirmPassword(){
        return new String(jpfConfirmPassword.getPassword());
    }
}
