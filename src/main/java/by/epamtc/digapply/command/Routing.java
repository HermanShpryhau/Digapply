package by.epamtc.digapply.command;

public class Routing {
    private final String page;
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
