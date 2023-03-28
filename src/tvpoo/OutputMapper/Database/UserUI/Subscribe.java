package tvpoo.OutputMapper.Database.UserUI;

import java.util.ArrayList;
import com.fasterxml.jackson.databind.node.ArrayNode;

import tvpoo.Movies.Movie;
import tvpoo.OutputMapper.Singleton.OutputActions;

import static tvpoo.OutputMapper.Error.errorStandard;

public final class Subscribe {
    private Subscribe() {
        //constructor for checkstyle
    }
    /**
     * Subscribe to a genre from a list of genres that movies have
     * @param moviesListArrNode ArrayNode with all the movies from user
     * @param a output actions
     * @param listMovies list with all the movies
     */
    public static void subscribeGenre(final OutputActions a,
                                      final ArrayNode moviesListArrNode,
                                      final ArrayList<Movie> listMovies) {
        int sizeMovieList = listMovies.size();
        if (sizeMovieList > 0) {
            String subscribedGenre = a.getAction().getSubscribedGenre();
            ArrayList<String> subscribedGenres = a.getUserLoggedIn().getSubscribedGenres();

            Movie movie = listMovies.get(0);
            ArrayList<String> movieGenres = movie.getGenres();

            boolean noMatch = subscribedGenres.stream().noneMatch(genre -> genre.equals(subscribedGenre));
            boolean match = movieGenres.stream().anyMatch(genre -> genre.equals(subscribedGenre));

            if (noMatch && match) {
                subscribedGenres.add(subscribedGenre);
            } else {
                errorStandard(moviesListArrNode, a);
            }
        } else {
            errorStandard(moviesListArrNode, a);
        }
    }
}
