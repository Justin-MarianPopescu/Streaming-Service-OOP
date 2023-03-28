package tvpoo.Strategy.Interfaces;

import java.util.ArrayList;

import tvpoo.Movies.Movie;

public interface Sort {
    /**
     * Only the signature of the method
     * @param movies list with all the movies from database
     * @param type this parameter is essential, data offered by user
     */
    void sort(ArrayList<Movie> movies, String type);
}
