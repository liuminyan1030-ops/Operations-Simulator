package handler;

import java.util.ArrayList;
import java.util.List;

import constants.ConfigurationConstants;
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
		if (parts.length != 3)
			return false;

		String stepName = parts[1].trim();
		String variableStr = parts[2].trim();

		if (stepName.isEmpty() || variableStr.isEmpty())
			return false;
		String[] variableParts = variableStr.split(",");
		for (String varPart : variableParts) {
			String[] varKeyValue = varPart.split(":");
			if (varKeyValue.length != 2)
				return false;
			String varName = varKeyValue[0].trim();
			String varValue = varKeyValue[1].trim();
			if (varName.isEmpty() || varValue.isEmpty())
				return false;
			try {
				Integer.parseInt(varValue);

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
		String[] variableParts = parts[2].trim().split(",");
		List<VariableChange> varChanges = new ArrayList<>();
		for (String variablePart : variableParts) {
			String[] variableKeyValue = variablePart.split(":");
			String varName = variableKeyValue[0].trim();
			String varValue = variableKeyValue[1].trim();
			int variableModifyValue = Integer.parseInt(varValue);
			varChanges.add(new VariableChange(varName, variableModifyValue));

		}
		return new StepDefinition(stepName, varChanges);

	}
}