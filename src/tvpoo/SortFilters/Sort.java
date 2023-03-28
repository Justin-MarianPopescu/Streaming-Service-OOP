package tvpoo.SortFilters;
public final class Sort {
    /**
     * rating - average grade that movie received from all users'
     * duration - time to watch movie
     */
    private String rating;
    private String duration;
    /**
     * @return obtain average grade received by movie
     */
    public String getRating() {
        return rating;
    }
    /**
     * @param rating modify average grade received by movie
     */
    public void setRating(final String rating) {
        this.rating = rating;
    }
    /**
     * @return obtain time to watch movie
     */
    public String getDuration() {
        return duration;
    }
    /**
     * @param duration modify time to watch movie
     */
    public void setDuration(final String duration) {
        this.duration = duration;
    }
}
