package Main;
import model.*;
import service.Simulator;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

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

        List<Transaction> transactions =
                simulator.run(configuration);

        for(Transaction t : transactions){

            System.out.println("--------------------------------");

            System.out.println(t.getDate());

            System.out.println(t.getDescription());

            for(VariableValue value : t.getVariableValues()){

                System.out.println(
                        value.getName()+" : "+value.getValue());

            }
        }
    }
}