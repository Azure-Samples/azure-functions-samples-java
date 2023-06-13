package com.functions;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

import java.util.*;

/**
 * Azure Functions with Azure Event Hub.
 * https://docs.microsoft.com/en-us/azure/azure-functions/functions-bindings-event-hubs-trigger?tabs=java
 */
public class EventHubTriggerFunction {
    /**
     * This function will be invoked when a new message is received at the specified EventHub. The message contents are provided as input to this function.
     */
    @FunctionName("EventHubTriggerAndOutputJSON")
    public void EventHubTriggerAndOutputJSON(
        @EventHubTrigger(name = "messages", eventHubName = "test-inputjson-java", connection = "AzureWebJobsEventHubSender", cardinality = Cardinality.MANY) List<String> messages,
        @EventHubOutput(name = "output", eventHubName = "test-outputjson-java", connection = "AzureWebJobsEventHubSender") OutputBinding<String> output,
        final ExecutionContext context
    ) {
        context.getLogger().info("Java Event Hub trigger received " + messages.size() + " messages");
        output.setValue(messages.get(0));
    }

    @FunctionName("EventHubTriggerCardinalityOneEventMetadata")
    public void EventHubTriggerCardinalityOneEventMetadata(
            @EventHubTrigger(
                name = "message",
                eventHubName = "test-input-java",
                consumerGroup = "$default",
                connection = "AzureWebJobsEventHubSender",
                cardinality = Cardinality.ONE
            ) String message,
            final ExecutionContext context,
            @BindingName("Properties") Map<String, Object> properties,
            @BindingName("SystemProperties") Map<String, Object> systemProperties,
            @BindingName("PartitionContext") Map<String, Object> partitionContext,
            @BindingName("EnqueuedTimeUtc") Object enqueuedTimeUtc,
            @BindingName("Offset") Object offset,
            @BindingName("PartitionKey") Object partitionKey,
            @BindingName("SequenceNumber") Object sequenceNumber) {
        // Parse query parameter
        context.getLogger().info("Java Event Hub trigger received message" + message);
        context.getLogger().info("Properties: " + properties);
        context.getLogger().info("System Properties: " + systemProperties);
        context.getLogger().info("PartitionContext: " + partitionContext);
        context.getLogger().info("EnqueuedTimeUtc: " + enqueuedTimeUtc);
        context.getLogger().info("Offset: " + offset);
        context.getLogger().info("PartitionKey: " + partitionKey);
        context.getLogger().info("SequenceNumber: " + sequenceNumber);
    }

    @FunctionName("EventHubTriggerCardinalityManyEventMetadata")
    public void EventHubTriggerCardinalityManyEventMetadata(
        @EventHubTrigger(
            name = "messages",
            eventHubName = "test-input-java",
            connection = "AzureWebJobsEventHubSender",
            dataType = "string",
            cardinality = Cardinality.MANY
        ) String[] messages,
        @BindingName("PropertiesArray") Map<String, Object>[] propertiesArray,
        @BindingName("SystemPropertiesArray") Map<String, Object>[] systemPropertiesArray,
        @BindingName("EnqueuedTimeUtcArray") List<Object> enqueuedTimeUtcArray,
        @BindingName("OffsetArray") List<String> offsetArray,
        @BindingName("PartitionKeyArray") List<String> partitionKeyArray,
        @BindingName("SequenceNumberArray") List<Long> sequenceNumberArray,
        final ExecutionContext context
    ) {
        context.getLogger().info("Java Event Hub trigger received " + messages.length + " messages");
        context.getLogger().info("message[0]=" + messages[0]);
        context.getLogger().info("Properties for message[0]=" + propertiesArray[0]);
        context.getLogger().info("SystemProperties for message[0]="+ systemPropertiesArray[0]);
        context.getLogger().info("EnqueuedTimeUtc for message[0]=" + enqueuedTimeUtcArray.get(0));
        context.getLogger().info("Offset for message[0]=" + offsetArray.get(0));
        context.getLogger().info("PartitionKey for message[0]=" + partitionKeyArray.get(0));
        context.getLogger().info("SequenceNumber for message[0]=" + sequenceNumberArray.get(0));
    }

    @FunctionName("EventHubTriggerAndOutputString")
    public void EventHubTriggerAndOutputString(
        @EventHubTrigger(name = "messages", eventHubName = "test-input-java", connection = "AzureWebJobsEventHubSender", dataType = "string", cardinality = Cardinality.MANY) String[] messages,
        @BindingName("SystemPropertiesArray") SystemProperty[] systemPropertiesArray,
        @EventHubOutput(name = "output", eventHubName = "test-output-java", connection = "AzureWebJobsEventHubSender") OutputBinding<String> output,
        final ExecutionContext context
    ) {
        context.getLogger().info("Java Event Hub trigger received " + messages.length + " messages");
        context.getLogger().info("SystemProperties for message[0]: EnqueuedTimeUtc=" + systemPropertiesArray[0].EnqueuedTimeUtc + " Offset=" + systemPropertiesArray[0].Offset);
        output.setValue(messages[0]);

    }

