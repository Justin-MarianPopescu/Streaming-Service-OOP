package tvpoo.OutputMapper.Visitor;

import com.fasterxml.jackson.databind.node.ArrayNode;
import tvpoo.OutputMapper.Singleton.OutputActions;

public interface Visitable {
    /**
     * @param v visitor it's visiting one of the 2 classes: Purchase(Account/Tokens)
     * @param currentMoviesList current list of movies that user has
     * @param a action object
     */
    void acceptBuy(Visitor v, ArrayNode currentMoviesList, OutputActions a);
}
