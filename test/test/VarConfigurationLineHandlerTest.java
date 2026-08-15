package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import handler.VarConfigurationLineHandler;
import model.VariableDefinition;

public class VarConfigurationLineHandlerTest {
	private VarConfigurationLineHandler handler;
	
	@BeforeEach
	void setUp() {
		handler=new VarConfigurationLineHandler();
	}
	
	@Test
	void testValidateConfigurationLine_validFormat() {
		assertTrue(handler.validateConfigurationLine("VAR | Nuts |100 |Quantity"));
		assertTrue(handler.validateConfigurationLine("VAR | Supply_Costs | 500.00 | Money"));
	}
	
	@ParameterizedTest
	@CsvSource({
	    "'VAR | Bolts| ', 'missing the variable value.'",
	    "'VAR | | 100', 'missing the variable name'",
	    "'VAR | | ', 'missing the variable name and variable value'",
	    "'VAR | Nuts | 100 | UnknownUnit', 'invalid unit value'",
	    "'VAR | SUPPLY_COST | 0 | Money', 'value cannot be 0'",          
	    "'VAR | SUPPLY_COST | 0.0 | Money', 'value cannot be decimal'"  
	})
    void testValidateConfigurationLine_InvalidFormat(String line, String problem) {
    	assertFalse(handler.validateConfigurationLine(line),"Failed scenario: "+problem);
    
    }
    
    @Test
    void testGetConfigurationValue_Success() {
    	Object value=handler.getConfigurationValue("VAR | Nuts |100 |Quantity");
    	assertTrue(value instanceof VariableDefinition);
    	VariableDefinition variableDefinition = (VariableDefinition) value;
        assertEquals("Nuts", variableDefinition.getName(), "Variable name should parse Nuts");
        assertEquals(100, variableDefinition.getStartValue(), "Variable start value should be 100");
        assertEquals("Quantity",variableDefinition.getUnit().toString(),"Variable Unit should be Quantity");
    	
    }
}
