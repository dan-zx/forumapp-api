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

import com.github.danzx.forumapp.api.dao.CommentDao;
import com.github.danzx.forumapp.api.domain.Comment;

import com.google.gson.JsonObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Comment Map based DAO.
 * 
 * @author Daniel Pedraza-Arcega
 */
@Repository
public class CommentMapDao extends BaseCrudMapDao<Comment> implements CommentDao {

    private static final Logger LOG = LoggerFactory.getLogger(CommentMapDao.class);

    /**
     * Creates a new Comment Map based DAO.
     * 
     * @param json the data from which the map is going to be created.
     */
    public @Autowired CommentMapDao(JsonObject json) {
        super(json, Comment.class);
    }

    /** {@inheritDoc} */
    @Override
    public Collection<Comment> findByPostId(int postId) {
        LOG.debug("Collection<Comment> findByPostId({})", postId);
        return deepClone(db.values().stream()
                .filter(comment -> Objects.equals(postId, comment.getPostId()))
                .collect(toList()));
    }

    /** {@inheritDoc} */
    @Override
    public Collection<Comment> findByUserEmail(String email) {
        LOG.debug("Collection<Comment> findByUserEmail({})", email);
        return deepClone(db.values().stream()
                .filter(comment -> Objects.equals(email, comment.getEmail()))
                .collect(toList()));
    }

    /** {@inheritDoc} */
    @Override
    public int countByPostId(int postId) {
        LOG.debug("int countByPostId({})", postId);
        return (int) db.values().stream()
                .filter(comment -> Objects.equals(postId, comment.getPostId()))
                .count();
    }

    /** {@inheritDoc} */
    @Override
    public int countByUserEmail(String email) {
        LOG.debug("int countByUserEmail({})", email);
        return (int) db.values().stream()
                .filter(comment -> Objects.equals(email, comment.getEmail()))
                .count();
    }

    /** {@inheritDoc} */
    @Override
    public void save(Comment comment) {
        Comment saved = doWithoutExtraProperties(comment, super::save);
        comment.setId(saved.getId());
    }

    /** {@inheritDoc} */
    @Override
    public void update(Comment comment) {
        doWithoutExtraProperties(comment, super::update);
    }

    /** {@inheritDoc} */
    @Override
    public int countByPostIdAndUserEmail(int postId, String email) {
        LOG.debug("int countByPostIdAndUserEmail({}, {})", postId, email);
        return (int) db.values().stream()
                .filter(comment -> Objects.equals(postId, comment.getPostId()) && Objects.equals(email, comment.getEmail()))
                .count();
    }

    /**
     * Deletes the comments associated with the given post id from the map.
     * 
     * @param postId the post id.
     */
    void deleteByPostId(int postId) {
        LOG.debug("void deleteByPostId({})", postId);
        db.values().stream()
            .filter(comment -> Objects.equals(postId, comment.getPostId()))
            .map(Comment::getId)
            .collect(toList())
            .forEach(this::delete);
    }

    /**
     * Creates a clone without extra properties if the object contains them, then the operation is
     * executed.
     * 
     * @param comment the object.
     * @param op the operation to be applied on the object.
     * @return the object used.
     */
    private Comment doWithoutExtraProperties(Comment comment, Consumer<Comment> op) {
        if (comment.getUser() != null || comment.getPost() != null) {
            comment = deepClone(comment);
            comment.setUser(null);
            comment.setPost(null);
        }
        op.accept(comment);
        return comment;
    }
}
