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

import java.util.Collection;

import com.github.danzx.forumapp.api.domain.BaseEntity;

/**
 * Defines the operations of read only data access objects.
 * 
 * @author Daniel Pedraza-Arcega
 *
 * @param <E> the entity type.
 */
public interface ReadOnlyDao<E extends BaseEntity> {

    /**
     * Retrieves an entity by its id.
     * 
     * @param id the entity id.
     * @return the entity with the given id or {@code null} if none found.
     */
    E findById(int id);

    /**
     * Returns all entities.
     * 
     * @return all entities or an empty collection if none found.
     */
    Collection<E> findAll();
}
