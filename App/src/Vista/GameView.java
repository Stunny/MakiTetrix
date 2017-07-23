package Vista;

import model.Pieza;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana del juego.
 * Created by jorti on 31/03/2017.
 */
public class GameView extends JFrame{
    private JPanel[][]caselles;
    private JPanel centre;
    private JLabel temps;
    private JLabel nivel;
    private JLabel puntuacion;
    private JPanel[][] siguientepieza;
    private JLabel observador;
    private int nespectadors;

    /**
     * Genera la ventana para jugar con los valores iniciales.
     */
    public GameView(){

        nespectadors = 0;
        setTitle("MakiTetrix - Game");
        setLocationRelativeTo(null);
        setSize(432,800);

        //Pantalla principal del juego
        centre = new JPanel(new GridLayout(25,10));
        centre.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        caselles = new JPanel[25][10];

        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 10; j++) {
                JPanel p = new JPanel();
                p.setBackground(Color.yellow);
                caselles[i][j] = p;
                caselles[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                centre.add(p);
            }
        }
        //Información necesaria en la parte de arriba del nivel

        temps = new JLabel ("00:00");
        nivel = new JLabel ("Nivel: 1");
        puntuacion = new JLabel ("Puntos: 0");

        JPanel north = new JPanel (new FlowLayout());
        north.add(temps);
        north.add(nivel);
        north.add(puntuacion);

        getContentPane().add(north, BorderLayout.NORTH);

