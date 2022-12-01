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

package com.l3r8yj.elegramapi;

import com.jcabi.http.request.JdkRequest;
import java.io.IOException;
import java.util.Locale;

/**
 * Default request to telegram api without a method.
 *
 * @since 0.0.0
 */
public class RqDefaultTelegram implements RqTelegram {

    /**
     * Default telegram address.
     */
    private final String address;

    /**
     * The uri to make request.
     */
    private final String token;

    /**
     * Http method.
     */
    private final String method;

    /**
     * Ctor.
     *
     * @param token The uri
     * @param method The http method
     */
    public RqDefaultTelegram(final String token, final String method) {
        this.token = token;
        this.method = method.toUpperCase(Locale.ROOT);
        this.address = "https://api.telegram.org/bot";
    }

    @Override
    public final String plainText() {
        return String.format(
            "%s%s/",
            this.address,
            this.token
        );
    }

    @Override
    public final String body() throws IOException {
        return new JdkRequest(
            this.plainText()
        ).method(this.method).fetch().body();
    }
}
