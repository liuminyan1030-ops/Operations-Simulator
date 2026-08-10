package parser;

import model.*;
import validation.*;
import handler.*;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

import constants.ConfigurationConstants;

public class ConfigurationParser {
	private final LineValidator lineValidator;
	private final List<ConfigurationLineHandler> handlers;

	public ConfigurationParser() {
		this.handlers = Arrays.asList(
				new StartDateConfigurationLineHandler(), 
				new EndDateConfigurationLineHandler(),
				new VarConfigurationLineHandler(), 
				new StepConfigurationLineHandler()
		);
		this.lineValidator = new LineValidator(this.handlers);
	}

	public Configuration parseFile(String filePath) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get(filePath));
		return parseLines(lines);
	}

	public List<ValidationError> validateLines(List<String> lines) {
		return lineValidator.validate(lines);
	}

	public Configuration parseLines(List<String> lines) {
		List<ValidationError> errors = validateLines(lines);
		if (!errors.isEmpty()) {
			StringBuilder sb = new StringBuilder("File validation failed:\n");
			for (ValidationError err : errors) {
				sb.append(err.toString()).append("\n");
			}
			throw new IllegalArgumentException(sb.toString());
		}

		LocalDate startDate = null;
		LocalDate endDate = null;
		List<VariableDefinition> variableDefinitions = new ArrayList<>();
		List<StepDefinition> stepDefinitions = new ArrayList<>();

		for (String line : lines) {
			String trimmedLine = line.trim();
			if (trimmedLine.isEmpty()) {
				continue;
			}
			for (ConfigurationLineHandler handler : handlers) {
				if (handler.validateConfigurationLine(trimmedLine)) {
					Object value = handler.getConfigurationValue(trimmedLine);
					String lineType = handler.getLineTypeHandled();
					
					switch (lineType) {
					case ConfigurationConstants.KEY_START_DATE:
						startDate = (LocalDate) value;
						break;
					case ConfigurationConstants.KEY_END_DATE:
						endDate = (LocalDate) value;
						break;
					case ConfigurationConstants.KEY_VAR:
						variableDefinitions.add((VariableDefinition) value);
						break;
					case ConfigurationConstants.KEY_STEP:
						stepDefinitions.add((StepDefinition) value);
						break;
					default:
						break;
					}

					break;
				}
			}
		}

		Scope scope = new Scope(startDate, endDate);
		return new Configuration(scope, variableDefinitions, stepDefinitions);
	}
}