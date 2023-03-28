package tvpoo.OutputMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import tvpoo.OutputMapper.Singleton.OutputActions;

public final class Error {

    /**
     * Mapping into json file error made in case of incorrect
     * action depending on current page & user & type action
     * @param movieListArrNode mapping the list of movies
     * @return object that contains field:
     * error, currentMoviesList, currentUser
     */
    public ObjectNode error(final ArrayNode movieListArrNode) {

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();

        objectNode.put("error", "Error");
        objectNode.set("currentMoviesList", movieListArrNode);
        objectNode.set("currentUser", null);

        return objectNode;
    }

    /**
     * Similar to this method are made the others errors !
     * This type of error was met a lot of times in the project
     * @param movieListArrNode mapping the list of movies
     * @param a action object adds at the end the "casual" error
     */
    public static void errorStandard(final ArrayNode movieListArrNode,
                                     final OutputActions a) {

        Error error = new Error();
        a.setObjectNode(error.error(movieListArrNode));
        a.getArrayNode().add(a.getObjectNode());
    }

    /**
     * Like "errorStandard" but it's not mapping into json file
     * @param movieListArrNode mapping the list of movies
     * @param a action object modifies at the end the error
     */
    public static void modifyErrorStandard(final ArrayNode movieListArrNode,
                                           final OutputActions a) {

        Error error = new Error();
        a.setObjectNode(error.error(movieListArrNode));
    }

    /**
     * Like "modifyErrorStandard" but it shows the movie name
     * this happens when we want to list a movie that is banned
     * @param movieListArrNode mapping the list of movies
     * @param a action object modifies at the end the error
     */
    public static void errorMovie(final ArrayNode movieListArrNode,
                                  final OutputActions a) {

        Error error = new Error();
        a.setCurrentMovie(String.valueOf(a.getMovieName()));
        a.setObjectNode(error.error(movieListArrNode));
    }
}
