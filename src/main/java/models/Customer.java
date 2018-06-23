package models;

/**
 * The type models.Customer.
 * @author PauloAvila
 * @version 1.0
 *
 */
public class Customer {

    private String name;
    private String phone;
    private String address;
    private String zipcode;
    private String email;
    private int id;

    /**
     * Instantiates a new models.Customer.
     *
     * @param name    the name
     * @param phone   the phone
     * @param address the address
     * @param zipcode the zipcode
     * @param email   the email
     */
    public Customer(String name, String phone, String address, String zipcode, String email) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.zipcode = zipcode;
        this.email = email;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets phone.
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets phone.
     *
     * @param phone the phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets zipcode.
     *
     * @return the zipcode
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     * Sets zipcode.
     *
     * @param zipcode the zipcode
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
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

        Customer customer = (Customer) o;

        if (id != customer.id) return false;
        if (!name.equals(customer.name)) return false;
        if (!phone.equals(customer.phone)) return false;
        if (!address.equals(customer.address)) return false;
        if (!zipcode.equals(customer.zipcode)) return false;
        return email.equals(customer.email);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + phone.hashCode();
        result = 31 * result + address.hashCode();
        result = 31 * result + zipcode.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + id;
        return result;
    }
}
