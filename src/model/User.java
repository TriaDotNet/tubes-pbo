package model;

public class User {

    private int id_user;
    private String username;
    private String password;
    private String role;
    private Integer id_kurir;

    public User() {
    }

    public User(int id_user, String username, String password, String role, Integer id_kurir) {
        this.id_user = id_user;
        this.username = username;
        this.password = password;
        this.role = role;
        this.id_kurir = id_kurir;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getId_kurir() {
        return id_kurir;
    }

    public void setId_kurir(Integer id_kurir) {
        this.id_kurir = id_kurir;
    }

    public boolean isAdmin() {
        return "Admin".equalsIgnoreCase(role);
    }

    public boolean isKurir() {
        return "Kurir".equalsIgnoreCase(role);
    }
}
