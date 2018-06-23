package models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourierTest {

    @Test
    void newCourier_CorrectlyInstantiated() {
        Courier courier = setCourier1();
        assertTrue(courier instanceof Courier);
    }

    @Test
    void setName() {
        Courier courier = setCourier1();
        String newName = "Bob Brown";
        courier.setName(newName);
        assertEquals(newName, courier.getName());
    }

    @Test
    void setEmail() {
        Courier courier = setCourier1();
        String newEmail = "bbrown@gmail.com";
        courier.setEmail(newEmail);
        assertEquals(newEmail, courier.getEmail());
    }

    @Test
    void setId() {
        Courier courier = setCourier1();
        int newId = 1;
        courier.setId(newId);
        assertEquals(newId, courier.getId());
    }

    //Helpers
    Courier setCourier1() {
        return new Courier("John Doe", "jd@gmail.com");
    }

    Courier setCourier2() {
        return new Courier("Megan Rose", "megan@gmail.com");
    }
}