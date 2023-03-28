package tvpoo.Strategy.Context;

import tvpoo.Movies.Movie;
import tvpoo.Strategy.Interfaces.Remove;

import java.util.ArrayList;

public final class ContextRemove {
    private final Remove remove;
    public ContextRemove(final Remove remove) {
        this.remove = remove;
    }

    /**
     * Executes method remove, remove movies depending
     * on country of actors & genres list
     * @param movies full list with its movies
     * @param types  this parameter is essential, now depends on
     *               type of the object created
     */
    public void executeRemove(final ArrayList<Movie> movies,
                              final ArrayList<String> types) {
        remove.remove(movies, types);
    }
}
