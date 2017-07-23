package View;

import Controller.ServerController;
import Model.JTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;

/**
 * Created by Admin on 20/03/2017.
 */
public class ServerAdminView extends JFrame{
    private final Color CONNECTED = new Color(11, 159, 0);

    public static final String ACTION_BORRAR = "Borrar";
    public static final String ACTION_SEARCH = "Busca";
    public static final String ACTION_REPLAY = "Replay";
    public static final String GRAPHS = "Grafiques";

    private JTable leftJTable;
    private JButton borrar;
    private JTextField buscador;
    private JButton busca;
    private JButton grafics;
    private JButton replays;
    private JTable rightJTable;

    /**
     * Constructor de la ventana de administracion del servidor
     */
    public ServerAdminView() {
        //creem els components principals
        JPanel principal = new JPanel(new BorderLayout());
        JPanel north = new JPanel();
        JPanel south = new JPanel(new GridLayout(1, 2));
        JPanel rightPane = new JPanel(new BorderLayout());
        JPanel leftPane = new JPanel(new BorderLayout());
        rightPane.setBorder(BorderFactory.createTitledBorder("Informacio usuari"));
        leftPane.setBorder(BorderFactory.createTitledBorder("Usuaris"));

        //afegim un panell a la dreta i un a l'esquerra al panell inferior
        south.add(leftPane);
        south.add(rightPane);

        //afegim el panell inferior i superior al panell principal
        principal.add(north, BorderLayout.NORTH);
        principal.add(south, BorderLayout.CENTER);
        setContentPane(principal);

        //afegim els components al panell superior
        JPanel topPane = new JPanel(new BorderLayout());
        JLabel topLabel = new JLabel("ADMINISTRADOR");
        topLabel.setFont (topLabel.getFont ().deriveFont (40.0f));
        JPanel aux = new JPanel(new GridLayout(1, 2));
        buscador = new JTextField();
        busca = new JButton("Busca Usuari");
        busca.setActionCommand(ACTION_SEARCH);
        grafics = new JButton("Mostra grafiques");
        grafics.setActionCommand(GRAPHS);
        aux.add(busca);
        aux.add(grafics);
        topPane.add(topLabel, BorderLayout.NORTH);
        topPane.add(buscador, BorderLayout.CENTER);
        topPane.add(aux, BorderLayout.SOUTH);
        north.add(topPane);

        //afegim els components al panell inferior
        rightJTable = new JTable();
        JTableModel jTableModelLeft = new JTableModel();
        JTableModel jTableModelRight = new JTableModel();

        leftJTable = new JTable(jTableModelLeft){

            public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
                Component c = super.prepareRenderer(renderer, row, column);

                c.setForeground(((String)getValueAt(row, column)).contains("(Online)")? CONNECTED : Color.BLACK);

                return c;
            }

        };
        leftJTable.getTableHeader().setReorderingAllowed(false);


        rightJTable = new JTable(jTableModelRight);
        rightJTable.getTableHeader().setReorderingAllowed(false);

        borrar = new JButton("Borrar");
        borrar.setActionCommand(ACTION_BORRAR);
        replays = new JButton("Mostra replays");
        replays.setActionCommand(ACTION_REPLAY);
        final JScrollPane rightScroll = new JScrollPane(rightJTable);
        final JScrollPane leftScroll = new JScrollPane(leftJTable);
        rightPane.add(replays, BorderLayout.NORTH);
        rightPane.add(borrar, BorderLayout.SOUTH);
        rightPane.add(rightScroll, BorderLayout.CENTER);
        leftPane.add(leftScroll, BorderLayout.CENTER);

        //configuracio basica de la nostra finestra
        setSize(700, 700);
        setTitle("Administrator view");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    /**
     * Registro de controlador de acciones
     * @param sController Controlador principal de la aplicacion servidor
     */
    public void controladorBoto(ServerController sController){
        leftJTable.addMouseListener(sController);
        borrar.addActionListener(sController);
        busca.addActionListener(sController);
        grafics.addActionListener(sController);
        replays.addActionListener(sController);
    }

    /**
     * @return JTable con lista de usuarios
     */
    public JTable getLeftTable() {
        return leftJTable;
    }

    /**
     * @return JTable de información de usuario seleccionado
     */
    public JTable getRightJTable() {
        return rightJTable;
    }

    /**
     * Actualiza en la vista el estado de conexión del usuario si es el que está seleccionado
     * @param username usuario al que actualizar estado
     * @param status estado de conexión del usuario
     */
    public void updateUserStatus(String username, boolean status){

        int rows = leftJTable.getModel().getRowCount();
        JTableModel model = (JTableModel) leftJTable.getModel();

        int userRow = -1;
        String selectedUser = "";

        for(int i = 0; i < rows; i++){

            selectedUser = (String) model.getValueAt(i, 0);

            if(selectedUser.contains(username)){
                userRow = i;
                break;
            }
        }

        if(userRow != -1 && selectedUser.contains(username)){

            if(status) {
                model.setValueAt(username + "(Online)", userRow, 0);
                leftJTable.setModel(model);
            }
            else if(selectedUser.contains("(Online)")){
                model.setValueAt(username, userRow, 0);
                leftJTable.setModel(model);
            }
        }
    }

    /**
     * Actualiza la lista de usuarios
     * @param leftList Lista de usuarios
     */
    public void setTable(JTable leftList) {
        this.leftJTable = leftList;
    }

    /**
     * @return Campo de texto de busqueda
     */
    public JTextField getBuscador() {
        return buscador;
    }

    public void mostraError() {
        String[] options = { "OK" };
        JOptionPane.showOptionDialog(this, "Error! Se debe seleccionar un usuario a borrar haciendo doble click sobre el mismo",
                "ERROR AL INICIAR BORRAR USUARIO", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                null, options, options[0]);

    }
}
