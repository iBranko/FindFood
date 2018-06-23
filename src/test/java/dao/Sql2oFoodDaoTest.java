package dao;

import models.Restaurant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.util.Arrays;
import java.util.List;
import models.Food;

import static org.junit.jupiter.api.Assertions.*;

class Sql2oFoodDaoTest {

    private Connection conn;
    private Sql2oFoodDao foodDao;
    private Sql2oRestaurantDao restaurantDao;


    @BeforeEach
    void setUp() {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        foodDao = new Sql2oFoodDao(sql2o);
        conn = sql2o.open();
    }

    @AfterEach
    void tearDown() {
        conn.close();
    }

    @Test
    void add_CorrectlyIdAssociated() {
        Food food = setupChineseFood();
        foodDao.add(food);
        assertEquals(1, food.getId());
    }

    @Test
    void getById_CorrectFoodReturned() {
        Food food = setupChineseFood();
        foodDao.add(food);
        assertEquals(food, foodDao.getById(food.getId()));
    }

    @Test
    void getAll_AllFoodsReturned() {
        Food food1 = setupBrazilianFood();
        Food food2 = setupChineseFood();
        foodDao.add(food1);
        foodDao.add(food2);
        Food[] addedFoods = {food1, food2};
        List<Food> allFoods = foodDao.getAll();
        assertEquals(Arrays.asList(addedFoods), allFoods);
    }

    @Test
    void delete_CorrectFoodDeleted() {
        Food food = setupChineseFood();
        foodDao.add(food);
        int foodId = food.getId();
        foodDao.deleteById(foodId);
        assertNull(foodDao.getById(foodId));
    }

    @Test
    void addFoodTypeToRestaurant_CorrectlyAdded() {
        Restaurant restaurant1 = setupRestaurant1();
        Restaurant restaurant2 = setupRestaurant2();

        Food foodtype = setupChineseFood();

        foodDao.add(foodtype);

        foodDao.addFoodToRestaurant(foodtype, restaurant1);
        foodDao.addFoodToRestaurant(foodtype, restaurant2);

        assertEquals(2, foodDao.getAllRestaurantsForAFoodType(foodtype.getId()).size());
    }

    //Helpers
    Food setupChineseFood() {
        return new Food("Chinese");
    }

    Food setupBrazilianFood() {
        return new Food("Brazilian");
    }

    Food setupThaiFood() {
        return new Food("Thai");
    }

    //Helpers
    Restaurant setupRestaurant1() {
        return new Restaurant("While Whale", "333 Papa St", "12345", "504-222-3333", "www.whilewhale.com", "www.while@whale.com");
    }

    Restaurant setupRestaurant2() {
        return new Restaurant("FishFood", "207 St Avenue", "92844", "402-123-6543");
    }


}