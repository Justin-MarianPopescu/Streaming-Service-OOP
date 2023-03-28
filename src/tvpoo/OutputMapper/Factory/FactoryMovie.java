package tvpoo.OutputMapper.Factory;

public class FactoryMovie {
    /**
     * @param nameAction name of the action one of these 4
     * @return an object that is designed to perform his type
     * of actionMovie method
     */
    public ActionMovie getAction(final String nameAction) {
        if (nameAction == null) {
            return null;
        }
        return switch (nameAction) {
            case "rate" -> new Rate();
            case "like" -> new Like();
            case "watch" -> new Watch();
            case "purchase" -> new Purchase();
            default -> null;
        };
    }
}
