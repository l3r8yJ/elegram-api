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

package com.l3r8yj.elegramapi.action;

import com.l3r8yj.elegramapi.Action;
import com.l3r8yj.elegramapi.Bot;

/**
 * Send text message.
 *
 * @since 0.0.0
 */
public final class ActSendTextMessage implements Action {

    /**
     * The sender.
     */
    private final Bot bot;

    /**
     * Text to send.
     */
    private final String text;

    /**
     * The id of consumer.
     */
    private final long chat;

    /**
     * Ctor.
     *
     * @param bot The sender
     * @param text Text to send
     * @param chat The id of consumer
     */
    public ActSendTextMessage(final Bot bot, final String text, final long chat) {
        this.bot = bot;
        this.text = text;
        this.chat = chat;
    }

    @Override
    public void act() {
        this.bot.sendMessage(this.chat, this.text);
    }
}
