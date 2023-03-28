package tvpoo.OutputMapper.Singleton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.concurrent.atomic.AtomicReference;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import tvpoo.InputMapper.InputMapper;

import tvpoo.Movies.Contains;
import tvpoo.Movies.Movie;

import tvpoo.OutputMapper.Error;
import tvpoo.SortFilters.Sort;

import tvpoo.Users.Credentials;
import tvpoo.Users.Notification;
import tvpoo.Users.User;

import tvpoo.Strategy.Context.ContextFilter;
import tvpoo.Strategy.Context.ContextRemove;
import tvpoo.Strategy.Context.ContextSort;
import tvpoo.Strategy.FRS.SortDuration;
import tvpoo.Strategy.FRS.SortRating;
import tvpoo.Strategy.FRS.RemoveActor;
import tvpoo.Strategy.FRS.RemoveGenre;
import tvpoo.Strategy.FRS.FilterCountry;

public final class InstanceAction {
    public static final int PREMIUM = 15;
    private static InstanceAction instance = null;
    private InstanceAction() {
        //constructor for checkstyle
    }

    /**
     * @return instance with methods for writing in result_basic:
     * -------------------------------------------------------------------------
     * A big part of the code was duplicated & I tried to break them in more little methods
     * with real & important meaning for this design Pattern "Singleton"
     * -------------------------------------------------------------------------
     * showDetailsMovie - details of the movie
     * showUserVF - details of the user: credentials, lists of movies...
     * showCredentialsNode - details with infos about user
     * setCurrentUser - setting lists of purchased, watched, liked, rated movies
     * listDetails - add a new movie in the movie list with all the movies
     * showLogin - details with all the information about the user logged in
     * notifyArrNode - ArrayNode that contains all notifications for a user
     * showFilter list all the movies filtered after a sorting criterion
     * showNotFilter list all the user details & sets purchased, watched,... movies
     * listMovieContaining list all movies including the new one if it's not yet in DB
     * addMovieNode add a new movie ObjectNode
     * showRegister after register action list all details about the new user
     * showSearch show movies that start with a given string startsWith
     * listMovieSearch list movies that are not banned in a country
     * movieNode ObjectNode with information about the movie
     * credentialsNode ObjectNode with user credentials
     * showPageMovies list movie it is not banned in user country (is using listDetails)
     * showNotifications list all notifications that a user has
     * recommendationPremium writes at end of result_basic recommendation for current user
     *                       premium at the end of file
     */
    public static InstanceAction getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new InstanceAction();
        return instance;
    }

    /**
     * @param a output actions
     * @param objectMapper object that maps nodes into json file
     * @param inputMapper object that takes nodes from json file
     * @param moviesCp copy of the full movie list
     */
    public void showDetailsMovie(final OutputActions a,
                                 final ObjectMapper objectMapper,
                                 final InputMapper inputMapper,
                                 final ArrayList<Movie> moviesCp) {

        ArrayNode moviesListArrNode = objectMapper.createArrayNode();
        listDetails(objectMapper, a.getMovieList(), a.getMovie(), moviesListArrNode);

        moviesCp.clear();
        moviesCp.add(a.getMovie());

        ArrayList<User> usersVF = inputMapper.getUsers();
        showUserVF(objectMapper, a.getCurrent(), a.getObjectNode(), moviesListArrNode, usersVF);
    }

    /**
     * @param objectMapper object that maps nodes into json file
     * @param currentUserPosition position of the user from ArrayNode json
     * @param objectNode node in which will set/put all infos about the user
     * @param movieListArrNode arrayNode with all the movies from database
     * @param usersVF user verified, its credentials are ok
     */
    private void showUserVF(final ObjectMapper objectMapper,
                            final int currentUserPosition,
                            final ObjectNode objectNode,
                            final ArrayNode movieListArrNode,
                            final ArrayList<User> usersVF) {

        ObjectNode credentialsNode = objectMapper.createObjectNode();
        ObjectNode userNode = objectMapper.createObjectNode();

        User userVF = usersVF.get(currentUserPosition);
        ArrayNode purchasedMvs = objectMapper.createArrayNode();
        ArrayList<Movie> purchasedMvsList = userVF.getPurchasedMovies();
        listMovieContaining(objectMapper, purchasedMvsList, purchasedMvs);

        ArrayNode watchedMvs = objectMapper.createArrayNode();
        ArrayList<Movie> watchedMvsList = userVF.getWatchedMovies();
        listMovieContaining(objectMapper, watchedMvsList, watchedMvs);

        ArrayNode likedMvs = objectMapper.createArrayNode();
        ArrayList<Movie> likedMvsList = userVF.getLikedMovies();
        listMovieContaining(objectMapper, likedMvsList, likedMvs);

        ArrayNode ratedMvs = objectMapper.createArrayNode();
        ArrayList<Movie> ratedMvsList = userVF.getRatedMovies();
        listMovieContaining(objectMapper, ratedMvsList, ratedMvs);

        Credentials credentialsVF = userVF.getCredentials();
        String name = credentialsVF.getName();
        String password = credentialsVF.getPassword();
        String accountType = credentialsVF.getAccountType();
        String country = credentialsVF.getCountry();
        String balance = credentialsVF.getBalance();

        showCredentialsNode(objectMapper, objectNode, movieListArrNode, userVF,
                name, password, accountType, country, balance,
                credentialsNode, userNode,
                purchasedMvs, watchedMvs, likedMvs, ratedMvs);
    }

    /**
     * @param objectMapper object that maps nodes into json file
     * @param objectNode node in which will set/put all infos about the user
     * @param movieListArrNode arrayNode with all the movies from database
     * @param userVF user verified, its credentials are ok
     * @param name name of the user
     * @param password password of the user
     * @param accountType type of the user account STANDARD/PREMIUM
     * @param country country of the user
     * @param balance balance of the user
     * @param credentialsNode node with information about the user
     * @param userNode node that retains notifications for the user
     * @param purchasedMvs list of purchased movies
     * @param watchedMvs list of watched movies
     * @param likedMvs list of liked movies
     * @param ratedMvs list of rated movies
     */
    private void showCredentialsNode(final ObjectMapper objectMapper,
                                     final ObjectNode objectNode,
                                     final ArrayNode movieListArrNode,
                                     final User userVF,
                                     final String name,
                                     final String password,
                                     final String accountType,
                                     final String country,
                                     final String balance,
                                     final ObjectNode credentialsNode,
                                     final ObjectNode userNode,
                                     final ArrayNode purchasedMvs,
                                     final ArrayNode watchedMvs,
                                     final ArrayNode likedMvs,
                                     final ArrayNode ratedMvs) {

        credentialsNode(credentialsNode, name, password, accountType, country, balance);

        int tokensCount = userVF.getTokensCount();
        int numMovies = userVF.getNumFreePremiumMovies();
        userNode.set("credentials", credentialsNode);
        userNode.put("tokensCount", tokensCount);
        userNode.put("numFreePremiumMovies", numMovies);

        setCurrentUser(objectMapper, objectNode, movieListArrNode,
                userVF, userNode, purchasedMvs, watchedMvs, likedMvs, ratedMvs);
    }

    /**
     * @param objectMapper object that maps nodes into json file
     * @param objectNode node in which will set/put all infos about the user
     * @param movieListArrNode arrayNode with all the movies from database
     * @param userVF user verified, its credentials are ok
     * @param currentUser current user taken from json input file
     * @param purchasedMvs ArrayNode with all purchased movies by user
     * @param watchedMvs ArrayNode with all watched movies by user
     * @param likedMVs ArrayNode with all liked movies by user
     * @param ratedMvs ArrayNode with all rated movies by user
     */
    private void setCurrentUser(final ObjectMapper objectMapper,
                                final ObjectNode objectNode,
                                final ArrayNode movieListArrNode,
                                final User userVF,
                                final ObjectNode currentUser,
                                final ArrayNode purchasedMvs,
                                final ArrayNode watchedMvs,
                                final ArrayNode likedMVs,
                                final ArrayNode ratedMvs) {

        currentUser.set("purchasedMovies", purchasedMvs);
        currentUser.set("watchedMovies", watchedMvs);
        currentUser.set("likedMovies", likedMVs);
        currentUser.set("ratedMovies", ratedMvs);

        notifyArrNode(objectMapper, movieListArrNode, userVF, objectNode, currentUser);
    }

    /**
     * @param objectMapper object that maps nodes into json file
     * @param movieList list with all the movies in database
     * @param movie new movie added
     * @param movieListArrNode arrayNode with all the movies from database
     */
    private void listDetails(final ObjectMapper objectMapper,
                             final ArrayList<Movie> movieList,
                             final Movie movie,
                             final ArrayNode movieListArrNode) {

        movieList.add(movie);
        addMovieNode(objectMapper, movieListArrNode, movie);
    }

    /**
     * @param objectMapper object that maps nodes into json file
     * @param movieListArrNode arrayNode with all the movies from database
     * @param notifications list with notifications of the user
     * @param userVF user verified, its credentials are ok
     * @param a output actions
     * @param credentialsVF information about the verified user
     */
    public void showLogin(final ObjectMapper objectMapper,
                          final ArrayNode movieListArrNode,
                          final ArrayList<Notification> notifications,
                          final User userVF,
                          final OutputActions a,
                          final Credentials credentialsVF) {

        ArrayList<Movie> purchasedMvsList = userVF.getPurchasedMovies();
        ArrayNode purchasedMvs = objectMapper.createArrayNode();

        ArrayList<Movie> watchedMvsList = userVF.getWatchedMovies();
        ArrayNode watchedMvs = objectMapper.createArrayNode();

        ArrayList<Movie> likedMvsList = userVF.getLikedMovies();
        ArrayNode likedMvs = objectMapper.createArrayNode();

        ArrayList<Movie> ratedMvsList = userVF.getRatedMovies();
        ArrayNode ratedMvs = objectMapper.createArrayNode();

        String accountType = credentialsVF.getAccountType();
        String country = credentialsVF.getCountry();
        String balance = credentialsVF.getBalance();
        String name = credentialsVF.getName();
        String password = credentialsVF.getPassword();

        ObjectNode credentialsNode = objectMapper.createObjectNode();
        ObjectNode currentUser = objectMapper.createObjectNode();

        credentialsNode(objectMapper, userVF,
                name, password, accountType, country, balance,
                currentUser, credentialsNode,
                purchasedMvs, watchedMvs, likedMvs, ratedMvs,
                purchasedMvsList, watchedMvsList, likedMvsList, ratedMvsList);

        notifyArrNode(objectMapper, movieListArrNode, userVF, a.getObjectNode(), currentUser);
        ArrayList<Notification> arrNotifications = userVF.getNotifications();
        Collections.copy(notifications, arrNotifications);
    }

    /**
     * @param objectMapper object that maps nodes into json file
     * @param movieListArrNode arrayNode with all the movies from database
     * @param userVF user verified, its credentials are ok
     * @param objectNode node in which will set/put all infos about the user
     * @param userNode node that retains notifications for the user
     */
    private void notifyArrNode(final ObjectMapper objectMapper,
                               final ArrayNode movieListArrNode,
                               final User userVF,
                               final ObjectNode objectNode,
                               final ObjectNode userNode) {

        ArrayList<Notification> notificationsArray = userVF.getNotifications();
        ArrayNode notifications = objectMapper.createArrayNode();

        showNotifications(objectMapper, notifications, notificationsArray);

        userNode.set("notifications", notifications);
        objectNode.put("error", (String) null);
        objectNode.set("currentMoviesList", movieListArrNode);
        objectNode.set("currentUser", userNode);
    }

    /**
     * @param contains details of the movie
     * @param a output actions
     * @param userVF user verified, its credentials are ok
     * @param movieListArrNode arrayNode with all the movies from database
     * @param objectMapper object that maps nodes into json file
     * @param sort method of sorting movies after duration/rating
     */
    public void showFilter(final Contains contains,
                           final OutputActions a,
                           final User userVF,
                           final ArrayNode movieListArrNode,
                           final ObjectMapper objectMapper,
                           final Sort sort) {
        String rating = sort.getRating();
        String duration = sort.getDuration();

        ContextFilter contextFilterCountry = new ContextFilter(new FilterCountry());
        contextFilterCountry.executeFilter(a.getUserLoggedIn(), a.getContainsMovieList());

        ContextSort contextSortRating = new ContextSort(new SortRating());
        if (rating != null) {
            contextSortRating.executeSort(a.getContainsMovieList(), rating);
        }

        ContextSort contextSortDuration = new ContextSort(new SortDuration());
        if (duration != null) {
            contextSortDuration.executeSort(a.getContainsMovieList(), duration);
        }

        if (!(duration == null || rating == null)) {
            if ((duration.equals("decreasing")) && (rating.equals("increasing"))) {

                int bound = a.getContainsMovieList().size() - 1;

                IntStream.range(0, bound).forEachOrdered(j -> {

                    Movie m1 = a.getContainsMovieList().get(j),
                            m2 = a.getContainsMovieList().get(j + 1);

                    double r1 = m1.getRating(), r2 = m2.getRating();
                    if (r2 < r1) {
                        int t1 = m1.getDuration(), t2 = m2.getDuration();
                        if (t2 == t1) {
                            Collections.swap(a.getContainsMovieList(), j + 1, j);
                        }
                    }

                });
            }
        }

        ContextRemove contextRemoveActor = new ContextRemove(new RemoveActor());
        ContextRemove contextRemoveGenre = new ContextRemove(new RemoveGenre());
        if (contains != null) {

            if (contains.getGenre() != null) {
                contextRemoveGenre.executeRemove(a.getContainsMovieList(), contains.getGenre());
            }

            if (contains.getActors() != null) {
                contextRemoveActor.executeRemove(a.getContainsMovieList(), contains.getActors());
            }

        }

        listMovieContaining(objectMapper, a.getContainsMovieList(), movieListArrNode);

        Credentials credentialsVF = a.getUserLoggedIn().getCredentials();
        String name = credentialsVF.getName();
        String password = credentialsVF.getPassword();
        String accountType = credentialsVF.getAccountType();
        String country = credentialsVF.getCountry();
        String balance = credentialsVF.getBalance();

        ObjectNode credentialsNode = objectMapper.createObjectNode();
        credentialsNode(credentialsNode, name, password, accountType, country, balance);
        ObjectNode userNode = objectMapper.createObjectNode();

        int tokensCount = userVF.getTokensCount();
        int numMovies = userVF.getNumFreePremiumMovies();
        userNode.set("credentials", credentialsNode);
        userNode.put("tokensCount", tokensCount);
        userNode.put("numFreePremiumMovies", numMovies);

        ArrayNode purchasedMvs = objectMapper.createArrayNode();
        ArrayList<Movie> purchasedMvsList = a.getUserLoggedIn().getPurchasedMovies();
        listMovieContaining(objectMapper, purchasedMvsList, purchasedMvs);

        ArrayNode watchedMvs = objectMapper.createArrayNode();
        ArrayList<Movie> watchedMvsList = a.getUserLoggedIn().getWatchedMovies();
        listMovieContaining(objectMapper, watchedMvsList, watchedMvs);

        ArrayNode likedMvs = objectMapper.createArrayNode();
        ArrayList<Movie> likedMvsList = a.getUserLoggedIn().getLikedMovies();
        listMovieContaining(objectMapper, likedMvsList, likedMvs);

        ArrayNode ratedMvs = objectMapper.createArrayNode();
        ArrayList<Movie> ratedMvsList = a.getUserLoggedIn().getRatedMovies();
        listMovieContaining(objectMapper, ratedMvsList, ratedMvs);

        setCurrentUser(objectMapper, a.getObjectNode(), movieListArrNode, userVF,
                userNode, purchasedMvs, watchedMvs, likedMvs, ratedMvs);
    }

    /**
     * @param objectMapper object that maps nodes into json file
     * @param movies list with movies from the database
     * @param moviesListArrNode ArrayNode with all the movies from database
     */
    private void listMovieContaining(final ObjectMapper objectMapper,
                                     final ArrayList<Movie> movies,
                                     final ArrayNode moviesListArrNode) {
        movies.forEach(movie -> addMovieNode(objectMapper, moviesListArrNode, movie));
    }

    /**
     * @param objectMapper object that maps nodes into json file
     * @param moviesListArrNode ArrayNode with all the movies from database
     * @param movie new movie added
     */
    private void addMovieNode(final ObjectMapper objectMapper,
                              final ArrayNode moviesListArrNode,
                              final Movie movie) {

        ObjectNode movieNode = objectMapper.createObjectNode();
        String name = movie.getName();
        movieNode(objectMapper, moviesListArrNode, movie, movieNode, name);
    }

    /**
     * @param objectMapper object that maps nodes into json file
     * @param inputMapper object that takes nodes from json file
     * @param usersVF user verified, its credentials are ok
     * @param user new user added in list of users
     */
    public void showRegister(final ObjectMapper objectMapper,
                             final InputMapper inputMapper,
                             final ArrayList<User> usersVF,
                             final User user,
                             final OutputActions a) {

        Credentials credentials = a.getAction().getCredentials();
        ArrayNode moviesListArrNode = objectMapper.createArrayNode();
        ObjectNode objectNode = objectMapper.createObjectNode();

        String name = credentials.getName();
        String password = credentials.getPassword();
        String accountType = credentials.getAccountType();
        String country = credentials.getCountry();
        String balance = credentials.getBalance();

        boolean verify = usersVF.stream().map(User::getCredentials)
                .map(Credentials::getName)
                .noneMatch(checkName -> checkName.equals(name));

        if (!verify) {
            Error errorError = new Error();
            objectNode = errorError.error(moviesListArrNode);
            a.getArrayNode().add(objectNode);
        }

        user.setTokensCount(0);
        user.setNumFreePremiumMovies(PREMIUM);

        ArrayList<User> users = inputMapper.getUsers();
        Credentials userCredentials = new Credentials();

        ObjectNode credentialsNode = objectMapper.createObjectNode();

        ObjectNode currentUser = objectMapper.createObjectNode();
        ArrayNode movies = objectMapper.createArrayNode();

        user.setCredentials(userCredentials);
        userCredentials.setName(name);
        credentialsNode.put("name", name);
        userCredentials.setPassword(password);
        credentialsNode.put("password", password);
        userCredentials.setAccountType(accountType);
        credentialsNode.put("accountType", accountType);
        userCredentials.setCountry(country);
        credentialsNode.put("country", country);
        userCredentials.setBalance(balance);
        credentialsNode.put("balance", balance);
        users.add(user);

        currentUser.set("credentials", credentialsNode);
        currentUser.put("tokensCount", 0);
        currentUser.put("numFreePremiumMovies", PREMIUM);

        currentUser.set("purchasedMovies", movies);
        currentUser.set("watchedMovies", movies);
        currentUser.set("likedMovies", movies);
        currentUser.set("ratedMovies", movies);
        currentUser.set("notifications", movies);

        objectNode.put("error", (String) null);
        objectNode.set("currentMoviesList", moviesListArrNode);
        objectNode.set("currentUser", currentUser);
        a.getArrayNode().add(objectNode);
    }

    /**
     * @param objectMapper object that maps nodes into json file
     * @param inputMapper object that takes nodes from json file
     * @param startsWith find movie if it's movie starts with the given string
     * @param moviesListArrNode ArrayNode with all the movies from database
     * @param a output action, gets current information from json file
     */
    public void showSearch(final OutputActions a,
                           final ObjectMapper objectMapper,
                           final InputMapper inputMapper,
                           final String startsWith,
                           final ArrayNode moviesListArrNode) {

        if (a.isPrevFilter()) {
            listMovieSearch(objectMapper, a.getContainsMovieList(),
                    a.getUserLoggedIn(), startsWith, moviesListArrNode);
        } else {
            ArrayList<Movie> movies = inputMapper.getMovies();
            listMovieSearch(objectMapper, movies,
                    a.getUserLoggedIn(), startsWith, moviesListArrNode);
        }

        ArrayList<User> usersVF = inputMapper.getUsers();
        User userVF = usersVF.get(a.getCurrent());

        Credentials credentialsVF = userVF.getCredentials();
        String name = credentialsVF.getName();
        String password = credentialsVF.getPassword();
        String accountType = credentialsVF.getAccountType();
        String country = credentialsVF.getCountry();
        String balance = credentialsVF.getBalance();

        ObjectNode credentialsNode = objectMapper.createObjectNode();
        ObjectNode currentUser = objectMapper.createObjectNode();
        ArrayNode moviesNode = objectMapper.createArrayNode();

        showCredentialsNode(objectMapper, a.getObjectNode(), moviesListArrNode, userVF,
                name, password, accountType, country, balance,
                credentialsNode, currentUser,
                moviesNode, moviesNode, moviesNode, moviesNode);

    }

    /**
     * @param objectMapper object that maps nodes into json file
     * @param moviesContaining list with movies, verify if a given movie is in
     * @param userLoggedIn current user logged in
     * @param startsWith find movie if it's movie starts with the given string
     * @param currentMoviesList ArrayNode with all the movies in database now
     */
    private void listMovieSearch(final ObjectMapper objectMapper,
                                 final ArrayList<Movie> moviesContaining,
                                 final User userLoggedIn,
                                 final String startsWith,
                                 final ArrayNode currentMoviesList) {

        moviesContaining.forEach(movie -> {
            ObjectNode movieNode = objectMapper.createObjectNode();

            if ((!movie.getName().startsWith(startsWith))
                    || (movie.getCountriesBanned()
                    .contains(userLoggedIn.getCredentials().getCountry()))) {
                return;
            }
            movieNode(objectMapper, currentMoviesList, movie, movieNode, movie.getName());
        });
    }

    /**
     * @param objectMapper object that maps nodes into json file
     * @param movieListArrNode arrayNode with all the movies from database
     * @param movie new movie added
     * @param movieNode node with details about the movie
     * @param name name of the movie
     */
    private void movieNode(final ObjectMapper objectMapper,
                           final ArrayNode movieListArrNode,
                           final Movie movie,
                           final ObjectNode movieNode,
                           final String name) {

        ArrayList<String> genresName = movie.getGenres();
        ArrayNode genresArr = objectMapper.createArrayNode();
        genresName.forEach(genresArr::add);

        ArrayList<String> actorsName = movie.getActors();
        ArrayNode actorsArr = objectMapper.createArrayNode();
        actorsName.forEach(actorsArr::add);

        ArrayList<String> countriesBannedName = movie.getCountriesBanned();
        ArrayNode countriesBannedArr = objectMapper.createArrayNode();
        countriesBannedName.forEach(countriesBannedArr::add);

        String year = movie.getYear();
        int numLikes = movie.getNumLikes();
        double rating = movie.getRating();
        int numRating = movie.getNumRating();
        int duration = movie.getDuration();

        movieNode.put("name", name);
        movieNode.put("year", year);
        movieNode.put("duration", duration);

        movieNode.set("genres", genresArr);
        movieNode.set("actors", actorsArr);
        movieNode.set("countriesBanned", countriesBannedArr);

        movieNode.put("numLikes", numLikes);
        movieNode.put("rating", rating);
        movieNode.put("numRatings", numRating);
        movieListArrNode.add(movieNode);
    }

    /**
     * @param objectMapper object that maps nodes into json file
     * @param movieListArrNode arrayNode with all the movies from database
     * @param userVF user verified, its credentials are ok
     */
    public void showNotFilter(final OutputActions a,
                              final ObjectMapper objectMapper,
                              final ArrayNode movieListArrNode,
                              final User userVF) {

        ArrayNode moviesNode = objectMapper.createArrayNode();
        ObjectNode userNode = objectMapper.createObjectNode();
        ObjectNode credentialsNode = objectMapper.createObjectNode();

        Credentials credentialsVF = a.getUserLoggedIn().getCredentials();

        int tokensCount = userVF.getTokensCount();
        int numMovies = userVF.getNumFreePremiumMovies();
        userNode.put("tokensCount", tokensCount);
        userNode.put("numFreePremiumMovies", numMovies);

        String name = credentialsVF.getName();
        String password = credentialsVF.getPassword();
        String accountType = credentialsVF.getAccountType();
        String country = credentialsVF.getCountry();
        String balance = credentialsVF.getBalance();
        credentialsNode(credentialsNode, name, password, accountType, country, balance);

        userNode.set("credentials", credentialsNode);

        setCurrentUser(objectMapper, a.getObjectNode(), movieListArrNode, userVF,
                userNode, moviesNode, moviesNode, moviesNode, moviesNode);
    }

    /**
     * @param objectMapper object that maps nodes into json file
     * @param movie new movie added
     * @param inputMapper object that takes nodes from json file
     * @param currentUserPosition position of the user from ArrayNode json
     * @param objectNode node in which will set/put all infos about the user
     */
    public void showMovie(final ObjectMapper objectMapper,
                          final Movie movie,
                          final InputMapper inputMapper,
                          final int currentUserPosition,
                          final ObjectNode objectNode) {

        Credentials userCredentialsVF =
                inputMapper.getUsers().get(currentUserPosition).getCredentials();
        User userVF = inputMapper.getUsers().get(currentUserPosition);

        ObjectNode credentialsNode = objectMapper.createObjectNode();
        ObjectNode userNode = objectMapper.createObjectNode();
        ArrayNode moviesListArrNode = objectMapper.createArrayNode();

        addMovieNode(objectMapper, moviesListArrNode, movie);

        String name = userCredentialsVF.getName();
        String password = userCredentialsVF.getPassword();
        String accountType = userCredentialsVF.getAccountType();
        String country = userCredentialsVF.getCountry();
        String balance = userCredentialsVF.getBalance();
        credentialsNode(credentialsNode, name, password, accountType, country, balance);

        int tokensNr = userVF.getTokensCount();
        int numMvs = userVF.getNumFreePremiumMovies();
        userNode.put("tokensCount", tokensNr);
        userNode.put("numFreePremiumMovies", numMvs);
        userNode.set("credentials", credentialsNode);

        ArrayNode purchasedMvs = objectMapper.createArrayNode();
        ArrayList<Movie> purchasedMvsList = userVF.getPurchasedMovies();
        listMovieContaining(objectMapper, purchasedMvsList, purchasedMvs);

        ArrayNode watchedMvs = objectMapper.createArrayNode();
        ArrayList<Movie> watchedMvsList = userVF.getWatchedMovies();
        listMovieContaining(objectMapper, watchedMvsList, watchedMvs);

        ArrayNode likedMvs = objectMapper.createArrayNode();
        ArrayList<Movie> likedMvsList = userVF.getLikedMovies();
        listMovieContaining(objectMapper, likedMvsList, likedMvs);

        ArrayNode ratedMvs = objectMapper.createArrayNode();
        ArrayList<Movie> ratedMvsList = userVF.getRatedMovies();
        listMovieContaining(objectMapper, ratedMvsList, ratedMvs);

        userNode.set("credentials", credentialsNode);
        userNode.put("tokensCount", tokensNr);
        userNode.put("numFreePremiumMovies", numMvs);

        setCurrentUser(objectMapper, objectNode, moviesListArrNode, userVF, userNode,
                purchasedMvs, watchedMvs, likedMvs, ratedMvs);
    }

    /**
     * @param objectMapper object that maps nodes into json file
     * @param userLoggedIn current user logged in
     * @param objectNode node in which will set/put all infos about the user
     * @param userLoggedCred current user credentials logged in account
     * @param inputMapper object that takes nodes from json file
     */
    public void recommendationPremium(final ObjectMapper objectMapper,
                                      final User userLoggedIn,
                                      final ObjectNode objectNode,
                                      final Credentials userLoggedCred,
                                      final InputMapper inputMapper) {

        ArrayNode purchasedMvs = objectMapper.createArrayNode();
        ArrayList<Movie> purchasedMvsList = userLoggedIn.getPurchasedMovies();

        ArrayNode watchedMvs = objectMapper.createArrayNode();
        ArrayList<Movie> watchedMvsList = userLoggedIn.getWatchedMovies();

        ArrayNode likedMvs = objectMapper.createArrayNode();
        ArrayList<Movie> likedMvsList = userLoggedIn.getLikedMovies();

        ArrayNode ratedMvs = objectMapper.createArrayNode();
        ArrayList<Movie> ratedMvsList = userLoggedIn.getRatedMovies();

        ArrayNode notifications = objectMapper.createArrayNode();

        ArrayList<Notification> notificationsArray = userLoggedIn.getNotifications();
        ObjectNode notification = objectMapper.createObjectNode();

        ArrayList<Movie> arrayAux = inputMapper.getMovies();
        ArrayList<Movie> moviesAuxx = new ArrayList<>(arrayAux);
        ArrayList<Movie> likedGenres = userLoggedIn.getLikedMovies();

        String country = userLoggedCred.getCountry();
        String name = userLoggedCred.getName();
        String password = userLoggedCred.getPassword();
        String accountType = userLoggedCred.getAccountType();
        String balance = userLoggedCred.getBalance();

        ObjectNode currentUser = objectMapper.createObjectNode();
        ObjectNode credentialsNode = objectMapper.createObjectNode();
        credentialsNode(objectMapper, userLoggedIn,
                name, password, accountType, country, balance,
                currentUser, credentialsNode,
                purchasedMvs, watchedMvs, likedMvs, ratedMvs,
                purchasedMvsList, watchedMvsList, likedMvsList, ratedMvsList);

        showNotifications(objectMapper, notifications, notificationsArray);

        ArrayList<String> subscribedGenres = likedGenres.stream()
                .map(Movie::getGenres)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Movie> movies = new ArrayList<>();
        moviesAuxx.forEach(m -> {
            ArrayList<String> genres = m.getGenres();

            if (genres.stream().anyMatch(subscribedGenres::contains)) {
                movies.add(m);
            }
        });

        IntStream.iterate(movies.size() - 1, aux -> aux > -1, aux -> aux - 1)
                .forEachOrdered(aux -> {

                    Movie m = movies.get(aux);
                    String nm = m.getName();

                    watchedMvsList.stream()
                            .map(Movie::getName)
                            .filter(listNotify -> listNotify.equals(nm))
                            .mapToInt(listNotify -> aux)
                            .forEachOrdered(movies::remove);
                });

        movies.sort(Comparator.comparingInt(Movie::getNumLikes));
        Collections.reverse(movies);

        int bound = movies.size() - 1;
        IntStream.range(0, bound).forEachOrdered(j -> {
            int numLikesJ1 = movies.get(j + 1).getNumLikes();
            int numLikesJ = movies.get(j).getNumLikes();
            if (numLikesJ1 != numLikesJ) {
                return;
            }
            Collections.swap(movies, j + 1, j);
        });

        notification.put("message", "Recommendation");

        if (movies.size() != 0) {
            AtomicReference<String> recommendedName
                    = new AtomicReference<>(movies.get(0).getName());
            notification.put("movieName", recommendedName.get());
        } else {
            notification.put("movieName", "No recommendation");
        }

        notifications.add(notification);
        currentUser.set("notifications", notifications);
        objectNode.put("error", (String) null);
        objectNode.set("currentMoviesList", null);
        objectNode.set("currentUser", currentUser);
    }

    /**
     * @param objectMapper object that maps nodes into json file
     * @param a output actions
     * @param inputMapper object that takes nodes from json file
     */
    public void showPageMovies(final ObjectMapper objectMapper,
                               final OutputActions a,
                               final InputMapper inputMapper) {
        ArrayList<User> usersVF = inputMapper.getUsers();
        ArrayNode moviesListArrNode = objectMapper.createArrayNode();
        ArrayList<Movie> moviesList = inputMapper.getMovies();

        moviesList.forEach(movie -> {
            String country = a.getUserLoggedIn().getCredentials().getCountry();
            ArrayList<String> countriesBanned = movie.getCountriesBanned();

            if (countriesBanned.contains(country)) {
                return;
            }
            listDetails(objectMapper, a.getMovieList(), movie, moviesListArrNode);
        });

        showUserVF(objectMapper, a.getCurrent(), a.getObjectNode(), moviesListArrNode, usersVF);
    }

    /**
     * @param objectMapper object that maps nodes into json file
     * @param userLoggedIn current user logged in
     * @param name name of the user
     * @param password password of the user
     * @param accountType type of the user account STANDARD/PREMIUM
     * @param country country of the user
     * @param balance balance of the user
     * @param currentUser current user that we map his information
     * @param credentialsNode Node with the credentials of the user
     * @param purchasedMvs ArrayNode of purchased movies
     * @param watchedMvs ArrayNode of watched movies
     * @param likedMvs ArrayNode of liked movies
     * @param ratedMvs ArrayNode of rated movies
     * @param purchasedMvsList list of purchased movies
     * @param watchedMvsList list of watched movies
     * @param likedMvsList list of liked movies
     * @param ratedMvsList list of rated movies
     */
    private void credentialsNode(final ObjectMapper objectMapper,
                                 final User userLoggedIn,
                                 final String name,
                                 final String password,
                                 final String accountType,
                                 final String country,
                                 final String balance,
                                 final ObjectNode currentUser,
                                 final ObjectNode credentialsNode,
                                 final ArrayNode purchasedMvs,
                                 final ArrayNode watchedMvs,
                                 final ArrayNode likedMvs,
                                 final ArrayNode ratedMvs,
                                 final ArrayList<Movie> purchasedMvsList,
                                 final ArrayList<Movie> watchedMvsList,
                                 final ArrayList<Movie> likedMvsList,
                                 final ArrayList<Movie> ratedMvsList) {

        credentialsNode(credentialsNode, name, password, accountType, country, balance);

        listMovieContaining(objectMapper, purchasedMvsList, purchasedMvs);
        listMovieContaining(objectMapper, watchedMvsList, watchedMvs);
        listMovieContaining(objectMapper, likedMvsList, likedMvs);
        listMovieContaining(objectMapper, ratedMvsList, ratedMvs);

        currentUser.set("credentials", credentialsNode);
        int tokensCount = userLoggedIn.getTokensCount();
        int numMovies = userLoggedIn.getNumFreePremiumMovies();
        currentUser.put("tokensCount", tokensCount);

        currentUser.put("numFreePremiumMovies", numMovies);
        currentUser.set("purchasedMovies", purchasedMvs);
        currentUser.set("watchedMovies", watchedMvs);
        currentUser.set("likedMovies", likedMvs);
        currentUser.set("ratedMovies", ratedMvs);
    }

    /**
     * @param objectMapper object that maps nodes into json file
     * @param notifications ArrayNode with notifications for user
     * @param notificationsArray list with all notifications for user
     */
    private void showNotifications(final ObjectMapper objectMapper,
                                   final ArrayNode notifications,
                                   final ArrayList<Notification> notificationsArray) {

        notificationsArray.forEach(notification -> {
            ObjectNode notificationNode = objectMapper.createObjectNode();
            String movieName = notification.getMovieName();
            notificationNode.put("movieName", movieName);

            String message = notification.getMessage();
            notificationNode.put("message", message);
            notifications.add(notificationNode);
        });
    }

    /**
     * @param credentialsNode node that retains all the following fields
     * @param name name of the user
     * @param password password of the user
     * @param accountType type of the user account STANDARD/PREMIUM
     * @param country country of the user
     * @param balance balance of the user
     */
    private void credentialsNode(final ObjectNode credentialsNode,
                                 final String name,
                                 final String password,
                                 final String accountType,
                                 final String country,
                                 final String balance) {

        credentialsNode.put("name", name);
        credentialsNode.put("password", password);
        credentialsNode.put("accountType", accountType);
        credentialsNode.put("country", country);
        credentialsNode.put("balance", balance);
    }
}
