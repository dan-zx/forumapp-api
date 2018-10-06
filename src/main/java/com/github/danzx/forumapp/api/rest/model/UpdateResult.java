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
package com.github.danzx.forumapp.api.rest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Result of an update.
 *
 * @author Daniel Pedraza-Arcega
 */
@ApiModel("Result of an update")
public class UpdateResult {

    public static final UpdateResult SUCCESSFUL;
    public static final UpdateResult FAILURE;

    static {
        SUCCESSFUL = new UpdateResult();
        SUCCESSFUL.successful = true;
        FAILURE = new UpdateResult();
        FAILURE.successful = false;
    }

    @ApiModelProperty("Whether the update was successful or not")
    private boolean successful;

    public boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }
}
