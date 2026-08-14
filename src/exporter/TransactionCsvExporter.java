package exporter;

import constants.ConfigurationConstants;
import model.Configuration;
import model.Transaction;
import model.VariableDefinition;
import model.VariableValue;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import java.util.List;

public class TransactionCsvExporter {

    public String generateCsvContent(List<Transaction> transactions, Configuration configuration) {
        if (transactions == null || transactions.isEmpty()) {
            return "";
        }

        List<String> rawVariableNames = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getVariableValues() != null) {
                for (VariableValue varValue : transaction.getVariableValues()) {
                    String name = varValue.getName();
                    if (!rawVariableNames.contains(name)) {
                        rawVariableNames.add(name);
                    }
                }
            }
        }
      

        StringBuilder sb = new StringBuilder();
        sb.append("Date, Description");
        for (String varName : rawVariableNames) {
            sb.append(", ");
            String unit = getUnitForVariable(configuration, varName);
            if (unit != null && !unit.trim().isEmpty()) {
                sb.append(varName).append(" (").append(unit.trim()).append(")");
            } else {
                sb.append(varName);
            }
        }
        sb.append("\n");

        for (Transaction transaction : transactions) {
            String formattedDate = "";
            if (transaction.getDate() != null) {
                formattedDate = transaction.getDate().format(ConfigurationConstants.DATE_FORMATTER);
            }

            sb.append(formattedDate).append(", ");
            sb.append(transaction.getDescription());

            for (String varName : rawVariableNames) {
                sb.append(", ");
                String unit = getUnitForVariable(configuration, varName);
                try {
                    int value = transaction.getVariableValue(varName);
                    sb.append(formatValueByUnit(value, unit));
                } catch (IllegalArgumentException e) {
                    sb.append(formatValueByUnit(0, unit));
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    private String getUnitForVariable(Configuration configuration, String varName) {
        if (configuration != null && configuration.getVariableDefinitions() != null) {
            for (VariableDefinition varDefinition : configuration.getVariableDefinitions()) {
                if (varDefinition.getName().equals(varName)) {
                    return varDefinition.getUnit();
                }
            }
        }
        return null;
    }

    private String formatValueByUnit(int value, String unit) {
        if (ConfigurationConstants.UNIT_MONEY.equalsIgnoreCase(unit)) {
            return String.format("$%.2f", (double) value);
        }
        return String.valueOf(value);
    }

 
    public void exportToFile(List<Transaction> transactions, Configuration configuration, String filePath) throws IOException {
        String csvContent = generateCsvContent(transactions, configuration);
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.print(csvContent);
        }
    }
}