package tvpoo.Strategy.FRS;

import java.util.ArrayList;
import java.util.stream.IntStream;

import tvpoo.Movies.Movie;
import tvpoo.Strategy.Interfaces.Remove;

public final class RemoveActor implements Remove {
    /**
     * Remove from movie list the movie which contains at least
     * one of the actors mentioned in the actors
     * @param movies list with all the movies from the database
     * @param actors list with all actors wanted to be not seen
     *               in user movie list
     */
    @Override
    public void remove(final ArrayList<Movie> movies,
                       final ArrayList<String> actors) {
        IntStream
                .iterate(movies.size() - 1, k -> k > -1, k -> k - 1)
                .mapToObj(movies::get)
                .forEachOrdered(movie -> {
                    ArrayList<String> actorList = movie.getActors();
                    actors
                            .stream()
                            .filter(actor -> !actorList.contains(actor))
                            .map(actor -> movie).forEachOrdered(movies::remove);
                });
    }
}
