package tvpoo.OutputMapper.Singleton;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import tvpoo.InputMapper.Action;
import tvpoo.InputMapper.InputMapper;

import tvpoo.Movies.Contains;
import tvpoo.Movies.Movie;

import tvpoo.OutputMapper.Error;
import tvpoo.OutputMapper.Visitor.PurchaseAccount;
import tvpoo.SortFilters.Filters;
import tvpoo.SortFilters.Sort;

import tvpoo.Users.Credentials;
import tvpoo.Users.Notification;
import tvpoo.Users.User;

import static tvpoo.OutputMapper.Error.errorStandard;

/* E1 - ACTIONS ON MOVIES */
import tvpoo.OutputMapper.Factory.ActionMovie;
import tvpoo.OutputMapper.Factory.FactoryMovie;
import tvpoo.OutputMapper.Visitor.Visitor;
import tvpoo.OutputMapper.Visitor.PurchaseTokens;
import static tvpoo.OutputMapper.Visitor.Buy.buyPremium;
import static tvpoo.OutputMapper.Visitor.Buy.upgradeBuyTokens;
import static tvpoo.OutputMapper.Database.SeeDetails.seeDetailsMovieChangePage;
import static tvpoo.OutputMapper.Database.SeeDetails.seeDetailsPageChangePage;
/* E2 - ACTIONS ON MOVIES */
import static tvpoo.OutputMapper.Database.UserUI.Subscribe.subscribeGenre;
import static tvpoo.OutputMapper.Database.UserUI.Recommend.recommend;
import static tvpoo.OutputMapper.Database.Database.listMoviesInDatabase;
import static tvpoo.OutputMapper.Database.Database.databaseOperations;
/* ************************ */

import static tvpoo.OutputMapper.Output.MOVIE_TOKEN;

