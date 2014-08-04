package com.confluex.modules.error.config;

import org.mule.config.spring.factories.MessageProcessorChainFactoryBean;
import org.mule.config.spring.parsers.delegate.ParentContextDefinitionParser;
import org.mule.config.spring.parsers.generic.ChildDefinitionParser;
import org.mule.config.spring.parsers.generic.MuleOrphanDefinitionParser;

public class RunWithErrorHandlerDefinitionParser extends ParentContextDefinitionParser {
    public RunWithErrorHandlerDefinitionParser() {
        super(
                MuleOrphanDefinitionParser.ROOT_ELEMENT,
                new MuleOrphanDefinitionParser(MessageProcessorChainFactoryBean.class, false)
        );
        otherwise(new ChildDefinitionParser("messageProcessor", RunWithErrorHandlerFactoryBean.class));
        addReference("catch-ref");
    }
}
