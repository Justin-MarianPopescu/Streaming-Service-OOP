package tvpoo.Strategy.Context;

import tvpoo.Movies.Movie;
import tvpoo.Strategy.Interfaces.Filter;
import tvpoo.Users.User;

import java.util.ArrayList;

public final class ContextFilter {
    private final Filter filter;
    public ContextFilter(final Filter filter) {
        this.filter = filter;
    }

    /**
     * Executes method filter, filters movies depending
     * on country of user & movie
     * @param userLoggedIn current user logged in
     * @param movies full list with userLoggedIn movies
     */
    public void executeFilter(final User userLoggedIn,
                              final ArrayList<Movie> movies) {
        filter.filter(userLoggedIn, movies);
    }
}
