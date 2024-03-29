---
page_type: sample
languages:
- java
products:
- azure-functions
- azure
description: "This repository contains sample for Azure Functions in Java"
urlFragment: "azure-functions-java"
---

# Azure functions samples in Java

This repository contains samples which show the usage of [Azure Functions](https://docs.microsoft.com/en-us/azure/azure-functions/) in Java for the below scenarios.

| Scenario       | Description                                |
|-------------------|--------------------------------------------|
| [HttpTrigger](./triggers-bindings/src/main/java/com/functions/Function.java) | Basic HttpTrigger and FixedDelayRetry with HttpTrigger.  |
| [BlobTrigger](./triggers-bindings/src/main/java/com/functions/BlobTriggerFunction.java) | BlobTrigger, read blob using BlobInput binding and output to blob using BlobOutput binding.  |
| [CosmosDBTrigger](./triggers-bindings/src/main/java/com/functions/CosmosDBTriggerFunction.java)  | CosmosDBTrigger, read cosmos DB entries with CosmosDBInput binding and output to cosmos DB CosmosDBOutput binding.  |
| [TimerTrigger](./triggers-bindings/src/main/java/com/functions/TimerTriggerFunction.java) | Basic periodic TimerTrigger.  |
| [EventGridTrigger](./triggers-bindings/src/main/java/com/functions/EventGridTriggerFunction.java) | EventGridTrigger and send event to Event Grid using EventGridOutput binding.  |
| [EventHubTrigger](./triggers-bindings/src/main/java/com/functions/EventHubTriggerFunction.java) | EventHubTrigger for message received in event grid and output to event grid using EventHubOutput binding.  |
| [KafkaTrigger](./triggers-bindings/src/main/java/com/functions/KafkaTriggerFunction.java) | KafkaTrigger with KafkaOutput and QueueOutput example.  |
| [QueueTrigger](./triggers-bindings/src/main/java/com/functions/QueueTriggerFunction.java) | QueueTrigger to read content from queue and output to queue using QueueOutput binding.  |
| [ServiceBusQueueTrigger](./triggers-bindings/src/main/java/com/functions/ServiceBusQueueTriggerFunction.java) | ServiceBusQueueTrigger to read message from a queue in service bus and output to service bus queue using ServiceBusQueueOutput binding.  |
| [ServiceBusTopicTrigger](./triggers-bindings/src/main/java/com/functions/ServiceBusTopicTriggerFunction.java) | ServiceBusTopicTrigger to read message from a topic in service bus and output to service bus topic using ServiceBusTopicOutput binding.  |
| [Table function](./triggers-bindings/src/main/java/com/functions/TableFunction.java) | Basic example to read and write to table in Azure Storage using TableInput and TableOutput binding.  |
| [Durable function](./durable-function/src/main/java/com/azfs/example/DurableFunction.java) | Durable function example to start an orchestration and follow activity chaining.  |
| [Distributed tracing](./distributed-tracing/src/main/java/com/azfs/example/DistributedTracingFunction.java) | Distributed tracing example that adds entries to a MYSQL database via JDBC, adds custom metrics to the Application Insights and outputs to cosmos DB CosmosDBOutput binding.  |
| [Dependency injection - Dagger](./dependency-injection/dagger-function/src/main/java/com/azfs/Function.java) | Dagger example that shows the usage of dependency injection with azure functions.  |
| [Dependency injection - Guice](./dependency-injection/guice-function/src/main/java/com/azfs/Function.java) | Google guice example that shows the usage of dependency injection with azure functions.  |
| [Spring cloud](./spring-cloud/src/main/java/example/uppercase/UppercaseHandler.java) | Usage of Spring cloud to develop a function.  |


## Contents

Outlines the contents of this repository. It helps users navigate the codebase, build configuration and any related assets.

| File/folder       | Description                                |
|-------------------|--------------------------------------------|
| `triggers-bindings`      | Triggers and bindings available in Azure functions.    |
| `dependency-injection`   | Integration of Google Guice and Dagger with Azure Functions.      |
| `spring-cloud`           | Usage of Spring cloud to develop a Java function.      |
| `distributed-tracing`        | Sample that shows the usage of distributed tracing coupled with azure functions.      |
| `durable-function`           | Durable function example to run orchestrations and activities.      |
| `.gitignore`      | Define what to ignore at commit time.      |
| `CHANGELOG.md`    | List of changes to the sample.             |
| `CONTRIBUTING.md` | Guidelines for contributing to the sample. |
| `README.md`       | This README file.                          |
| `LICENSE.txt`     | The license for the sample.                |

## Prerequisites

- Gradle 4.10+
- Latest [Function Core Tools](https://aka.ms/azfunc-install)
- Azure CLI. This plugin use Azure CLI for authentication, please make sure you have Azure CLI installed and logged in.

## Setup

- Please refer individual directories for sample specific setup. Some common setup instructions are provided below.

- ```cmd
    az login
    az account set -s <your subscription id>
    ```
- Update the Application settings in Azure portal with the required parameters as below
  - AzureWebJobsStorage: Connection string to your storage account
  - CosmosDBDatabaseName: Cosmos database name. Example: ItemCollectionIn
  - CosmosDBCollectionName:Cosmos database collection name. Example: ItemDb
  - AzureWebJobsCosmosDBConnectionString: Connection string to your Cosmos database
  - AzureWebJobsEventGridOutputBindingTopicUriString: Event Grid URI
  - AzureWebJobsEventGridOutputBindingTopicKeyString: Event Grid string
  - AzureWebJobsEventHubSender, AzureWebJobsEventHubSender_2 : Event hub connection string
  - AzureWebJobsServiceBus: Service bus connection string
  - SBQueueName: Service bus queue name. Example: test-input-java
  - SBTopicName: Service bus topic name. Example: javaworkercitopic2
  - SBTopicSubName: Service bus topic name. Example: javaworkercisub
  - Documentation on how to [manage connection strings](https://docs.microsoft.com/en-gb/azure/storage/common/storage-account-keys-manage?tabs=azure-portal) and [access keys](https://docs.microsoft.com/en-gb/azure/storage/common/storage-configure-connection-string#create-a-connection-string-for-an-azure-storage-account)
- Update `host.json` with the right extension bundle version. `V3 - [1.*, 2.0.0) and V4 - [2.*, 3.0.0)`


## Running the sample

```cmd
./mvnw clean package azure-functions:run
```

```cmd
./gradlew clean azureFunctionsRun
```

## Deploy the sample on Azure


```cmd
./mvnw clean package azure-functions:deploy
```

```cmd
./gradlew clean azureFunctionsDeploy
```

> NOTE: please replace '/' with '\\' when you are running on windows.


## Contributing

This project welcomes contributions and suggestions.  Most contributions require you to agree to a
Contributor License Agreement (CLA) declaring that you have the right to, and actually do, grant us
the rights to use your contribution. For details, visit https://cla.opensource.microsoft.com.

When you submit a pull request, a CLA bot will automatically determine whether you need to provide
a CLA and decorate the PR appropriately (e.g., status check, comment). Simply follow the instructions
provided by the bot. You will only need to do this once across all repos using our CLA.

This project has adopted the [Microsoft Open Source Code of Conduct](https://opensource.microsoft.com/codeofconduct/).
For more information see the [Code of Conduct FAQ](https://opensource.microsoft.com/codeofconduct/faq/) or
contact [opencode@microsoft.com](mailto:opencode@microsoft.com) with any additional questions or comments.

## Telemetry
This project collects usage data and sends it to Microsoft to help improve our products and services.
Read Microsoft's [privacy statement](https://privacy.microsoft.com/en-us/privacystatement) to learn more.
If you would like to opt out of sending telemetry data to Microsoft, you can set `allowTelemetry` to false in the plugin configuration.
Please read our [document](https://github.com/microsoft/azure-gradle-plugins/wiki/Configuration) to find more details about *allowTelemetry*.
