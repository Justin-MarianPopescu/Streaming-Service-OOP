package tvpoo.OutputMapper.Visitor;

import com.fasterxml.jackson.databind.node.ArrayNode;
import tvpoo.OutputMapper.Singleton.OutputActions;
import tvpoo.Users.Credentials;

import static tvpoo.OutputMapper.Error.errorStandard;
import static tvpoo.OutputMapper.Output.PREMIUM_TOKEN;

public final class Buy {
    private Buy() {
        //constructor for checkstyle
    }

    /**
     * Buy a nr of tokens that a user want
     * @param count nr of tokens that user want to buy
     * @param movieListArrNode ArrayNode with all the movies that user has
     * @param a action object
     */
    public static void upgradeBuyTokens(final OutputActions a,
                                        final int balance,
                                        final int count,
                                        final ArrayNode movieListArrNode) {

        int tokensCount = a.getUserLoggedIn().getTokensCount() + count;

        if (count > balance) {
            errorStandard(movieListArrNode, a);
            return;
        }

        a.getUserLoggedIn().setTokensCount(tokensCount);
    }

    /**
     * Change from STANDARD to PREMIUM account type
     * @param credentials information about the user
     * @param accountType type of the user STANDARD/PREMIUM
     * @param a object action
     * @param movieListArrNode ArrayNode with all the movies that user has
     */
    public static void buyPremium(final OutputActions a,
                                  final Credentials credentials,
                                  final String accountType,
                                  final ArrayNode movieListArrNode) {

        credentials.setAccountType("premium");
        int tokensNr = a.getUserLoggedIn().getTokensCount() - PREMIUM_TOKEN;

        if ("premium".equals(accountType) && tokensNr < PREMIUM_TOKEN) {
            errorStandard(movieListArrNode, a);
            return;
        }

        a.getUserLoggedIn().setTokensCount(tokensNr);
    }

}
