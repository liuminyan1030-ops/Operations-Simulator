package validation;
import java.time.LocalDate;
import java.time.format.*;
import java.util.*;


public class LineValidator {
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	private static final String ALPHANUMERIC_REGEX = "^[a-zA-Z0-9]+$";

	public List<ValidationError> validate(List<String> lines) {
		List<ValidationError> validationErrors = new ArrayList<>();

		for (int i = 0; i < lines.size(); i++) {
			int lineNumber = i + 1;
			String line = lines.get(i).trim();

			if (line.isEmpty()) {
				continue;
			}

			String[] parts = line.split("\\|");
			for (int p = 0; p < parts.length; p++) {
				parts[p] = parts[p].trim();
			}

			String command = parts[0];

			switch (command) {
			case "START_DATE":
			case "END_DATE":
				validateDateLine(lineNumber, line, parts, validationErrors);
				break;
			case "VAR":
				validateVarLine(lineNumber, line, parts, validationErrors);
				break;
			case "STEP":
				validateStepLine(lineNumber, line, parts, validationErrors);
				break;
			default:
				validationErrors.add(new ValidationError(lineNumber, line, "Unknown command: " + command));
				break;
			}
		}

		return validationErrors;
	}

	private void validateDateLine(int lineNumber, String rawLine, String[] parts,
			List<ValidationError> validationErrors) {
		if (parts.length < 2 || parts[1].isEmpty()) {
			validationErrors.add(new ValidationError(lineNumber, rawLine, "Missing date value"));
			return;
		}

		try {
			LocalDate.parse(parts[1], DATE_FORMATTER);
		} catch (DateTimeParseException e) {
			validationErrors.add(new ValidationError(lineNumber, parts[1], "Invalid date format. Expected yyyy/MM/dd"));
		}
	}

	private void validateVarLine(int lineNumber, String rawLine, String[] parts,
			List<ValidationError> validationErrors) {

		if (parts.length < 2 || parts[1].isEmpty()) {
			validationErrors.add(
					new ValidationError(lineNumber, rawLine, "VAR line is missing variableName and variableValue"));
			return;
		}

		if (parts.length < 3 || parts[2].isEmpty()) {
			validationErrors.add(new ValidationError(lineNumber, rawLine, "VAR line is missing variableValue"));
			return;
		}

		String varName = parts[1];
		if (!varName.matches(ALPHANUMERIC_REGEX)) {
			validationErrors.add(new ValidationError(lineNumber, varName,
					"Variable name must not contain whitespace or non-alphanumeric characters"));
		}

		try {
			Integer.parseInt(parts[2]);
		} catch (NumberFormatException e) {
			validationErrors.add(new ValidationError(lineNumber, parts[2], "Variable value must be a valid integer"));
		}
	}

	private void validateStepLine(int lineNumber, String rawLine, String[] parts,
			List<ValidationError> validationErrors) {

		if (parts.length < 2 || parts[1].isEmpty()) {
			validationErrors.add(new ValidationError(lineNumber, rawLine,
					"STEP line is missing stepName, variableName, and variableValue"));
			return;
		}

		if (parts.length < 3 || parts[2].isEmpty()) {
			validationErrors.add(new ValidationError(lineNumber, rawLine,
					"STEP line is missing variable specific action (variableName: variableValue)"));
			return;
		}

		String stepName = parts[1];
		if (stepName.isEmpty()) {
			validationErrors.add(new ValidationError(lineNumber, rawLine, "Step name cannot be empty"));
		}

		String[] variableParts = parts[2].split(":");
		if (variableParts.length < 2) {
			validationErrors.add(new ValidationError(lineNumber, parts[2],
					"Variable specific action must be in format 'variableName: variableValue'"));
			return;
		}

		String targetVarName = variableParts[0].trim();
		if (!targetVarName.matches(ALPHANUMERIC_REGEX)) {
			validationErrors.add(new ValidationError(lineNumber, targetVarName,
					"Target variable name in step must not contain whitespace or non-alphanumeric characters"));
		}

		try {
			Integer.parseInt(variableParts[1].trim());
		} catch (NumberFormatException e) {
			validationErrors.add(
					new ValidationError(lineNumber, variableParts[1].trim(), "variableValue must be a valid integer"));
		}
	}
}