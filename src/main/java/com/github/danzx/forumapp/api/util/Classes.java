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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classes utility class.
 * 
 * @author Daniel Pedraza-Arcega
 */
public class Classes {

    private static final Logger LOG = LoggerFactory.getLogger(Classes.class);
    private static final String ARRAY_CLASS_NAME_FORMAT = "[L%s;";

    private Classes() {
        throw new AssertionError();
    }

    /**
     * @param <T> the class type.
     * @param clazz a non primitive class.
     * @return the array class of the given class.
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T[]> arrayClassOf(Class<T> clazz) {
        try {
            return (Class<T[]>) Class.forName(String.format(ARRAY_CLASS_NAME_FORMAT, clazz.getName()));
        } catch (ClassNotFoundException ex) {
            LOG.error("{} array class can't be obtained", clazz, ex);
            throw new RuntimeException(ex);
        }
    }
}
