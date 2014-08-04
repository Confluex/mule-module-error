package com.confluex.modules.error

import org.junit.Test
import org.mule.DefaultMuleMessage
import org.mule.api.MuleMessage
import org.mule.api.client.LocalMuleClient
import org.mule.tck.junit4.FunctionalTestCase

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertNotNull


class ErrorModuleFunctionalTest extends FunctionalTestCase {
    @Override
    protected String getConfigFile() {
        return "error-config.xml"
    }

    @Test
    public void errorShouldNotPreventProcessing() throws Exception {
        def message = new DefaultMuleMessage("Joe", muleContext)

        def result = muleContext.client.send("vm://test.hello", message)
        assert "Hello Joe!" == result?.payloadAsString

        def error = muleContext.client.request("vm://test.error", 1000)
        assertNotNull(error)
    }

    @Test
    public void errorShouldNotBreakForeachLoop() throws Exception {
        int msgCount = 5
        List<String> payload = new ArrayList<String>()
        for (int i = 0; i < msgCount; i++) {
            payload.add("Message #${i}")
        }

        def message = new DefaultMuleMessage(payload, muleContext)
        def result = muleContext.client.send("vm://test.loop", message)
        assert result?.payload == payload

        for (int i = 0; i < msgCount; i++) {
            int counter = i + 1 // mule's foreach counter is 1 based
            boolean isEven = counter % 2 == 0
            def location = isEven ? "vm://test.loop.success" : "vm://test.error"
            assert  muleContext.client.request(location, 1000)?.payloadAsString ==  "Message #${i}"
        }

    }
}
