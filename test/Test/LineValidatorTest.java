package Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import validation.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LineValidatorTest {
    private LineValidator lineValidator;

    @BeforeEach
    public void setUp() {
    	lineValidator = new LineValidator();
    }

    @Test
    public void testValidLinesShouldHaveNoErrors() {
        List<String> lines = Arrays.asList(
            "START_DATE | 2026/08/01",
            "END_DATE | 2026/11/01",
            "VAR | Nuts | 100",
            "STEP | Order nuts | Nuts: 50"
        );
        List<ValidationError> errors = lineValidator.validate(lines);
        assertTrue(errors.isEmpty(), "Valid lines should not produce any errors");
    }

    @Test
    public void testInvalidVarNameWithSpacesShouldFail() {
        List<String> lines = Arrays.asList("VAR | 5mm Bolts | 200");
        List<ValidationError> errors = lineValidator.validate(lines);

        assertEquals(1, errors.size());
        assertEquals("5mm Bolts", errors.get(0).getProblematicText());
    }

    @Test
    public void testInvalidDateFormatShouldFail() {
        List<String> lines = Arrays.asList("START_DATE | abc");
        List<ValidationError> errors = lineValidator.validate(lines);

        assertEquals(1, errors.size());
        assertEquals("abc", errors.get(0).getProblematicText());
    }
    
    @Test
    public void testInvalidDigitalForVariableValueShouldFail() {
    	List<String> lines=Arrays.asList("STEP | Order nuts | Nuts: cde");
    	List<ValidationError> errors=lineValidator.validate(lines);
    	assertEquals(1, errors.size());
    	assertEquals("cde", errors.get(0).getProblematicText());
    	
    }
}