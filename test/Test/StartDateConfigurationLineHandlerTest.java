package Test;

import handler.StartDateConfigurationLineHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @Test
    void testValidateConfigurationLine_InvalidFormat() {
        assertFalse(handler.validateConfigurationLine("START_DATE | 2026-08-01")); 
        assertFalse(handler.validateConfigurationLine("END_DATE | 2026/08/01"));   
    }

    @Test
    void testGetConfigurationValue_Success() {
        Object value = handler.getConfigurationValue("START_DATE | 2026/08/01");
        assertTrue(value instanceof LocalDate);
        assertEquals(LocalDate.of(2026, 8, 1), value);
    }
}