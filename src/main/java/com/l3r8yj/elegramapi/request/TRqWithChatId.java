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

package com.l3r8yj.elegramapi.request;

import com.jcabi.http.Response;
import com.jcabi.http.request.JdkRequest;
import java.io.IOException;
import org.cactoos.text.Concatenated;
import org.cactoos.text.FormattedText;

/**
 * Telegram request with chat id as param.
 * This is a decorator for {@link TelegramRequest} inheritors.
 *
 * @since 0.0.0
 */
public final class TRqWithChatId implements TelegramRequest {

    /**
     * The origin.
     */
    private final TelegramRequest origin;

    /**
     * The chat.
     */
    private final long chat;

    /**
     * Ctor.
     *
     * @param origin Origin request
     * @param chat Chat id to add
     */
    public TRqWithChatId(final TelegramRequest origin, final long chat) {
        this.origin = origin;
        this.chat = chat;
    }

    @Override
    public String plainText() {
        return new Concatenated(
            this.origin.plainText(),
            new FormattedText("?chat_id=%d", this.chat).toString()
        ).toString();
    }

    @Override
    public Response response() throws IOException {
        return new JdkRequest(this.plainText()).fetch();
    }
}
