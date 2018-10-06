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
package com.github.danzx.forumapp.api.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Comment entity.
 *
 * @author Daniel Pedraza-Arcega
 */
@ApiModel("Post comment")
public class Comment extends BaseEntity {

    @ApiModelProperty("The ID of the post this comment belongs")
    private Integer postId;

    @ApiModelProperty("The name of this comment")
    private String name;

    @ApiModelProperty(value = "The email of the user who created this comment", example = "Peter_Petrelli@primatechpaper.co")
    private String email;

    @ApiModelProperty("The body of this comment")
    private String body;

    @ApiModelProperty("The post this comment belongs, can be null")
    private Post post;

    @ApiModelProperty("The user who created this comment, can be null")
    private User user;

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
