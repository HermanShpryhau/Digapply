package by.epamtc.digapply.entity;

import java.io.Serializable;

public class Result implements Identifiable, Serializable {
    private long resultId;
    private long applicationId;
    private long subjectId;
    private int score;
    private String certificateId;

    public Result() {
    }

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

        Result result = (Result) o;

        if (resultId != result.resultId) return false;
        if (applicationId != result.applicationId) return false;
        if (subjectId != result.subjectId) return false;
        if (score != result.score) return false;
        return certificateId.equals(result.certificateId);
    }

    @Override
    public long getId() {
        return resultId;
    }

    @Override
    public int hashCode() {
        int result = (int) (resultId ^ (resultId >>> 32));
        result = 31 * result + (int) (applicationId ^ (applicationId >>> 32));
        result = 31 * result + (int) (subjectId ^ (subjectId >>> 32));
        result = 31 * result + score;
        result = 31 * result + certificateId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Result{")
                .append("resultId=").append(resultId)
                .append(", applicationId=").append(applicationId)
                .append(", subjectId=").append(subjectId)
                .append(", score=").append(score)
                .append(", certificateId='")
                .append(certificateId).append('\'')
                .append('}')
                .toString();
    }
}
