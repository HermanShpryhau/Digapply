package by.epamtc.digapply.entity;

public class Faculty implements Identifiable{
    private long facultyId;
    private String facultyName;
    private String facultyShortDescription;
    private String facultyDescription;
    private int places;

    public Faculty() {}

    @Override
    public long getId() {
        return facultyId;
    }

    public long getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(long facultyId) {
        this.facultyId = facultyId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getFacultyShortDescription() {
        return facultyShortDescription;
    }

    public void setFacultyShortDescription(String facultyShortDescription) {
        this.facultyShortDescription = facultyShortDescription;
    }

    public String getFacultyDescription() {
        return facultyDescription;
    }

    public void setFacultyDescription(String facultyDescription) {
        this.facultyDescription = facultyDescription;
    }

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;

        Faculty other = (Faculty) o;

        if (facultyId != other.facultyId) return false;
        if (places != other.places) return false;
        if (!facultyName.equals(other.facultyName)) return false;
        return facultyDescription.equals(other.facultyDescription);
    }

    @Override
    public int hashCode() {
        int result = (int) (facultyId ^ (facultyId >>> 32));
        result = 31 * result + facultyName.hashCode();
        result = 31 * result + facultyDescription.hashCode();
        result = 31 * result + places;
        return result;
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "facultyId=" + facultyId +
                ", facultyName='" + facultyName + '\'' +
                ", facultyDescription='" + facultyDescription + '\'' +
                ", places=" + places +
                '}';
    }
}
