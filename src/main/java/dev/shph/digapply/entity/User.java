package dev.shph.digapply.entity;

import java.io.Serializable;

public class User implements Identifiable, Serializable {
    private long userId;
    private String email;
    private String password;
    private String name;
    private String surname;
    private UserRole role;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
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
        if (o == null || !(o.getClass().equals(getClass()))) return false;

        User other = (User) o;

        if (userId != other.userId) return false;
        if (roleId != other.roleId) return false;
        if (!email.equals(other.email)) return false;
        if (!password.equals(other.password)) return false;
        if (!name.equals(other.name)) return false;
        if (!surname.equals(other.surname)) return false;
        return role == other.role;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + email.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + surname.hashCode();
        result = 31 * result + role.hashCode();
        result = 31 * result + (int) (roleId ^ (roleId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder(getClass().getSimpleName())
                .append("{")
                .append("userId=").append(userId)
                .append(", email='").append(email).append('\'')
                .append(", password='").append(password).append('\'')
                .append(", name='").append(name).append('\'')
                .append(", surname='").append(surname).append('\'')
                .append(", role=").append(role)
                .append(", roleId=").append(roleId)
                .append('}')
                .toString();
    }
}