        //Siguiente figura.
        JPanel east = new JPanel(new GridLayout(2,1));
        siguientepieza = new JPanel[4][4];
        JPanel auxsiguientepieza = new JPanel(new GridLayout(4,4));
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                JPanel p = new JPanel();
                p.setBackground(Color.blue);
                siguientepieza[i][j] = p;
                siguientepieza[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                auxsiguientepieza.add(p);
            }
        }
        east.add(auxsiguientepieza);

        //Se unen el panel del juego como el de proxima pieza
        JPanel auxmid = new JPanel (new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.ipadx = 144;
        c.ipady = 384;
        c.gridx = 0;
        c.gridy = 0;
        auxmid.add(centre,c);
        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.PAGE_START;
        c.ipadx = 50;
        c.ipady = 72;
        c.gridx = 2;
        c.gridy = 0;
        auxmid.add(east,c);

        getContentPane().add(auxmid,BorderLayout.CENTER);

        //Creación panel de abajo para que se vean tus observadores

        JPanel observadores = new JPanel (new FlowLayout());
        observador = new JLabel ("Numero de espectadores:"+nespectadors);
        observadores.add(observador);

        getContentPane().add(observadores, BorderLayout.SOUTH);

    }

    /**
     * Printa la interfaz del juego en la array de JPanel caselles.
     *
     * @param interfaz      Valores que se recibin de la matriz.
     */
    public void printarPantalla (int[][] interfaz){
        for (int i = 0; i < interfaz.length; i++){
            for (int j = 0; j < interfaz[0].length; j++){
                switch (interfaz[i][j]){
                    case 0:
                        caselles[i][j].setBackground(Color.YELLOW);
                        break;
                    case 1:
                        caselles[i][j].setBackground(Color.CYAN);
                        break;
                    case 2:
                        caselles[i][j].setBackground(Color.RED);
                        break;
                    case 3:
                        caselles[i][j].setBackground(Color.GREEN);
                        break;
                    case 4:
                        caselles[i][j].setBackground(Color.ORANGE);
                        break;
                    case 5:
                        caselles[i][j].setBackground(Color.PINK);
                        break;
                    case 6:
                        caselles[i][j].setBackground(Color.MAGENTA);
                        break;
                    default:
                        caselles[i][j].setBackground(Color.white);
                        break;
                }
            }
        }
    }

    /**
     * Identifica la pieza, y la representa en la matriz de
     * JPanles siguientepieza.
     * @param piece Pieza a representar.
     */
    public void printarNextPiece (Pieza piece){
        Pieza aux = piece.clone();
        int[][] nextpiece = new int[4][4];
        for (int i = 0; i < nextpiece.length; i++){
            for (int j = 0; j < nextpiece[i].length; j++){
                nextpiece[i][j] = -1;
            }
        }
        aux.setPosx(1);
        aux.setPosy(1);
        switch (aux.getTipo()){
            case 0:
                nextpiece[aux.getPosx()][aux.getPosy()] = aux.getTipo();
                nextpiece[aux.getPosx()][aux.getPosy()+1] = aux.getTipo();
                nextpiece[aux.getPosx()+1][aux.getPosy()] = aux.getTipo();
                nextpiece[aux.getPosx()+1][aux.getPosy()+1] = aux.getTipo();
                break;
            case 1:
                nextpiece[aux.getPosx()-1][aux.getPosy()] = aux.getTipo();
                nextpiece[aux.getPosx()][aux.getPosy()] = aux.getTipo();
                nextpiece[aux.getPosx()+1][aux.getPosy()] = aux.getTipo();
                nextpiece[aux.getPosx()+2][aux.getPosy()] = aux.getTipo();
                break;
            case 2:
                nextpiece[aux.getPosx()-1][aux.getPosy()-1] = aux.getTipo();
                nextpiece[aux.getPosx()-1][aux.getPosy()] = aux.getTipo();
                nextpiece[aux.getPosx()][aux.getPosy()] = aux.getTipo();
                nextpiece[aux.getPosx()][aux.getPosy()+1] = aux.getTipo();
                break;
            case 3:
                nextpiece[aux.getPosx()][aux.getPosy()-1] = aux.getTipo();
                nextpiece[aux.getPosx()-1][aux.getPosy()] = aux.getTipo();
                nextpiece[aux.getPosx()][aux.getPosy()] = aux.getTipo();
                nextpiece[aux.getPosx()-1][aux.getPosy()+1] = aux.getTipo();
                break;
            case 4:
                nextpiece[aux.getPosx()][aux.getPosy()-1] = aux.getTipo();
                nextpiece[aux.getPosx()][aux.getPosy()] = aux.getTipo();
                nextpiece[aux.getPosx()][aux.getPosy()+1] = aux.getTipo();
                nextpiece[aux.getPosx()-1][aux.getPosy()+1] = aux.getTipo();
                break;
            case 5:
                nextpiece[aux.getPosx()-1][aux.getPosy()-1] = aux.getTipo();
                nextpiece[aux.getPosx()][aux.getPosy()-1] = aux.getTipo();
                nextpiece[aux.getPosx()][aux.getPosy()] = aux.getTipo();
                nextpiece[aux.getPosx()][aux.getPosy()+1] = aux.getTipo();
                break;
            case 6:
                nextpiece[aux.getPosx()][aux.getPosy()-1] = aux.getTipo();
                nextpiece[aux.getPosx()-1][aux.getPosy()] = aux.getTipo();
                nextpiece[aux.getPosx()][aux.getPosy()] = aux.getTipo();
                nextpiece[aux.getPosx()][aux.getPosy()+1] = aux.getTipo();
                break;
        }

        for (int i = 0; i < siguientepieza.length; i++){
            for (int j = 0; j < siguientepieza[i].length; j++){
                try {
                    switch (nextpiece[i][j]) {
                        case 0:
                            siguientepieza[i][j].setBackground(Color.YELLOW);
                            break;
                        case 1:
                            siguientepieza[i][j].setBackground(Color.CYAN);
                            break;
                        case 2:
                            siguientepieza[i][j].setBackground(Color.RED);
                            break;
                        case 3:
                            siguientepieza[i][j].setBackground(Color.GREEN);
                            break;
                        case 4:
                            siguientepieza[i][j].setBackground(Color.ORANGE);
                            break;
                        case 5:
                            siguientepieza[i][j].setBackground(Color.PINK);
                            break;
                        case 6:
                            siguientepieza[i][j].setBackground(Color.MAGENTA);
                            break;
                        default:
                            siguientepieza[i][j].setBackground(Color.GRAY);
                            break;
                    }
                } catch (ArrayIndexOutOfBoundsException aioobe){
                    siguientepieza[i][j].setBackground(Color.GRAY);
                }
            }
        }

    }

    public int saveGame() {
        Object[] options = {"Save", "Don't Save"};
        return JOptionPane.showOptionDialog (this, "Do you want save the game?",
                "Save Game?", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                options, options[0]);
    }

    public int pauseGame() {
        String[] options = {"Continue"};
        return JOptionPane.showOptionDialog(this, "Press continue for resume the game",
                "PAUSE", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                options, options[0]);
    }

    //Getters && Setters

    public void setTemps (int time){
        int min = time/60;
        int seg = time%60;
        if (min < 10 && seg < 10){
            temps.setText("0" + min + ":0" + seg);
        } else if (min < 10 && seg >= 10){
            temps.setText("0" + min + ":" + seg);
        } else if (min >= 10 && seg < 10){
            temps.setText(min + ":0" + seg);
        } else if (min >= 10 && seg >= 10){
            temps.setText(min + ":" + seg);
        }
    }

    public void setNivel(int nivel){
        this.nivel.setText("Nivel: " + nivel);
    }

    public void setPuntuacion (int puntuacion){
        this.puntuacion.setText("Puntos: " + puntuacion);
    }

    public JLabel getTemps() {
        return temps;
    }

    public void setNespectadors (int e){
        nespectadors = e;
    }

}