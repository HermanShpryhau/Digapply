package by.epamtc.digapply.entity;

import java.io.Serializable;

/**
 * User roles.
 */
public enum Role implements Serializable {
    ADMIN(1L), USER(2L), GUEST(3L);

    private final long ID;

    Role(long id) {
        this.ID = id;
    }

    public long getId() {
        return ID;
    }
}
