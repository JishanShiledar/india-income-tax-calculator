package com.Tax.taxcalculator;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class TaxCalculatorApp {

    public static void main(String[] args){
        System.out.println("🚀 Income Tax Calculator with lambda - Application Started!!");

        List<Person> people = CsvLoader.loadPeopleFromCsv();

        if(people.isEmpty()){
            System.out.println("⚠️  people.csv not found in project root → Using default sample data\n");
            people = List.of(
                    new Person("Rahul Sharma", 450000),
                    new Person("Sneha Reddy", 280000),
                    new Person("Priya Patel", 850000),
                    new Person("Amit Kumar", 850000),
                    new Person("Vikas Singh", 2500000)

            );
        }else {
            System.out.println(" ✅ Successfully Loaded " + people.size() + " people from people.csv using lambdas!!\n");
        }

        System.out.println("=== People in the System ===");
        people.forEach(person ->{
            System.out.println("◉ " + person.name() + " -> ₹" + person.income());
        });

        // For Interactive Choice
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nChoose Tax Regime:");
        System.out.println("1. New Regime (2025 slabs)");
        System.out.println("2. Old Regime");
        System.out.println("3. Senior Citizen Regime (higher exemption)");
        System.out.println("Enter your choice (1-3): ");

        int choice = scanner.nextInt();
        TaxCalculator baseCalculator = switch (choice){
            case 1 -> TaxRules.NEW_REGIME;
            case 2 -> TaxRules.OLD_REGIME;
            case 3 -> TaxRules.SENIOR_REGIME;
            default -> {
                System.out.println("Invalid choice ➡️ Defaulting to NEW REGIME");
                yield TaxRules.NEW_REGIME;
            }
        };

        String regimeName = switch (choice){
            case 1 -> "NEW REGIME";
            case 2 -> "OLD REGIME";
            case 3 -> "SENIOR CITIZEN REGIME";
            default -> "NEW REGIME";
        };

        System.out.println("\nUsing: " + regimeName);
        System.out.println("-".repeat(90));

        TaxCalculator finalCalculator = income -> {
            double tax = baseCalculator.calculate(income);
            if(income > 5000000){
                tax += tax * 0.10;
                System.out.println("   [Surcharge of 10% applied for high income]");
            }
            return tax;
        };


        System.out.println("\n=== TAX CALCULATION (Full Stream Pipeline) ===\n");

        List<TaxResult> results = people.stream()
                .map(person -> {
                    double tax = finalCalculator.calculate(person.income());
                    return new TaxResult(person, tax);
                })
                .sorted(Comparator.comparingDouble(TaxResult::tax).reversed())
                .toList();

        // For Printing Individual Results
        results.forEach(result -> {
            System.out.printf("%-20s | Income: ₹%-12.0f | Tax: ₹%8.2f%n",
                    result.person().name(),
                    result.person().income(),
                    result.tax());
        });
        System.out.println("-".repeat(90));


        // Summary Statistics using Lambdas
        double totalTax = results.stream()
                .mapToDouble(TaxResult::tax)
                .sum();
        double avgTax = results.stream()
                .mapToDouble(TaxResult::tax)
                .average()
                .orElse(0.0);
        String highest = results.get(0).person().name();

        System.out.println("=== SUMMARY STATISTICS ===");
        System.out.printf("Total Tax Collected     : ₹%.2f%n", totalTax);
        System.out.printf("Average Tax per Person  : ₹%.2f%n", avgTax);
        System.out.printf("Highest Tax Payer       : %s (₹%.2f)%n", highest, results.get(0).tax());
        scanner.close();
    }
}
