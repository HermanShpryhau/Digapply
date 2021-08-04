package by.epamtc.digapply.entity.dto;

import java.io.Serializable;

public class ResultDto implements Serializable {
    private long resultId;
    private long applicationId;
    private long subjectId;
    private String subjectName;
    private int score;
    private String certificateId;

    public ResultDto() {}

    public long getResultId() {
        return resultId;
    }

    public void setResultId(long resultId) {
        this.resultId = resultId;
    }

    public long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(long applicationId) {
        this.applicationId = applicationId;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;

        ResultDto other = (ResultDto) o;

        if (resultId != other.resultId) return false;
        if (applicationId != other.applicationId) return false;
        if (subjectId != other.subjectId) return false;
        if (score != other.score) return false;
        if (!subjectName.equals(other.subjectName)) return false;
        return certificateId.equals(other.certificateId);
    }

    @Override
    public int hashCode() {
        int result = (int) (resultId ^ (resultId >>> 32));
        result = 31 * result + (int) (applicationId ^ (applicationId >>> 32));
        result = 31 * result + (int) (subjectId ^ (subjectId >>> 32));
        result = 31 * result + subjectName.hashCode();
        result = 31 * result + score;
        result = 31 * result + certificateId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("ResultDto{")
                .append("resultId=").append(resultId)
                .append(", applicationId=").append(applicationId)
                .append(", subjectId=").append(subjectId)
                .append(", subjectName='").append(subjectName).append('\'')
                .append(", score=").append(score)
                .append(", certificateId='").append(certificateId).append('\'')
                .append('}').toString();
    }
}
