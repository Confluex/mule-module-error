package com.confluex.modules.error.config;

import com.confluex.modules.error.RunWithErrorHandlerMessageProcessor;
import org.mule.api.processor.MessageProcessor;
import org.springframework.beans.factory.FactoryBean;

import java.util.List;

public class RunWithErrorHandlerFactoryBean implements FactoryBean {

    protected List<MessageProcessor> processors;
    protected String name;

    public Class getObjectType() {
        return RunWithErrorHandlerMessageProcessor.class;
    }

    public void setMessageProcessors(List<MessageProcessor> processors) {
        this.processors = processors;
    }

    public Object getObject() throws Exception {
        return new RunWithErrorHandlerMessageProcessor(processors);
    }

    public boolean isSingleton() {
        return false;
    }

}
