package by.epamtc.digapply.entity;

public class Role implements Identifiable {
    private long roleId;
    private String roleName;

    public Role() {
    }

    public Role(long roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    @Override
    public long getId() {
        return roleId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;

        Role role = (Role) o;

        if (roleId != role.roleId) return false;
        return roleName.equals(role.roleName);
    }

    @Override
    public int hashCode() {
        int result = (int) (roleId ^ (roleId >>> 32));
        result = 31 * result + roleName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Role{")
                .append("roleId=").append(roleId)
                .append(", roleName='").append(roleName).append('\'')
                .append('}').toString();
    }
}
