# Processors

## error:try

This processor enhances Mule's exception strategies by allowing contextual error handling. Think of it like a try
catch block in code.

```xml
<flow name="AwesomeFlow">
    <inbound-endpoint ref="stuffHappened"/>
    <message-processor/>     
    <foreach collection="#[payload.stuff]">
        <error:try catch-ref="cloudOperationErrorHandler">
            <cloud:operation/>
        </error:try>
    </foreach>
    <outbound-endpoint/>
    <exception-strategy ref="errorHandler"/>
</flow>
<catch-exception-strategy name="cloudOperationErrorHandler">
    <!-- handle cloud:operation exceptions here -->  
</catch-exception-strategy>
<catch-exception-strategy name="errorHandler">
    <!-- handle all other exceptions here -->  
</catch-exception-strategy>
```

Here we execute the cloud operation with its own error handler (the catch-ref) in context to the 
foreach loop. 

> Without this, the rest of the messages in the collection would not have been processed after the first error. This
> probably isn't what you'd want in most cases. Defining your error handling in a smaller scope can help you avoid
> these types of problems.



| Attribute     | Description   |
| ------------- |---------------| 
| catch-ref     | Reference to a global exception strategy. The value returned from the exception strategy will be returned back to the flow. If un-configured, any errors will be logged and the input payload returned.|


# Mule supported versions

3.4.x-3.5.x

# Installation 

You can add the Maven dependency to your project's pom.xml:

```xml
<dependency>
    <groupId>com.confluex</groupId>
    <artifactId>mule-module-error</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

> TODO: Manual installation instructions


Configure the namespace for your flow:

```xml
<mule xmlns="http://www.mulesoft.org/schema/mule/core"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xmlns:error="http://www.mulesoft.org/schema/mule/error"
      xsi:schemaLocation="
        http://www.mulesoft.org/schema/mule/core http://www.mulesoft.org/schema/mule/core/current/mule.xsd
        http://www.mulesoft.org/schema/mule/error http://www.mulesoft.org/schema/mule/error/current/mule-error.xsd
">
</mule>
```
