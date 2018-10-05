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

import static com.github.danzx.forumapp.api.util.Cloners.deepClone;

import java.util.Objects;
import java.util.Optional;

import com.github.danzx.forumapp.api.dao.UserDao;
import com.github.danzx.forumapp.api.domain.User;

import com.google.gson.JsonObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * User Map based DAO.
 * 
 * @author Daniel Pedraza-Arcega
 */
@Repository
public class UserMapDao extends BaseReadOnlyMapDao<User> implements UserDao {

    private static final Logger LOG = LoggerFactory.getLogger(UserMapDao.class);

    /**
     * Creates a new User Map based DAO.
     * 
     * @param json the data from which the map is going to be created.
     */
    public @Autowired UserMapDao(JsonObject json) {
        super(json, User.class);
    }

    /** {@inheritDoc} */
    @Override
    public String findNameById(int id) {
        LOG.debug("String getNameById({})", id);
        return Optional.ofNullable(db.get(id)).map(User::getName).orElse(null);
    }

    /** {@inheritDoc} */
    @Override
    public User findByEmail(String email) {
        LOG.debug("User findByEmail({})", email);
        return deepClone(db.values().stream()
                .filter(user -> Objects.equals(email, user.getEmail()))
                .findFirst()
                .orElse(null));
    }
}
