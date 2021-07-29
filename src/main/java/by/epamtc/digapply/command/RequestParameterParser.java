package by.epamtc.digapply.command;

import java.util.Optional;

public class RequestParameterParser {
    public static final long INVALID_POSITIVE_LONG = -1L;
    public static final int INVALID_POSITIVE_INT = -1;

    public static long parsePositiveLong(Optional<String> optional) {
        long number = INVALID_POSITIVE_INT;
        if (optional.isPresent()) {
            try {
                number = Long.parseLong(optional.get());
            } catch (NumberFormatException ignored) {
            }
        }
        return number;
    }

    public static int parsePositiveInt(Optional<String> optional) {
        int number = INVALID_POSITIVE_INT;
        if (optional.isPresent()) {
            try {
                number = Integer.parseInt(optional.get());
            } catch (NumberFormatException ignored) {
            }
        }
        return number;
    }
}
