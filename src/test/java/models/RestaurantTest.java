package models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    @Test
    void newRestaurant_CorrectlyInstantiated() {
        Restaurant restaurant = setupRestaurant1();
        assertTrue(restaurant instanceof Restaurant);
    }

    @Test
    void setName() {
        Restaurant restaurant = setupRestaurant1();
        String newName = "Whale While";
        restaurant.setName(newName);
        assertEquals(newName, restaurant.getName());
    }

    @Test
    void setAddress() {
        Restaurant restaurant = setupRestaurant1();
        String newAddress = "12 St St";
        restaurant.setAddress(newAddress);
        assertEquals(newAddress, restaurant.getAddress());
    }

    @Test
    void setZipcode() {
        Restaurant restaurant = setupRestaurant1();
        String newZipcode = "54321";
        restaurant.setZipcode(newZipcode);
        assertEquals(newZipcode, restaurant.getZipcode());
    }

    @Test
    void setPhone() {
        Restaurant restaurant = setupRestaurant1();
        String newPhone = "111-222-3333";
        restaurant.setPhone(newPhone);
        assertEquals(newPhone, restaurant.getPhone());
    }

    @Test
    void setWebsite() {
        Restaurant restaurant = setupRestaurant2();
        String newWebsite = "www.fishfood.ca";
        restaurant.setWebsite(newWebsite);
        assertEquals(newWebsite, restaurant.getWebsite());
    }

    @Test
    void setEmail() {
        Restaurant restaurant = setupRestaurant2();
        String newEmail = "www.fish@food.ca";
        restaurant.setEmail(newEmail);
        assertEquals(newEmail, restaurant.getEmail());
    }

    @Test
    void setId() {
        Restaurant restaurant = setupRestaurant2();
        int newId = 1;
        restaurant.setId(newId);
        assertEquals(newId, restaurant.getId());
    }

    //Helpers
    Restaurant setupRestaurant1() {
        return new Restaurant("While Whale", "333 Papa St", "12345", "504-222-3333", "www.whilewhale.com", "www.while@whale.com");
    }

    Restaurant setupRestaurant2() {
        return new Restaurant("FishFood", "207 St Avenue", "92844", "402-123-6543");
    }

}