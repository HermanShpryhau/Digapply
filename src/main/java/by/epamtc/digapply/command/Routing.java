package by.epamtc.digapply.command;

/**
 * Result of command execution.
 */
public class Routing {
    public static final Routing ERROR_404 = new Routing(PagePath.ERROR_404_PAGE, RoutingType.FORWARD);
    public static final Routing ERROR_500 = new Routing(PagePath.ERROR_500_PAGE, RoutingType.FORWARD);
    public static final Routing ERROR = new Routing(PagePath.ERROR_PAGE, RoutingType.FORWARD);

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
