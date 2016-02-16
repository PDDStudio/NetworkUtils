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

package com.pddstudio.networkutils.abstracts;

/**
 * The abstract class all Services extend from.
 */
public abstract class AbstractService {

    private final Object className;

    protected AbstractService(Object className) {
        this.className = className;
    }

    /**
     * Returns the Service's Name
     * @return The Service's Name
     */
    public String getServiceName() {
        if(className instanceof Class) return ((Class) className).getSimpleName();
        return className.getClass().getSimpleName();
    }

    /**
     * Returns the Service's Callback Object-Type, this might be used to cast the incoming Object the correct way.
     * @return The Service's Callback Object-Type, this might be used to cast the incoming Object the correct way.
     */
    public abstract Object getResponseType();

}
