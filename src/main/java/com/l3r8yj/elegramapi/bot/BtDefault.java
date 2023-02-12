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
package com.l3r8yj.elegramapi.bot;

import com.jcabi.http.response.JsonResponse;
import com.jcabi.log.Logger;
import com.l3r8yj.elegramapi.command.Command;
import com.l3r8yj.elegramapi.request.TRqGetUpdates;
import com.l3r8yj.elegramapi.request.TRqPost;
import com.l3r8yj.elegramapi.request.TRqSendMessage;
import com.l3r8yj.elegramapi.request.TRqWithChatId;
import com.l3r8yj.elegramapi.request.TRqWithOffset;
import com.l3r8yj.elegramapi.request.TRqWithText;
import com.l3r8yj.elegramapi.update.UpdDefault;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.cactoos.list.ListOf;
import org.json.JSONArray;
import org.json.JSONObject;

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
    private final ReentrantReadWriteLock rwlock;

    /**
     * Ctor.
     *
     * @param token The token
     * @param commands All custom commands
     */
    public BtDefault(final String token, final Command... commands) {
        this.token = token;
        this.commands = new ListOf<>(commands);
        this.processed = Collections.synchronizedSet(new HashSet<>(0));
        this.rwlock = new ReentrantReadWriteLock();
    }

    @Override
    public final void start() {
        try {
            this.handleUpdates();
        } catch (final InterruptedException | IOException ex) {
            this.logErrorWhile("handling updates", ex);
            throw new IllegalStateException(ex);
        }
    }

    @Override
    public final JsonResponse sendMessage(final long chat, final String text) {
        try {
            return new TRqPost(
                new TRqWithChatId(
                    new TRqWithText(
                        new TRqSendMessage(this.token),
                        text
                    ),
                    chat
                )
            ).response();
        } catch (final IOException ex) {
            this.logErrorWhile("sending message", ex);
            throw new IllegalStateException("Can't send a message to user!", ex);
        }
    }

    /**
     * Handles the updates.
     *
     * @throws InterruptedException Thread interrupted
     * @throws IOException When getting updates went wrong
     */
    private void handleUpdates() throws InterruptedException, IOException {
        final BlockingQueue<JSONObject> updates = new LinkedBlockingQueue<>();
        this.updatingThread(updates).start();
        this.processUpdates(updates);
    }

    /**
     * Processing updates.
     *
     * @param updates The queue with updates
     * @throws InterruptedException When thread interrupted
     */
    private void processUpdates(final BlockingQueue<JSONObject> updates)
        throws InterruptedException {
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
     * @return The thread
     */
    private Thread updatingThread(final BlockingQueue<JSONObject> updates) {
        return new Thread(
            () -> {
                while (!Thread.currentThread().isInterrupted()) {
                    this.rwlock.writeLock().lock();
                    try {
                        this.fillUpdates(updates);
                    } finally {
                        this.rwlock.writeLock().unlock();
                    }
                }
            }
        );
    }

    /**
     * Check new updates.
     *
     * @param accum Queue with updates
     */
    private void fillUpdates(final BlockingQueue<JSONObject> accum) {
        final AtomicInteger offset = new AtomicInteger();
        try {
            this.putNewUpdates(accum, offset, this.updates(offset));
        } catch (final IOException | InterruptedException ex) {
            this.logErrorWhile("filling updates:\n%s", ex);
            throw new IllegalStateException(ex);
        }
    }

    /**
     * Updates as json.
     *
     * @param offset The offset
     * @return The updates
     * @throws IOException When connection went wrong
     */
    private JSONArray updates(final AtomicInteger offset) throws IOException {
        return new JSONObject(
            new TRqWithOffset(
                new TRqGetUpdates(this.token),
                offset.get()
            ).response().body()
        ).getJSONArray("result");
    }

    /**
     * Iterating through updates with offset calculation.
     *
     * @param updates The updates queue
     * @param offset The offset for each update
     * @param server The data from server as JSON
     * @throws InterruptedException When interrupted
     */
    private void putNewUpdates(
        final BlockingQueue<JSONObject> updates,
        final AtomicInteger offset,
        final Iterable<Object> server
    ) throws InterruptedException {
        for (final Object upd : server) {
            final int idx = new JSONObject(upd.toString()).getInt("update_id");
            if (this.processed.contains((long) idx)) {
                continue;
            }
            this.processed.add((long) idx);
            offset.set(idx + 1);
            updates.put(new JSONObject(upd.toString()));
        }
    }

    /**
     * Just logging.
     *
     * @param method Method name
     * @param exc The exception
     */
    private void logErrorWhile(final String method, final Exception exc) {
        Logger.error(
            this,
            "An error occurred while %s:\n%s", method, exc.getMessage()
        );
    }
}
