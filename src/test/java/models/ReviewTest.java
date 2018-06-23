package models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReviewTest {
    @Test
    void newReview_CorrectlyInstantiated() {
        Review review = setupReview1();
        assertTrue(review instanceof Review);
    }

    @Test
    void setContent() {
        Review review = setupReview2();
        String newContent = "Actually, it was pretty good";
        review.setContent(newContent);
        assertEquals(newContent, review.getContent());
    }

    @Test
    void setRating() {
        Review review = setupReview2();
        int newRating = 4;
        review.setRating(newRating);
        assertEquals(newRating, review.getRating());
    }

    @Test
    void setTicketId() {
        Review review = setupReview1();
        int newTickettId = 1;
        review.setTicketId(newTickettId);
        assertEquals(newTickettId, review.getTicketId());
    }

    @Test
    void setId() {
        Review review = setupReview1();
        int newId = 1;
        review.setId(newId);
        assertEquals(newId, review.getId());
    }

    //Helpers
    Review setupReview1() {
        return new Review("Amazing experience", 5);
    }

    Review setupReview2() {
        return new Review("Good, I think...",  3);
    }
}