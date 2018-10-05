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
package com.github.danzx.forumapp.api.dao.impl.map;

import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonDataProvider {

    private static final Logger LOG = LoggerFactory.getLogger(JsonDataProvider.class);
    private static final String JSON_FILE = "data.json";

    @Bean
    public JsonObject jsonData() {
        return loadJsonFromClasspathFile(JSON_FILE);
    }

    /**
     * Creates a JSON object from the given classpath file path.
     *
     * @param file the JSON classpath file path.
     * @return a new JSON object.
     */
    private JsonObject loadJsonFromClasspathFile(String file) {
        try (InputStreamReader reader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream(file))) {
            return new Gson().fromJson(reader, JsonObject.class);
        } catch (Exception ex) {
            LOG.error("Could not parse json file {}", file, ex);
            throw new RuntimeException(ex);
        }
    }
}
