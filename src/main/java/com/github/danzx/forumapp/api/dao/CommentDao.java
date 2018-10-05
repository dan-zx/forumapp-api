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

import com.github.danzx.forumapp.api.domain.Comment;

/**
 * Comment data access object.
 * 
 * @author Daniel Pedraza-Arcega
 */
public interface CommentDao extends CrudDao<Comment> {

    /**
     * Retrieves the comments associated to the given post.
     * 
     * @param postId the post id.
     * @return comments or an empty collection if none found.
     */
    Collection<Comment> findByPostId(int postId);

    /**
     * Retrieves all comments the given user has.
     * 
     * @param email the user email.
     * @return comments or an empty collection if none found.
     */
    Collection<Comment> findByUserEmail(String email);

    /**
     * Counts the number of comments the given post has.
     *  
     * @param postId the post id.
     * @return the number of comments.
     */
    int countByPostId(int postId);

    /**
     * Counts the number of comments the user post has.
     *  
     * @param email the user email.
     * @return the number of comments.
     */
    int countByUserEmail(String email);

    /**
     * Counts the number of comments the user post has in the given post.
     * 
     * @param postId the post id.
     * @param email the user email.
     * @return the number of comments.
     */
    int countByPostIdAndUserEmail(int postId, String email);
}
