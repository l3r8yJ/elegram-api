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

package ru.elegramapi.www.bot;

import com.jcabi.http.response.JsonResponse;
import com.jcabi.log.Logger;
import java.io.IOException;
import org.cactoos.text.FormattedText;
import ru.elegramapi.www.Bot;

/**
 * Decorator for {@link Bot}.
 * Logging for decorated bot.
 *
 * @since 0.0.0
 */
public final class BtLogged implements Bot {

    /**
     * The origin bot.
     */
    private final Bot origin;

    /**
     * Ctor.
     *
     * @param origin The bot
     */
    public BtLogged(final Bot origin) {
        this.origin = origin;
    }

    @Override
    public void start() {
        Logger.info(
            this,
            new FormattedText(
                "%s is started",
                this.origin.getClass().toString()
            ).toString()
        );
        try {
            this.origin.start();
        } catch (final IOException | InterruptedException ex) {
            throw new IllegalStateException(
                this.logErrorMessage("start", ex),
                ex
            );
        }
    }

    @Override
    public JsonResponse sendMessage(final long chat, final String text) {
        Logger.info(
            this,
            new FormattedText(
                "#sendMessage: sending message to %d",
                chat
            ).toString()
        );
        try {
            return this.origin.sendMessage(chat, text);
        } catch (final IOException ex) {
            throw new IllegalStateException(
                this.logErrorMessage("sendMessage", ex),
                ex
            );
        }
    }

    /**
     * Log error and throw exception.
     *
     * @param method The method that called
     * @param exc The exception
     * @return Error as {@link String} and make log
     */
    private String logErrorMessage(final String method, final Exception exc) {
        final String error = new FormattedText(
            "#%s: An error occurred. Cause: %s",
            method,
            exc.getMessage()
        ).toString();
        Logger.error(this, error);
        return error;
    }
}
