package handler;

import constants.ConfigurationConstants;
import model.StepDefinition;

public class StepConfigurationLineHandler extends AbstractConfigurationLineHandler {
	public StepConfigurationLineHandler() {
		super(ConfigurationConstants.KEY_STEP);
	}

    @Override
    public boolean validateConfigurationLine(String line) {
        if (!hasValidPrefix(line)) return false;
       

        String[] parts = line.split("\\|");
        if (parts.length != 3) return false;

        String stepName = parts[1].trim();
        String variableStr = parts[2].trim();

        if (stepName.isEmpty()|| variableStr.isEmpty()) return false;
        String[] variableParts = variableStr.split(":");
        if (variableParts.length != 2) return false;

        String variableName = variableParts[0].trim();
        String variableModifyValue = variableParts[1].trim();

        if (variableName.isEmpty()||variableModifyValue.isEmpty()) return false;

        try {
            Integer.parseInt(variableModifyValue);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public StepDefinition getConfigurationValue(String line) {
        String[] parts = line.split("\\|");
        String stepName = parts[1].trim();
        String[] variableParts = parts[2].split(":");
        String variableName = variableParts[0].trim();
        int variableModifyValue = Integer.parseInt(variableParts[1].trim());

        return new StepDefinition(stepName, variableName, variableModifyValue);
    }
}