package by.epamtc.digapply.command;

import java.util.Optional;

public class RequestParameterParser {
    public static final long INVALID_ID = -1L;

    public static long parseOptionalIdString(Optional<String> idString) {
        long id = INVALID_ID;
        if (idString.isPresent()) {
            try {
                id = Long.parseLong(idString.get());
            } catch (NumberFormatException ignored) {
            }
        }
        return id;
    }
}
