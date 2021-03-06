package dev.shph.digapply.entity.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class ApplicationDto implements Serializable {
    private long applicationId;
    private long applicantId;
    private String applicantName;
    private String facultyName;
    private List<ResultDto> results;
    private int totalScore;
    private Timestamp applyDate;
    private boolean isApproved;
    private Timestamp approveDate;

    public ApplicationDto() {}

    public long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(long applicationId) {
        this.applicationId = applicationId;
    }

    public long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(long applicantId) {
        this.applicantId = applicantId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public List<ResultDto> getResults() {
        return results;
    }

    public void setResults(List<ResultDto> results) {
        this.results = results;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;

        ApplicationDto other = (ApplicationDto) o;

        if (applicationId != other.applicationId) return false;
        if (applicationId != other.getApplicantId()) return false;
        if (totalScore != other.totalScore) return false;
        if (isApproved != other.isApproved) return false;
        if (!applicantName.equals(other.applicantName)) return false;
        if (!facultyName.equals(other.facultyName)) return false;
        if (!results.equals(other.results)) return false;
        if (!applyDate.equals(other.applyDate)) return false;
        return approveDate != null ? approveDate.equals(other.approveDate) : other.approveDate == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (applicationId ^ (applicationId >>> 32));
        result = 31 * result + (int) (applicantId ^ (applicationId >>> 32));
        result = 31 * result + applicantName.hashCode();
        result = 31 * result + facultyName.hashCode();
        result = 31 * result + results.hashCode();
        result = 31 * result + totalScore;
        result = 31 * result + applyDate.hashCode();
        result = 31 * result + (isApproved ? 1 : 0);
        result = 31 * result + (approveDate != null ? approveDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("ApplicationDto{")
                .append("applicationId=").append(applicationId)
                .append(", applicantId=").append(applicantId)
                .append(", applicantName='").append(applicantName).append('\'')
                .append(", facultyName='").append(facultyName).append('\'')
                .append(", results=").append(results)
                .append(", totalScore=").append(totalScore)
                .append(", applyDate=").append(applyDate)
                .append(", isApproved=").append(isApproved)
                .append(", approveDate=").append(approveDate)
                .append('}').toString();
    }
}
