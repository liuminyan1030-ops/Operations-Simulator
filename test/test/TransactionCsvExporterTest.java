package test;

import exporter.TransactionCsvExporter;
import model.Configuration;
import model.Frequency;
import model.Scope;
import model.StepDefinition;
import model.Transaction;
import model.Unit;
import model.VariableChange;
import model.VariableDefinition;
import model.VariableValue;
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
            new VariableDefinition("Nuts", 100,Unit.QUANTITY),
            new VariableDefinition("Bolts", 200, Unit.QUANTITY),
            new VariableDefinition("Supply_Costs", 0, Unit.MONEY),
            new VariableDefinition("Revenue", 0, Unit.MONEY)
        );

        List<StepDefinition> stepDefinitions = Arrays.asList(
            new StepDefinition("Order nuts",Arrays.asList(new VariableChange("Nuts", 50),new VariableChange("Supply_Costs",100)),Frequency.MONTHSTART ),
            new StepDefinition("Order bolts", Arrays.asList(new VariableChange("Bolts", 25),new VariableChange("Supply_Costs",50)),Frequency.MONTHSTART),
            new StepDefinition("Sales of nuts", Arrays.asList(new VariableChange("Nuts", -75),new VariableChange("Revenue",200)),Frequency.MONTHEND),
            new StepDefinition("Sales of bolts", Arrays.asList(new VariableChange("Bolts", -50),new VariableChange("Revenue",400)),Frequency.MONTHEND)
        );

        mockConfiguration = new Configuration(scope, variableDefinitions, stepDefinitions);
    }

    @Test
    void testGenerateCsvContent_ValidTransactions_ReturnsFormattedCsv() {
    	LocalDate startDate = LocalDate.of(2026, 8, 1);
    	LocalDate monthEnd = LocalDate.of(2026, 8, 31);

        List<VariableValue> variableValues1 = Arrays.asList(
            new VariableValue("Nuts", 100),
            new VariableValue("Bolts", 200),
            new VariableValue("Supply_Costs", 0),
            new VariableValue("Revenue",0)
        );

        List<VariableValue> variableValues2 = Arrays.asList(
            new VariableValue("Nuts", 150),
            new VariableValue("Bolts", 200),
            new VariableValue("Supply_Costs", 100),
            new VariableValue("Revenue",0)
            
        );

        List<VariableValue> variableValues3 = Arrays.asList(
            new VariableValue("Nuts", 150),
            new VariableValue("Bolts", 225),
            new VariableValue("Supply_Costs", 150),
            new VariableValue("Revenue",0)
        );
        
        List<VariableValue> variableValues4 = Arrays.asList(
                new VariableValue("Nuts", 75),
                new VariableValue("Bolts", 225),
                new VariableValue("Supply_Costs", 150),
                new VariableValue("Revenue",200)
            );
        List<VariableValue> variableValues5 = Arrays.asList(
                new VariableValue("Nuts", 75),
                new VariableValue("Bolts", 175),
                new VariableValue("Supply_Costs", 150),
                new VariableValue("Revenue",600)
            );

        List<Transaction> transactions = Arrays.asList(
            new Transaction(startDate, "Start simulation", variableValues1),
            new Transaction(startDate, "Order nuts", variableValues2),
            new Transaction(startDate, "Order bolts", variableValues3),
            new Transaction(monthEnd, "Sales of nuts", variableValues4),
            new Transaction(monthEnd, "Sales of bolts", variableValues5)
        );

        String result = exporter.generateCsvContent(transactions, mockConfiguration);

        String expected =
        	    "Date, Description, Nuts (Quantity), Bolts (Quantity), Supply_Costs (Money), Revenue (Money)\n" +
        	    "2026/08/01, Start simulation, 100, 200, $0.00, $0.00\n" +
        	    "2026/08/01, Order nuts, 150, 200, $100.00, $0.00\n" +
        	    "2026/08/01, Order bolts, 150, 225, $150.00, $0.00\n" +
        	    "2026/08/31, Sales of nuts, 75, 225, $150.00, $200.00\n" +
        	    "2026/08/31, Sales of bolts, 75, 175, $150.00, $600.00\n";

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