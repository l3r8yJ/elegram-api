/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2022 Ivanchuck Ivan.
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
/*
 * @todo #zero test issue for zero puzzle.
 *  Make new issue via 0pdd.
 */
package com.l3r8yj.elegramapi;

import java.util.Arrays;
import java.util.List;

/**
 * The default implementation of {@link Bot}.
 *
 * @since 0.0.0
 */
public final class DefaultBot implements Bot {

    /**
     * All commands.
     */
    private final List<Command> commands;

    /**
     * The token.
     */
    private final String token;

    /**
     * Ctor.
     *
     * @param token The token
     * @param commands All custom commands
     */
    public DefaultBot(final String token, final Command... commands) {
        this.token = token;
        this.commands = Arrays.asList(commands);
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Operation not supported...");
    }

}
