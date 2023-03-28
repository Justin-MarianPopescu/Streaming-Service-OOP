package tvpoo.OutputMapper.Database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;
import tvpoo.Movies.Movie;

import tvpoo.InputMapper.InputMapper;
import tvpoo.OutputMapper.Error;
import tvpoo.OutputMapper.Singleton.InstanceAction;
import tvpoo.OutputMapper.Singleton.OutputActions;

import static tvpoo.OutputMapper.Error.errorStandard;

public final class SeeDetails {
    private SeeDetails() {
        //constructor for checkstyle
    }
    /**
     * @param a output actions
     * @param objectMapper object that maps nodes into json file
     * @param inputMapper object that takes nodes from json file
     * @param moviesListArrNode ArrayNode with all the movies that user has
     * @param movieName name of the movie user wants to see details
     * @param actionOnMovie instance that writes in result json, show methods
     * @param listMovies list with all the movies
     */
    public static void seeDetailsMovieChangePage(final ArrayNode moviesListArrNode,
                                                 final String movieName,
                                                 final OutputActions a,
                                                 final InstanceAction actionOnMovie,
                                                 final ObjectMapper objectMapper,
                                                 final InputMapper inputMapper,
                                                 final ArrayList<Movie> listMovies) {

        if (!a.isPrevFilter()) {
            ArrayList<Movie> list = a.getMovieList();
            for (Movie movie : list) {
                String name = movie.getName();
                if (!name.equals(movieName)) {
                    continue;
                }

                ArrayList<String> countriesBanned = movie.getCountriesBanned();
                String country = a.getUserLoggedIn().getCredentials().getCountry();

                if (countriesBanned.contains(country)) {
                    break;
                }

                a.setMovie(movie);

                a.setChecked(true);
                break;
            }
        } else {

            for (int i = 0; i < a.getDetailsMovieList().size(); i++) {
                Movie movie = a.getDetailsMovieList().get(i);

                String name = movie.getName();
                if (!movieName.equals(name)) {
                    continue;
                }

                ArrayList<String> countriesBanned = movie.getCountriesBanned();
                String country = a.getUserLoggedIn().getCredentials().getCountry();

                if (countriesBanned.contains(country)) {
                    break;
                }

                a.setMovie(movie);

                a.setChecked(true);
                break;
            }
        }

        if (a.isChecked()) {
            a.getPageList().add(a.getCurrentPage());
            a.setCurrentPage(a.getPage());
            actionOnMovie.showDetailsMovie(a,
                    objectMapper, inputMapper, listMovies);

        } else {
            a.setCurrentMovie(String.valueOf(movieName));
            Error.modifyErrorStandard(moviesListArrNode, a);
        }

        a.getArrayNode().add(a.getObjectNode());
    }

    /**
     * @param a output actions
     * @param objectMapper object that maps nodes into json file
     * @param inputMapper object that takes nodes from json file
     * @param moviesListArrNode ArrayNode with all the movies that user has
     */
    public static void seeDetailsPageChangePage(final OutputActions a,
                                                final ObjectMapper objectMapper,
                                                final InstanceAction moviesAction,
                                                final InputMapper inputMapper,
                                                final ArrayNode moviesListArrNode) {
        if ("movies".equals(a.getPage())) {

            a.getPageList().add(a.getCurrentPage());
            a.setMovieList(new ArrayList<>());
            a.setCurrentPage(a.getPage());

            moviesAction.showPageMovies(objectMapper, a, inputMapper);

            a.getArrayNode().add(a.getObjectNode());

        } else if ("homepage autentificat|upgrades".matches(a.getPage())) {

            a.getPageList().add(a.getCurrentPage());
            a.setCurrentPage(a.getPage());

        } else if ("logout".equals(a.getPage())) {

            a.getPageList().clear();
            a.setCurrentPage("homepage neautentificat");

        } else {
            errorStandard(moviesListArrNode, a);
        }
    }
}
