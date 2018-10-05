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

import java.util.Set;

import com.github.danzx.forumapp.api.dao.CrudDao;
import com.github.danzx.forumapp.api.domain.BaseEntity;

import com.google.gson.JsonObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Defines the base behavior of {@link java.util.Map} based CRUD DAOs.
 * 
 * @author Daniel Pedraza-Arcega
 *
 * @param <E> the entity type.
 */
abstract class BaseCrudMapDao<E extends BaseEntity> extends BaseReadOnlyMapDao<E> implements CrudDao<E> {

    private static final Logger LOG = LoggerFactory.getLogger(BaseCrudMapDao.class);

    /** {@inheritDoc} */
    protected BaseCrudMapDao(JsonObject json, Class<E> clazz) {
        super(json, clazz);
    }

    /** {@inheritDoc} */
    protected BaseCrudMapDao(Class<E> clazz) {
        super(clazz);
    }

    /** {@inheritDoc} */
    @Override
    public void save(E entity) {
        LOG.debug("void save({})", entity);
        entity.setId(genereteId());
        db.put(entity.getId(), entity);
    }

    /** {@inheritDoc} */
    @Override
    public void update(E entity) {
        LOG.debug("void update({})", entity);
        db.put(entity.getId(), entity);
    }

    /** {@inheritDoc} */
    @Override
    public void delete(int id) {
        LOG.debug("void delete({})", id);
        db.remove(id);
    }

    /** @return a genereted id. */
    protected int genereteId() {
        int nextId = db.size()+1;
        Set<Integer> ids = db.keySet();
        while (ids.contains(nextId)) nextId++;
        return nextId;
    }
}
