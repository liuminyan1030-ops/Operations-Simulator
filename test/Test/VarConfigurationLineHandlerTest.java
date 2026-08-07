package Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
		assertTrue(handler.validateConfigurationLine("VAR | Nuts |100"));
	}
	
    @Test
    void testValidateConfigurationLine_InvalidFormat() {
    	assertFalse(handler.validateConfigurationLine("VAR | Bolts| "));
    	assertFalse(handler.validateConfigurationLine("VAR | | 100"));
    	assertFalse(handler.validateConfigurationLine("VAR | | "));
    }
    
    @Test
    void testGetConfigurationValue_Success() {
    	Object value=handler.getConfigurationValue("VAR | Nuts |100");
    	assertTrue(value instanceof VariableDefinition);
    	VariableDefinition variableDefinition = (VariableDefinition) value;
        assertEquals("Nuts", variableDefinition.getName(), "Variable name should parse Nuts");
        assertEquals(100, variableDefinition.getStartValue(), "Variable start value should be 100");
    	
    }
}
