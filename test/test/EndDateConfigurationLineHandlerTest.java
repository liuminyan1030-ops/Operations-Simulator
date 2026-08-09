package test;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import handler.EndDateConfigurationLineHandler;

public class EndDateConfigurationLineHandlerTest {

    private EndDateConfigurationLineHandler handler;

    @BeforeEach
    void setUp() {
        handler = new EndDateConfigurationLineHandler();
    }

    @Test
    void testValidateConfigurationLine_ValidFormat() {
        assertTrue(handler.validateConfigurationLine("END_DATE | 2026/10/01"));
    }

    @ParameterizedTest
    @CsvSource({
        "'END_DATE | 2016-18-01', 'Invalid date format (month > 12)'",
        "'START_DATE | 2026/08/01', 'Wrong prefix for EndDate handler'"
    })
    void testValidateConfigurationLine_InvalidFormat(String line, String scenarioDescription) {
        assertFalse(handler.validateConfigurationLine(line), "Failed in scenario: " + scenarioDescription);
    }

    @Test
    void testGetConfigurationValue_Success() {
        Object value = handler.getConfigurationValue("END_DATE | 2026/10/01");
        assertTrue(value instanceof LocalDate);
        assertEquals(LocalDate.of(2026, 10, 1), value);
    }
}