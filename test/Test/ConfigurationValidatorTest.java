package Test;

import model.*;
import validation.ConfigurationValidator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigurationValidatorTest {

    private ConfigurationValidator cofigValidator;

    @BeforeEach
    void setUp() {
    	cofigValidator = new ConfigurationValidator();
    }

    @Test
    void testValidate_ValidConfiguration_NoErrors() {
        
        Scope scope = new Scope(LocalDate.of(2026, 8, 1), LocalDate.of(2026, 8, 31));
        
        List<VariableDefinition> variableDefinition = new ArrayList<>();
        variableDefinition.add(new VariableDefinition("Nuts", 100));

        List<StepDefinition> stepDefinition = new ArrayList<>();
        stepDefinition.add(new StepDefinition("Order Nuts", "Nuts", 50));

        Configuration config = new Configuration(scope, variableDefinition, stepDefinition);

       
        List<String> errors = cofigValidator.validate(config);

       
        assertTrue(errors.isEmpty(), "Valid configuration should not produce any error messages.");
    }

    @Test
    void testValidate_StartDateAfterEndDate_HasError() {
        
        Scope invalidScope = new Scope(LocalDate.of(2026, 8, 31), LocalDate.of(2026, 8, 1));
        Configuration config = new Configuration(invalidScope, new ArrayList<>(), new ArrayList<>());

        List<String> errors = cofigValidator.validate(config);

        assertFalse(errors.isEmpty(), "Should catch an exception for an invalid date order.");
        assertTrue(errors.get(0).contains("must be before END_DATE"));
    }

    @Test
    void testValidate_DuplicateVariables_HasError() {
        Scope scope = new Scope(LocalDate.of(2026, 8, 1), LocalDate.of(2026, 8, 31));
        
        
        List<VariableDefinition> variableDefinition = new ArrayList<>();
        variableDefinition.add(new VariableDefinition("Nuts", 100));
        variableDefinition.add(new VariableDefinition("Nuts", 200));

        Configuration config = new Configuration(scope, variableDefinition, new ArrayList<>());

        List<String> errors = cofigValidator.validate(config);

        assertFalse(errors.isEmpty(), "Should catch an exception for duplicate variable names.");
        assertTrue(errors.get(0).contains("Duplicate variable definition found"));
    }

    @Test
    void testValidate_UndefinedVariableInStep_HasError() {
        Scope scope = new Scope(LocalDate.of(2026, 8, 1), LocalDate.of(2026, 8, 31));
        
       
        List<VariableDefinition> variableDefinition = new ArrayList<>();
        variableDefinition.add(new VariableDefinition("Nuts", 100));

        
        List<StepDefinition> stepDefinition = new ArrayList<>();
        stepDefinition.add(new StepDefinition("Order Bolts", "Bolts", 50));

        Configuration config = new Configuration(scope, variableDefinition, stepDefinition);

        List<String> errors = cofigValidator.validate(config);

        assertFalse(errors.isEmpty(), "Should throw an exception when a step references an undefined variable.");
        assertTrue(errors.get(0).contains("references an undefined variable"));
    }
}