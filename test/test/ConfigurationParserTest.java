package test;

import model.Configuration;
import model.Frequency;

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
      assertEquals(4,config.getVariableDefinitions().size());
      assertEquals(4,config.getStepDefinitions().size());
      assertEquals(Frequency.MONTHSTART, config.getStepDefinitions().get(0).getFrequency());
      assertEquals(Frequency.MONTHSTART, config.getStepDefinitions().get(1).getFrequency());
      assertEquals(Frequency.MONTHEND, config.getStepDefinitions().get(2).getFrequency());
      assertEquals(Frequency.MONTHEND, config.getStepDefinitions().get(3).getFrequency());

    }

    @Test
    void testParseLines_InvalidInput_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            configurationParser.parseFile("config_invalid.txt");
        });
    }
}