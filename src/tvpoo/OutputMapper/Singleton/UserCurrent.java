package tvpoo.OutputMapper.Singleton;

import tvpoo.Users.User;

public final class UserCurrent {
    private static UserCurrent instance = null;
    private final User user = new User();
    private UserCurrent() {
        //constructor for checkstyle
    }

    /**
     * @return instance in which we obtain
     * all infos about current user, Singleton - lazy init
     */
    public static UserCurrent getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new UserCurrent();
        return instance;
    }

    /**
     * @return obtain the current user
     */
    public User getUser() {
        return user;
    }
}
