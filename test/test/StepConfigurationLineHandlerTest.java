package test;

import handler.StepConfigurationLineHandler;
import model.Frequency;
import model.StepDefinition;
import model.VariableChange;
import java.util.List;
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
	    "'STEP | Order Nuts | Nuts: 50 | MONTHSTART', 'one variable change plus frequency'",
	    "'STEP | Order Nuts | Nuts: 50, Supply_Costs: 100 | MONTHSTART', 'two variable changes plus frequency'",
	    "'STEP | Sales of Nuts | Nuts: -75 | MONTHEND', 'MONTHEND with negative change'",
	    "'STEP | Weekly check | Nuts: 1 | WEEKLY', 'Frequency is WEEKLY'",
	    "'STEP | Daily check | Nuts: 1 | DAILY', 'Frequency is DAILY'"
	})
	void testValidateConfigurationLine_ValidFormat(String line, String description) {
		assertTrue(handler.validateConfigurationLine(line), "Successful scenario: " + description);
	}

	@ParameterizedTest
	@CsvSource({
		"'STEP | | Nuts: 50 | MONTHSTART', 'Missing step name'",
		"'STEP | Order Nuts |  | MONTHSTART', 'Missing variable definition parts'",
		"'STEP | Order Nuts | Nuts | MONTHSTART', 'Missing colon and amount in variable mapping'",
		"'STEP | Order Nuts | Nuts: | MONTHSTART', 'Missing amount after colon'",
		"'STEP | Order Nuts | Nuts: ABC | MONTHSTART', 'Non-numeric amount value'",
		"'VAR | Order Nuts | Nuts: 50 | MONTHSTART', 'Incorrect prefix for Step handler'",
		"'STEP | Order Nuts | Nuts: 50, Supply_Costs: ABC | MONTHSTART', 'Non-numeric amount in second variable change'",
		"'STEP | Order Nuts | Nuts: 50, Supply_Costs: | MONTHSTART', 'Missing amount in second variable change'",
		"'STEP | Order Nuts | Nuts: 50, : 100 | MONTHSTART', 'Missing variable name in second variable change'",
		"'STEP | Order Nuts | Nuts: 50, Supply_Costs | MONTHSTART', 'Missing colon and variable value in second variable change'",
		"'STEP | Order Nuts | Nuts: 50, Supply_Costs: 100 | ', 'Missing step frequency'",
		"'STEP | Order Nuts | Nuts: 50, Supply_Costs: 100 | 123', 'invalid frequency'"
})
	void testValidateConfigurationLine_InvalidFormat(String line, String problem) {
		assertFalse(handler.validateConfigurationLine(line), "Failed scenario: " + problem);
	}

	@Test
	void testGetConfigurationValue_Success() {
		Object value = handler.getConfigurationValue("STEP | Order Nuts | Nuts: 50, Supply_Costs: 100 |MONTHSTART");

		assertTrue(value instanceof StepDefinition);
		StepDefinition stepDefinition = (StepDefinition) value;
		List<VariableChange> varChanges = stepDefinition.getVariableChanges();
		assertEquals("Order Nuts", stepDefinition.getName(), "the step name should parse Order Nuts");
		assertEquals("Nuts", varChanges.get(0).getVariableName(), "the variable name should parse Nuts");
		assertEquals("Supply_Costs", varChanges.get(1).getVariableName(),"the variable name should parse Supply_Costs");
		assertEquals(50, varChanges.get(0).getModifyBy(), "the modify value of Nuts should be 50");
		assertEquals(100, varChanges.get(1).getModifyBy(), "the modify value of Supply_Costs should be 100");
		assertEquals(Frequency.MONTHSTART,stepDefinition.getFrequency());
	}
}