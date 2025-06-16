package minicla03.coinquylife.Auth.DOMAIN.model;

public class User {
    private String id_user;
    private String username;
    private String name;
    private String password;
    private String surname;
    private String email;
    private String language;
    private byte[] profileImage;
    private String houseUser;

    public User() { }

    public User(String username, String name, String password, String surname, String email) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.surname = surname;
        this.email = email;
        this.profileImage = null;
        this.houseUser = null;
    }

    public User(String username, String pass) {
        this.username = username;
        this.password = pass;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public String getHouseUser() {
        return houseUser;
    }

    public void setHouseUser(String houseUser) {
        this.houseUser = houseUser;
    }
}
