package models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void newCustomer_CorrectlyInstantiated() {
        Customer customer = setupCustomer1();
        assertTrue(customer instanceof Customer);
    }

    @Test
    void setName() {
        Customer customer = setupCustomer1();
        String newName = "Ice T";
        customer.setName(newName);
        assertEquals(newName, customer.getName());
    }

    @Test
    void setPhone() {
        Customer customer = setupCustomer1();
        String newPhone = "(27) 1234-5678";
        customer.setPhone(newPhone);
        assertEquals(newPhone, customer.getPhone());
    }

    @Test
    void setAddress() {
        Customer customer = setupCustomer1();
        String newAddress = "1 rock st";
        customer.setAddress(newAddress);
        assertEquals(newAddress, customer.getAddress());
    }

    @Test
    void setZipcode() {
        Customer customer = setupCustomer1();
        String newZipcode = "19284";
        customer.setZipcode(newZipcode);
        assertEquals(newZipcode, customer.getZipcode());
    }

    @Test
    void setEmail() {
        Customer customer = setupCustomer1();
        String newEmail = "icet@gmail.com";
        customer.setEmail(newEmail);
        assertEquals(newEmail, customer.getEmail());
    }

    @Test
    void setId() {
        Customer customer = setupCustomer1();
        int newId = 1;
        customer.setId(newId);
        assertEquals(newId, customer.getId());
    }

    //Helpers
    Customer setupCustomer1() {
        return new Customer("Milly Silva", "(11) 92451-2215", "703 Alamo av", "92813", "msilva@gmail.com");
    }

    Customer setupCustomer2() {
        return new Customer("Jackson Ville", "(91) 3412-2281", "612 Mt View", "92817", "jackville@gmail.com");
    }
}