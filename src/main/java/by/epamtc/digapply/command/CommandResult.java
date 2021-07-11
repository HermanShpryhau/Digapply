package by.epamtc.digapply.command;

public class CommandResult {
    private final String page;
    private final RoutingType type;

    public CommandResult(String page, RoutingType resultType) {
        this.page = page;
        type = resultType;
    }

    public RoutingType getRoutingType() {
        return type;
    }

    public String getPage() {
        return page;
    }
}
