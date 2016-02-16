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
