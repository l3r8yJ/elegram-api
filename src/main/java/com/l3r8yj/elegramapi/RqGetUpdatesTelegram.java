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
 * @todo #Redesign/RqGetUpdatesTelegram.
 * Change the design of this class and make it like RqFileTelegram.
 */
package com.l3r8yj.elegramapi;

import com.jcabi.http.Response;
import com.jcabi.http.request.JdkRequest;
import java.io.IOException;
import org.cactoos.text.Concatenated;

/**
 * GetUpdates request to telegram api.
 *
 * @since 0.0.0
 */
public final class RqGetUpdatesTelegram implements RqTelegram {

    /**
     * The origin request.
     */
    private final RqTelegram origin;

    /**
     * Ctor.
     *
     * @param origin The origin request
     */
    public RqGetUpdatesTelegram(final RqTelegram origin) {
        this.origin = origin;
    }

    @Override
    public String plainText() {
        return new Concatenated(
            this.origin.plainText(),
            "/getUpdates"
        ).toString();
    }

    @Override
    public Response response() throws IOException {
        return new JdkRequest(this.plainText()).fetch();
    }
}