public final class OutputActions {
    private ArrayNode arrayNode;
    private ObjectNode objectNode;
    private String feature;
    private String type;
    private int current;
    private int nrAction;
    private boolean check;
    private boolean prevFilter;
    private User userLoggedIn;
    private String page;
    private String currentPage;
    private final ArrayList<String> pageList;
    private String movieName;
    private Movie movie;
    private String currentMovie;
    private ArrayList<Movie> containsMovieList;
    private ArrayList<Movie> detailsMovieList;
    private ArrayList<Movie> movieList;
    private Action action;
    private ArrayList<Action> actions = new ArrayList<>();
    /**
     * @return obtain the current action from the JSON ArrayNode
     */
    public ArrayList<Action> getActions() {
        return actions;
    }
    /** returns value of the member check - if an action caused error
     */
    public boolean isChecked() {
        return check;
    }
    /**
     * @param isChecked modify if an action failed
     */
    public void setChecked(final boolean isChecked) {
        this.check = isChecked;
    }
    /**
     * @return obtain true/false if an action was made before
     */
    public boolean isPrevFilter() {
        return prevFilter;
    }
    /**
     * @param prevFilter modify true/false if an action was made before
     */
    public void setPrevFilter(final boolean prevFilter) {
        this.prevFilter = prevFilter;
    }
    /**
     * @return obtain current page on which user is in the moment
     */
    public String getCurrentPage() {
        return currentPage;
    }
    /**
     * @param currentPage modify current page on which user is in the moment
     */
    public void setCurrentPage(final String currentPage) {
        this.currentPage = currentPage;
    }
    /**
     * @return obtain a page
     */
    public String getPage() {
        return page;
    }
    /**
     * @param page modify a page
     */
    public void setPage(final String page) {
        this.page = page;
    }
    /**
     * @return obtain list of movies
     */
    public ArrayList<Movie> getMovieList() {
        return movieList;
    }
    /**
     * @param movieList modify list of movies
     */
    public void setMovieList(final ArrayList<Movie> movieList) {
        this.movieList = movieList;
    }
    /**
     * @return obtain the name of the action
     */
    public String getFeature() {
        return feature;
    }
    /**
     * @param feature modify name of the action
     */
    public void setFeature(final String feature) {
        this.feature = feature;
    }
    /**
     * @return obtain type of the command ON PAGE/CHANGE PAGE
     */
    public String getType() {
        return type;
    }
    /**
     * @param type modify type of the command ON PAGE/CHANGE PAGE
     */
    public void setType(final String type) {
        this.type = type;
    }
    /**
     * @return obtain list with all pages visited by the user
     */
    public ArrayList<String> getPageList() {
        return pageList;
    }
    /**
     * @return obtain current movie
     */
    public String getCurrentMovie() {
        return currentMovie;
    }
    /**
     * @param currentMovie modify current movie
     */
    public void setCurrentMovie(final String currentMovie) {
        this.currentMovie = currentMovie;
    }
    /**
     * @return obtain the array node with all actions made
     */
    public ArrayNode getArrayNode() {
        return arrayNode;
    }
    /**
     * @param arrayNode modify the array node with all actions made
     */
    public void setArrayNode(final ArrayNode arrayNode) {
        this.arrayNode = arrayNode;
    }
    /**
     * @return obtain an object node
     */
    public ObjectNode getObjectNode() {
        return objectNode;
    }
    /**
     * @param objectNode modify an object node
     */
    public void setObjectNode(final ObjectNode objectNode) {
        this.objectNode = objectNode;
    }
    /**
     * @return obtain current movie
     */
    public Movie getMovie() {
        return movie;
    }
    /**
     * @param movie modify current movie
     */
    public void setMovie(final Movie movie) {
        this.movie = movie;
    }
    /**
     * @return obtain current index position from basic
     */
    public int getCurrent() {
        return current;
    }
    /**
     * @param current modify current index position from basic
     */
    public void setCurrent(final int current) {
        this.current = current;
    }
    /**
     * @return obtain nr of actions made in a simulations (basic)
     */
    public int getNrAction() {
        return nrAction;
    }
    /**
     * @param nrAction modify nr of actions made in a simulations (basic)
     */
    public void setNrAction(final int nrAction) {
        this.nrAction = nrAction;
    }
    /**
     * @param actions - change the current action from the JSON ArrayNode
     */
    public void setActions(final ArrayList<Action> actions) {
        this.actions = actions;
    }
    /**
     * @return obtain the name of the movie
     */
    public String getMovieName() {
        return movieName;
    }
    /**
     * @param movieName modify name of the movie
     */
    public void setMovieName(final String movieName) {
        this.movieName = movieName;
    }
    /**
     * @return obtain list of movies shown for "see details"
     */
    public ArrayList<Movie> getDetailsMovieList() {
        return detailsMovieList;
    }
    /**
     * @param detailsMovieList modify list of movies shown for "see details"
     */
    public void setDetailsMovieList(final ArrayList<Movie> detailsMovieList) {
        this.detailsMovieList = detailsMovieList;
    }
    /**
     * @return obtain list of movies in which will find a given name movie
     */
    public ArrayList<Movie> getContainsMovieList() {
        return containsMovieList;
    }
    /**
     * @param containsMovieList modify list of movies in which will find a given name movie
     */
    public void setContainsMovieList(final ArrayList<Movie> containsMovieList) {
        this.containsMovieList = containsMovieList;
    }
    /**
     * @return obtain current user logged in
     */
    public User getUserLoggedIn() {
        return userLoggedIn;
    }
    /**
     * @param userLoggedIn modify current user logged in
     */
    public void setUserLoggedIn(final User userLoggedIn) {
        this.userLoggedIn = userLoggedIn;
    }
    /**
     * @return obtain action
     */
    public Action getAction() {
        return action;
    }
    /**
     * @param action modify action members
     */
    public void setAction(final Action action) {
        this.action = action;
    }
    /**
     * Default constructor
     * @param actions retain actions received from Output class
     *                into object OutputActions
     */
    public OutputActions(final ArrayList<Action> actions) {

        this.pageList = new ArrayList<>();
        this.movieList = new ArrayList<>();
        this.detailsMovieList = new ArrayList<>();
        this.containsMovieList = new ArrayList<>();
        this.current = 0;
        this.nrAction = 0;
        this.currentMovie = null;
        this.currentPage = "homepage neautentificat";
        this.setActions(actions);
    }

