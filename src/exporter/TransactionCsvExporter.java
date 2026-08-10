package exporter;

import model.Transaction;
import model.VariableValue;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class TransactionCsvExporter {

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

	public String generateCsvContent(List<Transaction> transactions) {
		if (transactions == null || transactions.isEmpty()) {
			return "";
		}

		Set<String> variableNames = new LinkedHashSet<>();
		for (Transaction transaction : transactions) {
			if (transaction.getVariableValues() != null) {
				for (VariableValue varValue : transaction.getVariableValues()) {
					variableNames.add(varValue.getName());
				}
			}
		}

		List<String> sortedVarNames = new ArrayList<>(variableNames);
		Collections.sort(sortedVarNames);
		StringBuilder sb = new StringBuilder();
		sb.append("Date, Description");
		for (String varName : sortedVarNames) {
			sb.append(", ").append(varName);
		}
		sb.append("\n");

		for (Transaction transaction : transactions) {
			String formattedDate = "";

			if (transaction.getDate() != null) {
				formattedDate = transaction.getDate().format(DATE_FORMATTER);
			}

			sb.append(formattedDate).append(", ");
			sb.append(transaction.getDescription());

			List<VariableValue> currentVars = transaction.getVariableValues();
			for (String varName : sortedVarNames) {
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