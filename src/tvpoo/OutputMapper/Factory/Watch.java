package tvpoo.OutputMapper.Factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;
import tvpoo.Movies.Movie;

import tvpoo.InputMapper.InputMapper;
import tvpoo.OutputMapper.Singleton.InstanceAction;
import tvpoo.OutputMapper.Singleton.OutputActions;

import static tvpoo.OutputMapper.Error.errorStandard;

public class Watch implements ActionMovie {
    public Watch() {
    }
    /**
     * Factory method for adding a new movie in "watched list" only if the
     * movie wasn't seen & or the name of the movie exists in database
     * @param a output actions
     * @param moviesAction instance with methods for writing in result_basic
     * @param objectMapper object that maps nodes into json file
     * @param inputMapper object that takes nodes from json file
     * @param moviesListArrNode ArrayNode with all the movies from user
     */
    @Override
    public void actionMovie(final OutputActions a,
                            final InstanceAction moviesAction,
                            final ObjectMapper objectMapper,
                            final InputMapper inputMapper,
                            final ArrayNode moviesListArrNode) {

        a.setChecked(false);
        a.setMovie(new Movie());
        //get previous movie, from previous action
        String movie = a.getAction().getMovie();
        if (movie == null) {
            movie = a.getCurrentMovie();
        }

        ArrayList<Movie> movies = a.getUserLoggedIn().getWatchedMovies();
        ArrayList<Movie> purchased = a.getUserLoggedIn().getPurchasedMovies();

        for (Movie m : purchased) {

            String name = m.getName();

            if (name.equals(movie)) {

                a.setMovie(m);
                boolean verify = movies.stream().map(Movie::getName).noneMatch(name::equals);

                if (verify) {
                    movies.add(m);
                }

                a.setChecked(true);
                break;
            }
        }

        if (a.isChecked()) {
            moviesAction.showMovie(objectMapper,
                    a.getMovie(), inputMapper,
                    a.getCurrent(), a.getObjectNode());
            a.getArrayNode().add(a.getObjectNode());
        } else {
            errorStandard(moviesListArrNode, a);
        }
    }
}
