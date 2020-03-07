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

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import org.slf4j.Logger;

import java.util.function.Consumer;

public interface BaclavaFramework {
    /**
     * Get the generated JDA instance after building the framework.
     *
     * @return The generated JDA instance.
     */
    JDA getJDA();

    /**
     * Get the bot prefix, set when building the framework.
     *
     * @return The bot prefix.
     */
    String getPrefix();

    /**
     * Get the bot owner ID. Useful for admin listeners, as admin commands are
     * already supported by the framework.
     *
     * @return The owner ID.
     *
     * @see Command#setCategory(String category)
     */
    long getOwnerId();

    /**
     * The command registerer class instance, parent of your bot's
     * command registerer class. Useful to retrieve all commands for
     * a help generator, for example.
     *
     * @return The command registerer instance.
     *
     * @see CommandRegisterer#getCommands()
     * @see CommandRegisterer#getCommandsAsList()
     * @see CommandRegisterer#getCommandByName(String name)
     */
    CommandRegisterer getCommandRegisterer();

    /**
     * Get the bot logger.
     *
     * @param main Your main {@link Class} object.
     * @param <T> Your main class.
     * @return The bot logger.
     */
    <T> Logger getLogger(Class<T> main);

    /**
     * Create a listener for a ready event.
     * It is recommended to always use this in your bot.
     * <b>DO NOT</b> rely on line sequence as bot startup
     * is asynchronous.
     *
     * @param e The ready event callback.
     */
    void onReady(Consumer<ReadyEvent> e);

    /**
     * Create a listener for any JDA {@link GenericEvent}.
     * Do not use this for commands. Command listeners
     * are part of the framework.
     *
     * @param event Event {@link Class} object.
     * @param callback Callback for the listener.
     * @param <T> Any JDA {@link GenericEvent}.
     *
     * @see Command
     * @see CommandEvent
     * @see CommandRegisterer
     */
    <T extends GenericEvent> void on(Class<T> event, Consumer<? super T> callback);
}
