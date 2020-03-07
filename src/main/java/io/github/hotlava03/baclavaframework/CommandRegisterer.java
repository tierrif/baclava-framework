/*
 * BaclavaFramework - Simple JDA command client.
 * Copyright (C) 2020 HotLava03
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.hotlava03.baclavaframework;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * You must extend this class at least once in your JDA bot project.
 * This class is responsible for registering commands in your bot,
 * through {@link CommandRegisterer#handleRegistration()}.
 *
 * @see CommandRegisterer#handleRegistration()
 * @see CommandRegisterer#registerCommand(String name, Command command)
 * @see CommandRegisterer#register(CommandDescription description, Function commandCallback)
 * @see CommandRegisterer#register(String name, Function commandCallback)
 */
public abstract class CommandRegisterer {
    private final Map<String, Command> commands = new HashMap<>();

    /**
     * Add all registered commands here. This method is called when you build {@link BaclavaFramework}
     * ({@link BaclavaFrameworkBuilder#build()}).<br>
     * Usage: {@link CommandRegisterer#registerCommand(String name, Command command)}.<br><br>
     *
     * You may also fully register commands without the need of implementing {@link Command}
     * in a class by using {@link CommandRegisterer#register(CommandDescription description, Function commandCallback)}.<br>
     * If your command only requires a name, you may even use
     * {@link CommandRegisterer#register(String name, Function commandCallback)}.<br><br>
     *
     * @see CommandRegisterer#registerCommand(String name, Command command)
     * @see CommandRegisterer#register(CommandDescription description, Function commandCallback)
     * @see CommandRegisterer#register(String name, Function commandCallback)
     */
    public abstract void handleRegistration();

    /**
     * Register a command when you dedicated a class for it. It is not recommended to instantiate
     * the abstract class, use {@link CommandRegisterer#register(CommandDescription description, Function commandCallback)}
     * instead, which will be much cleaner with lambda syntax.
     *
     * @param name The name of the command.
     * @param command The command which extends {@link Command}, instantiated.
     */
    public void registerCommand(String name, Command command) {
        this.commands.put(name, command);
    }

    /**
     * Register a command with lambda syntax. Useful when your command is short enough, for example.<br>
     * Only use this method variation if you only require the command to have a name.
     *
     * @param name The name of the command.
     * @param commandCallback The callback.
     *
     * @see CommandRegisterer#register(CommandDescription description, Function commandCallback)
     */
    public void register(String name, Function<CommandEvent, String> commandCallback) {
        register(new CommandDescription(name), commandCallback);
    }

    /**
     * Register a command with lambda syntax. Useful when your command is short enough, for example.<br>
     * {@link CommandDescription} will contain all command data, such as name, aliases, category, etc.
     *
     * @param description The full command description.
     * @param commandCallback The callback.
     *
     * @see CommandDescription
     */
    public void register(CommandDescription description, Function<CommandEvent, String> commandCallback) {
        Command command = new Command() {
            @Override
            protected String onCommand(CommandEvent e) {
                return commandCallback.apply(e);
            }
        };

        command.setAliases(description.getAliases())
                .setCategory(description.getCategory())
                .setExamples(description.getExamples())
                .setFlags(description.getFlags())
                .setUsage(description.getUsage());

        this.commands.put(description.getName(), command);
    }

    /**
     * Get a registered command by its name.
     *
     * @param name The command name.
     * @return The first command found with that name or null if it does not exist.
     */
    @Nullable
    public Command getCommandByName(String name) {
        if (commands.get(name) != null) return commands.get(name);
        for (Map.Entry<String, Command> command : commands.entrySet())
            for (String alias : command.getValue().getAliases())
                if (alias.equalsIgnoreCase(name)) return command.getValue();
        return null;
    }

    /**
     * Get all registered commands in {@link Map} format.
     *
     * @return All registered commands.
     */
    public Map<String, Command> getCommands() {
        return this.commands;
    }

    /**
     * Get all registered commands in {@link List} format.<br>
     * Beware that the command name is not included in the list.<br>
     * Use {@link CommandRegisterer#getCommands()} for this instead.<br>
     *
     * @return All registered commands, without the name.
     */
    public List<Command> getCommandsAsList() {
        return new ArrayList<>(this.commands.values());
    }

    /**
     * Get all registered command names.
     *
     * @return All registered command names.
     */
    public List<String> getAllCommandNames() {
        return new ArrayList<>(this.commands.keySet());
    }
}
