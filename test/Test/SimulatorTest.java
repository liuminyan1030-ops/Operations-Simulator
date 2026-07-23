package Test;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import model.*;

import service.Simulator;



public class SimulatorTest {

    @Test
    public void Test_Run_DefaultScenario_Should_CreateSevenTransactions() {

        Configuration configuration =
                new Configuration(

                        new Scope(
                                LocalDate.of(2026,8,1),
                                LocalDate.of(2026,10,31)),

                        Arrays.asList(
                                new VariableDefinition("Nuts",100),
                                new VariableDefinition("Bolts",200)
                        ),

                        Arrays.asList(
                                new StepDefinition("Order Nuts","Nuts",50),
                                new StepDefinition("Order Bolts","Bolts",25)
                        )
                );

        Simulator simulator = new Simulator();

        List<Transaction> result =
                simulator.run(configuration);

        assertEquals(7, result.size());
    }

    @Test
    public void Test_Run_DefaultScenario_Should_EndWithCorrectInventory() {

        Configuration configuration =
                new Configuration(

                        new Scope(
                                LocalDate.of(2026,8,1),
                                LocalDate.of(2026,10,31)),

                        Arrays.asList(
                                new VariableDefinition("Nuts",100),
                                new VariableDefinition("Bolts",200)
                        ),

                        Arrays.asList(
                                new StepDefinition("Order Nuts","Nuts",50),
                                new StepDefinition("Order Bolts","Bolts",25)
                        )
                );

        Simulator simulator = new Simulator();

        List<Transaction> result =
                simulator.run(configuration);

        Transaction last =
                result.get(result.size()-1);

        assertEquals(
                250,
                last.getVariableValues().get(0).getValue());

        assertEquals(
                275,
                last.getVariableValues().get(1).getValue());
    }

}