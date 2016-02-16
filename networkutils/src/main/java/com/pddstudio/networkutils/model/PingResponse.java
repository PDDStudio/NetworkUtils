/*
 * Copyright 2016 Patrick J
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package com.pddstudio.networkutils.model;

/**
 * The result type for {@link com.pddstudio.networkutils.interfaces.ProcessCallback} calls when using {@link com.pddstudio.networkutils.PingService}
 */
public class PingResponse {

    private String responseMessage;

    public PingResponse() {}

    /**
     * Returns the String of the Ping's response.
     * @return The String of the Ping's response.
     */
    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
