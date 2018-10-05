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

import static com.github.danzx.forumapp.api.rest.Embed.EMBED_COMMENTS;
import static com.github.danzx.forumapp.api.rest.Embed.EMBED_NOTHING;
import static com.github.danzx.forumapp.api.rest.Expand.EXPAND_NOTHING;
import static com.github.danzx.forumapp.api.rest.Expand.EXPAND_USER;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Collection;
import java.util.Optional;

import com.github.danzx.forumapp.api.domain.Post;

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
 * Location: ../[ApplicatonPath]/posts/..
 * 
 * @author Daniel Pedraza-Arcega
 */
@RestController
@RequestMapping("/posts")
@Api(description = "Operations for posts", tags = { "Posts" })
public class PostRestWebService extends BaseRestWebService {

    private static final int EMBED_COMMENTS_EXPAND_USER = EMBED_COMMENTS|EXPAND_USER;

    /**
     * GET ../
     * Content-Type: "application/json"
     *
     * @param embedComments posts|comments.
     * @param expandUser user|post.
     * @return a JSON array.
     */
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Finds all posts.")
    public Collection<Post> findAll(
            @RequestParam(name = "_embed", required = false, defaultValue = "") Embed embedComments,
            @RequestParam(name = "_expand", required = false, defaultValue = "") Expand expandUser) {
        Collection<Post> posts = getPostDao().findAll();
        if (!posts.isEmpty()) {
            int flags = getFlags(embedComments, expandUser);
            switch (flags & EMBED_COMMENTS_EXPAND_USER) {
                case EMBED_COMMENTS_EXPAND_USER:
                    posts.forEach(post -> {
                        appendUser(post);
                        appendComments(post);
                    });
                    break;
                case EMBED_COMMENTS:
                    posts.forEach(post -> {
                        appendUserFullName(post);
                        appendComments(post);
                    });
                    break;
                case EXPAND_USER:
                    posts.forEach(post -> {
                        appendUser(post);
                        appendCountOfComments(post);
                    });
                    break;
                default:
                    posts.forEach(post -> {
                        appendUserFullName(post);
                        appendCountOfComments(post);
                    });
                    break;
            }
        }
        return posts;
    }

    /**
     * GET ../{id}
     * Content-Type: "application/json"
     *
     * @param embedComments posts|comments.
     * @param expandUser user|post.
     * @param id the id of a post.
     * @return a JSON object.
     */
    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Finds a post by its id.")
    public Post findById(
            @PathVariable("id") int id,
            @RequestParam(name = "_embed", required = false, defaultValue = "") Embed embedComments,
            @RequestParam(name = "_expand", required = false, defaultValue = "") Expand expandUser) {
        Optional<Post> post = Optional.ofNullable(getPostDao().findById(id));
        post.ifPresent(p -> {
            int flags = getFlags(embedComments, expandUser);
            switch (flags & EMBED_COMMENTS_EXPAND_USER) {
                case EMBED_COMMENTS_EXPAND_USER:
                    appendUser(p);
                    appendComments(p);
                    break;
                case EMBED_COMMENTS:
                    appendUserFullName(p);
                    appendComments(p);
                    break;
                case EXPAND_USER:
                    appendUser(p);
                    appendCountOfComments(p);
                    break;
                default:
                    appendUserFullName(p);
                    appendCountOfComments(p);
                    break;
            }
        });
        return post.orElse(null);
    }

    /**
     * POST ../
     * Content-Type: "application/json"
     * 
     * @param post a JSON of the post save.
     * @return a JSON of the post.
     */
    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Creates a new post")
    public Post add(@RequestBody Post post) {
        getPostDao().save(post);
        appendUserFullName(post);
        post.setNumOfComments(0);
        return post;
    }

    /**
     * PUT ../
     * Content-Type: "application/json"
     *
     * @param id the post id.
     * @param post a JSON of the post update.
     * @return a JSON of the post.
     */
    @PutMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Updates a post")
    public Post update(@PathVariable("id") int id, @RequestBody Post post) {
        if (post.getId() == null) {
            Post oldPost = getPostDao().findById(id);
            if (post.getTitle() != null) oldPost.setTitle(post.getTitle());
            if (post.getBody() != null) oldPost.setBody(post.getBody());
            if (post.getUserId() != null) oldPost.setUserId(post.getUserId());
            getPostDao().update(oldPost);
            return oldPost;
        } else {
            getPostDao().update(post);
            return post;
        }
    }

    /**
     * DELETE ../{id}
     * Content-Type: "application/json"
     * 
     * @param id the id of the post to delete.
     * @return a JSON object indicating if the operation was successful.
     */
    @DeleteMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Deletes a post")
    public UpdateResult delete(@PathVariable("id") int id) {
        getPostDao().delete(id);
        return UpdateResult.SUCCESSFUL;
    }

    private void appendUser(Post post) {
        post.setUser(getUserDao().findById(post.getUserId()));
    }

    private void appendComments(Post post) {
        post.setComments(getCommentDao().findByPostId(post.getId()));
    }

    private void appendUserFullName(Post post) {
        post.setUserFullName(getUserDao().findNameById(post.getUserId()));
    }

    private void appendCountOfComments(Post post) {
        post.setNumOfComments(getCommentDao().countByPostId(post.getId()));
    }

    private int getFlags(Embed embed, Expand expand) {
        return (embed == null ? EMBED_NOTHING : embed.value()) |
               (expand == null ? EXPAND_NOTHING : expand.value());
    }
}
