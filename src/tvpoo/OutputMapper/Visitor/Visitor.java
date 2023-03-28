package tvpoo.OutputMapper.Visitor;

import com.fasterxml.jackson.databind.node.ArrayNode;
import tvpoo.OutputMapper.Singleton.OutputActions;

public interface Visitor {
    /**
     * @param pTokens type of object that is visited for buying tokens
     * @param a action object
     * @param currentMoviesList current list of movies that user has
     */
    void visitorBuy(PurchaseTokens pTokens, OutputActions a,
                    ArrayNode currentMoviesList);

    /**
     * @param pAccount type of object that is visited for buying PREMIUM account
     * @param a action object
     * @param currentMoviesList current list of movies that user has
     */
    void visitorBuy(PurchaseAccount pAccount, OutputActions a,
                    ArrayNode currentMoviesList);
}
