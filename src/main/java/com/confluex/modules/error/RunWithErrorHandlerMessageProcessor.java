package com.confluex.modules.error;

import org.mule.VoidMuleEvent;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.exception.MessagingExceptionHandler;
import org.mule.api.processor.MessageProcessor;
import org.mule.processor.chain.DefaultMessageProcessorChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class RunWithErrorHandlerMessageProcessor extends DefaultMessageProcessorChain {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    protected MessagingExceptionHandler exceptionStrategy;

    public RunWithErrorHandlerMessageProcessor(List<MessageProcessor> processors) {
        super(processors);
    }

    public void setExceptionStrategy(MessagingExceptionHandler exceptionStrategy) {
        this.exceptionStrategy = exceptionStrategy;
    }

    @Override
    protected MuleEvent doProcess(MuleEvent event) throws MuleException {
        log.debug("error:try received a new event");
        try {
            return super.doProcess(event);
        } catch (MuleException e) {
            if (exceptionStrategy != null) {
                return exceptionStrategy.handleException(e, event);
            } else {
                log.error("error:try handled an error: {}", e.getMessage());
                return VoidMuleEvent.getInstance();
            }
        }
    }
}
