# Processors

## error:try

This processor enhances Mule's exception strategies by allowing contextual error handling. 

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