    /**
     * Log In into account and write into result of the authentication
     * @param inputMapper object that takes nodes from json file
     * @param objectMapper object that maps nodes into json file
     * @param a output action, gets current information from json file
     * @param notes notifications of the user appear when he's logging into account
     * @param moviesListArrNode ArrayNode with list of movies shown
     *                          depending on username & password
     * @param moviesAction instance for writing into result movie that was searched
     */
    private static void logInCredentials(final InputMapper inputMapper,
                                         final ObjectMapper objectMapper,
                                         final OutputActions a,
                                         final ArrayList<Notification> notes,
                                         final ArrayNode moviesListArrNode,
                                         final InstanceAction moviesAction) {

        a.setChecked(true);

        String name = a.getAction().getCredentials().getName();
        String password = a.getAction().getCredentials().getPassword();

        ArrayList<User> users = inputMapper.getUsers();

        for (int i = 0, idxUser = 0; i < users.size(); i++, idxUser++) {
            User user = users.get(i);

            Credentials credentialsUser = user.getCredentials();
            String nameUser = user.getCredentials().getName();
            String passwordUser = user.getCredentials().getPassword();

            if (nameUser.equals(name) && passwordUser.equals(password)) {

                a.setCurrentPage("homepage autentificat");
                a.setCurrent(idxUser);
                moviesAction.showLogin(objectMapper, moviesListArrNode,
                        notes, user, a, credentialsUser);
                a.setUserLoggedIn(user);
                a.getArrayNode().add(a.getObjectNode());

                a.setChecked(false);
                break;
            }
        }

        if (a.isChecked()) {
            a.setCurrentPage("homepage neautentificat");
            errorStandard(moviesListArrNode, a);
        }
    }

    /**
     * Previous Command "on page" with Action "filter"
     * We can filter only if we've made early an action
     * @param a output action, gets current information from json file
     */
    public static void getPreviousCommand(final OutputActions a) {

        if (a.getNrAction() > 1) {

            Action action = a.getActions().get(a.getNrAction() - MOVIE_TOKEN);
            String command = action.getType();
            String feature = action.getFeature();

            if ("on page".equals(command) && "filter".equals(feature)) {
                a.setPrevFilter(true);
            }
        }
    }

    /**
     * Listing movies depending on each user country
     * @param a output action, gets current information from json file
     * @param moviesAction instance for writing into result movie that was searched
     * @param moviesListArrNode ArrayNode with list of movies shown depending on user country
     * @param inputMapper object that takes nodes from json file
     * @param objectMapper object that maps nodes into json file
     * @param movieList list of all movies that a user can see in his country
     */
    public static void listMovies(final OutputActions a,
                                  final InstanceAction moviesAction,
                                  final ArrayNode moviesListArrNode,
                                  final ObjectMapper objectMapper,
                                  final InputMapper inputMapper,
                                  final ArrayList<Movie> movieList) {

        String movieName = a.getAction().getMovie();
        a.setCurrentMovie(String.valueOf(movieName));
        a.setMovie(new Movie());
        a.setChecked(false);

        if (a.isPrevFilter()) {

            ArrayList<Movie> list = a.getDetailsMovieList();
            for (Movie movie : list) {
                String name = movie.getName();

                if (name.equals(movieName)) {

                    ArrayList<String> countriesBanned = movie.getCountriesBanned();
                    Credentials credentials = a.getUserLoggedIn().getCredentials();
                    String country = credentials.getCountry();

                    if (countriesBanned.contains(country)) {
                        break;
                    }

                    a.setMovie(movie);
                    a.setChecked(true);
                    break;

                }
            }

        } else {

            ArrayList<Movie> list = a.getMovieList();
            for (Movie movie : list) {
                String name = movie.getName();

                if (name.equals(movieName)) {

                    ArrayList<String> countriesBanned = movie.getCountriesBanned();
                    Credentials credentials = a.getUserLoggedIn().getCredentials();
                    String country = credentials.getCountry();

                    if (countriesBanned.contains(country)) {
                        break;
                    }

                    a.setMovie(movie);
                    a.setChecked(true);
                    break;

                }
            }
        }

        if (!a.isChecked()) {
            Error.errorMovie(moviesListArrNode, a);
        } else {
            a.setCurrentPage(a.getPage());
            moviesAction.showDetailsMovie(a, objectMapper,
                    inputMapper, movieList);
        }

        a.getArrayNode().add(a.getObjectNode());
    }

