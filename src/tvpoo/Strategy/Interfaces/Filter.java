package tvpoo.Strategy.Interfaces;

import java.util.ArrayList;

import tvpoo.Movies.Movie;
import tvpoo.Users.User;

public interface Filter {
    /**
     * Only the signature of method
     * @param userLoggedIn current User logged in
     * @param movies full list with its movies
     */
    void filter(User userLoggedIn, ArrayList<Movie> movies);
}
