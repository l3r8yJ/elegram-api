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

import com.l3r8yj.elegramapi.bot.Bot;
import com.l3r8yj.elegramapi.bot.BtDefault;
import com.l3r8yj.elegramapi.command.Command;
import com.l3r8yj.elegramapi.update.Update;
import javax.ws.rs.core.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Just simple integration test.
 *
 * @since 0.0.0
 */
final class ItSimpleTest {

    @Test
    void sendsTheMessage() {
        MatcherAssert.assertThat(
            new BtDefault(
                "5735860614:AAHsneN3fWj76dfXejtdSmNGLf4kq-bUGgg",
                new TestCommand()
            )
                .sendMessage(389_133_054, "Hi, i'm Ruby!")
                .status(),
            Matchers.equalTo(Response.Status.OK.getStatusCode())
        );
    }

    /**
     * The test command.
     *
     * @since 0.0.0
     */
    static class TestCommand implements Command {

        @Override
        public final void act(final Update update, final Bot bot) {
            if (update.message().text().equals("/start")) {
                bot.sendMessage(update.message().chatId(), "Hi!");
            }
        }
    }
}
