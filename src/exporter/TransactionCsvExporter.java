package exporter;

import constants.ConfigurationConstants;
import model.Configuration;
import model.Transaction;
import model.Unit;
import model.VariableDefinition;
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
        if (configuration != null && configuration.getVariableDefinitions() != null) {
            for (VariableDefinition varDefinition : configuration.getVariableDefinitions()) {
                rawVariableNames.add(varDefinition.getName());
            }
        }
      

        StringBuilder sb = new StringBuilder();
        sb.append("Date, Description");
        for (String varName : rawVariableNames) {
            sb.append(", ");
            Unit unit = getUnitForVariable(configuration, varName);
            if (unit != null) {
            	String unitStr=unit.toString();
            	if(!unitStr.trim().isEmpty()) {
            		sb.append(varName).append(" (").append(unitStr.trim()).append(")");
            	} else {
                    sb.append(varName);
                }               
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
                Unit unit = getUnitForVariable(configuration, varName);
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

    private Unit getUnitForVariable(Configuration configuration, String varName) {
        if (configuration != null && configuration.getVariableDefinitions() != null) {
            for (VariableDefinition varDefinition : configuration.getVariableDefinitions()) {
                if (varDefinition.getName().equals(varName)) {
                    return varDefinition.getUnit();
                }
            }
        }
        return Unit.NONE;
    }

    private String formatValueByUnit(int value, Unit unit) {
    	if (unit == Unit.MONEY) {
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