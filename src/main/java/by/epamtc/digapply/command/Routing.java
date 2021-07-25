package by.epamtc.digapply.command;

/**
 * Result of command execution.
 */
public class Routing {
    /**
     * Path to page of routing.
     */
    private final String page;

    /**
     * Type of routing. Can be forward or redirect.
     */
    private final RoutingType type;

    public Routing(String page, RoutingType resultType) {
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
