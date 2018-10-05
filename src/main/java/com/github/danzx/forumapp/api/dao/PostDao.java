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

import com.github.danzx.forumapp.api.domain.Post;

/**
 * Post data access object.
 * 
 * @author Daniel Pedraza-Arcega
 */
public interface PostDao extends CrudDao<Post> {

    /**
     * Counts the number of post the given user has.
     *  
     * @param userId the user id.
     * @return the number of posts.
     */
    int countByUserId(int userId);

    /**
     * Retrieves all posts the given user has.
     *  
     * @param userId the user id.
     * @return posts.
     */
    Collection<Post> findByUserId(int userId);
}
