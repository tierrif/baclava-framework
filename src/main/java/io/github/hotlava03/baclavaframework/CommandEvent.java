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
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Command event object used on command.
 * Only obtainable when a command event is triggered.
 *
 * @see Command#onCommand(CommandEvent e)
 */
public class CommandEvent {
    private final String content;
    private MessageReceivedEvent event;
    private String command;
    private MessageChannel channel;
    private Guild guild;
    private User author;
    private Member member;
    private JDA jda;
    private String[] args;
    private String[] flags;

    CommandEvent(MessageReceivedEvent e, String command, String content) {
        this.event = e;
        this.command = command;
        this.channel = e.getChannel();
        this.guild = e.getGuild();
        this.author = e.getAuthor();
        this.member = e.getMember();
        this.jda = e.getJDA();
        this.content = content;
        this.findArgsAndFlags();
    }

    /**
     * Check if the user has used a flag in the command.
     *
     * @param name The flag name. Does not include "-".
     * @return True if the flag exists in the run command, false if not.
     */
    public boolean hasFlag(String name) {
        for (String flag : flags)
            if (flag.equalsIgnoreCase(name)) return true;
        return false;
    }

    /**
     * Send a message without needing to worry with {@link net.dv8tion.jda.api.requests.RestAction}s.
     * The message is sent to the same channel as the one the command was executed from.
     *
     * @param msg The message to reply with.
     * @throws IllegalArgumentException If msg is null.
     */
    public void reply(Object msg) {
        if (msg == null) throw new IllegalArgumentException("The message cannot be null!");
        this.getChannel().sendMessage(msg.toString()).queue();
    }

    /**
     * Send a message without needing to worry with {@link net.dv8tion.jda.api.requests.RestAction}s.
     * The message is sent to the same channel as the one the command was executed from.
     * Supports lambda syntax to execute a callback after success.
     *
     * @param msg The message to reply with.
     * @param andThen The callback to run after success.
     * @throws IllegalArgumentException If msg and/or andThen is/are null.
     */
    public void reply(Object msg, Consumer<? super Message> andThen) {
        if (msg == null || andThen == null) throw new IllegalArgumentException("The message and/or callback cannot be null!");
        this.getChannel().sendMessage(msg.toString()).queue(andThen);
    }

    /**
     * Send a message embed without needing to worry with {@link net.dv8tion.jda.api.requests.RestAction}s.
     * The message embed is sent to the same channel as the one the command was executed from.
     *
     * @param embed The message embed to reply with.
     * @throws IllegalArgumentException If embed is null.
     *
     * @see net.dv8tion.jda.api.EmbedBuilder
     */
    public void reply(MessageEmbed embed) {
        if (embed == null) throw new IllegalArgumentException("The embed cannot be null!");
        this.getChannel().sendMessage(embed).queue();
    }

    /**
     * Send a message embed without needing to worry with {@link net.dv8tion.jda.api.requests.RestAction}s.
     * The message embed is sent to the same channel as the one the command was executed from.
     * Supports lambda syntax to execute a callback after success.
     *
     * @param embed The message embed to reply with.
     * @param andThen The callback to run after success.
     * @throws IllegalArgumentException If embed and/or andThen are null.
     *
     * @see net.dv8tion.jda.api.EmbedBuilder
     */
    public void reply(MessageEmbed embed, Consumer<? super Message> andThen) {
        this.getChannel().sendMessage(embed).queue(andThen);
    }

    /**
     * Send a message through JDA's {@link Message} object without needing to
     * worry with {@link net.dv8tion.jda.api.requests.RestAction}s.
     *
     * @param message The message to send.
     *
     * @see net.dv8tion.jda.api.MessageBuilder
     */
    public void reply(Message message) {
        if (message == null) throw new IllegalArgumentException("Message cannot be null!");
        this.getChannel().sendMessage(message).queue();
    }

    /**
     * Send a message through JDA's {@link Message} object without needing to
     * worry with {@link net.dv8tion.jda.api.requests.RestAction}s.
     * Supports lambda syntax to execute a callback after success.
     *
     * @param message The message to send.
     * @param andThen The callback to run after success.
     *
     * @see net.dv8tion.jda.api.MessageBuilder
     */
    public void reply(Message message, Consumer<? super Message> andThen) {
        this.getChannel().sendMessage(message).queue(andThen);
    }

    /**
     * Check if the command contains a guild. When a command isn't executed inside
     * a guild, guild and member are null.
     *
     * @return True if the command was run in a guild and guild and member aren't null,
     *         false if not.
     */
    public boolean hasGuild() {
        return this.guild != null;
    }

    private void findArgsAndFlags() {
        if (this.content.isEmpty()) {
            this.args = new String[]{};
            this.flags = new String[]{};
            return;
        }

        String[] rawArgs = this.content.substring(this.content.indexOf(" ")).trim().split("\\s+");
        List<String> args = new ArrayList<>();
        List<String> flags = new ArrayList<>();

        for (String arg : rawArgs)
            if (arg.startsWith("--")) flags.add(arg.substring(2));
            else args.add(arg);

        this.args = args.toArray(new String[0]);
        this.flags = flags.toArray(new String[0]);
    }

    // Getters and setters

    /**
     * @return The command arguments.
     */
    public String[] getArgs() {
        return args;
    }

    /**
     * @return The original {@link MessageReceivedEvent}.
     */
    public MessageReceivedEvent getEvent() {
        return event;
    }

    /**
     * @return The original {@link Message}.
     */
    public Message getMessage() {
        return event.getMessage();
    }

    /**
     * @return The command name.
     */
    public String getCommand() {
        return command;
    }

    /**
     * @return The {@link MessageChannel}.
     */
    public MessageChannel getChannel() {
        return channel;
    }

    /**
     * @return The {@link Guild} or null if the command wasn't run in a guild.
     */
    @Nullable
    public Guild getGuild() {
        return guild;
    }

    /**
     * @return The {@link User} who ran the command.
     */
    public User getAuthor() {
        return author;
    }

    /**
     * @return The {@link Member} who ran the command or null if the command wasn't
     *         run in a guild.
     */
    @Nullable
    public Member getMember() {
        return member;
    }

    /**
     * @return The original {@link JDA} instance.
     */
    public JDA getJda() {
        return jda;
    }
}
