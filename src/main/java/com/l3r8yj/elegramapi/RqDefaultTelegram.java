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

import com.jcabi.http.Request;
import com.jcabi.http.Response;
import com.jcabi.http.request.JdkRequest;
import java.io.IOException;
import org.cactoos.text.Concatenated;

/**
 * Default request to telegram api without a method.
 *
 * @since 0.0.0
 */
public abstract class RqDefaultTelegram implements RqTelegram {

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
     * The http request.
     */
    private final Request request;

    /**
     * Ctor.
     *
     * @param token The token
     */
    protected RqDefaultTelegram(final String token) {
        this(
            token,
            RqDefaultTelegram.ADDR,
            new JdkRequest(
                new Concatenated(
                    RqDefaultTelegram.ADDR,
                    token
                ).toString()
            )
        );
    }

    /**
     * Ctor.
     *
     * @param token The token
     * @param address The address
     */
    protected RqDefaultTelegram(final String token, final String address) {
        this(
            token,
            address,
            new JdkRequest(
                new Concatenated(
                    address,
                    token
                ).toString()
            )
        );
    }

    /**
     * Ctor.
     *
     * @param token The token
     * @param address The address
     * @param request The http request
     */
    protected RqDefaultTelegram(
        final String token,
        final String address,
        final Request request
    ) {
        this.token = token;
        this.address = address;
        this.request = request;
    }

    @Override
    public final String plainText() {
        return new Concatenated(
            this.address,
            this.token
        ).toString();
    }

    @Override
    public final Response response() throws IOException {
        return this.request.fetch();
    }
}
