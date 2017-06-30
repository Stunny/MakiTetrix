package network;

import model.User;

/**
 * Interface that defines how the user login and register for the MakeTetrix app should behave
 * Created by avoge on 04/04/2017.
 */
public interface UserAccessRepository {

    /**
     * Sends an existing user's credentials to the server
     * @return True if login success
     */
    void login(User user);

    /**
     * Sends a new user's credentials to the server.
     * @return True if register success
     */
    void register(User user);

    /**
     * Tells the server to close connection with the user.
     * @return True if logout success
     */
    boolean logout();

    /**
     * Checks if the username is available for use
     * @param userName Desired username by the user
     * @return true if available
     */
    boolean checkUserName(String userName);

    /**
     * Checks if email is available for use
     * @param userEmail Registering user's email
     * @return true if not previously registered
     */
    boolean checkEmail(String userEmail);


    String response();

    /**
     * Treats server's response
     * @return True if response is satisfactory. False if otherwise
     */
    boolean tractaResposta();

    /**
     * Sends to server the selected user to espectate from current client
     * @param userNameToEspectate User name selected to espectate
     */
    void sendUserToEspectate(String userNameToEspectate);
}
