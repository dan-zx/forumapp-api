/*
 * Copyright 2018 Daniel Pedraza-Arcega
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.danzx.forumapp.api.rest;

import com.github.danzx.forumapp.api.util.BitFlag;

public class Embed implements BitFlag {

    private static final String POSTS_STR = "posts";
    private static final String COMMENTS_STR = "comments";

    static final int EMBED_NOTHING = 0b0000;
    static final int EMBED_POSTS = 0b0001;
    static final int EMBED_COMMENTS = 0b0010;
    static final int EMBED_ALL = EMBED_POSTS|EMBED_COMMENTS;

    private final int value;

    public Embed(String value) {
        switch (value) {
            case POSTS_STR:
                this.value = EMBED_POSTS;
                break;
            case COMMENTS_STR:
                this.value = EMBED_COMMENTS;
                break;
            default:
                this.value = EMBED_NOTHING;
                break;
        }
    }

    @Override
    public int value() {
        return value;
    }
}
