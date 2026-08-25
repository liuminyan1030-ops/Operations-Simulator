package test;

import model.*;
import validation.ConfigurationValidator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigurationValidatorTest {

	private ConfigurationValidator configValidator;
	private Configuration validConfig;

	@BeforeEach
	void setUp() {
		configValidator = new ConfigurationValidator();
		validConfig = createValidConfiguration();
	}

	private Configuration createValidConfiguration() {
		Scope scope = new Scope(LocalDate.of(2026, 8, 1), LocalDate.of(2026, 11, 1));

		List<VariableDefinition> variablesDefinition = new ArrayList<>();
		variablesDefinition.add(new VariableDefinition("Nuts", 100, Unit.QUANTITY));
		variablesDefinition.add(new VariableDefinition("Supply_Costs", 0, Unit.MONEY));

		List<StepDefinition> stepDefinition = new ArrayList<>();
		stepDefinition.add(new StepDefinition("Order Nuts", Arrays.asList(
				new VariableChange("Nuts", 50),
				new VariableChange("Supply_Costs", 100))));

		return new Configuration(scope, variablesDefinition, stepDefinition);
	}

	@Test
	void testValidate_ValidConfiguration_NoErrors() {

		List<String> errors = configValidator.validate(validConfig);

		assertTrue(errors.isEmpty(), "Valid configuration should not produce any error messages.");
	}

	@Test
	void testValidate_StartDateAfterEndDate_HasError() {

		Scope invalidScope = new Scope(LocalDate.of(2026, 11, 1), LocalDate.of(2026, 8, 1));
		Configuration invalidConfig = new Configuration(invalidScope, validConfig.getVariableDefinitions(),
				validConfig.getStepDefinitions());

		List<String> errors = configValidator.validate(invalidConfig);

		assertFalse(errors.isEmpty(), "Should catch an exception for an invalid date order.");
		assertTrue(errors.get(0).contains("must be before END_DATE"));
	}

	@Test
	void testValidate_DuplicateVariables_HasError() {
		validConfig.getVariableDefinitions().add(new VariableDefinition("Nuts", 200,Unit.QUANTITY));

		List<String> errors = configValidator.validate(validConfig);

		assertFalse(errors.isEmpty(), "Should catch an exception for duplicate variable names.");
		assertTrue(errors.get(0).contains("Duplicate variable definition found"));
	}

	@Test
	void testValidate_UndefinedVariableInStep_HasError() {

		validConfig.getStepDefinitions().add(new StepDefinition("Order Bolts", Arrays.asList(new VariableChange("Bolts", 50))));

		List<String> errors = configValidator.validate(validConfig);

		assertFalse(errors.isEmpty(), "Should throw an exception when a step references an undefined variable.");
		assertTrue(errors.get(0).contains("references an undefined variable"));
	}
}