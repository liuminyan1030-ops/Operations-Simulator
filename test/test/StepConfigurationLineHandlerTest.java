package test;

import handler.StepConfigurationLineHandler;
import model.StepDefinition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class StepConfigurationLineHandlerTest {

	private StepConfigurationLineHandler handler;

	@BeforeEach
	void setUp() {
		handler = new StepConfigurationLineHandler();
	}

	@ParameterizedTest
    @CsvSource({
        "'STEP | Order Nuts | Nuts: 50', 'Valid step line with Nuts variable'",
        "'STEP | Order Bolts | Bolts: 10', 'Valid step line with Bolts variable'"
    })
    void testValidateConfigurationLine_ValidFormat(String line, String description) {
        assertTrue(handler.validateConfigurationLine(line), "Successful scenario: " + description);
    }

   
    @ParameterizedTest
    @CsvSource({
        "'STEP | | Nuts: 50', 'Missing step name'",
        "'STEP | Order Nuts | ', 'Missing variable definition parts'",
        "'STEP | Order Nuts | Nuts', 'Missing colon and amount in variable mapping'",
        "'STEP | Order Nuts | Nuts:', 'Missing amount after colon'",
        "'STEP | Order Nuts | Nuts: ABC', 'Non-numeric amount value'",
        "'VAR | Order Nuts | Nuts: 50', 'Incorrect prefix for Step handler'"
    })
    void testValidateConfigurationLine_InvalidFormat(String line, String problem) {
        assertFalse(handler.validateConfigurationLine(line), "Failed scenario: " + problem);
    }
	@Test
	void testGetConfigurationValue_Success() {
		Object value = handler.getConfigurationValue("STEP | Order Nuts | Nuts: 50");

		assertTrue(value instanceof StepDefinition);

		StepDefinition stepDefinition = (StepDefinition) value;
		assertEquals("Order Nuts", stepDefinition.getName(), "the step name should parse Order Nuts");
		assertEquals("Nuts", stepDefinition.getVariableName(), "the variable name should parse Nuts");
		assertEquals(50, stepDefinition.getModifyBy(), "the modify value should be 50");
	}
}