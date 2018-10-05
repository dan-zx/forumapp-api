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
package com.github.danzx.forumapp.api.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Bit flag enum type.
 *
 * @author Daniel Pedraza-Arcega
 */
public interface BitFlag {

    int value();

    static int getFlags(List<? extends BitFlag> bitFlags) {
        return getFlags(bitFlags.stream());
    }

    static int getFlags(BitFlag... bitFlags) {
        return getFlags(Arrays.stream(bitFlags));
    }

    static int getFlags(Stream<? extends BitFlag> stream) {
        return stream.mapToInt(BitFlag::value).reduce(0, (a, b) -> (a|b));
    }
}
