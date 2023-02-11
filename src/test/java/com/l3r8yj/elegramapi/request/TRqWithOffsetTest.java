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

import java.io.IOException;
import javax.ws.rs.core.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link TRqWithOffset}.
 *
 * @since 0.0.0
 */
final class TRqWithOffsetTest {

    /**
     * Under test request.
     */
    private TelegramRequest request;

    @BeforeEach
    void setUp() {
        this.request = new TRqWithOffset(
            new TRqSendMessage("tkn"),
            32
        );
    }

    @Test
    void responses() throws IOException {
        MatcherAssert.assertThat(
            this.request.response().status(),
            Matchers.equalTo(Response.Status.NOT_FOUND.getStatusCode())
        );
    }

    @Test
    void createsUriWithRightParam() {
        MatcherAssert.assertThat(
            this.request.uri().toString().contains("offset=32"),
            Matchers.equalTo(true)
        );
    }
}
