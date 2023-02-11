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
/**
 * @todo #94 Change request to send message.
 * Need to change this {@link com.l3r8yj.elegramapi.request.TRqWithChatId},
 * {@link com.l3r8yj.elegramapi.request.TRqWithOffset},
 * {@link com.l3r8yj.elegramapi.request.TRqSendMessage}
 * to fix multiple sending requests.
 */
package com.l3r8yj.elegramapi.request;

import com.jcabi.http.response.JsonResponse;
import java.io.IOException;

/**
 * Request to telegram-api.
 *
 * @since 0.0.0
 */
public interface TelegramRequest {

    /**
    * Represents RqTelegram in plain String.
    *
    * @return String value of RqTelegram
    */
    String plainText();

    /**
     * The response from request.
     *
     * @return The response
     * @throws IOException When something went wrong
     */
    JsonResponse response() throws IOException;
}
