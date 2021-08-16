package by.epamtc.digapply.command;

/**
 * Result of {@link Command} execution.
 */
public class Routing {
    public static final Routing ERROR_404 = new Routing(PagePath.ERROR_404_PAGE_REDIRECT, RoutingType.REDIRECT);
    public static final Routing ERROR_500 = new Routing(PagePath.ERROR_500_PAGE_REDIRECT, RoutingType.REDIRECT);
    public static final Routing ERROR = new Routing(PagePath.ERROR_PAGE_REDIRECT, RoutingType.REDIRECT);

    /**
     * Path to resulting resource.
     */
    private final String resource;

    /**
     * Type of routing. Either forwarding or redirection.
     */
    private final RoutingType type;

    public Routing(String resource, RoutingType resultType) {
        this.resource = resource;
        type = resultType;
    }

    public RoutingType getRoutingType() {
        return type;
    }

    public String getResource() {
        return resource;
    }
}
