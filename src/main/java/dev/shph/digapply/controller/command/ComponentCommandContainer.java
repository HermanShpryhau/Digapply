package dev.shph.digapply.controller.command;

import dev.shph.commandeur.Command;
import dev.shph.commandeur.container.CommandContainer;
import dev.shph.commandeur.container.CommandeurException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@Component
public class ComponentCommandContainer implements CommandContainer {
    @Autowired
    private Map<String, Command> commands;

    @Override
    public Command resolve(Supplier<String> supplier) {
        return resolve(supplier.get());
    }

    @Override
    public Command resolve(String commandName) {
        return Optional.ofNullable(commands.get(commandName)).orElse(new Command.Empty());
    }

    @Override
    public void register(String commandName, Class<?> commandClass) {
        try {
            Command commandInstance = (Command) commandClass.newInstance();
            register(commandName, commandInstance);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new CommandeurException("Cannot create instance of " + commandClass.getName(), e);
        }
    }

    @Override
    public void register(String commandName, Command command) {
        if (commands.get(commandName) != null) {
            throw new CommandeurException("Duplicate command name \"" + commandName +"\"");
        }
        commands.put(commandName, command);
    }
}
