package com.confluex.modules.error.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class ErrorNamespaceHandler extends NamespaceHandlerSupport {
    public void init() {
        registerBeanDefinitionParser("try", new RunWithErrorHandlerDefinitionParser());
    }
}
