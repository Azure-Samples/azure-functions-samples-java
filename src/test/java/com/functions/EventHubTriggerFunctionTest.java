package com.functions;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.OutputBinding;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class EventHubTriggerFunctionTest {
    /**
     * Unit test for EventHubTriggerFunction class.
     */
    // Define the shared variables globally.
    ExecutionContext context;
    EventHubTriggerFunction eventHubTriggerFunction;
    OutputBinding<String> output;

    @BeforeEach
    void setup() {
        // Mock the Execution Context and its logger since we will be using this in the Azure Functions.
        this.context = mock(ExecutionContext.class);
        doReturn(Logger.getGlobal()).when(context).getLogger();

        // Define the properties of the OutputBinding object.
        this.output = new OutputBinding<String>() {
            String value;

            @Override
            public String getValue() {
                return value;
            }

            @Override
            public void setValue(String value) {
                this.value = value;
            }
        };

        // Instantiate the EventHubTriggerFunction class so that we can call its methods.
        this.eventHubTriggerFunction = new EventHubTriggerFunction();
    }

    @Test
    public void testEventHubTriggerAndOutputJSONJava() throws Exception {
        List<String> messages = new ArrayList<>();
        for(int i = 0; i < 10; ++i) {
            messages.add("Message" + i);
        }

        eventHubTriggerFunction.EventHubTriggerAndOutputJSON(messages, output, context);

        assertEquals(messages.get(0), output.getValue());
    }

    @Test
    public void testEventHubTriggerAndOutputStringJava() throws Exception {
        String message = "Hello World!";

        eventHubTriggerFunction.EventHubTriggerCardinalityOne(message, output, context);

        assertEquals(message, output.getValue());
    }
}
