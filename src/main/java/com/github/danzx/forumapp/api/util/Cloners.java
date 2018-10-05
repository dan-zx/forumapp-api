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

import com.rits.cloning.Cloner;

/**
 * Clone utility class.
 * 
 * @author Daniel Pedraza-Arcega
 */
public class Cloners {

    private static final Cloner CLONER = new Cloner();

    static {
        CLONER.setDumpClonedClasses(true);
    }

    private Cloners() {
        throw new AssertionError();
    }

    /**
     * Deep clones "o".
     *
     * @param <T> the class type.
     * @param o the object to be deep-cloned.
     * @return a deep-clone of "o".
     */
    public static <T> T deepClone(T o) {
        return CLONER.deepClone(o);
    }
}
