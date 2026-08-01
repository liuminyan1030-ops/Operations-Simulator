package Test;

import model.*;
import service.Simulator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class SimulatorTest {

	private Configuration configuration;
	private Simulator simulator;

	@BeforeEach
	public void setUp() {
		configuration = createDefaultConfiguration();
		simulator = new Simulator(configuration);
	}

	@Test
	public void Test_Run_DefaultScenario_Should_CreateSevenTransactions() {

		List<Transaction> result = simulator.run();

		assertEquals(7, result.size());
	}

	@Test
	public void Test_Run_DefaultScenario_Should_EndWithCorrectInventory() {

		List<Transaction> result = simulator.run();

		Transaction last = result.get(result.size() - 1);
		assertEquals(2, last.getVariableValues().size());

		assertContainsVariable(last, "Nuts", 250);
		assertContainsVariable(last, "Bolts", 275);
	}

	private Configuration createDefaultConfiguration() {
		return new Configuration(new Scope(LocalDate.of(2026, 8, 1), LocalDate.of(2026, 10, 31)),
				Arrays.asList(new VariableDefinition("Nuts", 100), new VariableDefinition("Bolts", 200)), 
				Arrays.asList(new StepDefinition("Order Nuts", "Nuts", 50), new StepDefinition("Order Bolts", "Bolts", 25)));
	}

	private void assertContainsVariable(Transaction transaction, String variableName, int expectedValue) {
		for (VariableValue value : transaction.getVariableValues()) {
			if (value.getName().equals(variableName)) {
				assertEquals(expectedValue, value.getValue(), "Variable " + variableName + " value mismatched!");
				return;
			}
		}
		fail("Variable with name '" + variableName + "' was not found in transaction!");
	}
}