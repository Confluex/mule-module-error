package com.confluex.modules.error;

import org.junit.Test;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.api.client.LocalMuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ErrorModuleFunctionalTest extends FunctionalTestCase {
    @Override
    protected String getConfigFile() {
        return "error-config.xml";
    }

    @Test
    public void errorShouldNotPreventProcessing() throws Exception {
        LocalMuleClient client = muleContext.getClient();
        DefaultMuleMessage message = new DefaultMuleMessage("Joe", muleContext);

        MuleMessage result = client.send("vm://test.hello", message);
        assertEquals("Hello Joe!", result.getPayloadAsString());

        MuleMessage error = client.request("vm://test.error", 1000);
        assertNotNull(error);
    }

    @Test
    public void errorShouldNotBreakForeachLoop() throws Exception {
        LocalMuleClient client = muleContext.getClient();
        int msgCount = 5;
        List<String> payload = new ArrayList<String>();
        for (int i = 0; i < msgCount; i++) {
            payload.add("Joe " + i);
        }
        DefaultMuleMessage message = new DefaultMuleMessage(payload, muleContext);
        MuleMessage result = client.send("vm://test.loop", message);

        assertNotNull(result);
        assertEquals(payload, result.getPayload());

        for (int i = 0; i < msgCount; i++) {
            boolean isEven = i % 2 == 0;
            if (isEven) {
                MuleMessage success = client.request("vm://test.loop.success", 5000);
                assertNotNull("No success found for: " + i, success);
            } else {
                MuleMessage error = client.request("vm://test.error", 5000);
                assertNotNull("No error found for: " + i, error);
            }
        }

    }
}
