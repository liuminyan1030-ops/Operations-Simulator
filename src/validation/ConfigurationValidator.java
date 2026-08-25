package validation;

import model.*;
import java.util.*;

import constants.ConfigurationConstants;

public class ConfigurationValidator {

	public List<String> validate(Configuration config) {
		List<String> errors = new ArrayList<>();

		if (config == null) {
			errors.add("Configuration cannot be null.");
			return errors;
		}

		Scope scope = config.getScope();
		if (scope == null || scope.getStartDate() == null || scope.getEndDate() == null) {
			errors.add("Both " + ConfigurationConstants.KEY_START_DATE + "  and  " + ConfigurationConstants.KEY_END_DATE
					+ " must be specified");
		} else if (!scope.getStartDate().isBefore(scope.getEndDate())) {
			errors.add(ConfigurationConstants.KEY_START_DATE + " (" + config.getScope().getStartDate()
					+ ") must be before " + ConfigurationConstants.KEY_END_DATE + " (" + config.getScope().getEndDate()
					+ ").");
		}

		Set<String> declaredVariablesName = new HashSet<>();

		if (config.getVariableDefinitions() != null) {
			for (VariableDefinition variableDefinition : config.getVariableDefinitions()) {
				String variableName = variableDefinition.getName();

				if (declaredVariablesName.contains(variableName)) {
					errors.add(ConfigurationConstants.KEY_VAR + ":" + "Duplicate variable definition found: '"
							+ variableName + "'.");
				} else {

					declaredVariablesName.add(variableName);
				}
			}
		}

		if (config.getStepDefinitions() != null) {
			for (StepDefinition stepDefinition : config.getStepDefinitions()) {
				List<VariableChange> varChanges = stepDefinition.getVariableChanges();
				for (VariableChange varChange : varChanges) {
					String varName = varChange.getVariableName();
					if (varName != null && !declaredVariablesName.contains(varName)) {
						errors.add(ConfigurationConstants.KEY_STEP + ":" + stepDefinition.getName()
								+ " references an undefined variable: " + varName + ".");
					}
				}

			}
		}

		return errors;
	}
}