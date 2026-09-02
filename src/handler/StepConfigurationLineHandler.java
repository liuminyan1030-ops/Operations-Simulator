package handler;

import java.util.ArrayList;
import java.util.List;

import constants.ConfigurationConstants;
import model.Frequency;
import model.StepDefinition;
import model.VariableChange;

public class StepConfigurationLineHandler extends AbstractConfigurationLineHandler {
	public StepConfigurationLineHandler() {
		super(ConfigurationConstants.KEY_STEP);
	}

	@Override
	public boolean validateConfigurationLine(String line) {
		if (!hasValidPrefix(line))
			return false;

		String[] parts = line.split("\\|");
		if (parts.length != 4)
			return false;

		String stepName = parts[1].trim();
		String variableStr = parts[2].trim();
		String frequencyStr=parts[3].trim();

		if (stepName.isEmpty() || variableStr.isEmpty()||frequencyStr.isEmpty())
			return false;
		try {
			Frequency.valueOf(frequencyStr.toUpperCase());
		}catch(IllegalArgumentException e) {
			return false;
		}
		String[] variableChangeParts = variableStr.split(",");
		for (String variableChangePart : variableChangeParts) {
			String[] variableChangeKeyValue = variableChangePart.split(":");
			if (variableChangeKeyValue.length != 2)
				return false;
			String variableChangeVariableName = variableChangeKeyValue[0].trim();
			String variableChangeModifyBy = variableChangeKeyValue[1].trim();
			if (variableChangeVariableName.isEmpty() || variableChangeModifyBy.isEmpty())
				return false;
			try {
				Integer.parseInt(variableChangeModifyBy);

			} catch (NumberFormatException e) {
				return false;
			}
		}
		return true;
	}

	@Override
	public StepDefinition getConfigurationValue(String line) {
		String[] parts = line.split("\\|");
		String stepName = parts[1].trim();
		String[] variableChangeParts = parts[2].trim().split(",");
		String frequencyStr=parts[3].trim();
		List<VariableChange> varChanges = new ArrayList<>();
		for (String variableChangePart : variableChangeParts) {
			String[] variableChangeKeyValue = variableChangePart.split(":");
			String variableChangeVariableName = variableChangeKeyValue[0].trim();
			String variableChangeVariableValue = variableChangeKeyValue[1].trim();
			int variableModifyValue = Integer.parseInt(variableChangeVariableValue);
			varChanges.add(new VariableChange(variableChangeVariableName, variableModifyValue));

		}
		return new StepDefinition(stepName, varChanges,Frequency.valueOf(frequencyStr.toUpperCase()));

	}
}