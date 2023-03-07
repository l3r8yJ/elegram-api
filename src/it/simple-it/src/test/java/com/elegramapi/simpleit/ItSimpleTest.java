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

package com.elegramapi.simpleit;

import com.jcabi.http.response.JsonResponse;
import java.io.IOException;
import javax.ws.rs.core.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.elegramapi.www.Bot;
import ru.elegramapi.www.Command;
import ru.elegramapi.www.Update;
import ru.elegramapi.www.bot.BtDefault;
import ru.elegramapi.www.bot.BtLogged;
import ru.elegramapi.www.command.CmdStart;

/**
 * Just simple integration test.
 *
 * @since 0.0.0
 */
final class ItSimpleTest {

    /**
     * Under test bot.
     */
    private Bot bot;

    @BeforeEach
    void setUp() {
        this.bot = new BtLogged(
            new BtDefault(
                "5735860614:AAHsneN3fWj76dfXejtdSmNGLf4kq-bUGgg",
                new CmdStart("This is a message that responses to /start.")
            )
        );
    }

    @Test
    @Disabled
    void runsTheBot() {
        Assertions.assertDoesNotThrow(
            () -> this.bot.start()
        );
    }

    @Test
    void sendsTheMessage() throws IOException {
        final JsonResponse response = this.bot.sendMessage(
            389_133_054L,
            "Hi, i'm Ruby!"
        );
        MatcherAssert.assertThat(
            response.status(),
            Matchers.equalTo(Response.Status.OK.getStatusCode())
        );
        MatcherAssert.assertThat(
            response.back().toString().contains("POST"),
            Matchers.equalTo(true)
        );
    }
}
