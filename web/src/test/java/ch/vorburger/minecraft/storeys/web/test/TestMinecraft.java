/*
 * ch.vorburger.minecraft.storeys
 *
 * Copyright (C) 2016 - 2018 Michael Vorburger.ch <mike@vorburger.ch>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ch.vorburger.minecraft.storeys.web.test;

import ch.vorburger.minecraft.storeys.api.HandType;
import ch.vorburger.minecraft.storeys.api.ItemType;
import ch.vorburger.minecraft.storeys.api.LoginResponse;
import ch.vorburger.minecraft.storeys.api.Minecraft;
import ch.vorburger.minecraft.storeys.api.Token;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link Minecraft} suitable for testing.
 *
 * @author Michael Vorburger.ch
 */
public class TestMinecraft implements Minecraft {

    private static final Logger LOG = LoggerFactory.getLogger(TestMinecraft.class);

    public Map<String, String> results = new ConcurrentHashMap<>();
    public Map<HandType, ItemType> itemsHeld = new ConcurrentHashMap<>();
    public List<String> ranCommands = new CopyOnWriteArrayList<>();

    @Override
    public void showTitle(String playerUUID, String message, Handler<AsyncResult<Void>> handler) {
        LOG.info("showTitle({}, {})", playerUUID, message);
        results.put("lastTitle", message);
        handler.handle(Future.succeededFuture());
    }

    @Override
    public void narrate(String playerUUID, String entity, String text, Handler<AsyncResult<Void>> handler) {
        LOG.info("narrate({}, {}, {})", playerUUID, entity, text);
        results.put("entity", entity);
        results.put("text", text);
        handler.handle(Future.succeededFuture());
    }

    @Override
    public void runCommand(String playerUUID, String command, Handler<AsyncResult<Void>> handler) {
        ranCommands.add(command);
        handler.handle(Future.succeededFuture());
    }

    @Override
    public void getItemHeld(String playerUUID, HandType hand, Handler<AsyncResult<ItemType>> handler) {
        handler.handle(Future.succeededFuture(itemsHeld.getOrDefault(hand, ItemType.Nothing)));
    }

    @Override
    public void addRemoveItem(String playerUUID, int amount, ItemType item, Handler<AsyncResult<Void>> handler) {
        LOG.info("addRemoveItem({}, {})", amount, item);
        results.put("addRemoveItem", amount + item.toString());
        handler.handle(Future.succeededFuture());
    }

    @Override
    public void whenInside(String playerUUID, String name, Handler<AsyncResult<Void>> handler) {
        LOG.info("whenInside({})", name);
        handler.handle(Future.succeededFuture());

    }
}
