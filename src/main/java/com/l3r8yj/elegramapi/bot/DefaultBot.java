/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2022 Ivanchuck Ivan.
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
 * @todo #70 Design/ Implement run().
 * Need to design proper implementation for DefaultBot.
 * */
/*
 * @todo #60 Design/ Handling.
 * Base handling written we have to write the handling from messages after 91 line.
 * Add handling for each type of response.
 * Warning implementation of handleUpdates is experimental!
 * */
package com.l3r8yj.elegramapi.bot;

import com.l3r8yj.elegramapi.command.Command;
import com.l3r8yj.elegramapi.request.RqGetUpdatesTelegram;
import com.l3r8yj.elegramapi.request.RqWithOffsetTelegram;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import org.cactoos.list.ListOf;
import org.json.JSONObject;

/**
 * The default implementation of {@link Bot}.
 *
 * @since 0.0.0
 */
public final class DefaultBot implements Bot {

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
    public DefaultBot(final String token, final Command... commands) {
        this.token = token;
        this.commands = new ListOf<>(commands);
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Operation not supported...");
    }

    /**
     * Handles the updates.
     *
     * @throws InterruptedException When something went wrong
     */
    private void handleUpdates() throws InterruptedException {
        final BlockingQueue<JSONObject> updates = new LinkedBlockingQueue<>(0);
        this.getUpdateThread(updates).start();
        while (true) {
            final JSONObject update = updates.take();
            if (update.has("message")) {
                final JSONObject msg = update.getJSONObject("message");
                final String txt = msg.getString("text");
                final String sender =
                    msg.getJSONObject("from").getString("username");
            } else if (update.has("callback_query")) {
                new JSONObject(update.get("callback_query"));
            }
        }
    }

    /**
     * Creates thread for handling updates.
     *
     * @param updates Queue with updates
     * @return The thread
     */
    private Thread getUpdateThread(final BlockingQueue<JSONObject> updates) {
        return new Thread(
            () -> {
                while (!Thread.currentThread().isInterrupted()) {
                    this.parseResponse(updates);
                }
            }
        );
    }

    /**
     * Parsing response for updates.
     *
     * @param updates Queue with updates
     */
    private void parseResponse(final BlockingQueue<JSONObject> updates) {
        final AtomicInteger offset = new AtomicInteger();
        try {
            new JSONObject(this.getUpdatesBody(offset))
                .getJSONArray("result")
                .forEach(upd -> putUpdateInQueue(updates, offset, upd));
        } catch (final IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    /**
     * Puts update into a queue.
     *
     * @param updates Queue with updates
     * @param offset The offset
     * @param upd The single update
     */
    private static void putUpdateInQueue(
        final BlockingQueue<JSONObject> updates,
        final AtomicInteger offset,
        final Object upd
    ) {
        final int id = new JSONObject(upd).getInt("update_id");
        offset.set(id + 1);
        try {
            updates.put(new JSONObject(upd));
        } catch (final InterruptedException ex) {
            throw new IllegalStateException(ex);
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
        return new RqWithOffsetTelegram(
            new RqGetUpdatesTelegram(this.token),
            offset.get()
        )
            .response()
            .body();
    }
}
