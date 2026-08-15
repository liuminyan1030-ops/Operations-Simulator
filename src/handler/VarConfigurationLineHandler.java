package handler;
import model.Unit;
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
		if (parts.length != 4)
			return false;

		String varName = parts[1].trim();
		String varValue = parts[2].trim();
		String varUnit=parts[3].trim();

		if (varName.isEmpty() || varValue.isEmpty()||varUnit.isEmpty())
			return false;

		try {
		    Integer.parseInt(varValue);
		    Unit.valueOf(varUnit.toUpperCase());
		    return true; 
		} catch (IllegalArgumentException e) {
		    return false;
		}
	}

	@Override
	public VariableDefinition getConfigurationValue(String line) {
		String[] parts = line.split("\\|");
		String varName = parts[1].trim();
		int initialValue = Integer.parseInt(parts[2].trim());
		String varUnit=parts[3].trim();
		Unit unit;
		try {
			unit = Unit.valueOf(varUnit.toUpperCase());
		} catch (IllegalArgumentException e) {
			unit = Unit.NONE;
		}

		return new VariableDefinition(varName, initialValue, unit);
	}
}