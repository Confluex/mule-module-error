package com.confluex.modules.error

import org.junit.Test
import org.mule.DefaultMuleMessage
import org.mule.tck.junit4.FunctionalTestCase

import static org.junit.Assert.*

class ErrorModuleFunctionalTest extends FunctionalTestCase {
    @Override
    String getConfigFile() {
        return "error-config.xml"
    }

    @Test
    void errorShouldNotPreventProcessing() throws Exception {
        def message = new DefaultMuleMessage("Joe", muleContext)

        def result = muleContext.client.send("vm://test.hello", message)
        assert "Hello Joe!" == result?.payloadAsString

        def error = muleContext.client.request("vm://test.error", 1000)
        assertNotNull(error)
    }

    @Test
    void errorShouldNotBreakForeachLoop() throws Exception {
        int msgCount = 5
        List<String> payload = new ArrayList<String>()
        msgCount.times {
            payload.add("Message #${it}")
        }

        def message = new DefaultMuleMessage(payload, muleContext)
        def result = muleContext.client.send("vm://test.loop", message)
        assert result?.payload == payload

        msgCount.times {
            def counter = it + 1 // mule's foreach counter is 1 based
            def isEven = counter % 2 == 0
            def location = isEven ? "vm://test.loop.success" : "vm://test.error"
            assert muleContext.client.request(location, 1000)?.payloadAsString == "Message #${it}"
        }

    }
}
