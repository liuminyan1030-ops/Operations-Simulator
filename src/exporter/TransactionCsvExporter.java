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
	private final ValueFormatter valueFormatter;
	
	public TransactionCsvExporter(ValueFormatter valueFormatter) {
		this.valueFormatter=valueFormatter;
	}
	
	public TransactionCsvExporter() {
	    this(new ValueFormatter());
	}

    public String generateCsvContent(List<Transaction> transactions, Configuration configuration) {
        if (transactions == null || transactions.isEmpty()) {
            return "";
        }

        List<VariableDefinition> varDefinitions = new ArrayList<>();
        if (configuration != null && configuration.getVariableDefinitions() != null) {
           varDefinitions=configuration.getVariableDefinitions();
        }
      

        StringBuilder sb = new StringBuilder();
        sb.append("Date, Description");
        for (VariableDefinition varDefinition:varDefinitions) {
            sb.append(", ");
            Unit unit = varDefinition.getUnit();
            if (unit != null) {
            	String unitStr=unit.toString();
            	if(!unitStr.trim().isEmpty()) {
            		sb.append(varDefinition.getName()).append(" (").append(unitStr.trim()).append(")");
            	} else {
                    sb.append(varDefinition.getName());
                }               
            }else {
                sb.append(varDefinition.getName());
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

            for (VariableDefinition varDefinition : varDefinitions) {
                sb.append(", ");
                Unit unit = varDefinition.getUnit();
                try {
                    int value = transaction.getVariableValue(varDefinition.getName());
                    sb.append(valueFormatter.formatValueByUnit(value, unit));
                } catch (IllegalArgumentException e) {
                    sb.append(valueFormatter.formatValueByUnit(0, unit));
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }


 
    public void exportToFile(List<Transaction> transactions, Configuration configuration, String filePath) throws IOException {
        String csvContent = generateCsvContent(transactions, configuration);
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.print(csvContent);
        }
    }
}