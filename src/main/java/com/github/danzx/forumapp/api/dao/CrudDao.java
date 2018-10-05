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
package com.github.danzx.forumapp.api.dao;

import com.github.danzx.forumapp.api.domain.BaseEntity;

/**
 * Defines the operations of CRUD data access objects.
 * 
 * @author Daniel Pedraza-Arcega
 *
 * @param <E> the entity type.
 */
public interface CrudDao<E extends BaseEntity> extends ReadOnlyDao<E> {

    /**
     * Saves a given entity.
     * 
     * @param entity must not be null.
     */
    void save(E entity);

    /**
     * Updates a given entity.
     * 
     * @param entity must not be null.
     */
    void update(E entity);

    /**
     * Deletes the entity with the given id.
     * 
     * @param id the entity id.
     */
    void delete(int id);
}
