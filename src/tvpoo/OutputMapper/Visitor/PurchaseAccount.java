package tvpoo.OutputMapper.Visitor;
import com.fasterxml.jackson.databind.node.ArrayNode;
import tvpoo.OutputMapper.Singleton.OutputActions;


public class PurchaseAccount implements Visitable {
    /**
     * @param v visitor it's visiting method: buyPremium
     * @param currentMoviesList current list of movies that user has
     * @param a action object
     */
    @Override
    public void acceptBuy(final Visitor v,
                          final ArrayNode currentMoviesList,
                          final OutputActions a) {
        v.visitorBuy(this, a, currentMoviesList);
    }
}
