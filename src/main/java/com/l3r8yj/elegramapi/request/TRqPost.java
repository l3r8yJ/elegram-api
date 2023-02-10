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
import java.io.IOException;

/**
 * Telegram request with POST.
 * This is a decorator for {@link TelegramRequest}
 *
 * @since 0.0.0
 */
public final class TRqPost implements TelegramRequest {

    /**
     * The origin request.
     */
    private final TelegramRequest origin;

    /**
     * Ctor.
     *
     * @param origin The origin
     */
    public TRqPost(final TelegramRequest origin) {
        this.origin = origin;
    }

    @Override
    public String plainText() {
        return this.origin.plainText();
    }

    @Override
    public Response response() throws IOException {
        return this.origin.response()
            .back()
            .method("POST")
            .fetch();
    }
}
