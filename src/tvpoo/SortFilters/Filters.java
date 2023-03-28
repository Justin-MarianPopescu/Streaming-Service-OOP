package tvpoo.SortFilters;

import tvpoo.Movies.Contains;
public final class Filters {
    /**
     * contains - all information about the movie
     * sort - method of sorting movie duration/rating
     */
    private final Contains contains = new Contains();
    private Sort sort = new Sort();

    /**
     * @return obtain contains of the movie
     */
    public Contains getContains() {
        return contains;
    }
    /**
     * @return obtain method of how will sort the movies
     */
    public Sort getSort() {
        return sort;
    }
    /**
     * @param sort modify method of how will sort the movies
     */
    public void setSort(final Sort sort) {
        this.sort = sort;
    }

}
