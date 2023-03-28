package tvpoo.OutputMapper.Factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;
import tvpoo.Movies.Movie;
import tvpoo.Movies.RatedMovie;

import tvpoo.InputMapper.InputMapper;
import tvpoo.OutputMapper.Error;

import tvpoo.OutputMapper.Singleton.InstanceAction;
import tvpoo.OutputMapper.Singleton.OutputActions;

import static tvpoo.OutputMapper.Output.MAX_RATING;

public class Rate implements ActionMovie {
    public Rate() {
    }
    /**
     * Factory method for rating a movie in "rated list" only if the
     * movie wasn't rated & match known criteria:
     * rate is a grade from 1 to 5
     * to rate a movie, movie that was seen before
     * it can be modified an old rating with a new
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
        int rate = a.getAction().getRate();
        a.setMovie(new Movie());
        a.setChecked(false);

        if ((rate >= 1) && (rate <= MAX_RATING)) {
            //get previous movie, from previous action
            String movieName = a.getAction().getMovie();
            if (movieName == null) {
                movieName = a.getCurrentMovie();
            }

            ArrayList<Movie> ratedMovies = a.getUserLoggedIn().getRatedMovies();
            ArrayList<Movie> watchedMovies = a.getUserLoggedIn().getWatchedMovies();

            for (Movie movie : watchedMovies) {

                String name = movie.getName();
                double rating = movie.getRating();
                int numRating = movie.getNumRating();

                if (name.equals(movieName)) {
                    if (ratedMovies.contains(movie)) {
                        //MODIFY OLD RATING
                        int grade = 0;
                        ArrayList<RatedMovie> gradeRatedMovies =
                                a.getUserLoggedIn().getGradeRatedMovies();

                        for (RatedMovie gradeRatedMovie : gradeRatedMovies) {
                            String gradeName = gradeRatedMovie.getName();
                            if (!gradeName.equals(movieName)) {
                                continue;
                            }
                            grade = gradeRatedMovie.getRate();
                        }

                        a.setMovie(movie);

                        rating = (((rating * numRating) - grade) + rate) / numRating;
                        movie.setRating(rating);

                        a.setChecked(true);
                    } else {
                        //NEW RATING FOR MOVIE, never rated before
                        RatedMovie gradeRatedMovie = new RatedMovie();
                        gradeRatedMovie.setRate(rate);
                        gradeRatedMovie.setName(movieName);

                        ArrayList<RatedMovie> gradeRatedMovies =
                                a.getUserLoggedIn().getGradeRatedMovies();

                        gradeRatedMovies.add(gradeRatedMovie);
                        a.setMovie(movie);

                        if (numRating == 0) {
                            movie.setNumRating(1);
                            movie.setRating(rate);
                        } else {
                            rating = ((rating * numRating) + rate) / (numRating + 1);
                            numRating = numRating + 1;

                            movie.setNumRating(numRating);
                            movie.setRating(rating);
                        }

                        ratedMovies.add(movie);
                        a.setChecked(true);

                        break;
                    }
                }
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
