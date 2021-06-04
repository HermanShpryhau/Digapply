package by.epamtc.digapply.controller.command;

public class CommandResult {
    private final String page;
    private final boolean isRedirect;

    private CommandResult(String page, boolean redirect) {
        this.page = page;
        isRedirect = redirect;
    }

    public static CommandResult redirect(String page) {
        return new CommandResult(page, true);
    }

    public static CommandResult forward(String page) {
        return new CommandResult(page, false);
    }

    public String getPage() {
        return page;
    }

    public boolean isRedirect() {
        return isRedirect;
    }
}
