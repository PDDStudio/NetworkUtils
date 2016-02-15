package com.pddstudio.networkutils;

import com.pddstudio.networkutils.abstracts.AbstractService;
import com.pddstudio.networkutils.async.AsyncPortTask;
import com.pddstudio.networkutils.interfaces.ProcessCallback;
import com.pddstudio.networkutils.model.PortResponse;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * This Class was created by Patrick J
 * on 12.02.16. For more Details and Licensing
 * have a look at the README.md
 */
public class PortService extends AbstractService {

    final ProcessCallback processCallback;
    final List<Integer> portList;
    String targetAddress;
    AsyncPortTask asyncPortTask;

    protected PortService(ProcessCallback processCallback) {
        super(PortService.class);
        this.processCallback = processCallback;
        this.portList = new LinkedList<>();
    }

    public PortService setTargetAddress(String targetAddress) {
        this.targetAddress = targetAddress;
        return this;
    }

    public PortService addPort(int port) {
        this.portList.add(port);
        return this;
    }

    public PortService addPorts(Integer... ports) {
        this.portList.addAll(Arrays.asList(ports));
        return this;
    }

    public PortService addPortRange(int from, int until) {
        if(until > 65535) until = 65535;
        if(from < until) {
            for(; from <= until; from++) {
                this.portList.add(from);
            }
        }
        return this;
    }

    public void scan() {
        asyncPortTask = new AsyncPortTask(this, targetAddress, portList, processCallback);
        asyncPortTask.execute();
    }

    public void stop() {
        if(asyncPortTask != null && !asyncPortTask.isCancelled()) asyncPortTask.cancel(true);
    }

    @Override
    public Object getResponseType() {
        return null;
    }

}
