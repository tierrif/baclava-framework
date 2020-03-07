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

import java.util.function.Function;

/**
 * Primarily used in {@link CommandRegisterer#register(CommandDescription description, Function commandCallback)}.
 * Basic object to store command information.
 */
public class CommandDescription {
    private final String name;
    private String category;
    private String description;
    private String[] aliases = {};
    private String usage;
    private String examples;
    private String[] flags = {};

    /**
     * Create a new command description, to be used in
     * {@link CommandRegisterer#register(CommandDescription description, Function commandCallback)}.
     *
     * @param name The command name.
     */
    public CommandDescription(String name) {
        this.name = name;
    }

    /**
     * Create a new command description, to be used in
     * {@link CommandRegisterer#register(CommandDescription description, Function commandCallback)}.
     *
     * @param name The command name.
     * @param category The command category.
     */
    public CommandDescription(String name, String category) {
        this.name = name;
        this.category = category;
    }

    /**
     * Create a new command description, to be used in
     * {@link CommandRegisterer#register(CommandDescription description, Function commandCallback)}.
     *
     * @param name The command name.
     * @param category The command category.
     * @param description The command description.
     */
    public CommandDescription(String name, String category, String description) {
        this.name = name;
        this.category = category;
        this.description = description;
    }

    /**
     * Create a new command description, to be used in
     * {@link CommandRegisterer#register(CommandDescription description, Function commandCallback)}.
     *
     * @param name The command name.
     * @param category The command category.
     * @param description The command description.
     * @param aliases The command aliases.
     */
    public CommandDescription(String name, String category, String description, String[] aliases) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.aliases = aliases;
    }

    /**
     * Create a new command description, to be used in
     * {@link CommandRegisterer#register(CommandDescription description, Function commandCallback)}.
     *
     * @param name The command name.
     * @param category The command category.
     * @param description The command description.
     * @param aliases The command aliases.
     * @param usage The command syntax usage.
     */
    public CommandDescription(String name, String category, String description, String[] aliases, String usage) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.aliases = aliases;
        this.usage = usage;
    }

    /**
     * Create a new command description, to be used in
     * {@link CommandRegisterer#register(CommandDescription description, Function commandCallback)}.
     *
     * @param name The command name.
     * @param category The command category.
     * @param description The command description.
     * @param aliases The command aliases.
     * @param usage The command syntax usage.
     * @param examples The command syntax examples.
     */
    public CommandDescription(String name, String category, String description, String[] aliases, String usage, String examples) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.aliases = aliases;
        this.usage = usage;
        this.examples = examples;
    }

    /**
     * Create a new command description, to be used in
     * {@link CommandRegisterer#register(CommandDescription description, Function commandCallback)}.
     *
     * @param name The command name.
     * @param category The command category.
     * @param description The command description.
     * @param aliases The command aliases.
     * @param usage The command syntax usage.
     * @param examples The command syntax examples.
     * @param flags The command flags.
     */
    public CommandDescription(String name, String category, String description, String[] aliases, String usage, String examples, String... flags) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.aliases = aliases;
        this.usage = usage;
        this.examples = examples;
        this.flags = flags;
    }

    /**
     * @return The command name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The command category.
     */
    public String getCategory() {
        return category;
    }

    /**
     * @return The command description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return The command alias.
     */
    public String[] getAliases() {
        return aliases;
    }

    /**
     * @return The command usage.
     */
    public String getUsage() {
        return usage;
    }

    /**
     * @return The command examples.
     */
    public String getExamples() {
        return examples;
    }

    /**
     * @return The command flags.
     */
    public String[] getFlags() {
        return flags;
    }

    /**
     * @param category The category to set.
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param aliases The aliases to set.
     */
    public void setAliases(String... aliases) {
        this.aliases = aliases;
    }

    /**
     * @param usage The usage to set.
     */
    public void setUsage(String usage) {
        this.usage = usage;
    }

    /**
     * @param examples The examples to set.
     */
    public void setExamples(String examples) {
        this.examples = examples;
    }

    /**
     * @param flags The flags to set.
     */
    public void setFlags(String... flags) {
        this.flags = flags;
    }
}
