package models;

/**
 * The type Review.
 * @author PauloAvila
 * @version 1.0
 */
public class Review {

    private String content;
    private int rating;
    private int ticketId;
    private int id;

    /**
     * Instantiates a new Review.
     *
     * @param content the content
     * @param rating  the rating
     */
    public Review(String content, int rating) {
        this.content = content;
        this.rating = rating;
    }

    /**
     * Gets content.
     *
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets content.
     *
     * @param content the content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets rating.
     *
     * @return the rating
     */
    public int getRating() {
        return rating;
    }

    /**
     * Sets rating.
     *
     * @param rating the rating
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Gets ticket id.
     *
     * @return the ticket id
     */
    public int getTicketId() {
        return ticketId;
    }

    /**
     * Sets ticket id.
     *
     * @param ticketId the ticket id
     */
    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;

        if (rating != review.rating) return false;
        if (ticketId != review.ticketId) return false;
        if (id != review.id) return false;
        return content.equals(review.content);
    }

    @Override
    public int hashCode() {
        int result = content.hashCode();
        result = 31 * result + rating;
        result = 31 * result + ticketId;
        result = 31 * result + id;
        return result;
    }
}