    /**
     * Filter movies after a sorting criteria
     * @param a output action, gets current information from json file
     * @param moviesAction instance for writing into result movie that was searched
     * @param inputMapper object that takes nodes from json file
     * @param objectMapper object that maps nodes into json file
     * @param moviesListArrNode ArrayNode with list of movies shown after sort
     */
    public static void sortFilter(final OutputActions a,
                                  final InstanceAction moviesAction,
                                  final InputMapper inputMapper,
                                  final ObjectMapper objectMapper,
                                  final ArrayNode moviesListArrNode) {

        Filters filters = a.getAction().getFilters();
        Sort sorts = filters.getSort();
        Contains contains = filters.getContains();

        a.setContainsMovieList(new ArrayList<>(a.getMovieList()));
        a.setDetailsMovieList(a.getContainsMovieList());

        ArrayList<User> usersVF = inputMapper.getUsers();
        User userVF = usersVF.get(a.getCurrent());

        if (a.getMovieList().size() == 0) {
            moviesAction.showNotFilter(a, objectMapper,
                    moviesListArrNode, userVF);
        } else {
            moviesAction.showFilter(contains, a,
                    userVF, moviesListArrNode,
                    objectMapper, sorts);
        }

        a.getArrayNode().add(a.getObjectNode());
    }

    /**
     * Search a movie that starts with the given string
     * @param a output action, gets current information from json file
     * @param inputMapper object that takes nodes from json file
     * @param objectMapper object that maps nodes into json file
     * @param moviesAction instance for writing into result movie that was searched
     */
    public static void search(final OutputActions a,
                              final ObjectMapper objectMapper,
                              final InputMapper inputMapper,
                              final InstanceAction moviesAction) {

        String startsWith = a.getAction().getStartsWith();
        a.setContainsMovieList(new ArrayList<>(a.getMovieList()));
        a.setDetailsMovieList(a.getContainsMovieList());
        ArrayNode currentMoviesList = objectMapper.createArrayNode();

        moviesAction.showSearch(a, objectMapper, inputMapper, startsWith, currentMoviesList);

        a.getArrayNode().add(a.getObjectNode());
    }

    /**
     * @param a -
     * @param inputMapper object that takes nodes from json file
     * @param objectMapper object that maps nodes into json file
     * @param moviesAction -
     */
    public static void registerCredentials(final OutputActions a,
                                           final InputMapper inputMapper,
                                           final ObjectMapper objectMapper,
                                           final InstanceAction moviesAction) {
        User user = new User();
        ArrayList<User> usersVF = inputMapper.getUsers();
        a.setCurrent(usersVF.size());
        a.setUserLoggedIn(user);

        moviesAction.showRegister(objectMapper, inputMapper,
                usersVF, user, a);

        a.setCurrentPage("homepage autentificat");
    }

