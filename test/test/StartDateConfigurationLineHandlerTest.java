package test;

import handler.StartDateConfigurationLineHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class StartDateConfigurationLineHandlerTest {

    private StartDateConfigurationLineHandler handler;

    @BeforeEach
    void setUp() {
        handler = new StartDateConfigurationLineHandler();
    }

    @Test
    void testValidateConfigurationLine_ValidFormat() {
        assertTrue(handler.validateConfigurationLine("START_DATE | 2026/08/01"));
    }

    @ParameterizedTest
    @CsvSource({
        "'START_DATE | 2026:08:01', 'Invalid date format ( colon used instead of slash and hyphen)'",
        "'END_DATE | 2026/08/01', 'Incorrect prefix for StartDate handler'",
        "'START_DATE | 2026/13/01', 'Invalid date value (month exceeds 12)'",
        "'START_DATE | abc', 'Non-date string value'"
    })
    void testValidateConfigurationLine_InvalidFormat(String line, String problem) {
        assertFalse(handler.validateConfigurationLine(line), "Failed scenario: " + problem);
    }
    @Test
    void testGetConfigurationValue_Success() {
        Object value = handler.getConfigurationValue("START_DATE | 2026/08/01");
        assertTrue(value instanceof LocalDate);
        assertEquals(LocalDate.of(2026, 8, 1), value);
    }
}