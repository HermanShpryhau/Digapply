package by.epamtc.digapply.entity;

import java.io.Serializable;

public class User implements Serializable, Identifiable {
    private long userId;
    private String email;
    private String password;
    private String name;
    private String surname;
    private long roleId;

    public User() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    @Override
    public long getId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o.getClass().equals(getClass()))) return false;

        User other = (User) o;

        if (userId != other.userId) return false;
        if (roleId != other.roleId) return false;
        if (!email.equals(other.email)) return false;
        if (!password.equals(other.password)) return false;
        if (!name.equals(other.name)) return false;
        return surname.equals(other.surname);
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + email.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + surname.hashCode();
        result = 31 * result + (int) (roleId ^ (roleId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", roleId=" + roleId +
                '}';
    }
}
