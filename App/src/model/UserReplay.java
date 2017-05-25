package model;

import java.util.Date;

/**
 * Created by natal on 20/5/2017.
 */
public class UserReplay {

    private Date date;
    private int puntuacion;
    private int tiempo;
    private int picoEspectadores;

    public UserReplay(Date date, int puntuacion, int tiempo, int picoEspectadores){
        this.date = date;
        this.puntuacion = puntuacion;
        this.tiempo = tiempo;
        this.picoEspectadores = picoEspectadores;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public int getPicoEspectadores() {
        return picoEspectadores;
    }

    public void setPicoEspectadores(int picoEspectadores) {
        this.picoEspectadores = picoEspectadores;
    }
}
