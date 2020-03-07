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
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

class BaclavaFrameworkImpl implements BaclavaFramework {
    private final JDA jda;
    private final ReactiveEventManager manager;
    private final String prefix;
    private final CommandRegisterer registerer;
    private final long ownerId;

    BaclavaFrameworkImpl(JDA jda, ReactiveEventManager manager, String prefix, CommandRegisterer registerer, long ownerId) {
        this.jda = jda;
        this.manager = manager;
        this.prefix = prefix;
        this.registerer = registerer;
        this.ownerId = ownerId;
    }

    @Override
    public JDA getJDA() {
        return this.jda;
    }

    @Override
    public String getPrefix() {
        return this.prefix;
    }

    @Override
    public long getOwnerId() {
        return this.ownerId;
    }

    @Override
    public CommandRegisterer getCommandRegisterer() {
        return this.registerer;
    }

    @Override
    public <T> Logger getLogger(Class<T> main) {
        return LoggerFactory.getLogger(main);
    }

    @Override
    public void onReady(Consumer<ReadyEvent> e) {
        this.manager.on(ReadyEvent.class)
                .next()
                .subscribe(e);
    }

    @Override
    public <T extends GenericEvent> void on(Class<T> event, Consumer<? super T> callback) {
        this.manager.on(event)
                .next()
                .subscribe(callback);
    }
}
