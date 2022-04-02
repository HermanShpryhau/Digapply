package dev.shph.digapply.controller.command;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RequestParameterParser {
    private static final String NUMBER_REGEX = "\\d+";

    public static final long INVALID_POSITIVE_LONG = -1L;
    public static final int INVALID_POSITIVE_INT = -1;

    private RequestParameterParser() {
    }

    public static long parsePositiveLong(Optional<String> optional) {
        if (optional.isPresent() && isNumericString(optional.get())) {
            return Long.parseLong(optional.get());
        } else {
            return INVALID_POSITIVE_LONG;
        }
    }

    public static int parsePositiveInt(Optional<String> optional) {
        if (optional.isPresent() && isNumericString(optional.get())) {
            return Integer.parseInt(optional.get());
        } else {
            return INVALID_POSITIVE_INT;
        }
    }

    private static boolean isNumericString(String number) {
        Pattern numberPattern = Pattern.compile(NUMBER_REGEX);
        Matcher matcher = numberPattern.matcher(number);
        return matcher.matches();
    }
}
