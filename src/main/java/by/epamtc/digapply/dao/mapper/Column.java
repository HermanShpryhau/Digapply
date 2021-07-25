package by.epamtc.digapply.dao.mapper;

/**
 * Contains string constants that represent names columns of tables in DB.
 */
public class Column {
    public static final String USER_ID = "user_id";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_NAME = "name";
    public static final String USER_SURNAME = "surname";
    public static final String USER_ROLE = "role_id";

    public static final String FACULTY_ID = "faculty_id";
    public static final String FACULTY_NAME = "faculty_name";
    public static final String FACULTY_SHORT_DESCRIPTION = "faculty_short_description";
    public static final String FACULTY_DESCRIPTION = "faculty_description";
    public static final String FACULTY_PLACES = "places";
    public static final String IS_APPLICATION_CLOSED = "is_application_closed";

    public static final String SUBJECT_ID = "subject_id";
    public static final String SUBJECT_NAME = "subject_name";

    private Column() {}
}
