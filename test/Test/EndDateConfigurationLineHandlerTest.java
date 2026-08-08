package Test;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @Test
    void testValidateConfigurationLine_InvalidFormat() {
        assertFalse(handler.validateConfigurationLine("END_DATE | 2016-18-01")); 
        assertFalse(handler.validateConfigurationLine("START_DATE | 2026/08/01"));   
    }

    @Test
    void testGetConfigurationValue_Success() {
        Object value = handler.getConfigurationValue("END_DATE | 2026/10/01");
        assertTrue(value instanceof LocalDate);
        assertEquals(LocalDate.of(2026, 10, 1), value);
    }

}
