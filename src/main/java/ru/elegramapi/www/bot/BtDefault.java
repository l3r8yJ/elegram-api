/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2022-2023 Ivanchuck Ivan.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
/*
 * @todo #60 Design/ Testing.
 * Write tests for DefaultBot class after closing other issues in DefaultBot class.
 * */
/*
* @todo #106 Fix #handleUpdates.
* This method work unpredictable,
* need to fix work with incoming updates.
* */
package ru.elegramapi.www.bot;

import com.jcabi.http.response.JsonResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import org.cactoos.list.ListOf;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.elegramapi.www.Bot;
import ru.elegramapi.www.Command;
import ru.elegramapi.www.request.TRqGetUpdates;
import ru.elegramapi.www.request.TRqPost;
import ru.elegramapi.www.request.TRqSendMessage;
import ru.elegramapi.www.request.TRqWithChatId;
import ru.elegramapi.www.request.TRqWithOffset;
import ru.elegramapi.www.request.TRqWithText;
import ru.elegramapi.www.update.UpdDefault;

/**
 * The default implementation of {@link Bot}.
 *
 * @since 0.0.0
 */
public class BtDefault implements Bot {

    /**
     * All commands.
     */
    private final List<Command> commands;

    /**
     * The token.
     */
    private final String token;

    /**
     * The processed updates.
     */
    private final Set<Long> processed;

    /**
     * Read-write lock.
     */
    private Thread thread;

    /**
     * Ctor.
     *
     * @param token The token
     * @param commands All custom commands
     */
    public BtDefault(final String token, final Command... commands) {
        this.token = token;
        this.commands = new ListOf<>(commands);
        this.processed = new HashSet<>(0);
        this.thread = new Thread();
    }

    @Override
    public final void start() throws IOException, InterruptedException {
        this.handleUpdates();
    }

    @Override
    public final JsonResponse sendMessage(final long chat, final String text) throws IOException {
        return new TRqPost(
            new TRqWithChatId(
                new TRqWithText(
                    new TRqSendMessage(this.token),
                    text
                ),
                chat
            )
        ).response();
    }

    /**
     * Handles the updates.
     *
     * @throws InterruptedException Thread interrupted
     * @throws IOException When getting updates went wrong
     */
    private void handleUpdates() throws InterruptedException, IOException {
        final BlockingQueue<JSONObject> updates = new LinkedBlockingQueue<>();
        this.updatingThread(updates);
        this.processUpdates(updates);
    }

    /**
     * Processing updates.
     *
     * @param updates The queue with updates
     * @throws InterruptedException When thread interrupted
     * @throws IOException When something went wrong
     */
    private void processUpdates(final BlockingQueue<JSONObject> updates)
        throws InterruptedException, IOException {
        while (true) {
            for (final Command command : this.commands) {
                command.act(new UpdDefault(updates.take()), this);
            }
        }
    }

    /**
     * Creates thread for handling updates.
     *
     * @param updates Queue with updates
     */
    private void updatingThread(final BlockingQueue<JSONObject> updates) {
        if (!this.thread.isAlive()) {
            this.thread = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        this.fillUpdates(updates);
                    }
                }
            );
            this.thread.start();
        }
    }

    /**
     * Check new updates.
     *
     * @param accum Queue with updates
     */
    private void fillUpdates(final BlockingQueue<JSONObject> accum) {
        final AtomicInteger offset = new AtomicInteger();
        this.putNewUpdates(accum, offset, this.updates(offset));
        try {
            Thread.sleep(1500L);
        } catch (final InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(ex);
        }
    }

    /**
     * Updates as json.
     *
     * @param offset The offset
     * @return The updates
     */
    private JSONArray updates(final AtomicInteger offset) {
        try {
            return new JSONObject(
                new TRqWithOffset(
                    new TRqGetUpdates(this.token),
                    offset.get()
                ).response().body()
            ).getJSONArray("result");
        } catch (final IOException ex) {
            throw new IllegalStateException("Error while getting updates", ex);
        }
    }

    /**
     * Iterating through updates with offset calculation.
     *
     * @param updates The updates queue
     * @param offset The offset for each update
     * @param server The data from server as JSON
     */
    private void putNewUpdates(
        final BlockingQueue<JSONObject> updates,
        final AtomicInteger offset,
        final Iterable<Object> server
    ) {
        for (final Object upd : server) {
            final int idx = new JSONObject(upd.toString()).getInt("update_id");
            if (this.processed.contains((long) idx)) {
                continue;
            }
            this.processed.add((long) idx);
            offset.set(idx + 1);
            try {
                updates.put(new JSONObject(upd.toString()));
            } catch (final InterruptedException ex) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("Interrupted while filling updates", ex);
            }
        }
    }
}
