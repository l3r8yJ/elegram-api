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

package ru.elegramapi.www.request;

import com.jcabi.http.RequestURI;
import com.jcabi.http.request.JdkRequest;
import com.jcabi.http.response.JsonResponse;
import java.io.IOException;
import ru.elegramapi.www.TelegramRequest;

/**
 * Decorator for {@link TelegramRequest}.
 * Adds offset to request.
 *
 * @since 0.0.0
 */
public final class TRqWithOffset implements TelegramRequest {

    /**
     * The origin.
     */
    private final TelegramRequest origin;

    /**
     * The offset.
     */
    private final int offset;

    /**
     * Ctor.
     *
     * @param origin The origin.
     * @param offset The offset.
     */
    public TRqWithOffset(final TelegramRequest origin, final int offset) {
        this.origin = origin;
        this.offset = offset;
    }

    @Override
    public String plainText() {
        return this.uri().toString();
    }

    @Override
    public JsonResponse response() throws IOException {
        return new JdkRequest(this.uri().toString())
            .fetch()
            .as(JsonResponse.class);
    }

    @Override
    public RequestURI uri() {
        return this.origin.uri().queryParam("offset", this.offset);
    }
}
