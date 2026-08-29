package test;

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
	public void Test_Run_DefaultScenario_Should_CreateThirteenTransactions() {

		List<Transaction> result = simulator.run();

		assertEquals(13, result.size());
	}

	@Test
	public void Test_Run_DefaultScenario_Should_EndWithCorrectInventory() {

		List<Transaction> result = simulator.run();

		Transaction last = result.get(result.size() - 1);
		assertEquals(4, last.getVariableValues().size());

		assertContainsVariable(last, "Nuts", 25);
		assertContainsVariable(last, "Bolts", 125);
		assertContainsVariable(last, "Supply_Costs", 450);
		assertContainsVariable(last,"Revenue",1800);
	}
	
	@Test
	public void Test_Run_MonthStart_Should_RunOnFirstDayOfMonth() {
		Configuration config = new Configuration(
				new Scope(LocalDate.of(2026, 8, 1), LocalDate.of(2026, 9, 1)),
				Arrays.asList(new VariableDefinition("Nuts", 100, Unit.QUANTITY)),
				Arrays.asList(new StepDefinition("Order nuts",
						Arrays.asList(new VariableChange("Nuts", 50)),
						Frequency.MONTHSTART)));
		List<Transaction> result = new Simulator(config).run();
		assertEquals(2, result.size());
		assertEquals(LocalDate.of(2026, 8, 1), result.get(1).getDate());
		assertEquals("Order nuts", result.get(1).getDescription());
		assertContainsVariable(result.get(1), "Nuts", 150);
	}
	@Test
	public void Test_Run_MonthEnd_Should_RunOnLastDayOfMonth() {
		Configuration config = new Configuration(
				new Scope(LocalDate.of(2026, 8, 1), LocalDate.of(2026, 9, 1)),
				Arrays.asList(new VariableDefinition("Nuts", 100, Unit.QUANTITY)),
				Arrays.asList(new StepDefinition("Sales of nuts",
						Arrays.asList(new VariableChange("Nuts", -75)),
						Frequency.MONTHEND)));
		List<Transaction> result = new Simulator(config).run();
		assertEquals(2, result.size());
		assertEquals(LocalDate.of(2026, 8, 31), result.get(1).getDate());
		assertEquals("Sales of nuts", result.get(1).getDescription());
		assertContainsVariable(result.get(1), "Nuts", 25);
	}
	@Test
	public void Test_Run_Weekly_Should_FirstRunSevenDaysAfterStart() {
		Configuration config = new Configuration(
				new Scope(LocalDate.of(2026, 8, 1), LocalDate.of(2026, 8, 9)),
				Arrays.asList(new VariableDefinition("Nuts", 100, Unit.QUANTITY)),
				Arrays.asList(new StepDefinition("Weekly check",
						Arrays.asList(new VariableChange("Nuts", 1)),
						Frequency.WEEKLY)));
		List<Transaction> result = new Simulator(config).run();
		assertEquals(2, result.size());
		assertEquals(LocalDate.of(2026, 8, 8), result.get(1).getDate());
		assertEquals("Weekly check", result.get(1).getDescription());
		assertContainsVariable(result.get(1), "Nuts", 101);
	}
	@Test
	public void Test_Run_Daily_Should_RunEveryDayFromStart() {
		Configuration config = new Configuration(
				new Scope(LocalDate.of(2026, 8, 1), LocalDate.of(2026, 8, 3)),
				Arrays.asList(new VariableDefinition("Nuts", 100, Unit.QUANTITY)),
				Arrays.asList(new StepDefinition("Daily check",
						Arrays.asList(new VariableChange("Nuts", 1)),
						Frequency.DAILY)));
		List<Transaction> result = new Simulator(config).run();
		assertEquals(3, result.size());
		assertEquals(LocalDate.of(2026, 8, 1), result.get(1).getDate());
		assertContainsVariable(result.get(1), "Nuts", 101);
		assertEquals(LocalDate.of(2026, 8, 2), result.get(2).getDate());
		assertContainsVariable(result.get(2), "Nuts", 102);
	}

	private Configuration createDefaultConfiguration() {
		return new Configuration(new Scope(LocalDate.of(2026, 8, 1), LocalDate.of(2026, 11, 1)),
				Arrays.asList(new VariableDefinition("Nuts", 100,Unit.QUANTITY), new VariableDefinition("Bolts", 200,Unit.QUANTITY),new VariableDefinition("Supply_Costs", 0, Unit.MONEY),new VariableDefinition("Revenue",0,Unit.MONEY)), 
				Arrays.asList(new StepDefinition("Order Nuts",Arrays.asList(new VariableChange( "Nuts", 50),new VariableChange("Supply_Costs",100)),Frequency.MONTHSTART) ,
						new StepDefinition("Order Bolts", Arrays.asList(new VariableChange("Bolts", 25),new VariableChange("Supply_Costs",50)),Frequency.MONTHSTART),
						new StepDefinition("Sales of Nuts",Arrays.asList(new VariableChange( "Nuts", -75),new VariableChange("Revenue",200)),Frequency.MONTHEND) ,
						new StepDefinition("Sales of Bolts", Arrays.asList(new VariableChange("Bolts", -50),new VariableChange("Revenue",400)),Frequency.MONTHEND)));
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