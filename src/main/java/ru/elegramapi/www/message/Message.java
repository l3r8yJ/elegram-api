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

package ru.elegramapi.www.message;

import java.time.Instant;
import org.json.JSONObject;

/**
 * Just a message with all fields.
 *
 * @since 0.0.0
 */
public final class Message {

    /**
     * The json.
     */
    private final JSONObject json;

    /**
     * Ctor.
     *
     * @param json Data from server
     */
    public Message(final JSONObject json) {
        this.json = json;
    }

    /**
     * The message id.
     *
     * @return Just message id.
     */
    public Long messageId() {
        return this.json.getLong("message_id");
    }

    /**
     * The message id.
     *
     * @return Just message id.
     */
    public Long senderId() {
        return this.json.getJSONObject("from").getLong("id");
    }

    /**
     * The chat id.
     *
     * @return Just chat id.
     */
    public Long chatId() {
        return this.json.getJSONObject("chat").getLong("id");
    }

    /**
     * Get text of the message.
     *
     * @return Text of the message
     */
    public String text() {
        return this.json.getString("text");
    }

    /**
     * Get the date the message was sent in Unix time.
     *
     * @return Unix time
     */
    public Instant date() {
        return Instant.ofEpochSecond(this.json.getLong("date"));
    }
}
