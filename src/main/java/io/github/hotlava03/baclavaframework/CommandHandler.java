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

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

class CommandHandler {
    private final String prefix;
    private final CommandRegisterer registerer;
    private final long ownerId;

    CommandHandler(String prefix, CommandRegisterer registerer, long ownerId) {
        this.prefix = prefix;
        this.registerer = registerer;
        this.ownerId = ownerId;
    }

    void acceptCall(MessageReceivedEvent e) {
        String text = e.getMessage().getContentRaw().substring(this.prefix.length());
        String commandName = text.split("\\s+")[0];
        text = text.substring(commandName.length());
        Command command = this.registerer.getCommandByName(commandName);
        if (command == null) return;
        if (command.getCategory().equalsIgnoreCase("owner") && !e.getAuthor().getId().equals(String.valueOf(ownerId)))
            return;
        String response = command.execute(new CommandEvent(e, commandName, text));
        if (response != null && !response.isEmpty())
            e.getChannel().sendMessage(response).queue();
    }
}
