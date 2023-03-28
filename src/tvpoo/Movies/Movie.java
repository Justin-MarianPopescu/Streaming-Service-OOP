package tvpoo.Movies;

import java.util.ArrayList;
public final class Movie {
    /**
     * All movies have these members:
     * 1. name - title of the movie
     * 2. year - year of publishing
     * 3. duration - time that movies takes to watch
     * 4. genres - all genres that movie has: comedy, drama, horror, ...
     * 5. actors - all the actors involved in the movie
     * 6. countriesBanned - countries in which the movie can't be seen by users
     * 7. nrLikes - number of likes that a movie received from all users
     * 8. nrRatings - number of ratings that a movie received from all users
     * 9. rating - average rating of the movie obtained from all users that gave rating
     */
    private String name = "";
    private String year = "";
    private double rating;
    private int duration;
    private int numLikes;
    private int numRating;
    private ArrayList<String> genres = new ArrayList<>();
    private ArrayList<String> actors = new ArrayList<>();
    private ArrayList<String> countriesBanned = new ArrayList<>();
    /**
     * @return - obtain the total nr of likes that movie has received
     */
    public int getNumLikes() {
        return numLikes;
    }
    /**
     * @param numLikes - change the total nr of likes that movie has received
     */
    public void setNumLikes(final int numLikes) {
        this.numLikes = numLikes;
    }
    /**
     * @return - obtain the total nr of ratings that movie has received
     */
    public int getNumRating() {
        return numRating;
    }
    /**
     * @param numRating - change the total nr of ratings that movie has received
     */
    public void setNumRating(final int numRating) {
        this.numRating = numRating;
    }
    /**
     * @return - obtain one of the rating values of that movie has received
     */
    public double getRating() {
        return rating;
    }
    /**
     * @param rating - change one of the rating values of that movie has received
     */
    public void setRating(final double rating) {
        this.rating = rating;
    }
    /**
     * @return - obtain the name of the movie
     */
    public String getName() {
        return name;
    }
    /**
     * @param name - change the name of the movie
     */
    public void setName(final String name) {
        this.name = name;
    }
    /**
     * @return - obtain the year that the movie was published
     */
    public String getYear() {
        return year;
    }
    /**
     * @param year - change the year that the movie was published
     */
    public void setYear(final String year) {
        this.year = year;
    }
    /**
     * @return - obtain the time that movie takes you to see
     */
    public int getDuration() {
        return duration;
    }
    /**
     * @param duration - change the time that movie takes you to see
     */
    public void setDuration(final int duration) {
        this.duration = duration;
    }
    /**
     * @return - obtain the full list of genres that movies has
     */
    public ArrayList<String> getGenres() {
        return genres;
    }
    /**
     * @param genres - change the full list of genres that movies has
     */
    public void setGenres(final ArrayList<String> genres) {
        this.genres = genres;
    }
    /**
     * @return - obtain the full list of actors that played in a movie
     */
    public ArrayList<String> getActors() {
        return actors;
    }
    /**
     * @param actors - change the full list of actors that played in a movie
     */
    public void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }
    /**
     * @return - obtain the full list of countries in which the movie is banned
     */
    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }
    /**
     * @param countriesBanned - change the full list of countries in which the movie is banned
     */
    public void setCountriesBanned(final ArrayList<String> countriesBanned) {
        this.countriesBanned = countriesBanned;
    }
}
