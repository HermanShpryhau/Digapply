package by.epamtc.digapply.service.validation;

import java.util.Map;

/**
 * Validates application data submitted via HTML form.
 */
public interface ApplicationFormDataValidator {

    /**
     * Validates all data submitted with application form.
     * @param userId ID of user.
     * @param facultyId ID of faculty.
     * @param scores Map containing IDs of subjects and relevant scores.
     * @param certificateIds Map containing IDs of subjects and relevant certificate IDs.
     * @return {@code true} if data is valid, {@code false} otherwise.
     */
    boolean validate(long userId, long facultyId, Map<String, String> scores, Map<String, String> certificateIds);

    /**
     * Validates results data submitted with application form.
     * @param scores Map containing IDs of subjects and relevant scores.
     * @param certificateIds Map containing IDs of subjects and relevant certificate IDs.
     * @return {@code true} if data is valid, {@code false} otherwise.
     */
    boolean validateScoreMaps(Map<String, String> scores, Map<String, String> certificateIds);

}
