package dev.shph.digapply.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class Application  implements Identifiable, Serializable {
    private long applicationId;
    private long userId;
    private long facultyId;
    private Timestamp applyDate;
    private boolean isApproved;
    private Timestamp approveDate;

    public Application() {}

    public long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(long applicationId) {
        this.applicationId = applicationId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(long facultyId) {
        this.facultyId = facultyId;
    }

    public Timestamp getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Timestamp applyDate) {
        this.applyDate = applyDate;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public Timestamp getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Timestamp approveDate) {
        this.approveDate = approveDate;
    }

    @Override
    public long getId() {
        return applicationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Application)) return false;

        Application that = (Application) o;

        if (applicationId != that.applicationId) return false;
        if (userId != that.userId) return false;
        if (facultyId != that.facultyId) return false;
        if (isApproved != that.isApproved) return false;
        if (!applyDate.equals(that.applyDate)) return false;
        return Objects.equals(approveDate, that.approveDate);
    }

    @Override
    public int hashCode() {
        int result = (int) (applicationId ^ (applicationId >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (facultyId ^ (facultyId >>> 32));
        result = 31 * result + applyDate.hashCode();
        result = 31 * result + (isApproved ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder(getClass().getSimpleName())
                .append("{")
                .append("applicationId=").append(applicationId)
                .append(", userId=").append(userId)
                .append(", facultyId=").append(facultyId)
                .append(", applyDate=").append(applyDate)
                .append(", isApproved=").append(isApproved)
                .append(", approveDate=").append(approveDate)
                .append('}')
                .toString();
    }
}
