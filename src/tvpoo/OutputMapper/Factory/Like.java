package tvpoo.OutputMapper.Factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;
import tvpoo.Movies.Movie;
import tvpoo.InputMapper.InputMapper;

import tvpoo.OutputMapper.Error;
import tvpoo.OutputMapper.Singleton.InstanceAction;
import tvpoo.OutputMapper.Singleton.OutputActions;

public class Like implements ActionMovie {
    public Like() {
    }
    /**
     * Factory method for like a movie in "liked list"
     * only if the movie exists & was watched before & wasn't liked before
     * @param a output actions
     * @param objectMapper object that maps nodes into json file
     * @param inputMapper object that takes nodes from json file
     * @param moviesAction instance with methods for writing in result_basic
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

        String movie = a.getAction().getMovie();
        //get previous movie, from previous action
        if (movie == null) {
            movie = a.getCurrentMovie();
        }

        ArrayList<Movie> watched = a.getUserLoggedIn().getWatchedMovies();
        ArrayList<Movie> liked = a.getUserLoggedIn().getLikedMovies();

        for (Movie m : watched) {

            if (m.getName().equals(movie) && (!liked.contains(m))) {

                m.setNumLikes( m.getNumLikes() + 1);
                a.setMovie(m);
                liked.add(m);

                a.setChecked(true);
                break;
            }
        }

        if (a.isChecked()) {
            moviesAction.showMovie(objectMapper, a.getMovie(),
                    inputMapper, a.getCurrent(), a.getObjectNode());
        } else {
            Error.modifyErrorStandard(moviesListArrNode, a);
        }

        a.getArrayNode().add(a.getObjectNode());
    }
}
