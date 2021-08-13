package by.epamtc.digapply.service.validation;

public interface SubjectFormDataValidator {

    /**
     * Validate subject name against regex.
     * @param subjectName Submitted subject name.
     * @return {@code true} if name matches pattern, {@code false} otherwise.
     */
    boolean validate(String subjectName);
}
