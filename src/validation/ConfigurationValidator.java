package validation;

import model.*;
import java.util.*;



public class ConfigurationValidator {

	public List<String> validate(Configuration config) {
		List<String> errors = new ArrayList<>();

		if (config == null) {
			errors.add("Configuration cannot be null.");
			return errors;
		}

		Scope scope = config.getScope();
		if (scope != null && scope.getStartDate() != null && scope.getEndDate() != null) {
			if (!scope.getStartDate().isBefore(scope.getEndDate())) {
				errors.add("START_DATE (" + scope.getStartDate() + ") must be before END_DATE (" + scope.getEndDate()
						+ ").");
			}
		} else {
			errors.add("Both START_DATE and END_DATE must be specified.");
		}

		List<String> declaredVariables = new ArrayList<>();

		if (config.getVariableDefinitions() != null) {
			for (VariableDefinition variableDefinition : config.getVariableDefinitions()) {
				String variableName = variableDefinition.getName();

				if (declaredVariables.contains(variableName)) {
					errors.add("Duplicate variable definition found: '" + variableName + "'.");
				} else {

					declaredVariables.add(variableName);
				}
			}
		}

		if (config.getStepDefinitions() != null) {
			for (StepDefinition stepDefinition : config.getStepDefinitions()) {
				String targetVariable = stepDefinition.getVariableName();

				if (targetVariable != null && !declaredVariables.contains(targetVariable)) {
					errors.add("Step '" + stepDefinition.getName() + "' references an undefined variable: '"
							+ targetVariable + "'.");
				}
			}
		}

		return errors;
	}
}