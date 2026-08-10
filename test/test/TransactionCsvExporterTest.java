package test;

import exporter.TransactionCsvExporter;
import model.Transaction;
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

    @BeforeEach
    void setUp() {
        exporter = new TransactionCsvExporter();
    }

    @Test
    void testGenerateCsvContent_ValidTransactions_ReturnsFormattedCsv() {
       
        LocalDate date = LocalDate.of(2026, 8, 1);

   
        List<VariableValue> variableValues1 = Arrays.asList(
            new VariableValue("Nuts", 100),
            new VariableValue("Bolts", 200)
        );

        List<VariableValue> variableValues2 = Arrays.asList(
            new VariableValue("Nuts", 150),
            new VariableValue("Bolts", 200)
        );

        List<VariableValue> variableValues3 = Arrays.asList(
            new VariableValue("Nuts", 150),
            new VariableValue("Bolts", 225)
        );

        List<Transaction> transactions = Arrays.asList(
            new Transaction(date, "Start simulation", variableValues1),
            new Transaction(date, "Order nuts", variableValues2),
            new Transaction(date, "Order bolts", variableValues3)
        );

    
        String result = exporter.generateCsvContent(transactions);

        String expected = 
        	    "Date, Description, Bolts, Nuts\n" +
        	    "2026/08/01, Start simulation, 200, 100\n" +
        	    "2026/08/01, Order nuts, 200, 150\n" +
        	    "2026/08/01, Order bolts, 225, 150\n";

        assertEquals(expected, result, "Generated CSV string should match expected rows format.");
    }

    @Test
    void testGenerateCsvContent_EmptyList_ReturnsEmptyString() {
        String result = exporter.generateCsvContent(Collections.emptyList());
        assertEquals("", result, "Empty transactions list should return an empty string.");
    }

    @Test
    void testGenerateCsvContent_NullList_ReturnsEmptyString() {
        String result = exporter.generateCsvContent(null);
        assertEquals("", result, "Null input should return an empty string.");
    }
}