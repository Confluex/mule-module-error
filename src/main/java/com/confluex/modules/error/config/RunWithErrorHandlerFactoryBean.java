package com.confluex.modules.error.config;

import com.confluex.modules.error.RunWithErrorHandlerMessageProcessor;
import org.mule.api.exception.MessagingExceptionHandler;
import org.mule.api.processor.MessageProcessor;
import org.springframework.beans.factory.FactoryBean;

import java.util.List;

public class RunWithErrorHandlerFactoryBean implements FactoryBean {

    protected List<MessageProcessor> processors;
    protected String name;

    protected MessagingExceptionHandler exceptionStrategy;

    public void setCatch(MessagingExceptionHandler exceptionStrategy) {
       this.exceptionStrategy = exceptionStrategy;
    }

    public void setMessageProcessors(List<MessageProcessor> processors) {
        this.processors = processors;
    }

    public Class getObjectType() {
        return RunWithErrorHandlerMessageProcessor.class;
    }

    public Object getObject() throws Exception {
        RunWithErrorHandlerMessageProcessor processor = new RunWithErrorHandlerMessageProcessor(processors);
        processor.setExceptionHandler(exceptionStrategy);
        return processor;
    }

    public boolean isSingleton() {
        return false;
    }

}
