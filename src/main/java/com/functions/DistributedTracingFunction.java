package com.function;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.CosmosDBOutput;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Random;

public class Function {
    @FunctionName("JDBCTest")
    @CosmosDBOutput(
        name = "itemOut",
        databaseName = "database",
        containerName = "itemsOut",
        connection = "Cosmos"
    )
    public String run(
            @HttpTrigger(
                name = "req",
                methods = {HttpMethod.GET, HttpMethod.POST},
                authLevel = AuthorizationLevel.ANONYMOUS)
                HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {

        context.getLogger().info("Java HTTP trigger processed a request.");

        // Parse query parameter
        final String query = request.getQueryParameters().get("name");
        final String name = request.getBody().orElse(query);
        final String entry = name == null ? "No new name" : name;

        // Custom metric
        Meter meter = GlobalOpenTelemetry.getMeter("DistributedTracing.Demo");
        LongCounter entryCounter = meter.counterBuilder("EntryCounter").build();
        entryCounter.add(1);
        
        // Register the JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Add entry to MYSQL database
        String sqlInsertName = "INSERT INTO input(name) VALUES(?)";
        String user = System.getenv("JDBCUser");
        String password = System.getenv("JDBCPassword");
        String publicIp = System.getenv("JDBCIP");
        String connectionUrl = "jdbc:mysql://" + publicIp + ":3306/jdbcdemo";
        try (Connection con = DriverManager.getConnection(connectionUrl, user, password)) {
            PreparedStatement ps = con.prepareStatement(sqlInsertName);
            ps.setString(1, entry);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // Add entry to cosmos DB
        final int id = Math.abs(new Random().nextInt());
        final String jsonDocument = "{\"id\":\"" + id + "\", " +
                                    "\"description\": \"" + entry + "\"}";
        context.getLogger().info("Document to be saved: " + jsonDocument);
        return jsonDocument;
    }
}
