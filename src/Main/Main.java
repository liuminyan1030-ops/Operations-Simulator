package Main;

import model.*;
import parser.ConfigurationParser;
import service.Simulator;
import validation.ConfigurationValidator;

import java.util.List;

public class Main {

    public static void main(String[] args) {
          String filePath = "config.txt"; 
//        String filePath = "config_invalid.txt"; 

        try {        
            ConfigurationParser configurationParser = new ConfigurationParser();
            Configuration configuration = configurationParser.parseFile(filePath);

            
            ConfigurationValidator cofigValidator = new ConfigurationValidator();
            List<String> errors = cofigValidator.validate(configuration);

            if (!errors.isEmpty()) {
                System.out.println("Configuration validation failed：");
                for (String err : errors) {
                    System.out.println(err);
                }
                return;
            }

            Simulator simulator = new Simulator(configuration);
            List<Transaction> transactions = simulator.run();

            for (Transaction t : transactions) {
                System.out.println("--------------------------------");
                System.out.println(t.getDate());
                System.out.println(t.getDescription());

                for (VariableValue variableValue : t.getVariableValues()) {
                    System.out.println(variableValue.getName() + " : " + variableValue.getValue());
                }
            }

        } catch (Exception e) {
            System.err.println("Failed to read or parse the configuration file-- " + e.getMessage());
        }
    }
}