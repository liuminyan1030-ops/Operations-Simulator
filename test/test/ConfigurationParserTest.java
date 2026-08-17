package test;

import model.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import constants.ConfigurationConstants;
import parser.ConfigurationParser;

import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;

public class ConfigurationParserTest {
	
    private ConfigurationParser configurationParser;

    @BeforeEach
    void setUp() {
        configurationParser = new ConfigurationParser();
    }

    @Test
    void testParseLines_ValidInput_Success()throws IOException {
      Configuration config=configurationParser.parseFile("config.txt");
      assertNotNull(config);
      assertEquals("2026/08/01",config.getScope().getStartDate().format(ConfigurationConstants.DATE_FORMATTER));
      assertEquals("2026/11/01",config.getScope().getEndDate().format(ConfigurationConstants.DATE_FORMATTER));
      assertEquals(3,config.getVariableDefinitions().size());
      assertEquals(2,config.getStepDefinitions().size());

    }

    @Test
    void testParseLines_InvalidInput_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            configurationParser.parseFile("config_invalid.txt");
        });
    }
}