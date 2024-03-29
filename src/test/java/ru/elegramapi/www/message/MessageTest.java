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

package ru.elegramapi.www.message;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.elegramapi.www.fake.FkJMessage;

/**
 * Test case for {@link Message}.
 *
 * @since 0.0.0
 */
class MessageTest {

    /**
     * Under test message.
     */
    private Message msg;

    @BeforeEach
    void setUp() {
        this.msg = new Message(new FkJMessage().asJson());
    }

    @Test
    void containsMessageId() {
        MatcherAssert.assertThat(
            this.msg.messageId(),
            Matchers.equalTo(
                new FkJMessage().asJson().getLong("message_id")
            )
        );
    }

    @Test
    void containsSenderId() {
        MatcherAssert.assertThat(
            this.msg.senderId(),
            Matchers.equalTo(
                new FkJMessage()
                    .asJson()
                    .getJSONObject("from")
                    .getLong("id")
            )
        );
    }

    @Test
    void containsChatId() {
        MatcherAssert.assertThat(
            this.msg.chatId(),
            Matchers.equalTo(
                new FkJMessage()
                    .asJson()
                    .getJSONObject("chat")
                    .getLong("id")
            )
        );
    }

    @Test
    void containsText() {
        MatcherAssert.assertThat(
            this.msg.text(),
            Matchers.equalTo(
                new FkJMessage()
                    .asJson()
                    .getString("text")
            )
        );
    }

    @Test
    void containsDate() {
        MatcherAssert.assertThat(
            this.msg.date(),
            Matchers.notNullValue()
        );
    }
}
