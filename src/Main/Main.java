package Main;

import model.*;
import parser.ConfigurationParser;
import service.Simulator;
import validation.ConfigurationValidator;

import java.util.List;

import exporter.TransactionCsvExporter;

public class Main {

	public static void main(String[] args) {
          String filePath = "config.txt"; 
//        String filePath = "config_invalid.txt"; 

        try {        
            ConfigurationParser configurationParser = new ConfigurationParser();
            Configuration configuration = configurationParser.parseFile(filePath);

            
            ConfigurationValidator configValidator = new ConfigurationValidator();
            List<String> errors = configValidator.validate(configuration);

            if (!errors.isEmpty()) {
                System.out.println("Configuration validation failed：");
                for (String err : errors) {
                    System.out.println(err);
                }
                return;
            }

            Simulator simulator = new Simulator(configuration);
            List<Transaction> transactions = simulator.run();
            
            TransactionCsvExporter csvExporter=new TransactionCsvExporter();
            String csvContent=csvExporter.generateCsvContent(transactions,configuration);
            System.out.println(csvContent);
            csvExporter.exportToFile(transactions, configuration,"output.csv");
        }catch(Exception e) {
        	System.err.println("Failed to execute application: " + e.getMessage());
        }
    }

}