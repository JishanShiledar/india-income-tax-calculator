package com.Tax.taxcalculator;

public class TaxRules {

    // Applying slabs as per FY 2025-26. Will need update after next Budget.
    public static final TaxCalculator NEW_REGIME = income ->{
        if(income <= 300000) return 0;
        if(income <= 600000) return (income - 300000) * 0.05;
        if(income <= 900000) return (income - 600000) * 0.10 + 15000;
        if(income <= 1200000) return (income - 900000) * 0.15 + 45000;
        if(income <= 1500000) return (income - 1200000) * 0.20 + 90000;
        return (income - 150000) * 0.30 + 150000;
    };


    // OLD TAX REGIME (classic slabs with more deductions)
    public static final TaxCalculator OLD_REGIME = income -> {
        if (income <= 250000) return 0;
        if (income <= 500000) return (income - 250000) * 0.05;
        if (income <= 1000000) return (income - 500000) * 0.20 + 12500;
        return (income - 1000000) * 0.30 + 112500;   // 30% above 10 lakh
    };

    // For Senior Citizen Regime
    public static final TaxCalculator SENIOR_REGIME = income -> {
        if (income <= 500000) return 0;
        if (income <= 800000) return (income - 500000) * 0.05;
        if (income <= 1100000) return (income - 800000) * 0.10 + 15000;
        if (income <= 1400000) return (income - 1100000) * 0.15 + 45000;
        if (income <= 1700000) return (income - 1400000) * 0.20 + 90000;
        return (income - 1700000) * 0.30 + 150000;
    };
}
