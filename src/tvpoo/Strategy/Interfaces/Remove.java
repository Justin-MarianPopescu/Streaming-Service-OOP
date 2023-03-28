package tvpoo.Strategy.Interfaces;

import java.util.ArrayList;

import tvpoo.Movies.Movie;

public interface Remove {
    /**
     * Only the signature of method
     * @param movies list with all the movies from the database
     * @param types this parameter is essential, data offered by user
     */
    void remove(ArrayList<Movie> movies, ArrayList<String> types);
}
