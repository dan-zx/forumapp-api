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

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import static com.github.danzx.forumapp.api.util.Classes.arrayClassOf;
import static com.github.danzx.forumapp.api.util.Cloners.deepClone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.github.danzx.forumapp.api.dao.ReadOnlyDao;
import com.github.danzx.forumapp.api.domain.BaseEntity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Defines the base behavior of {@link Map} based read-only DAOs.
 * 
 * @author Daniel Pedraza-Arcega
 *
 * @param <E> the entity type.
 */
abstract class BaseReadOnlyMapDao<E extends BaseEntity> implements ReadOnlyDao<E> {

    private static final Logger LOG = LoggerFactory.getLogger(BaseReadOnlyMapDao.class);
    
    protected final Map<Integer, E> db;
    protected final Class<E> clazz;

    /**
     * Creates a new Map based DAO.
     * 
     * @param json the data from which the map is going to be created.
     * @param clazz the entity class.
     */
    protected BaseReadOnlyMapDao(JsonObject json, Class<E> clazz) {
        if (json == null) db = new HashMap<>();
        else db = createEntityMap(json, clazz);
        this.clazz = clazz;
    }

    /**
     * Creates a new Map based DAO with an empty map.
     * 
     * @param clazz the entity class.
     */
    protected BaseReadOnlyMapDao(Class<E> clazz) {
        this(null, clazz);
    }

    /** {@inheritDoc} */
    @Override
    public E findById(int id) {
        LOG.debug("{} findById({})", clazz.getSimpleName(), id);
        return deepClone(db.get(id));
    }

    /** {@inheritDoc} */
    @Override
    public Collection<E> findAll() {
        LOG.debug("Collection<{}> findByAll()", clazz.getSimpleName());
        return deepClone(new ArrayList<>(db.values()));
    }

    /**
     * Creates a new map using the given JSON. The JSON object is parsed to create objects of the
     * given class and then mapped to map using the objects id as their key.
     * 
     * @param json a JSON object.
     * @param clazz the entity class.
     * @return a new map.
     */
    private Map<Integer, E> createEntityMap(JsonObject json, Class<E> clazz) {
        String property = clazz.getSimpleName().toLowerCase() + "s";
        return Arrays.stream(new Gson().fromJson(json.get(property), arrayClassOf(clazz))).collect(toMap(E::getId, identity()));
    }
}
