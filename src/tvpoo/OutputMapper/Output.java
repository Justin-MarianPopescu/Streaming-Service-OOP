package tvpoo.OutputMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;

import tvpoo.InputMapper.Action;
import tvpoo.InputMapper.InputMapper;
import tvpoo.OutputMapper.Singleton.OutputActions;


public final class Output {
    public static final int MAX_RATING = 5;
    public static final int PREMIUM_TOKEN = 10;
    public static final int MOVIE_TOKEN = 2;

    /**
     * @param inputMapper mapping Object/Array(Nodes) from the json file
     * @return executes the full list of actions made in json file
     */
    public ArrayNode output(final InputMapper inputMapper) {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<Action> actions = inputMapper.getActions();

        OutputActions a = new OutputActions(actions);
        a.setArrayNode(objectMapper.createArrayNode());
        // Menu with the program is here ----------------------
        // Makes action depending on page type & feature action
        return OutputActions.listActions(inputMapper, objectMapper, a);
    }
}
