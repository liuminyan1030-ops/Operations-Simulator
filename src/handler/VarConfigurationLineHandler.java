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
        Unit unit;
		try {		  
		    unit=Unit.valueOf(varUnit.toUpperCase());
		} catch (IllegalArgumentException e) {
		    return false;
		}
		if(unit==Unit.MONEY) {
			return varValue.matches("^-?\\d+\\.\\d{2}$");
			}else if (unit==Unit.QUANTITY) {
				return varValue.matches("^-?\\d+$");}
		return true;
	}

	@Override
	public VariableDefinition getConfigurationValue(String line) {
		String[] parts = line.split("\\|");
		String varName = parts[1].trim();
		String varValue = parts[2].trim();
		String varUnit=parts[3].trim();
		Unit unit;
		try {
			unit = Unit.valueOf(varUnit.toUpperCase());
		} catch (IllegalArgumentException e) {
			unit = Unit.NONE;
		}
		int initiaValue;
		if(unit==Unit.MONEY) {
			initiaValue=(int)Math.round(Double.parseDouble(varValue));
		}else {
			initiaValue=Integer.parseInt(varValue);
		}

		return new VariableDefinition(varName, initiaValue, unit);
	}
}