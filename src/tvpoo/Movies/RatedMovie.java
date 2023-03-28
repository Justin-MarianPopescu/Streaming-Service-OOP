package tvpoo.Movies;

public final class RatedMovie {
    /**
     * name - name of the movie
     * rate - rate given by users, rate can be modified by
     *        a user even if he already rated the movie
     */
    private String name;
    private int rate;
    /**
     * @return - obtain name of the movie that was rated
     */
    public String getName() {
        return name;
    }
    /**
     * @param name - change name of the movie that was rated
     */
    public void setName(final String name) {
        this.name = name;
    }
    /**
     * @return - obtain rate of the movie
     */
    public int getRate() {
        return rate;
    }
    /**
     * @param rate - change rate of the movie
     */
    public void setRate(final int rate) {
        this.rate = rate;
    }
}
