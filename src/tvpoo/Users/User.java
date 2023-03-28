package tvpoo.Users;

import java.util.ArrayList;

import tvpoo.Movies.Movie;
import tvpoo.Movies.RatedMovie;

public final class User {
    /**
     * tokensCount - total nr of tokens that are in the account
     * numFreePremiumMovies - total nr of free movies that are in the account
     * credentials - information about the user
     * purchased - list of purchased movies
     * watched, liked, rated - same as purchased
     * subscribeGenres (E2) - list of genres that is subscribed
     * gradeRatedMovie - list of each movie associated with a grade
     *                  RatedMovie has a structure as a dictionary
     * notifications (E2) - list with all notifications
     *                  Notification is coming after ops ADD/DELETE from DATABASE
     */
    public static final int NR_PREMIUM_MOVIES = 15;
    private int tokensCount;
    private int numFreePremiumMovies = NR_PREMIUM_MOVIES;
    private Credentials credentials;
    private ArrayList<Movie> purchasedMovies = new ArrayList<>();
    private ArrayList<Movie> watchedMovies = new ArrayList<>();
    private ArrayList<Movie> likedMovies = new ArrayList<>();
    private ArrayList<Movie> ratedMovies = new ArrayList<>();
    private final ArrayList<String> subscribedGenres = new ArrayList<>();
    private final ArrayList<RatedMovie> gradeRatedMovies = new ArrayList<>();
    private ArrayList<Notification> notifications = new ArrayList<>();
    /**
     * @return retain for each movie watched the rating given by user
     */
    public ArrayList<RatedMovie> getGradeRatedMovies() {
        return gradeRatedMovies;
    }
    /**
     * @return retain all the notifications if a movie was added/deleted
     */
    public ArrayList<Notification> getNotifications() {
        return notifications;
    }
    /**
     * @return obtain all the favourite genres that the user is subscribed
     */
    public ArrayList<String> getSubscribedGenres() {
        return subscribedGenres;
    }
    /**
     * @param notifications modify a notification
     */
    public void setNotifications(final ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }
    /**
     * @return obtain the credentials of the user
     */
    public Credentials getCredentials() {
        return credentials;
    }
    /**
     * @param credentials modify the credentials of the user
     */
    public void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }
    /**
     * @return obtain the number of tokens that are in the account now
     */
    public int getTokensCount() {
        return tokensCount;
    }
    /**
     * @param tokensCount modify the nr of tokens that are in the account
     *                    in case of buying/spending the tokens
     */
    public void setTokensCount(final int tokensCount) {
        this.tokensCount = tokensCount;
    }
    /**
     * @return is used for PREMIUM users, for STANDARD users is 0
     * in case if a user become a PREMIUM one the nr of FREE MOVIES will be 15
     */
    public int getNumFreePremiumMovies() {
        return numFreePremiumMovies;
    }
    /**
     * @param numFreePremiumMovies modify the nr of FreeMovies that are in the account
     *                             in case of buying/spending the tokens
     */
    public void setNumFreePremiumMovies(final int numFreePremiumMovies) {
        this.numFreePremiumMovies = numFreePremiumMovies;
    }
    /**
     * @return obtain all the movies that are purchased
     */
    public ArrayList<Movie> getPurchasedMovies() {
        return purchasedMovies;
    }
    /**
     * @param purchasedMovies modify the list of purchased movies
     */
    public void setPurchasedMovies(final ArrayList<Movie> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }
    /**
     * @return obtain all the movies that are watched
     */
    public ArrayList<Movie> getWatchedMovies() {
        return watchedMovies;
    }
    /**
     * @param watchedMovies modify the list of watched movies
     */
    public void setWatchedMovies(final ArrayList<Movie> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }
    /**
     * @return obtain all the movies that are liked
     */
    public ArrayList<Movie> getLikedMovies() {
        return likedMovies;
    }
    /**
     * @param likedMovies modify the list of watched movies
     */
    public void setLikedMovies(final ArrayList<Movie> likedMovies) {
        this.likedMovies = likedMovies;
    }
    /**
     * @return obtain the list of rated movies
     */
    public ArrayList<Movie> getRatedMovies() {
        return ratedMovies;
    }
    /**
     * @param ratedMovies modify the list of rated movies
     */
    public void setRatedMovies(final ArrayList<Movie> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }
}
