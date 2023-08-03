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

package ru.elegramapi.www.command;

import java.io.IOException;
import ru.elegramapi.www.Bot;
import ru.elegramapi.www.Command;
import ru.elegramapi.www.Update;

/**
 * The basic handling of '/start' command.
 *
 * @since 0.0.0
 */
public final class CmdStart implements Command {

    /**
     * The text of response.
     */
    private final String text;

    /**
     * Ctor.
     *
     * @param text The text of response
     */
    public CmdStart(final String text) {
        this.text = text;
    }

    @Override
    public void act(final Update update, final Bot bot) throws IOException {
        if (!update.message().text().contains("/start")) {
            return;
        }
        bot.sendMessage(update.message().chatId(), this.text);
    }
}
