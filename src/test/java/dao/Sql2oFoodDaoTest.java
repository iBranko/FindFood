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
        restaurantDao = new Sql2oRestaurantDao(sql2o);
        conn = sql2o.open();
    }

    @AfterEach
    void tearDown() {
        conn.close();
    }

    //CREATE
    @Test
    void add_CorrectlyIdAssociated() {
        Food food = setupChineseFood();
        foodDao.add(food);
        assertEquals(1, food.getId());
    }

    //READ
    @Test
    void getById_CorrectFoodReturned() {
        Food food = setupChineseFood();
        foodDao.add(food);
        assertEquals(food, foodDao.getById(food.getId()));
    }

    @Test
    void getByName_CorrectFoodReturned() {
        Food food = setupChineseFood();
        foodDao.add(food);

        assertEquals(food, foodDao.getByName(food.getName()).get(0));
    }

    @Test
    void getByName_MultipleFoodsReturnedWithSameName() {
        Food food1 = setupChineseFood();
        foodDao.add(food1);

        Food food2 = setupChineseFood();
        foodDao.add(food2);

        List<Food> foundFoods = foodDao.getByName(food1.getName());

        assertTrue(foundFoods.contains(food1));
        assertTrue(foundFoods.contains(food2));
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
    void getAllRestaurantsByFoodId_AllRestaurantsForASpecificFoodReturned() {
        Food food = setupChineseFood();
        foodDao.add(food);

        Restaurant restaurant1 = setupRestaurant1();
        restaurantDao.add(restaurant1);
        foodDao.addFoodToRestaurant(food, restaurant1);


        Restaurant restaurant2 = setupRestaurant2();
        restaurantDao.add(restaurant2);
        foodDao.addFoodToRestaurant(food, restaurant2);

        List<Restaurant> restaurants = foodDao.getAllRestaurantsForAFoodType(food.getId());
        assertTrue(restaurants.contains(restaurant1) && restaurants.contains(restaurant2));
    }

    //UPDATE
    @Test
    void update_InformationsUpdated() {
        Food food = setupChineseFood();
        foodDao.add(food);

        String newName = "Caribean";
        food.setName(newName);
        foodDao.update(food);

        Food updatedFood = foodDao.getById(food.getId());
        assertEquals(food, updatedFood);
    }

    @Test
    void update_OnlyTargetFoodUpdated() {
        Food food1 = setupChineseFood();
        foodDao.add(food1);

        Food food2 = setupBrazilianFood();
        foodDao.add(food2);

        String newName = "Caribean";
        food1.setName(newName);
        foodDao.update(food1);

        food2 = foodDao.getById(food2.getId());

        Food updatedFood = foodDao.getById(food1.getId());
        assertNotEquals(food2.getName(), updatedFood.getName());
    }

    //DELETE
    @Test
    void delete_CorrectFoodDeleted() {
        Food food = setupChineseFood();
        foodDao.add(food);
        int foodId = food.getId();
        foodDao.deleteById(foodId);
        assertNull(foodDao.getById(foodId));
    }

    @Test
    void clearAll_AllFoodsDeleted() {
        Food food1 = setupChineseFood();
        foodDao.add(food1);

        Food food2 = setupBrazilianFood();
        foodDao.add(food2);

        foodDao.clearAll();

        int foodsAfterClear = foodDao.getAll().size();

        assertEquals(0, foodsAfterClear);
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

    Restaurant setupRestaurant1() {
        return new Restaurant("While Whale", "333 Papa St", "12345", "504-222-3333", "www.whilewhale.com", "www.while@whale.com");
    }

    Restaurant setupRestaurant2() {
        return new Restaurant("FishFood", "207 St Avenue", "92844", "402-123-6543");
    }


}