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

import com.github.danzx.forumapp.api.dao.CommentDao;
import com.github.danzx.forumapp.api.dao.PostDao;
import com.github.danzx.forumapp.api.dao.UserDao;

import com.google.gson.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Base REST web service.
 * 
 * @author Daniel Pedraza-Arcega
 */
abstract class BaseRestWebService {

    private UserDao userDao;
    private PostDao postDao;
    private CommentDao commentDao;

    protected UserDao getUserDao() {
        return userDao;
    }

    public @Autowired void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    protected PostDao getPostDao() {
        return postDao;
    }

    public @Autowired void setPostDao(PostDao postDao) {
        this.postDao = postDao;
    }

    protected CommentDao getCommentDao() {
        return commentDao;
    }

    public @Autowired void setCommentDao(CommentDao commentDao) {
        this.commentDao = commentDao;
    }
}
