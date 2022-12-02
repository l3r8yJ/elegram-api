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
 * @todo #Tests/RqFileTelegram write tests for this class.
 */
package com.l3r8yj.elegramapi;

import com.jcabi.http.Response;
import com.jcabi.http.request.JdkRequest;
import java.io.IOException;
import org.cactoos.text.Concatenated;

/**
 * Request to file api.
 *
 * @since 0.0.0
 */
public class RqFileTelegram implements RqTelegram {

    /**
     * The default address.
     */
    private static final String ADDRESS = "https://api.telegram.org/file/bot";

    /**
     * The address.
     */
    private final String addr;

    /**
     * The token.
     */
    private final String token;

    /**
     * Ctor.
     *
     * @param token The token
     */
    public RqFileTelegram(final String token) {
        this(RqFileTelegram.ADDRESS, token);
    }

    /**
     * Ctor.
     *
     * @param address The address of api
     * @param token The token
     */
    public RqFileTelegram(final String address, final String token) {
        this.token = token;
        this.addr = address;
    }

    @Override
    public final String plainText() {
        return new Concatenated(
            RqFileTelegram.ADDRESS,
            "/",
            this.token
        ).toString();
    }

    @Override
    public final Response response() throws IOException {
        return new JdkRequest(this.plainText()).fetch();
    }
}
