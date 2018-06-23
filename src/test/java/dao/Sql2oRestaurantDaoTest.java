package dao;

import models.Restaurant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class Sql2oRestaurantDaoTest {

    private Connection conn;
    private Sql2oRestaurantDao restaurantDao;

    @BeforeEach
    void setUp() {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        restaurantDao = new Sql2oRestaurantDao(sql2o);
        conn = sql2o.open();
    }

    @AfterEach
    void tearDown() {
        conn.close();
    }

    @Test
    void add_CorrectlyIdAssociated() {
        Restaurant restaurant = setupRestaurant1();
        restaurantDao.add(restaurant);
        assertEquals(1, restaurant.getId());
    }

    @Test
    void getById_CorrectRestaurantReturned() {
        Restaurant restaurant = setupRestaurant1();
        restaurantDao.add(restaurant);
        assertEquals(restaurant, restaurantDao.getById(restaurant.getId()));
    }

    @Test
    void getAll_AllRestaurantsReturned() {
        Restaurant restaurant1 = setupRestaurant1();
        Restaurant restaurant2 = setupRestaurant2();
        restaurantDao.add(restaurant1);
        restaurantDao.add(restaurant2);
        Restaurant[] addedRestaurants = {restaurant1, restaurant2};
        List<Restaurant> allRestaurants = restaurantDao.getAll();
        assertEquals(Arrays.asList(addedRestaurants), allRestaurants);
    }

    @Test
    void delete_CorrectRestaurantDeleted() {
        Restaurant restaurant = setupRestaurant1();
        restaurantDao.add(restaurant);
        int restaurantId = restaurant.getId();
        restaurantDao.deleteById(restaurantId);
        assertNull(restaurantDao.getById(restaurantId));
    }

    //Helpers
    Restaurant setupRestaurant1() {
        return new Restaurant("While Whale", "333 Papa St", "12345", "504-222-3333", "www.whilewhale.com", "www.while@whale.com");
    }

    Restaurant setupRestaurant2() {
        return new Restaurant("FishFood", "207 St Avenue", "92844", "402-123-6543", "www.fishfood.com", "www.fish@food.com");
    }

}