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

package ru.elegramapi.www.update;

import java.util.Objects;
import org.json.JSONObject;
import ru.elegramapi.www.Update;
import ru.elegramapi.www.message.Message;

/**
 * The default update.
 *
 * @since 0.0.0
 */
public final class UpdDefault implements Update {

    /**
     * Update as json.
     */
    private final JSONObject json;

    /**
     * Handling for incoming update.
     *
     * @param update The update
     */
    public UpdDefault(final JSONObject update) {
        this.json = update;
    }

    @Override
    public long updateId() {
        return this.json.getLong("update_id");
    }

    @Override
    public Message message() {
        return new Message(this.json.getJSONObject("message"));
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.updateId());
    }

    @Override
    @SuppressWarnings("PMD.OnlyOneReturn")
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || !this.getClass().equals(obj.getClass())) {
            return false;
        }
        final Update update = (Update) obj;
        return update.updateId() == this.updateId();
    }
}
