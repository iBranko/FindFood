package models;

/**
 * The type models.Courier.
 * @author PauloAvila
 * @version 1.0
 */
public class Courier {

    private String name;
    private String email;
    private int id;

    /**
     * Instantiates a new models.Courier.
     *
     * @param name  the name
     * @param email the email
     */
    public Courier(String name, String email) {
        this.name = name;
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

        Courier courier = (Courier) o;

        if (id != courier.id) return false;
        if (!name.equals(courier.name)) return false;
        return email.equals(courier.email);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + id;
        return result;
    }
}
