package test;

import model.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parser.ConfigurationParser;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigurationParserTest {
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private ConfigurationParser configurationParser;

    @BeforeEach
    void setUp() {
        configurationParser = new ConfigurationParser();
    }

    @Test
    void testParseLines_ValidInput_Success()throws IOException {
      Configuration config=configurationParser.parseFile("config.txt");
      assertNotNull(config);
      assertEquals("2026/08/01",config.getScope().getStartDate().format(DATE_FORMATTER));
      assertEquals("2026/10/31",config.getScope().getEndDate().format(DATE_FORMATTER));
      assertEquals(2,config.getVariableDefinitions().size());
      assertEquals(2,config.getStepDefinitions().size());

    }

    @Test
    void testParseLines_InvalidInput_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            configurationParser.parseFile("config_invalid.txt");
        });
    }
}