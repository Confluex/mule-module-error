# Error Module

This module enhances mule exception strategies by allowing contextual error handling. For instance, Mule 
only allows you to configure a exception strategy for an entire flow.

```
<flow name="AwesomeFlow">
    <inbound-endpoint ref="stuffHappened"/>
    <message-processor/>     
    <foreach collection="#[payload.stuff]">
        <cloud:operation/>
    </foreach>
    <outbound-endpoint/>
    <exception-strategy ref="errorHandler"/>
</flow>
```

While this works, it's pretty restrictive and can lead to some very common (and costly) mistakes. If you think
 about this in code, it's the equivalent of having a single try catch block for an entire method. 
 
What happens if the cloud:operation processor in the foreach loops throws validation error due to a bad entry 
in the middle of the collection? The rest of the messages in the collection will be passed to the exception
strategy even though they may have been perfectly fine.

To fix the problem, you could refactor your flow and extract the <cloud:operation/> to its own flow with its own exception 
strategy:

```
<flow name="AwesomeFlow">
    <inbound-endpoint ref="stuffHappened"/>
    <message-processor/>     
    <foreach collection="#[payload.stuff]">
        <flow-ref name="CloudOperation"/>
    </foreach>
    <outbound-endpoint/>
    <exception-strategy ref="errorHandler"/>
</flow>
<flow name="CloudOperation">
    <cloud:operation/>
    <exception-strategy ref="cloudOperationErrorHandler"/>
</flow>
```
 
That's better but not very expressive. The error:try processor may be helpful to you here:

```
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
```
Here we execute the cloud operation with its own error handler (the catch-ref) in context to the original flow.

# Mule supported versions

TODO

# Installation 

TODO

#Usage

TODO

# Reporting Issues

TODO