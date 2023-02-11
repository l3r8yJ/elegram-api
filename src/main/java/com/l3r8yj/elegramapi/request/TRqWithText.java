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

import com.jcabi.http.response.JsonResponse;
import java.io.IOException;

/**
 * Telegram request with text.
 * This is a decorator for {@link TelegramRequest} inheritors.
 *
 * @since 0.0.0
 */
public final class TRqWithText implements TelegramRequest {

    /**
     * The origin.
     */
    private final TelegramRequest origin;

    /**
     * The text.
     */
    private final String text;

    /**
     * Ctor.
     *
     * @param origin The origin request
     * @param text The text to add
     */
    public TRqWithText(final TelegramRequest origin, final String text) {
        this.origin = origin;
        this.text = text;
    }

    @Override
    public String plainText() {
        return this.origin.plainText();
    }

    @Override
    public JsonResponse response() throws IOException {
        return this.origin.response()
            .back()
            .uri()
            .queryParam("text", this.text)
            .back()
            .fetch()
            .as(JsonResponse.class);
    }
}