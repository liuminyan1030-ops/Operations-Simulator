package handler;

import constants.ConfigurationConstants;
import model.VariableDefinition;

public class VarConfigurationLineHandler extends AbstractConfigurationLineHandler {
	public VarConfigurationLineHandler() {
		super(ConfigurationConstants.KEY_VAR);
	}

	@Override
	public boolean validateConfigurationLine(String line) {
		if (!hasValidPrefix(line))
			return false;
		
		String[] parts = line.split("\\|");
		if (parts.length != 3)
			return false;

		String varName = parts[1].trim();
		String varValue = parts[2].trim();

		if (varName.isEmpty() || varValue.isEmpty())
			return false;

		try {
			Integer.parseInt(varValue);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@Override
	public VariableDefinition getConfigurationValue(String line) {
		String[] parts = line.split("\\|");
		String varName = parts[1].trim();
		int initialValue = Integer.parseInt(parts[2].trim());
		return new VariableDefinition(varName, initialValue);
	}
}