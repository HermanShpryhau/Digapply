package by.epamtc.digapply.service.validation;

/**
 * Validates subject data submitted via HTML form.
 */
public interface SubjectFormDataValidator {

    /**
     * Validate subject name against regex.
     * @param subjectName Submitted subject name.
     * @return {@code true} if name matches pattern, {@code false} otherwise.
     */
    boolean validate(String subjectName);
}
