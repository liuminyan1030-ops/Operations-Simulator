package test;

import exporter.TransactionCsvExporter;
import model.Configuration;
import model.Scope;
import model.StepDefinition;
import model.Transaction;
import model.VariableDefinition;
import model.VariableValue;
import constants.ConfigurationConstants;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionCsvExporterTest {

    private TransactionCsvExporter exporter;
    private Configuration mockConfiguration;

    @BeforeEach
    void setUp() {
        exporter = new TransactionCsvExporter();

        Scope scope = new Scope(LocalDate.of(2026, 8, 1), LocalDate.of(2026, 11, 1));

        List<VariableDefinition> variableDefinitions = Arrays.asList(
            new VariableDefinition("Nuts", 100, ConfigurationConstants.UNIT_QUANTITY),
            new VariableDefinition("Bolts", 200, ConfigurationConstants.UNIT_QUANTITY),
            new VariableDefinition("Supply_Costs", 0, ConfigurationConstants.UNIT_MONEY)
        );

        List<StepDefinition> stepDefinitions = Arrays.asList(
            new StepDefinition("Order nuts", "Nuts", 50),
            new StepDefinition("Order bolts", "Bolts", 25)
        );

        mockConfiguration = new Configuration(scope, variableDefinitions, stepDefinitions);
    }

    @Test
    void testGenerateCsvContent_ValidTransactions_ReturnsFormattedCsv() {
        LocalDate date = LocalDate.of(2026, 8, 1);

        List<VariableValue> variableValues1 = Arrays.asList(
            new VariableValue("Nuts", 100),
            new VariableValue("Bolts", 200),
            new VariableValue("Supply_Costs", 0)
        );

        List<VariableValue> variableValues2 = Arrays.asList(
            new VariableValue("Nuts", 150),
            new VariableValue("Bolts", 200),
            new VariableValue("Supply_Costs", 0)
        );

        List<VariableValue> variableValues3 = Arrays.asList(
            new VariableValue("Nuts", 150),
            new VariableValue("Bolts", 225),
            new VariableValue("Supply_Costs", 0)
        );

        List<Transaction> transactions = Arrays.asList(
            new Transaction(date, "Start simulation", variableValues1),
            new Transaction(date, "Order nuts", variableValues2),
            new Transaction(date, "Order bolts", variableValues3)
        );

        String result = exporter.generateCsvContent(transactions, mockConfiguration);

        String expected = 
        	    "Date, Description, Nuts (Quantity), Bolts (Quantity), Supply_Costs (Money)\n" +
        	    "2026/08/01, Start simulation, 100, 200, $0.00\n" +
        	    "2026/08/01, Order nuts, 150, 200, $0.00\n" +
        	    "2026/08/01, Order bolts, 150, 225, $0.00\n";

        assertEquals(expected, result, "Generated CSV string should match expected rows format with units.");
    }

    @Test
    void testGenerateCsvContent_EmptyList_ReturnsEmptyString() {
        String result = exporter.generateCsvContent(Collections.emptyList(), mockConfiguration);
        assertEquals("", result, "Empty transactions list should return an empty string.");
    }

    @Test
    void testGenerateCsvContent_NullList_ReturnsEmptyString() {
        String result = exporter.generateCsvContent(null, mockConfiguration);
        assertEquals("", result, "Null input should return an empty string.");
    }
}