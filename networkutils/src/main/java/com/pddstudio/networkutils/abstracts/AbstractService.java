package com.pddstudio.networkutils.abstracts;

/**
 * This Class was created by Patrick J
 * on 12.02.16. For more Details and Licensing
 * have a look at the README.md
 */
public abstract class AbstractService {

    private final Object className;

    protected AbstractService(Object className) {
        this.className = className;
    }

    public String getServiceName() {
        if(className instanceof Class) return ((Class) className).getSimpleName();
        return className.getClass().getSimpleName();
    }

    public abstract Object getResponseType();

}
