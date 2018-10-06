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
package com.github.danzx.forumapp.api.rest.model;

import com.github.danzx.forumapp.api.util.BitFlag;

/**
 * Expand bif flag.
 * values:
 * <ul>
 *     <li>{@code "user" -> 0b0100}</li>
 *     <li>{@code "post" -> 0b1000}</li>
 *     <li>{@code "user"|"post" -> 0b1100}</li>
 *     <li>{@code others -> 0b0000}</li>
 * </ul>
 *
 * @author Daniel Pedraza-Arcega
 */
public class Expand implements BitFlag {

    private static final String USER_STR = "user";
    private static final String POST_STR = "post";

    public static final int EXPAND_NOTHING = 0b0000;
    public static final int EXPAND_USER = 0b0100;
    public static final int EXPAND_POST = 0b1000;
    public static final int EXPAND_ALL = EXPAND_USER|EXPAND_POST;

    private final int value;

    public Expand(String value) {
        switch (value) {
            case USER_STR:
                this.value = EXPAND_USER;
                break;
            case POST_STR:
                this.value = EXPAND_POST;
                break;
            default:
                this.value = EXPAND_NOTHING;
                break;
        }
    }

    @Override
    public int value() {
        return value;
    }
}