    /**
     * FULL MENU IMPLEMENTATION PROJECT, ALL ACTIONS ON PAGE & CHANGE PAGE
     * it has in total 321/150 lines, in big, there are call of functions
     * and cases depending on action name, page name & CHANGE/ON PAGE
     * throw argument - in case if it didn't enter in a case statement
     * --------------------------------------------------------------------
     * I HOPE YOU TO UNDERSTAND THE EFFORT MADE FOR THIS METHOD,
     * I COULDN'T MAKE IT SMALLER THAN THIS :)
     * --------------------------------------------------------------------
     * @param inputMapper object that takes nodes from json file
     * @param objectMapper object that maps nodes into json file
     * @param a output action, gets current information from json file
     * @return final ArrayNode with all actions made in basic file
     */
    public static ArrayNode listActions(final InputMapper inputMapper,
                                        final ObjectMapper objectMapper,
                                        final OutputActions a) {

        a.setUserLoggedIn(UserCurrent.getInstance().getUser());

        ArrayList<Movie> copyMoviesList = new ArrayList<>();
        ArrayList<Notification> logInNode = new ArrayList<>();

        for (Action action : a.getActions()) {
            InstanceAction moviesAction = InstanceAction.getInstance();
            ArrayNode currentMoviesList = objectMapper.createArrayNode();
            a.setAction(action);
            a.setFeature(a.getAction().getFeature());
            a.setType(a.getAction().getType());
            a.setPrevFilter(false);
            a.setNrAction(a.getNrAction() + 1);
            a.setObjectNode(objectMapper.createObjectNode());
            getPreviousCommand(a);

            switch (a.getType()) {
                case "change page" -> {
                    a.setPage(a.getAction().getPage());
                    switch (a.getCurrentPage()) {
                        case "homepage neautentificat" -> {
                            if (a.getPage().matches("login|register")) {
                                a.setCurrentPage(a.getPage());
                                continue;
                            }
                            errorStandard(currentMoviesList, a);
                        }
                        case "homepage autentificat" -> {
                            switch (a.getPage()) {
                                case "movies" -> {
                                    a.getPageList().add(a.getCurrentPage());
                                    listMoviesInDatabase(a, objectMapper,
                                            inputMapper, moviesAction);
                                }
                                case "upgrades" -> {
                                    a.getPageList().add(a.getCurrentPage());
                                    a.setCurrentPage(a.getPage());
                                }
                                case "logout" -> {
                                    a.getPageList().clear();
                                    a.setCurrentPage("homepage neautentificat");
                                }
                                default -> errorStandard(currentMoviesList, a);
                            }
                        }
                        case "movies" -> {
                            switch (a.getPage()) {
                                case "movies" -> {
                                    a.getPageList().add(a.getCurrentPage());
                                    listMoviesInDatabase(a, objectMapper,
                                            inputMapper, moviesAction);
                                }
                                case "homepage autentificat" -> {
                                    a.getPageList().add(a.getCurrentPage());
                                    a.setCurrentPage(a.getPage());
                                }
                                case "see details" -> {
                                    String movieName = a.getAction().getMovie();
                                    a.setCurrentMovie(String.valueOf(movieName));
                                    a.setMovie(new Movie());
                                    a.setChecked(false);
                                    seeDetailsMovieChangePage(currentMoviesList, movieName, a,
                                            moviesAction, objectMapper, inputMapper,
                                            copyMoviesList);
                                }
                                case "logout" -> {
                                    a.getPageList().clear();
                                    a.setCurrentPage("homepage neautentificat");
                                }
                                default -> errorStandard(currentMoviesList, a);
                            }
                        }
                        case "upgrades" -> {
                            switch (a.getPage()) {
                                case "movies" -> {
                                    a.getPageList().add(a.getCurrentPage());
                                    listMoviesInDatabase(a, objectMapper,
                                            inputMapper, moviesAction);
                                }
                                case "homepage autentificat" -> {
                                    a.getPageList().add(a.getCurrentPage());
                                    a.setCurrentPage(a.getPage());
                                }
                                case "logout" -> {
                                    a.getPageList().clear();
                                    a.setCurrentPage("homepage neautentificat");
                                }
                                default -> errorStandard(currentMoviesList, a);
                            }
                        }
                        case "see details" -> seeDetailsPageChangePage(a, objectMapper,
                                moviesAction, inputMapper, currentMoviesList);
                        default -> throw new IllegalArgumentException("INCORRECT");
                    }
                }
                case "on page" -> {
                    if (a.getCurrentPage().equals("homepage autentificat")) {
                        errorStandard(currentMoviesList, a);
                        continue;
                    } else if ("register".equals(a.getCurrentPage())
                            && "register".equals(a.getFeature())) {
                        registerCredentials(a, inputMapper, objectMapper, moviesAction);
                        continue;
                    } else if ("login".equals(a.getCurrentPage())
                            && "login".equals(a.getFeature())) {
                        logInCredentials(inputMapper, objectMapper, a,
                                logInNode, currentMoviesList, moviesAction);
                        continue;
                    }
                    switch (a.getCurrentPage()) {
                        case "movies" -> {
                            if (!"search".equals(a.getFeature())) {
                                if ("filter".equals(a.getFeature())) {
                                    sortFilter(a, moviesAction, inputMapper,
                                            objectMapper, currentMoviesList);
                                    continue;
                                }
                            } else {
                                search(a, objectMapper, inputMapper, moviesAction);
                                continue;
                            }
                        }
                        case "upgrades" -> {
                            Visitor v = new Visitor() {
                                @Override
                                public void visitorBuy(final PurchaseTokens pTokens,
                                                       final OutputActions a,
                                                       final ArrayNode currentMoviesList) {
                                    Credentials credentials = a.getUserLoggedIn().getCredentials();
                                    String balance = credentials.getBalance();

                                    int bal = Integer.parseInt(balance);
                                    int count = a.getAction().getCount();

                                    upgradeBuyTokens(a, bal, count, currentMoviesList);

                                    bal -= count;
                                    balance = Integer.toString(bal);

                                    credentials.setBalance(balance);
                                }

                                @Override
                                public void visitorBuy(final PurchaseAccount pAccount,
                                                       final OutputActions a,
                                                       final ArrayNode currentMoviesList) {
                                    Credentials credentials = a.getUserLoggedIn().getCredentials();
                                    String exAccountType = credentials.getAccountType();
                                    buyPremium(a, credentials, exAccountType, currentMoviesList);
                                }
                            };
                            PurchaseAccount pAccount = new PurchaseAccount();
                            PurchaseTokens pTokens = new PurchaseTokens();
                            if ("buy tokens".equals(a.getFeature())) {
                                pTokens.acceptBuy(v, currentMoviesList, a);
                                continue;
                            } else if ("buy premium account".equals(a.getFeature())) {
                                pAccount.acceptBuy(v, currentMoviesList, a);
                                continue;
                            }
                            continue;
                        }
                        case "see details" -> {
                            FactoryMovie factoryMovie = new FactoryMovie();
                            switch (a.getFeature()) {
                                case "purchase" -> {
                                    ActionMovie actionMovie =
                                            factoryMovie.getAction("purchase");
                                    actionMovie.actionMovie(a, moviesAction, objectMapper,
                                            inputMapper, currentMoviesList);
                                    continue;
                                }
                                case "watch" -> {
                                    ActionMovie actionMovie =
                                            factoryMovie.getAction("watch");
                                    actionMovie.actionMovie(a, moviesAction, objectMapper,
                                            inputMapper, currentMoviesList);
                                    continue;
                                }
                                case "like" -> {
                                    ActionMovie actionMovie =
                                            factoryMovie.getAction("like");
                                    actionMovie.actionMovie(a, moviesAction, objectMapper,
                                            inputMapper, currentMoviesList);
                                    continue;
                                }
                                case "subscribe" -> {
                                    subscribeGenre(a, currentMoviesList, copyMoviesList);
                                    continue;
                                }
                                case "rate" -> {
                                    ActionMovie actionMovie =
                                            factoryMovie.getAction("rate");
                                    actionMovie.actionMovie(a, moviesAction, objectMapper,
                                            inputMapper, currentMoviesList);
                                    continue;
                                }
                                default -> throw new IllegalArgumentException("INCORRECT");
                            }
                        }
                        default -> throw new IllegalArgumentException("INCORRECT");
                    }
                    errorStandard(currentMoviesList, a);
                }
                case "back" -> {
                    int pageSize = a.getPageList().size();
                    if (pageSize != 0) {
                        a.setPage(a.getPageList().get(pageSize - 1));
                        a.getPageList().remove(pageSize - 1);

                        if (!"homepage neautentificat".equals(a.getCurrentPage())) {
                            switch (a.getCurrentPage()) {
                                case "homepage autentificat" -> {
                                    switch (a.getPage()) {
                                        case "movies" -> {
                                            listMoviesInDatabase(a, objectMapper,
                                                    inputMapper, moviesAction);
                                            continue;
                                        }
                                        case "upgrades" -> {
                                            a.setCurrentPage(a.getPage());
                                            continue;
                                        }
                                        case "logout" -> {
                                            a.setCurrentPage("homepage neautentificat");
                                            continue;
                                        }
                                        default -> {
                                            errorStandard(currentMoviesList, a);
                                            continue;
                                        }
                                    }
                                }
                                case "movies" -> {
                                    if (!"movies".equals(a.getPage())) {
                                        if (!a.getPage()
                                                .matches("homepage autentificat|upgrades")) {
                                            if (!"see details".equals(a.getPage())) {
                                                if (!"logout".equals(a.getPage())) {
                                                    errorStandard(currentMoviesList, a);
                                                } else {
                                                    a.setCurrentPage("homepage neautentificat");
                                                }
                                            } else {
                                                listMovies(a, moviesAction, currentMoviesList,
                                                        objectMapper, inputMapper, copyMoviesList);
                                                continue;
                                            }
                                        } else {
                                            a.setCurrentPage(a.getPage());
                                            continue;
                                        }
                                    } else {
                                        listMoviesInDatabase(a, objectMapper, inputMapper,
                                                moviesAction);
                                        continue;
                                    }
                                }
                                case "upgrades" -> {
                                    switch (a.getPage()) {
                                        case "movies" -> {
                                            listMoviesInDatabase(a, objectMapper,
                                                    inputMapper, moviesAction);
                                            continue;
                                        }
                                        case "homepage autentificat" -> {
                                            a.setCurrentPage(a.getPage());
                                            continue;
                                        }
                                        case "logout" -> {
                                            a.setCurrentPage("homepage neautentificat");
                                            continue;
                                        }
                                        default -> {
                                            errorStandard(currentMoviesList, a);
                                            continue;
                                        }
                                    }
                                }
                                case "see details" -> {
                                    if (!"movies".equals(a.getPage())) {
                                        if (!"homepage autentificat|upgrades"
                                                .matches(a.getPage())) {
                                            if ("logout".equals(a.getPage())) {
                                                a.setCurrentPage("homepage neautentificat");
                                            } else {
                                                errorStandard(currentMoviesList, a);
                                            }
                                        } else {
                                            a.setCurrentPage(a.getPage());
                                        }
                                    } else {
                                        listMoviesInDatabase(a, objectMapper,
                                                inputMapper, moviesAction);
                                    }
                                    continue;
                                }
                                default -> Error.modifyErrorStandard(currentMoviesList, a);
                            }
                        } else {
                            if ("login|register".matches(a.getPage())) {
                                a.setCurrentPage(a.getPage());
                                continue;
                            }
                            errorStandard(currentMoviesList, a);
                        }
                    } else {
                        Error.modifyErrorStandard(currentMoviesList, a);
                    }
                    a.getArrayNode().add(a.getObjectNode());
                }
                case "subscribe" -> subscribeGenre(a, currentMoviesList, copyMoviesList);
                case "database" -> databaseOperations(a, inputMapper, currentMoviesList);
                default -> throw new IllegalArgumentException("INCORRECT");
            }
        }
        recommend(a, objectMapper, inputMapper);
        return a.getArrayNode();
    }
}
