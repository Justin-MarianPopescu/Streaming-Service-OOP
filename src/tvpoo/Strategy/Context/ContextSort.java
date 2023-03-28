package tvpoo.Strategy.Context;

import tvpoo.Movies.Movie;
import tvpoo.Strategy.Interfaces.Sort;

import java.util.ArrayList;

public final class ContextSort {
    private final Sort sort;
    public ContextSort(final Sort sort) {
        this.sort = sort;
    }

    /**
     * Executes method sort, sort movies depending
     * on duration & rate movies list
     * @param movies full list with its movies
     * @param type this parameter is essential, now depends on
     *             type of the object created
     */
    public void executeSort(final ArrayList<Movie> movies,
                            final String type) {
        sort.sort(movies, type);
    }
}
