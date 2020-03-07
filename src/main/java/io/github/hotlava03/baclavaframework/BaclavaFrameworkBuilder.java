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

import club.minnced.jda.reactor.ReactiveEventManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.security.auth.login.LoginException;

public class BaclavaFrameworkBuilder {
    private String token;
    private CommandRegisterer registerer;
    private String prefix;
    private long ownerId = -1;

    /**
     * Create a new {@link BaclavaFrameworkBuilder} without setting anything.
     */
    public BaclavaFrameworkBuilder() {
    }

    /**
     * @param token The bot token. Recommended to use {@link System#getenv(String envName)}
     *              (environment variables).
     */
    public BaclavaFrameworkBuilder(String token) {
        this.token = token;
    }

    /**
     * @param token The bot token. Recommended to use {@link System#getenv(String envName)}
     *              (environment variables).
     * @param registerer The command registerer.
     */
    public BaclavaFrameworkBuilder(String token, CommandRegisterer registerer) {
        this.token = token;
        this.registerer = registerer;
    }

    /**
     * @param token The bot token. Recommended to use {@link System#getenv(String envName)}
     *              (environment variables).
     * @param registerer The command registerer.
     * @param prefix The bot prefix.
     */
    public BaclavaFrameworkBuilder(String token, CommandRegisterer registerer, String prefix) {
        this.token = token;
        this.registerer = registerer;
        this.prefix = prefix;
    }

    /**
     * Build the client. Make sure you have set at least the token, the prefix, the owner ID and the
     * registerer.
     *
     * @return The built {@link BaclavaFramework}.
     * @throws LoginException When a LoginException occurs when building JDA.
     * @throws IllegalStateException Whenever token and/or prefix and/or ownerId and/or registerer aren't set.
     */
    public BaclavaFramework build() throws LoginException, IllegalStateException {
        if (this.token == null)
            throw new IllegalStateException("Please set the token before building the command client.");
        if (this.prefix == null)
            throw new IllegalStateException("The prefix must be set in order for commands to work.");
        if (this.ownerId == -1)
            throw new IllegalStateException("Please set the owner ID before building the command client.");
        if (this.registerer == null)
            throw new IllegalStateException("Please set the registerer before building the command client.");

        ReactiveEventManager manager = new ReactiveEventManager();
        this.registerer.handleRegistration();
        CommandHandler handler = new CommandHandler(this.prefix, registerer, this.ownerId);

        manager.on(MessageReceivedEvent.class)
                .filter(e -> !(e.getAuthor().isBot() || e.getAuthor().isFake()))
                .filter(e -> e.getMessage().getContentRaw().startsWith(prefix))
                .subscribe(handler::acceptCall);

        JDA jda = new JDABuilder()
                .setToken(this.token)
                .setEventManager(manager)
                .build();

        BaclavaFramework builtClient = new BaclavaFrameworkImpl(jda, manager, this.prefix, this.registerer, this.ownerId);

        builtClient.getLogger(this.getClass()).info("Successfully started Baclava Command Client v1.0.0.");

        return builtClient;
    }

    /**
     * @param token The bot token.
     * @return The current instance for chained calls.
     */
    public BaclavaFrameworkBuilder setToken(String token) {
        this.token = token;
        return this;
    }

    /**
     * @param registerer The command registerer.
     * @return The current instance for chained calls.
     */
    public BaclavaFrameworkBuilder setCommandRegisterer(CommandRegisterer registerer) {
        this.registerer = registerer;
        return this;
    }

    /**
     * @param id The bot owner ID.
     * @return The current instance for chained calls.
     */
    public BaclavaFrameworkBuilder setOwnerId(long id) {
        this.ownerId = id;
        return this;
    }

    /**
     * @param prefix The bot command prefix.
     * @return The current instance for chained calls.
     */
    public BaclavaFrameworkBuilder setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }
}
