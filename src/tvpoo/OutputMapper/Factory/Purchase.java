package tvpoo.OutputMapper.Factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;

import tvpoo.Movies.Movie;
import tvpoo.Users.Credentials;

import tvpoo.InputMapper.InputMapper;
import tvpoo.OutputMapper.Error;
import tvpoo.OutputMapper.Singleton.InstanceAction;
import tvpoo.OutputMapper.Singleton.OutputActions;

import static tvpoo.OutputMapper.Output.MOVIE_TOKEN;

public class Purchase implements ActionMovie {
    public Purchase() {
    }
    /**
     * Factory method for purchase a movie in "purchased list"
     * only if the movie wasn't:
     * purchased before, is not banned in the user country,
     * has enough tokens/free movies, these methods of buying
     * a movie depends on type of user
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

        ArrayList<Movie> movies = a.getUserLoggedIn().getPurchasedMovies();

        for (Movie m : a.getMovieList()) {

            String name = m.getName();

            ArrayList<String> banned = m.getCountriesBanned();

            String country = a.getUserLoggedIn().getCredentials().getCountry();

            if ((!movies.contains(m) && name.equals(movie)
                    && (!banned.contains(country)))) {

                a.setMovie(m);

                int tokensCount = a.getUserLoggedIn().getTokensCount();
                int numPremium = a.getUserLoggedIn().getNumFreePremiumMovies();

                Credentials credAccount = a.getUserLoggedIn().getCredentials();
                String account = credAccount.getAccountType();

                if ("premium".equals(account) && (numPremium > 0)) {
                    numPremium--;

                    a.getUserLoggedIn().setNumFreePremiumMovies(numPremium);

                    movies.add(m);
                    a.setChecked(true);
                } else if (tokensCount >= MOVIE_TOKEN) {
                    tokensCount -= MOVIE_TOKEN;

                    a.getUserLoggedIn().setTokensCount(tokensCount);

                    movies.add(m);
                    a.setChecked(true);
                }

                break;
            }
        }
        if (a.isChecked()) {
            moviesAction.showMovie(objectMapper,
                    a.getMovie(), inputMapper,
                    a.getCurrent(), a.getObjectNode());
        } else {
            Error.modifyErrorStandard(moviesListArrNode, a);
        }
        a.getArrayNode().add(a.getObjectNode());
    }
}
