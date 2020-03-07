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

/**
 * Must be extended if you use a dedicated class for a command.
 * Required by {@link CommandRegisterer#registerCommand(String name, Command command)}.
 *
 * @see CommandRegisterer
 */
public abstract class Command {
    private String category;
    private String[] aliases = {};
    private String usage;
    private String examples;
    private String[] flags = {};

    String execute(CommandEvent e) {
        return this.onCommand(e);
    }

    /**
     * Called whenever the command is executed.
     *
     * @param e The command event involved on the trigger.
     * @return The message to reply to the user. May be null if nothing is to be sent.
     */
    protected abstract String onCommand(CommandEvent e);

    /**
     * @return The category.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Set the command's category. Useful for generated help commands. Must use for owner commands!
     * For owner command usage, set <b>category</b> to "owner".
     *
     * @param category The category to set.
     * @return The current command instance.
     */
    protected Command setCategory(String category) {
        this.category = category;
        return this;
    }

    /**
     * @return The aliases.
     */
    public String[] getAliases() {
        return aliases;
    }

    /**
     * @param aliases The command aliases.
     * @return The current command object for chained calls.
     */
    protected Command setAliases(String... aliases) {
        this.aliases = aliases;
        return this;
    }

    /**
     * @return The usage.
     */
    public String getUsage() {
        return usage;
    }

    /**
     * @param usage The command syntax usage.
     * @return The current command object for chained calls.
     */
    protected Command setUsage(String usage) {
        this.usage = usage;
        return this;
    }

    /**
     * @return The examples.
     */
    public String getExamples() {
        return examples;
    }

    /**
     * @param examples The command usage examples, formatted in text.
     * @return The current command object for chained calls.
     */
    protected Command setExamples(String examples) {
        this.examples = examples;
        return this;
    }

    /**
     * @return The flags.
     */
    public String[] getFlags() {
        return flags;
    }

    /**
     * @param flags All flags the command must support.
     * @return The current command object for chained calls.
     */
    protected Command setFlags(String... flags) {
        this.flags = flags;
        return this;
    }

    /**
     * Check if the command supports a flag on declaration.
     * In order for this to work, the flag must be declared
     * with {@link Command#setFlags(String... flags)}
     *
     * @param name The flag name.
     * @return True if this command has the flag, false if not.
     */
    protected boolean hasFlag(String name) {
        for (String flag : this.flags)
            if (flag.equalsIgnoreCase(name)) return true;
        return false;
    }
}
