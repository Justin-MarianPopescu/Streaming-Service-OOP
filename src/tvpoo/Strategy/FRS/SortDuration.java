package tvpoo.Strategy.FRS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import tvpoo.Movies.Movie;
import tvpoo.Strategy.Interfaces.Sort;

public final class SortDuration implements Sort {
    /**
     * Sorts movies after "duration" in
     * increasing/decreasing order depending on time
     * @param movies list with all the movies from the database
     * @param time mentions the way of sorting the array
     */
    @Override
    public void sort(final ArrayList<Movie> movies,
                     final String time) {
        movies.sort(Comparator.comparingInt(Movie::getDuration));
        if ("decreasing".equals(time)) {
            Collections.reverse(movies);
        }
    }
}
