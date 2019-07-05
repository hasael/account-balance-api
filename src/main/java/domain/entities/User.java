package domain.entities;


public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String email;

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }
    public User(String id, String firstName,String lastName,String email){
        this.id = id;
        this.firstName = firstName;
        this.lastName= lastName;
        this.email = email;
    }
    // constructors, getters and setters
}
