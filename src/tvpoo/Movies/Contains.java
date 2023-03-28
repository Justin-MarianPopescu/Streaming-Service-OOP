package tvpoo.Movies;

import java.util.ArrayList;
public final class Contains {
    /**
     * Contains is used for movies that contain specific actors & genres of movies
     */
    private ArrayList<String> actors = new ArrayList<>();
    private final ArrayList<String> genre = new ArrayList<>();
    /**
     * @return - obtain a list of actors that are from the given movie
     */
    public ArrayList<String> getActors() {
        return actors;
    }
    /**
     * @param actors - change the list of actors that are from the given movie
     */
    public void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }
    /**
     * @return - obtain the types of movie it is: horror, comedy, ...
     */
    public ArrayList<String> getGenre() {
        return genre;
    }
}
