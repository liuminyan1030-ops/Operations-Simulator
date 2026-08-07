package Test;

import handler.StepConfigurationLineHandler;
import model.StepDefinition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StepConfigurationLineHandlerTest {

	private StepConfigurationLineHandler handler;

	@BeforeEach
	void setUp() {
		handler = new StepConfigurationLineHandler();
	}

	@Test
	void testValidateConfigurationLine_ValidFormat() {
		assertTrue(handler.validateConfigurationLine("STEP | Order Nuts | Nuts: 50"));
		assertTrue(handler.validateConfigurationLine("STEP | Order Bolts | Bolts: 10"));
	}

	@Test
	void testValidateConfigurationLine_InvalidFormat() {

		assertFalse(handler.validateConfigurationLine("STEP | | Nuts: 50"));

		assertFalse(handler.validateConfigurationLine("STEP | Order Nuts | "));

		assertFalse(handler.validateConfigurationLine("STEP | Order Nuts | Nuts"));
		assertFalse(handler.validateConfigurationLine("STEP | Order Nuts | Nuts:"));

		assertFalse(handler.validateConfigurationLine("STEP | Order Nuts | Nuts: ABC"));

		assertFalse(handler.validateConfigurationLine("VAR | Order Nuts | Nuts: 50"));
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