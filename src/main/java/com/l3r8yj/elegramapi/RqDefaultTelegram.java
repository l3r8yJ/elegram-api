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
 * @todo #Refactor/ refactor design of this class, for now it's a bit wrong.
 */
package com.l3r8yj.elegramapi;

import com.jcabi.http.Response;
import com.jcabi.http.request.JdkRequest;
import java.io.IOException;
import org.cactoos.text.FormattedText;

/**
 * Default request to telegram api without a method.
 *
 * @since 0.0.0
 */
public class RqDefaultTelegram implements RqTelegram {

    /**
     * Default url.
     */
    private static final String ADDR = "https://api.telegram.org/bot";

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
        this(token, RqDefaultTelegram.ADDR, method);
    }

    /**
     * Ctor.
     *
     * @param token The token
     * @param address The address
     * @param method The http method
     */
    public RqDefaultTelegram(
        final String token,
        final String address,
        final String method
    ) {
        this.token = token;
        this.address = address;
        this.method = method;
    }

    @Override
    public final String plainText() {
        return new FormattedText(
            "%s%s/",
            this.address,
            this.token
        ).toString();
    }

    @Override
    public final Response response() throws IOException {
        return new JdkRequest(
            this.plainText()
        ).method(this.method).fetch();
    }
}
