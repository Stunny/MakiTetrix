package model;

/**
 * Created by natal on 20/5/2017.
 */
public class LiveUser {

    private String userName;
    private int espectators;

    public LiveUser(String userName, int espectators){
        this.userName = userName;
        this.espectators = espectators;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getEspectators() {
        return espectators;
    }

    public void setEspectators(int espectators) {
        this.espectators = espectators;
    }
}
