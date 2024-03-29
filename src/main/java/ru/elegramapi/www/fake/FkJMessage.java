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

package ru.elegramapi.www.fake;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/**
 * The fake Message JSONObject with data.
 *
 * @since 0.0.0
 */
public final class FkJMessage {

    /**
     * Just test data.
     */
    private final JSONObject wrapped;

    /**
     * Ctor.
     */
    public FkJMessage() {
        this.wrapped = new JSONObject();
    }

    /**
     * Represents data as json.
     *
     *  @return The data
     */
    public JSONObject asJson() {
        final Map<String, Long> from = new HashMap<>(0);
        from.put("id", 213L);
        final Map<String, Long> chat = new HashMap<>(0);
        chat.put("id", 132L);
        this.wrapped.put("message_id", 21L);
        this.wrapped.put("from", from);
        this.wrapped.put("chat", new JSONObject(chat));
        this.wrapped.put("text", "Ruby, bark!");
        this.wrapped.put("date", System.currentTimeMillis());
        return this.wrapped;
    }
}
