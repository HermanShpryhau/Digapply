package dev.shph.digapply.entity;

import java.io.Serializable;

public class Faculty implements Identifiable, Serializable {
    private long facultyId;
    private String facultyName;
    private String facultyShortDescription;
    private String facultyDescription;
    private int places;
    private boolean isApplicationClosed;

    public Faculty() {}

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

    public boolean isApplicationClosed() {
        return isApplicationClosed;
    }

    public void setApplicationClosed(boolean applicationClosed) {
        isApplicationClosed = applicationClosed;
    }

    @Override
    public long getId() {
        return facultyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Faculty)) return false;

        Faculty faculty = (Faculty) o;

        if (facultyId != faculty.facultyId) return false;
        if (places != faculty.places) return false;
        if (isApplicationClosed != faculty.isApplicationClosed) return false;
        if (!facultyName.equals(faculty.facultyName)) return false;
        if (!facultyShortDescription.equals(faculty.facultyShortDescription)) return false;
        return facultyDescription.equals(faculty.facultyDescription);
    }

    @Override
    public int hashCode() {
        int result = (int) (facultyId ^ (facultyId >>> 32));
        result = 31 * result + facultyName.hashCode();
        result = 31 * result + facultyShortDescription.hashCode();
        result = 31 * result + facultyDescription.hashCode();
        result = 31 * result + places;
        result = 31 * result + (isApplicationClosed ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder(getClass().getSimpleName())
                .append("{")
                .append("facultyId=").append(facultyId)
                .append(", facultyName='").append(facultyName).append('\'')
                .append(", facultyShortDescription='").append(facultyShortDescription).append('\'')
                .append(", facultyDescription='").append(facultyDescription).append('\'')
                .append(", places=").append(places)
                .append(", isApplicationClosed=").append(isApplicationClosed)
                .append('}')
                .toString();
    }
}
