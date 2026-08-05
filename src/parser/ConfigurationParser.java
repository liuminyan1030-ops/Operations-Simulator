package parser;

import model.*;
import validation.*;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ConfigurationParser {
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	private final LineValidator lineValidator;

	public ConfigurationParser() {
		this.lineValidator = new LineValidator();
	}

	public Configuration parseFile(String filePath) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get(filePath));
		return parseLines(lines);
	}

	public Configuration parseLines(List<String> lines) {

		List<ValidationError> errors = lineValidator.validate(lines);
		if (!errors.isEmpty()) {
			StringBuilder sb = new StringBuilder("File validation failed:\n");
			for (ValidationError err : errors) {
				sb.append(err.toString()).append("\n");
			}
			throw new IllegalArgumentException(sb.toString());
		}

		LocalDate startDate = null;
		LocalDate endDate = null;
		List<VariableDefinition> variableDefinition = new ArrayList<>();
		List<StepDefinition> stepDefinition = new ArrayList<>();

		for (String line : lines) {
			line = line.trim();
			if (line.isEmpty()) {
				continue;
			}

			String[] parts = line.split("\\|");
			for (int i = 0; i < parts.length; i++) {
				parts[i] = parts[i].trim();
			}

			String command = parts[0];

			switch (command) {
			case "START_DATE":
				startDate = LocalDate.parse(parts[1], DATE_FORMATTER);
				break;
			case "END_DATE":
				endDate = LocalDate.parse(parts[1], DATE_FORMATTER);
				break;
			case "VAR":
				String varName = parts[1];
				int initialValue = Integer.parseInt(parts[2]);
				variableDefinition.add(new VariableDefinition(varName, initialValue));
				break;
			case "STEP":
				String stepName = parts[1];
				String[] variableParts = parts[2].split(":");
				String targetVar = variableParts[0].trim();
				int value = Integer.parseInt(variableParts[1].trim());
				stepDefinition.add(new StepDefinition(stepName, targetVar, value));
				break;
			}
		}

		Scope scope = new Scope(startDate, endDate);

		return new Configuration(scope, variableDefinition, stepDefinition);

	}
}