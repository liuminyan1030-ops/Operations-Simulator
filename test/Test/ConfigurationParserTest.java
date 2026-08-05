package Test;

import model.Configuration;
import parser.ConfigurationParser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ConfigurationParserTest {

    private ConfigurationParser configurationParser;

    @BeforeEach
    void setUp() {
    	configurationParser = new ConfigurationParser();
    }

    @Test
    void testParseLines_ValidInput_Success() {
        
        List<String> validLines = Arrays.asList(
            "START_DATE | 2026/08/01",
            "END_DATE | 2026/08/31",
            "VAR | Nuts | 100",
            "VAR | Bolts | 200",
            "STEP | Order Nuts | Nuts: 50"
        );

        
        Configuration config = configurationParser.parseLines(validLines);

     
        assertNotNull(config, "The parsed Configuration object should not be null");
        assertNotNull(config.getScope(), "Scope should not be null");
        assertEquals("2026-08-01", config.getScope().getStartDate().toString());
        assertEquals("2026-08-31", config.getScope().getEndDate().toString());
        assertEquals(2, config.getVariableDefinitions().size(), "Should parse 2 variable definitions");
        assertEquals(1, config.getStepDefinitions().size(), "Should parse 1 step definition");
    }

    @Test
    void testParseLines_InvalidInput_ThrowsException() {
       
        List<String> invalidLines = Arrays.asList(
            "START_DATE | 2026-08-01", 
            "END_DATE | 2026/08/31"
        );

        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> configurationParser.parseLines(invalidLines),
            "An IllegalArgumentException should be thrown for invalid text format."
        );

     
        assertTrue(exception.getMessage().contains("File validation failed"));
    }
}