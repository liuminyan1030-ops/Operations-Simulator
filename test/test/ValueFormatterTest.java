package test;

import exporter.ValueFormatter;
import model.Unit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValueFormatterTest {

    private final ValueFormatter formatter = new ValueFormatter();

    @ParameterizedTest
    @CsvSource({
        "0, MONEY, '$0.00'",
        "123, MONEY, '$123.00'",
        "-123, MONEY, '$-123.00'",
        "0, QUANTITY, '0'",
        "123, QUANTITY, '123'",
        "-123, QUANTITY, '-123'"
    })
    void testFormatValueByUnit(int value, Unit unit, String expected) {
        assertEquals(expected, formatter.formatValueByUnit(value, unit));
    }

    @Test
    void testFormatValueByNullUnit() {
        assertEquals("123", formatter.formatValueByUnit(123, null));
    }
}