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

package com.pddstudio.networkutils;

import com.pddstudio.networkutils.abstracts.AbstractService;
import com.pddstudio.networkutils.async.AsyncPingTask;
import com.pddstudio.networkutils.interfaces.ProcessCallback;
import com.pddstudio.networkutils.model.PingResponse;

/**
 * A Service for executing Ping requests on a given target address.
 */
public class PingService extends AbstractService {

    private final ProcessCallback processCallback;
    private String targetAddress = "127.0.0.1";
    private AsyncPingTask asyncPingTask;

    protected PingService(ProcessCallback processCallback) {
        super(PingService.class);
        this.processCallback = processCallback;
    }

    /**
     * Set the target address (localhost by default)
     * @param targetAddress - The target address to ping. This can be an internal or external address.
     * @return The current PingService instance.
     */
    public PingService setTargetAddress(String targetAddress) {
        this.targetAddress = targetAddress;
        return this;
    }

    /**
     * Start pinging the required target.
     */
    public void start() {
        asyncPingTask = new AsyncPingTask(this, targetAddress, processCallback);
        asyncPingTask.execute();
    }

    /**
     * Stop pinging the required target.
     */
    public void destroy() {
        if(asyncPingTask != null && !asyncPingTask.isCancelled()) {
            asyncPingTask.cancel(true);
        }
    }

    /**
     * Check whether the service is running or not.
     * @return True if the service is running, false if not.
     */
    public boolean isRunning() {
        return (asyncPingTask != null && !asyncPingTask.isCancelled());
    }

    /**
     * The return type of this service.
     * Objects retrieved via {@link ProcessCallback#onProcessUpdate(Object)} must be casted to {@link PingResponse} in order to be able to work with the response.
     * @return {@link PingResponse}
     */
    @Override
    public Object getResponseType() {
        return new PingResponse();
    }
}
