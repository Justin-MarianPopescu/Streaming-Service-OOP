package tvpoo.Strategy.FRS;

import java.util.ArrayList;
import java.util.stream.IntStream;

import tvpoo.Movies.Movie;
import tvpoo.Users.User;
import tvpoo.Strategy.Interfaces.Filter;

public final class FilterCountry implements Filter {
    /**
     * Remove from list of the use movies
     * Only the that is BANNED in the user country
     * @param userLoggedIn current User logged in
     * @param movies full list with its movies
     */
    public void filter(final User userLoggedIn,
                       final ArrayList<Movie> movies) {
        String country = userLoggedIn.getCredentials().getCountry();
        IntStream.iterate(movies.size() - 1, j -> j > -1, j -> j - 1)
                .forEachOrdered(j -> {
                    Movie movie = movies.get(j);
                    if (movie.getCountriesBanned().contains(country)) {
                        movies.remove(j);
                    }
                });
    }
}
