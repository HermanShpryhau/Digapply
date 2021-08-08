package by.epamtc.digapply.entity;

import java.io.Serializable;

public enum RoleEnum implements Serializable {
    ADMIN(1L), USER(2L), GUEST(3L);

    private final long ID;

    RoleEnum(long id) {
        this.ID = id;
    }

    public long getId() {
        return ID;
    }
}
