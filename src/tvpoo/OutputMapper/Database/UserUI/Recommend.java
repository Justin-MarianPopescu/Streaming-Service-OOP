package tvpoo.OutputMapper.Database.UserUI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import tvpoo.InputMapper.InputMapper;
import tvpoo.OutputMapper.Singleton.InstanceAction;
import tvpoo.OutputMapper.Singleton.OutputActions;
import tvpoo.Users.Credentials;

public final class Recommend {
    private Recommend() {
    }
    /**
     * Recommend only to PREMIUM USER that is currently logged in
     * @param inputMapper object that takes nodes from json file
     * @param objectMapper object that maps nodes into json file
     */
    public static void recommend(final OutputActions a,
                                 final ObjectMapper objectMapper,
                                 final InputMapper inputMapper) {

        ObjectNode objectNode = objectMapper.createObjectNode();

        InstanceAction actionOnMovie = InstanceAction.getInstance();
        Credentials userLoggedInCredentials = a.getUserLoggedIn().getCredentials();
        String typeUser = userLoggedInCredentials.getAccountType();

        if ("premium".equals(typeUser)) {

            actionOnMovie.recommendationPremium(objectMapper,
                    a.getUserLoggedIn(), objectNode,
                    userLoggedInCredentials, inputMapper);

            a.getArrayNode().add(objectNode);
        }
    }
}
