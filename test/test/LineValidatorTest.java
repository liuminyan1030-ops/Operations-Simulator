package test;

import handler.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import validation.LineValidator;
import validation.ValidationError;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LineValidatorTest {

    private LineValidator lineValidator;

    @BeforeEach
    void setUp() {
       
        List<ConfigurationLineHandler> handlers = Arrays.asList(
            new StartDateConfigurationLineHandler(),
            new EndDateConfigurationLineHandler(),
            new VarConfigurationLineHandler(),
            new StepConfigurationLineHandler()
        );
        lineValidator = new LineValidator(handlers);
    }

    @Test
    void testValidate_ValidLines_ReturnsNoErrors() {
        List<String> validLines = Arrays.asList(
            "START_DATE | 2026/08/01",
            "END_DATE | 2026/11/01",
            "VAR | Nuts | 100 | Quantity",
            "VAR | Supply_Costs | 100 | Money",
            "STEP | Order Nuts | Nuts: 50, Supply_Costs: 100 | MONTHSTART"
        );

        List<ValidationError> errors = lineValidator.validate(validLines);
        assertTrue(errors.isEmpty(), "Valid lines should produce zero validation errors");
    }

    @Test
    void testValidate_InvalidLines_ReturnsErrorsList() {
        List<String> invalidLines = Arrays.asList(
        		 "START_DATE | ",
                 "END_DATE | abc",
                 "VAR | Nuts | ",
                 "STEP | Order Nuts | Nuts:50,Supply_Costs: 100| abc "
                 
        );

        List<ValidationError> errors = lineValidator.validate(invalidLines);
        assertFalse(errors.isEmpty(), "Invalid lines should produce validation errors");
        assertEquals(4, errors.size(), "Should produce one error for each configuration line handler");
    }
}