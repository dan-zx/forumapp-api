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
package com.github.danzx.forumapp.api.rest;

import static com.github.danzx.forumapp.api.rest.Expand.EXPAND_ALL;
import static com.github.danzx.forumapp.api.rest.Expand.EXPAND_POST;
import static com.github.danzx.forumapp.api.rest.Expand.EXPAND_USER;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.github.danzx.forumapp.api.domain.Comment;
import com.github.danzx.forumapp.api.util.BitFlag;

import com.google.gson.JsonObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Comment REST rest.
 * Location: ../[ApplicatonPath]/comments/..
 * 
 * @author Daniel Pedraza-Arcega
 */
@RestController
@RequestMapping("/comments")
@Api(description = "Operations for comments", tags = { "Comments" })
public class CommentRestWebService extends BaseRestWebService {

    /**
     * GET ../
     * Content-Type: "application/json"
     *
     * @param postId only for this post.
     * @param expands user|post.
     * @return a JSON array.
     */
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Finds all comments.")
    public Collection<Comment> findAll(
            @RequestParam(name = "postId", required = false) Integer postId,
            @RequestParam(name = "_expand", required = false, defaultValue = "") List<Expand> expands) {
        Collection<Comment> comments = postId != null ? getCommentDao().findByPostId(postId) : getCommentDao().findAll();
        if (!comments.isEmpty()) {
            int flag = BitFlag.getFlags(expands);
            switch (flag & EXPAND_ALL) {
                case EXPAND_ALL:
                    comments.forEach(comment -> {
                        appendUser(comment);
                        appendPost(comment);
                    });
                    break;
                case EXPAND_USER:
                    comments.forEach(this::appendUser);
                    break;
                case EXPAND_POST:
                    comments.forEach(this::appendPost);
                    break;
                default:
                    break;
            }
        }
        return comments;
    }

    /**
     * GET ../{id}?postId={postId}
     * Content-Type: "application/json"
     * 
     * @param id the comment id.
     * @param expands user|post.
     * @return a JSON of the comment.
     */
    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Finds a comment by its id.")
    public Comment findById(
            @PathVariable("id") int id,
            @RequestParam(name = "_expand", required = false, defaultValue = "") List<Expand> expands) {
        Optional<Comment> comment = Optional.ofNullable(getCommentDao().findById(id));
        comment.ifPresent(c -> {
            int flag = BitFlag.getFlags(expands);
            switch (flag & EXPAND_ALL) {
                case EXPAND_ALL:
                    appendUser(c);
                    appendPost(c);
                    break;
                case EXPAND_USER:
                    appendUser(c);
                    break;
                case EXPAND_POST:
                    appendPost(c);
                    break;
                default:
                    break;
            }
        });
        return comment.orElse(null);
    }

    /**
     * GET ../
     * Content-Type: "application/json"
     * 
     * @param postId the post id.
     * @param email the user email.
     * @return a JSON of count.
     */
    @GetMapping(path = "/count", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Finds the count of comments for the given post and user")
    public String countByPostIdAndUserEmail(
            @RequestParam("postId") int postId,
            @RequestParam("email") String email) {
        JsonObject json = new JsonObject();
        json.addProperty("numOfComments", getCommentDao().countByPostIdAndUserEmail(postId, email));
        return json.toString();
    }

    /**
     * POST ../
     * Content-Type: "application/json"
     * 
     * @param comment a JSON of the comment save.
     * @return a JSON of the comment.
     */
    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Creates a new comment")
    public Comment add(@RequestBody Comment comment) {
        getCommentDao().save(comment);
        return comment;
    }

    /**
     * PUT ../
     * Content-Type: "application/json"
     *
     * @param id the comment id.
     * @param comment a JSON of the comment update.
     * @return a JSON of the post.
     */
    @PutMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Updates a comment")
    public Comment update(@PathVariable("id") int id, @RequestBody Comment comment) {
        if (comment.getId() == null) {
            Comment oldComment = getCommentDao().findById(id);
            if (comment.getName() != null) oldComment.setName(comment.getName());
            if (comment.getBody() != null) oldComment.setBody(comment.getBody());
            if (comment.getPostId() != null) oldComment.setPostId(comment.getPostId());
            if (comment.getEmail() != null) oldComment.setEmail(comment.getEmail());
            getCommentDao().update(oldComment);
            return oldComment;
        } else {
            getCommentDao().update(comment);
            return comment;
        }
    }

    /**
     * DELETE ../{id}
     * Content-Type: "application/json"
     * 
     * @param id the id of the comment to delete.
     * @return a JSON object indicating if the operation was successful.
     */
    @DeleteMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Deletes a comment")
    public UpdateResult delete(@PathVariable("id") int id) {
        getCommentDao().delete(id);
        return UpdateResult.SUCCESSFUL;
    }

    private void appendPost(Comment comment) {
        comment.setPost(getPostDao().findById(comment.getPostId()));
    }

    private void appendUser(Comment comment) {
        comment.setUser(getUserDao().findByEmail(comment.getEmail()));
    }
}
