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
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import org.cactoos.list.ListOf;
import org.cactoos.text.Concatenated;
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
     * Ctor.
     *
     * @param token The token
     * @param commands All custom commands
     */
    public BtDefault(final String token, final Command... commands) {
        this.token = token;
        this.commands = new ListOf<>(commands);
    }

    @Override
    public final void run() {
        try {
            this.handleUpdates();
        } catch (final InterruptedException | IOException ex) {
            Logger.error(
                this,
                new Concatenated(
                    "An error occurred while handling updates:\n",
                    ex.getMessage()
                ).toString()
            );
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
            Logger.error(
                this,
                new Concatenated(
                    "An error occurred while sending message:\n",
                    ex.getMessage()
                ).toString()
            );
        }
        throw new IllegalStateException("Can't send a message to user!");
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
        this.actWithNewUpdates(updates);
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
                    this.fillUpdates(updates);
                }
            }
        );
    }

    /**
     * Updates count check.
     *
     * @param updates The queue
     * @throws InterruptedException When thread was interrupted
     */
    private void actWithNewUpdates(final BlockingQueue<JSONObject> updates)
        throws InterruptedException {
        while (true) {
            for (final Command command : this.commands) {
                command.act(new UpdDefault(updates.take()), this);
            }
            Thread.sleep(500L);
        }
    }

    /**
     * Check new updates.
     *
     * @param accum Queue with updates
     */
    private void fillUpdates(final BlockingQueue<JSONObject> accum) {
        final AtomicInteger offset = new AtomicInteger();
        try {
            final JSONArray updates = new JSONObject(this.getUpdatesBody(offset))
                .getJSONArray("result");
            BtDefault.putUpdatesWithOffset(accum, offset, updates);
        } catch (final IOException | InterruptedException ex) {
            Logger.error(
                this,
                new Concatenated(
                    "Error occurred while filling updates:\n",
                    ex.getMessage()
                ).toString()
            );
            throw new IllegalStateException(ex);
        }
    }

    /**
     * Iterating through updates with offset calculation.
     *
     * @param updates The updates queue
     * @param offset The offset for each update
     * @param server The data from server as JSON
     * @throws InterruptedException When interrupted
     */
    private static void putUpdatesWithOffset(
        final BlockingQueue<JSONObject> updates,
        final AtomicInteger offset,
        final JSONArray server
    ) throws InterruptedException {
        for (final Object upd : server) {
            final int id = new JSONObject(upd.toString()).getInt("update_id");
            offset.set(id + 1);
            updates.put(new JSONObject(upd.toString()));
        }
    }

    /**
     * Just update body.
     *
     * @param offset The offset
     * @return The body as string
     * @throws IOException When something went wrong
     */
    private String getUpdatesBody(final AtomicInteger offset) throws IOException {
        return new TRqWithOffset(
            new TRqGetUpdates(this.token),
            offset.get()
        )
            .response()
            .body();
    }
}
