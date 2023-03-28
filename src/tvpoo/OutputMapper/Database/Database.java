package tvpoo.OutputMapper.Database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;

import tvpoo.Movies.Movie;
import tvpoo.Users.Credentials;
import tvpoo.Users.Notification;
import tvpoo.Users.User;

import tvpoo.InputMapper.InputMapper;
import tvpoo.OutputMapper.Singleton.InstanceAction;
import tvpoo.OutputMapper.Singleton.OutputActions;

import static tvpoo.OutputMapper.Error.errorStandard;
import static tvpoo.OutputMapper.Output.MOVIE_TOKEN;

public final class Database {
    private Database() {
        //constructor for checkstyle
    }
    /**
     * Remove a movie from list of movies
     * @param listMovies list with all the movies
     * @param deletedMovie movie that need to be deleted
     * @param posMovie position of the movie from the array
     * @param movie delete the movie if is in the database
     */
    public static void removeMovieArr(final ArrayList<Movie> listMovies,
                                      final String deletedMovie,
                                      final int posMovie,
                                      final Movie movie) {

        String movieName = movie.getName();
        if (movieName.equals(deletedMovie)) {
            listMovies.remove(posMovie);
        }
    }

    /**
     * Delete movie from liked, watched, rated lists
     * @param listMovies list with all the movies
     * @param deletedMovie movie that need to be deleted
     */
    public static void deleteMovieFromList(final ArrayList<Movie> listMovies,
                                           final String deletedMovie) {
        int position = 0;
        for (Movie mv : listMovies) {
            removeMovieArr(listMovies, deletedMovie, position, mv);
            position++;
        }
    }

    /**
     * Delete movie from list and write a message
     * @param listMovies list with all the movies
     * @param deletedMovie movie that need to be deleted
     * @param moviesListArrNode ArrayNode with all the movies from user
     * @param a output actions
     * @param inputMapper object that takes nodes from json file
     */
    public static void deleteMovie(final ArrayList<Movie> listMovies,
                                   final String deletedMovie,
                                   final ArrayNode moviesListArrNode,
                                   final OutputActions a,
                                   final InputMapper inputMapper) {

        int idxMovie = 0;
        a.setChecked(true);

        for (int i = 0, moviesSize = listMovies.size(); i < moviesSize; i++) {
            Movie movie = listMovies.get(i);
            String movieName = movie.getName();
            if (deletedMovie.equals(movieName)) {
                a.setChecked(false);
                idxMovie = i;
                break;
            }
        }

        if (!a.isChecked()) {

            ArrayList<User> users = inputMapper.getUsers();

            for (User user : users) {
                Credentials credentials = user.getCredentials();
                String accountType = credentials.getAccountType();
                Notification notification = new Notification();
                ArrayList<Movie> purchasedMvs = user.getPurchasedMovies();

                for (int i = 0, position = 0, purchasedMvsSize = purchasedMvs.size();
                     i < purchasedMvsSize; i++, position++) {
                    Movie purchasedMovie = purchasedMvs.get(i);

                    String purchasedName = purchasedMovie.getName();

                    if (purchasedName.equals(deletedMovie)) {
                        purchasedMvs.remove(position);

                        if ("standard".equals(accountType)) {
                            int tokensCount = user.getTokensCount() + MOVIE_TOKEN;
                            user.setTokensCount(tokensCount);
                        } else {
                            int numPremium = user.getNumFreePremiumMovies() + 1;
                            user.setNumFreePremiumMovies(numPremium);
                        }

                        ArrayList<Notification> listNotes = user.getNotifications();

                        notification.setMessage("DELETE");
                        notification.setMovieName(deletedMovie);

                        listNotes.add(notification);
                        break;
                    }
                }
                ArrayList<Movie> watchedMvs = user.getWatchedMovies();
                deleteMovieFromList(watchedMvs, deletedMovie);

                ArrayList<Movie> likedMvs = user.getLikedMovies();
                deleteMovieFromList(likedMvs, deletedMovie);

                ArrayList<Movie> ratedMvs = user.getRatedMovies();
                deleteMovieFromList(ratedMvs, deletedMovie);
            }

            listMovies.remove(idxMovie);
        } else {
            errorStandard(moviesListArrNode, a);
        }
    }

    /**
     * Add movie into the list and write a message
     * @param inputMapper object that takes nodes from json file
     * @param addedMovie movie that need
     * @param listMovies list with all the movies
     * @param a output actions
     * @param moviesListArrNode ArrayNode with all the movies from user
     */
    public static void addedMovie(final InputMapper inputMapper,
                                  final Movie addedMovie,
                                  final ArrayList<Movie> listMovies,
                                  final OutputActions a,
                                  final ArrayNode moviesListArrNode) {

        ArrayList<User> users = inputMapper.getUsers();

        ArrayList<String> addedGenres = addedMovie.getGenres();
        String addedName = addedMovie.getName();

        a.setChecked(true);
        for (Movie movie : listMovies) {
            String movieName = movie.getName();
            if (movieName.equals(addedName)) {
                a.setChecked(false);
                break;
            }
        }

        Notification notification = new Notification();
        if (a.isChecked()) {

            for (User user : users) {
                ArrayList<String> subscribedGenres = user.getSubscribedGenres();
                for (String genre : subscribedGenres) {
                    if (addedGenres.contains(genre)) {

                        notification.setMessage("ADD");
                        notification.setMovieName(addedName);
                        ArrayList<Notification> listNotes = user.getNotifications();
                        listNotes.add(notification);
                        break;
                    }
                }
            }
            listMovies.add(addedMovie);
        } else {
            errorStandard(moviesListArrNode, a);
        }
    }

    /**
     * Menu of actions made for database
     * @param inputMapper object that takes nodes from json file
     * @param moviesListArrNode ArrayNode with all the movies from user
     * @param a  output actions
     */
    public static void databaseOperations(final OutputActions a,
                                          final InputMapper inputMapper,
                                          final ArrayNode moviesListArrNode) {
        switch (a.getFeature()) {
            case "add" -> {

                Movie addedMovie = a.getAction().getAddedMovie();
                ArrayList<Movie> movies = inputMapper.getMovies();

                addedMovie(inputMapper, addedMovie, movies, a, moviesListArrNode);
            }
            case "delete" -> {

                String deletedMovie = a.getAction().getDeletedMovie();
                ArrayList<Movie> movies = inputMapper.getMovies();

                deleteMovie(movies, deletedMovie, moviesListArrNode, a, inputMapper);
            }
            default -> errorStandard(moviesListArrNode, a);
        }
    }

    /**
     * List all the movies from database
     * @param a output actions
     * @param objectMapper object that maps nodes into json file
     * @param inputMapper object that takes nodes from json file
     * @param moviesAction instance with methods for writing
     */
    public static void listMoviesInDatabase(final OutputActions a,
                                            final ObjectMapper objectMapper,
                                            final InputMapper inputMapper,
                                            final InstanceAction moviesAction) {

        a.setMovieList(new ArrayList<>());
        a.setCurrentPage(a.getPage());

        moviesAction.showPageMovies(objectMapper, a, inputMapper);

        a.getArrayNode().add(a.getObjectNode());
    }
}
