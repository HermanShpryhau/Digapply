package by.epamtc.digapply.entity;

import by.epamtc.digapply.model.DataBean;

import java.io.Serializable;

public class Subject implements DataBean, Serializable {
    private long subjectId;
    private String subjectName;

    public Subject() {}

    public Subject(long subjectId, String subjectName) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
    }

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Override
    public long getId() {
        return subjectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subject other = (Subject) o;

        if (subjectId != other.subjectId) return false;
        return subjectName.equals(other.subjectName);
    }

    @Override
    public int hashCode() {
        int result = (int) (subjectId ^ (subjectId >>> 32));
        result = 31 * result + subjectName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder(getClass().getSimpleName())
                .append("{")
                .append("subjectId=").append(subjectId)
                .append(", subjectName='").append(subjectName).append('\'')
                .append('}')
                .toString();
    }
}
