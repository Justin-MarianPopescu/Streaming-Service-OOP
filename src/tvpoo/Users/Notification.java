package tvpoo.Users;

public final class Notification {
    /**
     * name - name of the movie that has at least one genre
     *      that a user is subscribed to
     * message - ADD/DELETE depends on the ops made in Database
     */
    private String name = "";
    private String message = "";
    /**
     * @return obtain the movie name
     */
    public String getMovieName() {
        return name;
    }
    /**
     * @param name modify the name of the movie
     */
    public void setMovieName(final String name) {
        this.name = name;
    }
    /**
     * @return obtain the message ADD/DELETE
     */
    public String getMessage() {
        return message;
    }
    /**
     * @param message modify the message ADD/DELETE
     */
    public void setMessage(final String message) {
        this.message = message;
    }
}
