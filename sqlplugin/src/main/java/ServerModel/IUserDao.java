package ServerModel;

import java.util.List;

import modeling.User;

/**
 * Created by tyler on 12/5/2017.
 */

interface IUserDao {

    boolean registerUser(String userName, String password);

    User verifyUser(String name, String password);

    boolean clear();

    List<User> getAllUsers();
}
