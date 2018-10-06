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

import static com.github.danzx.forumapp.api.rest.model.Embed.EMBED_ALL;
import static com.github.danzx.forumapp.api.rest.model.Embed.EMBED_COMMENTS;
import static com.github.danzx.forumapp.api.rest.model.Embed.EMBED_POSTS;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.github.danzx.forumapp.api.domain.User;
import com.github.danzx.forumapp.api.rest.model.Embed;
import com.github.danzx.forumapp.api.util.BitFlag;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Users REST controller.
 * 
 * @author Daniel Pedraza-Arcega
 */
@RestController
@RequestMapping("/users")
@Api(description = "Operations for users", tags = { "Users" })
public class UserRestWebService extends BaseRestWebService {

    /**
     * Finds all users.
     *
     * @param embeds indicates if each user will have embedded its posts and/or comments.
     * @return all users.
     */
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Finds all users")
    public Collection<User> findAll(
            @ApiParam(value = "indicates if each user will have embedded its posts and/or comments", allowableValues = "posts,comments") @RequestParam(name = "_embed", required = false, defaultValue = "") List<Embed> embeds) {
        Collection<User> users = getUserDao().findAll();
        if (!users.isEmpty()) {
            int flags = BitFlag.getFlags(embeds);
            switch (flags & EMBED_ALL) {
                case EMBED_ALL:
                    users.forEach(user -> {
                        appendPosts(user);
                        appendComments(user);
                    });
                    break;
                case EMBED_POSTS:
                    users.forEach(user -> {
                        appendPosts(user);
                        appendCountOfComments(user);
                    });
                    break;
                case EMBED_COMMENTS:
                    users.forEach(user -> {
                        appendCountOfPosts(user);
                        appendComments(user);
                    });
                    break;
                default:
                    users.forEach(user -> {
                        appendCountOfPosts(user);
                        appendCountOfComments(user);
                    });
                    break;
            }
        }
        return users;
    }

    /**
     * Finds a user by its id.
     * 
     * @param id the user id.
     * @param embeds indicates if the user will have embedded its posts and/or comments.
     * @return the user or null.
     */
    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Finds a user by its id")
    public User findById(
            @ApiParam(value = "The user id", required = true) @PathVariable("id") int id,
            @ApiParam(value = "indicates if the user will have embedded its posts and/or comments", allowableValues = "posts,comments") @RequestParam(name = "_embed", required = false, defaultValue = "") List<Embed> embeds) {
        Optional<User> user = Optional.ofNullable(getUserDao().findById(id));
        user.ifPresent(u -> {
            int flags = BitFlag.getFlags(embeds);
            switch (flags & EMBED_ALL) {
                case EMBED_ALL:
                    appendPosts(u);
                    appendComments(u);
                    break;
                case EMBED_POSTS:
                    appendPosts(u);
                    appendCountOfComments(u);
                    break;
                case EMBED_COMMENTS:
                    appendCountOfPosts(u); 
                    appendComments(u);
                    break;
                default:
                    appendCountOfPosts(u);
                    appendCountOfComments(u);
                    break;
            }
        });
        return user.orElse(null);
    }

    private void appendPosts(User user) {
        user.setPosts(getPostDao().findByUserId(user.getId()));
    }

    private void appendComments(User user) {
        user.setComments(getCommentDao().findByUserEmail(user.getEmail()));
    }

    private void appendCountOfPosts(User user) {
        user.setNumOfPosts(getPostDao().countByUserId(user.getId()));
    }

    private void appendCountOfComments(User user) {
        user.setNumOfComments(getCommentDao().countByUserEmail(user.getEmail()));
    }
}
