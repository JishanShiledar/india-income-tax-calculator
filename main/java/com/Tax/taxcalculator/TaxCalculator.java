package com.Tax.taxcalculator;
@FunctionalInterface
public interface TaxCalculator {
    double calculate(double income);

    default double calculateTaxAndPrint(Person person){
        double tax = calculate(person.income());
        System.out.printf("%-20s | Income: ₹%-12.0f | Tax: ₹%.2f%n",
                person.name(), person.income(), tax);
        return tax;
    }
}
