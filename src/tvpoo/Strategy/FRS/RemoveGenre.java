package tvpoo.Strategy.FRS;

import java.util.ArrayList;
import java.util.stream.IntStream;

import tvpoo.Movies.Movie;
import tvpoo.Strategy.Interfaces.Remove;

public final class RemoveGenre implements Remove {
    /**
     * Remove from movie list the movie which contains at least
     * one of the genre mentioned in the genres
     * @param movies list with all the movies from the database
     * @param genres list with all actors wanted to be not seen
     *               in user movie list
     */
    @Override
    public void remove(final ArrayList<Movie> movies,
                       final ArrayList<String> genres) {
        IntStream
                .iterate(movies.size() - 1, k -> k > -1, k -> k - 1)
                .mapToObj(movies::get).forEachOrdered(movie -> {
                    ArrayList<String> genreList = movie.getGenres();
                    genres.stream().filter(genre -> !genreList.contains(genre))
                            .map(genre -> movie).forEachOrdered(movies::remove);
                });
    }
}
