package exporter;

import model.Transaction;
import model.VariableValue;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Collections;

import java.util.List;


import constants.ConfigurationConstants;

public class TransactionCsvExporter {


	public String generateCsvContent(List<Transaction> transactions) {
		if (transactions == null || transactions.isEmpty()) {
			return "";
		}

		List<String> variableNames = new ArrayList<>();
		for (Transaction transaction : transactions) {
			if (transaction.getVariableValues() != null) {
				for (VariableValue varValue : transaction.getVariableValues()) {
					String name=varValue.getName();
					if(!variableNames.contains(name)) {
						variableNames.add(name);
					}
					
				}
			}
		}
		Collections.sort(variableNames);
		StringBuilder sb = new StringBuilder();
		sb.append("Date, Description");
		for (String varName : variableNames) {
			sb.append(", ").append(varName);
		}
		sb.append("\n");

		for (Transaction transaction : transactions) {
			String formattedDate = "";

			if (transaction.getDate() != null) {
				formattedDate = transaction.getDate().format(ConfigurationConstants.DATE_FORMATTER);
			}

			sb.append(formattedDate).append(", ");
			sb.append(transaction.getDescription());

			List<VariableValue> currentVars = transaction.getVariableValues();
			for (String varName : variableNames) {
				sb.append(", ");
				int value = findVariableValue(currentVars, varName);
				sb.append(value);
			}
			sb.append("\n");
		}

		return sb.toString();
	}

	private int findVariableValue(List<VariableValue> variableValues, String varName) {
		if (variableValues != null) {
			for (VariableValue varValue : variableValues) {
				if (varName.equalsIgnoreCase(varValue.getName())) {
					return varValue.getValue();
				}
			}
		}
		return 0;
	}

	public void exportToFile(List<Transaction> transactions, String filePath) throws IOException {
		String csvContent = generateCsvContent(transactions);
		try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
			writer.print(csvContent);
		}
	}
}