    @FunctionName("EventHubTriggerCardinalityOne")
    public void EventHubTriggerCardinalityOne(
        @EventHubTrigger(name = "message", eventHubName = "test-inputOne-java", connection = "AzureWebJobsEventHubSender", dataType = "string", cardinality = Cardinality.ONE) String message,
        @EventHubOutput(name = "output", eventHubName = "test-outputone-java", connection = "AzureWebJobsEventHubSender") OutputBinding<String> output,
        final ExecutionContext context
    ) {
        context.getLogger().info("Java Event Hub trigger received message" + message);
        output.setValue(message);
    }

    /**
     * This function verifies the above functions
     */
    @FunctionName("EventHubOutputJson")
    public void TestEventHubOutputJson(
        @EventHubTrigger(name = "message", eventHubName = "test-outputjson-java", connection = "AzureWebJobsEventHubSender") String message,
        @QueueOutput(name = "output", queueName = "test-eventhuboutputjson-java", connection = "AzureWebJobsStorage") OutputBinding<String> output,
        final ExecutionContext context
    ) {
        context.getLogger().info("Java Event Hub Output function processed a message: " + message);
        output.setValue(message);
    }

    @FunctionName("EventHubOutput")
    public void TestEventHubOutput(
        @EventHubTrigger(name = "message", eventHubName = "test-output-java", connection = "AzureWebJobsEventHubSender", cardinality = Cardinality.ONE) String message,
        @QueueOutput(name = "output", queueName = "test-eventhuboutput-java", connection = "AzureWebJobsStorage") OutputBinding<String> output,
        final ExecutionContext context
    ) {
        context.getLogger().info("Java Event Hub Output function processed a message: " + message);
        output.setValue(message);
    }

    @FunctionName("EventHubOutputInputOne")
    public void TestEventHubOutputInputOne(
        @EventHubTrigger(name = "message", eventHubName = "test-outputone-java", connection = "AzureWebJobsEventHubSender", cardinality = Cardinality.ONE) String message,
        @QueueOutput(name = "output", queueName = "test-eventhuboutputone-java", connection = "AzureWebJobsStorage") OutputBinding<String> output,
        final ExecutionContext context
    ) {
        context.getLogger().info("Java Event Hub Output function processed a message: " + message);
        output.setValue(message);
    }

    @FunctionName("EventHubTriggerAndOutputBinaryCardinalityManyListBinary")
    public void EventHubTriggerAndOutputBinaryCardinalityManyListBinary(
        @EventHubTrigger(name = "messages", eventHubName = "test-binary-input-cardinality-many-list-java", connection = "AzureWebJobsEventHubSender_2", dataType = "binary", cardinality = Cardinality.MANY) List<byte[]> messages,
        @QueueOutput(name = "output", queueName = "test-binary-output-cardinality-many-list-java", connection = "AzureWebJobsStorage") OutputBinding<byte[]> output,
        final ExecutionContext context
    ) {
        context.getLogger().info("Java Event Hub trigger received " + messages.size() + " messages");
        output.setValue(messages.get(0));
    }

    @FunctionName("EventHubTriggerAndOutputBinaryCardinalityOne")
    public void EventHubTriggerAndOutputBinaryCardinalityOne(
        @EventHubTrigger(name = "message", eventHubName = "test-binary-input-cardinality-one-java", connection = "AzureWebJobsEventHubSender_2", dataType = "binary", cardinality = Cardinality.ONE) byte[] message,
        @QueueOutput(name = "output", queueName = "test-binary-output-cardinality-one-java", connection = "AzureWebJobsStorage") OutputBinding<byte[]> output,
        final ExecutionContext context
    ) {
        context.getLogger().info("Java Event Hub trigger received message" + message);
        output.setValue(message);
    }

    @FunctionName("EventHubTriggerAndOutputBinaryCardinalityManyArrayBinary")
    public void EventHubTriggerAndOutputBinaryCardinalityManyArrayBinary(
        @EventHubTrigger(name = "messages", eventHubName = "test-binary-input-cardinality-many-array-java", connection = "AzureWebJobsEventHubSender_2", dataType = "binary", cardinality = Cardinality.MANY) byte[][] messages,
        @QueueOutput(name = "output", queueName = "test-binary-output-cardinality-many-array-java", connection = "AzureWebJobsStorage") OutputBinding<byte[]> output,
        final ExecutionContext context
    ) {
        context.getLogger().info("Java Event Hub trigger received " + messages.length + " messages");
        output.setValue(messages[0]);
    }

    public static class SystemProperty {
        public String SequenceNumber;
        public String Offset;
        public String PartitionKey;
        public String EnqueuedTimeUtc;

        public SystemProperty(String sequenceNumber, String offset, String partitionKey, String enqueuedTimeUtc) {
            this.SequenceNumber = sequenceNumber;
            this.Offset = offset;
            this.PartitionKey = partitionKey;
            this.EnqueuedTimeUtc = enqueuedTimeUtc;
        }
    }
}
