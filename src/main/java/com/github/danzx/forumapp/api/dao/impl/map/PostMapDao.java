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

import static java.util.stream.Collectors.toList;

import static com.github.danzx.forumapp.api.util.Cloners.deepClone;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;

import com.github.danzx.forumapp.api.dao.PostDao;
import com.github.danzx.forumapp.api.domain.Post;

import com.google.gson.JsonObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Post Map based DAO.
 * 
 * @author Daniel Pedraza-Arcega
 */
@Repository
public class PostMapDao extends BaseCrudMapDao<Post> implements PostDao {

    private static final Logger LOG = LoggerFactory.getLogger(PostMapDao.class);

    private final CommentMapDao commentDao;

    /**
     * Creates a new PostMapDao.
     * 
     * @param json the data from which the map is going to be created.
     * @param commentDao a Comment Map based DAO.
     */
    public @Autowired PostMapDao(JsonObject json, CommentMapDao commentDao) {
        super(json, Post.class);
        this.commentDao = commentDao;
    }

    /** {@inheritDoc} */
    @Override
    public int countByUserId(int userId) {
        LOG.debug("int countByUserId({})", userId);
        return (int) db.values().stream()
                .filter(post -> Objects.equals(userId, post.getUserId()))
                .count();
    }

    /** {@inheritDoc} */
    @Override
    public void delete(int id) {
        commentDao.deleteByPostId(id);
        super.delete(id);
    }

    /** {@inheritDoc} */
    @Override
    public Collection<Post> findByUserId(int userId) {
        LOG.debug("Collection<Post> findByUserId({})", userId);
        return deepClone(db.values().stream()
                .filter(post -> Objects.equals(userId, post.getUserId()))
                .collect(toList()));
    }

    /** {@inheritDoc} */
    @Override
    public void save(Post post) {
        Post saved = doWithoutExtraProperties(post, super::save);
        post.setId(saved.getId());
    }

    /** {@inheritDoc} */
    @Override
    public void update(Post post) {
        doWithoutExtraProperties(post, super::update);
    }

    /**
     * Creates a clone without extra properties if the object contains them, then the operation is
     * executed.
     * 
     * @param post the object.
     * @param op the operation to be applied on the object.
     * @return the object used.
     */
    private Post doWithoutExtraProperties(Post post, Consumer<Post> op) {
        if (post.getUserFullName() != null || post.getUser() != null || post.getNumOfComments() != null || post.getComments() != null) {
            post = deepClone(post);
            post.setUserFullName(null);
            post.setNumOfComments(null);
            post.setUser(null);
            post.setComments(null);
        }
        op.accept(post);
        return post;
    }
}
