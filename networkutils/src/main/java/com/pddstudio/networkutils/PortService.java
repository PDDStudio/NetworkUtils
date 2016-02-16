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
import com.pddstudio.networkutils.async.AsyncPortTask;
import com.pddstudio.networkutils.interfaces.ProcessCallback;
import com.pddstudio.networkutils.model.PortResponse;

import java.util.LinkedList;
import java.util.List;

/**
 * A Service to scan for open ports (TCP) on a given target.
 */
public class PortService extends AbstractService {

    final ProcessCallback processCallback;
    final List<Integer> portList;
    String targetAddress;
    AsyncPortTask asyncPortTask;

    protected PortService(ProcessCallback processCallback) {
        super(PortService.class);
        this.processCallback = processCallback;
        this.targetAddress = "127.0.0.1";
        this.portList = new LinkedList<>();
    }

    /**
     * Sets the target address (by default localhost) where to scan for open ports.
     * @param targetAddress - The target address.
     * @return The current PortService instance.
     */
    public PortService setTargetAddress(String targetAddress) {
        this.targetAddress = targetAddress;
        return this;
    }

    /**
     * Adds the specified port to the list for required ports to scan for.
     * @param port - the port to scan for.
     * @return The current PortService instance.
     */
    public PortService addPort(int port) {
        if(port > 0 && port < 65535) this.portList.add(port);
        return this;
    }

    /**
     * Adds the specified ports to the list of required ports to scan for.
     * @param ports - The ports to scan for, separated by comma [,]
     * @return The current PortService instance.
     */
    public PortService addPorts(Integer... ports) {
        for(int port : ports) {
            if (port > 0 && port < 665535) this.portList.add(port);
        }
        return this;
    }

    /**
     * Adds the specified range of ports using the provided start- and endpoint.
     * For example using {@link PortService#addPortRange(int, int)} with a range of 1, 900 will add all ports from 1 until 900 to the list of required ports to scan for.
     * @param from - The port range's starting point.
     * @param until - The port range's ending point.
     * @return The current PortService instance.
     */
    public PortService addPortRange(int from, int until) {
        if(until > 65535) until = 65535;
        if(from < until) {
            for(; from <= until; from++) {
                this.portList.add(from);
            }
        }
        return this;
    }

    /**
     * Starts the scanning process.
     */
    public void scan() {
        asyncPortTask = new AsyncPortTask(this, targetAddress, portList, processCallback);
        asyncPortTask.execute();
    }

    /**
     * Stops the scanning process.
     */
    public void stop() {
        if(asyncPortTask != null && !asyncPortTask.isCancelled()) asyncPortTask.cancel(true);
    }

    /**
     * The return type of this service.
     * Objects retrieved via {@link ProcessCallback#onProcessUpdate(Object)} must be casted to {@link PortResponse} in order to be able to work with the response.
     * @return {@link PortResponse}
     */
    @Override
    public Object getResponseType() {
        return new PortResponse();
    }

}
