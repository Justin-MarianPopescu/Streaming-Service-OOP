package tvpoo.Strategy.FRS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import tvpoo.Movies.Movie;
import tvpoo.Strategy.Interfaces.Sort;

public final class SortRating implements Sort {
    /**
     * Sorts movies after "rate" in
     * increasing/decreasing order depending on rating
     * @param movies list with all the movies from the database
     * @param rating mentions the way of sorting the array
     */
    public void sort(final ArrayList<Movie> movies,
                     final String rating) {
        movies.sort(Comparator.comparingDouble(Movie::getRating));
        if ("decreasing".equals(rating)) {
            Collections.reverse(movies);
        }
    }
}
