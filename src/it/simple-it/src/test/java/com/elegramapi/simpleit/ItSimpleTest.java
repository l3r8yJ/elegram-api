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
import com.l3r8yj.elegramapi.Bot;
import com.l3r8yj.elegramapi.Command;
import com.l3r8yj.elegramapi.Update;
import com.l3r8yj.elegramapi.action.ActSendTextMessage;
import com.l3r8yj.elegramapi.bot.BtDefault;
import javax.ws.rs.core.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

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
        this.bot = new BtDefault(
            "5735860614:AAHsneN3fWj76dfXejtdSmNGLf4kq-bUGgg",
            new ItSimpleTest.CmdStart(),
            new ItSimpleTest.CmdEcho()
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
    void sendsTheMessage() {
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

    /**
     * The test command.
     *
     * @since 0.0.0
     */
    private static class CmdStart implements Command {

        CmdStart() {
        }

        @Override
        public final void react(final Update update, final Bot bot) {
            if ("/start".equals(update.message().text())) {
                new ActSendTextMessage(
                    bot,
                    "Hi",
                    update.message().chatId()
                ).act();
            }
        }
    }

    /**
     * The echo command.
     *
     * @since 0.0.0
     */
    private static class CmdEcho implements Command {

        CmdEcho() {
        }

        @Override
        public final void react(final Update update, final Bot bot) {
            if (!update.message().text().isEmpty()) {
                new ActSendTextMessage(
                    bot,
                    update.message().text(),
                    update.message().chatId()
                ).act();
            }
        }
    }
}
