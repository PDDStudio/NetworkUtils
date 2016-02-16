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
