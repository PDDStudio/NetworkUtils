package com.pddstudio.networkutils;

import com.pddstudio.networkutils.abstracts.AbstractService;
import com.pddstudio.networkutils.async.AsyncPingTask;
import com.pddstudio.networkutils.interfaces.ProcessCallback;
import com.pddstudio.networkutils.model.PingResponse;

/**
 * This Class was created by Patrick J
 * on 12.02.16. For more Details and Licensing
 * have a look at the README.md
 */
public class PingService extends AbstractService {

    private final ProcessCallback processCallback;
    private String targetAddress = "127.0.0.1";
    private AsyncPingTask asyncPingTask;

    protected PingService(ProcessCallback processCallback) {
        super(PingService.class);
        this.processCallback = processCallback;
    }

    public PingService setTargetAddress(String targetAddress) {
        this.targetAddress = targetAddress;
        return this;
    }

    public void start() {
        asyncPingTask = new AsyncPingTask(this, targetAddress, processCallback);
        asyncPingTask.execute();
    }

    public void destroy() {
        if(asyncPingTask != null && !asyncPingTask.isCancelled()) {
            asyncPingTask.cancel(true);
        }
    }

    public boolean isRunning() {
        return (asyncPingTask != null && !asyncPingTask.isCancelled());
    }

    @Override
    public Object getResponseType() {
        return new PingResponse();
    }
}
