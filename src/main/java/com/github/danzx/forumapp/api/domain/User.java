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

import java.util.Collection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * User entity.
 *
 * @author Daniel Pedraza-Arcega
 */
@ApiModel("Forum user")
public class User extends BaseEntity {

    @ApiModelProperty("The name of this user")
    private String name;

    @ApiModelProperty("The username of this user")
    private String username;

    @ApiModelProperty(value = "The email of this user", example = "Peter_Petrelli@primatechpaper.co")
    private String email;

    @ApiModelProperty(value = "The phone of this user", example = "+1 123 123-12-12 #60271")
    private String phone;

    @ApiModelProperty(value = "The website of the user's job", example = "http://www.primatechpaper.co")
    private String website;

    @ApiModelProperty("The name of the company this user is currently employed")
    private String company;

    @ApiModelProperty("The number of posts this user has created")
    private Integer numOfPosts;

    @ApiModelProperty("The number of comments this user has created")
    private Integer numOfComments;

    @ApiModelProperty("The posts this user has created, can be null")
    private Collection<Post> posts;

    @ApiModelProperty("The comments this user has created, can be null")
    private Collection<Comment> comments;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getNumOfPosts() {
        return numOfPosts;
    }

    public void setNumOfPosts(Integer numOfPosts) {
        this.numOfPosts = numOfPosts;
    }

    public Integer getNumOfComments() {
        return numOfComments;
    }

    public void setNumOfComments(Integer numOfComments) {
        this.numOfComments = numOfComments;
    }

    public Collection<Post> getPosts() {
        return posts;
    }

    public void setPosts(Collection<Post> posts) {
        this.posts = posts;
    }

    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }
}
