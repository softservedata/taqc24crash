package com.softserve.edu.opencart.tests;

import com.softserve.edu.opencart.data.Currencies;
import com.softserve.edu.opencart.pages.HomePage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


public class CurrencyTest extends TestRunnerFirst {

    public static Object[][] searchCurrencies() {
        return new Object[][] { 
            { Currencies.EURO },
            { Currencies.US_DOLLAR },
            { Currencies.POUND_STERLING },
            //{ Currencies.US_DOLLAR, "$" },
        };
    }

    @ParameterizedTest
    @MethodSource("searchCurrencies")
    public void checkFirst(Currencies currency) {
        //
        // Steps
        HomePage homePage = loadApplication()
               .chooseCurrency(currency);
        //
        presentationSleep();
        //
        // Check
        //Assert.assertTrue(homePage.getCurrencyText().contains("€"));
        //Assert.assertTrue(homePage.getCurrencyText().contains("$"));
        //Assert.assertTrue(homePage.getCurrencyText().contains(currencySymbol));
        Assertions.assertTrue(homePage.getCurrencyText().contains(currency.getSymbol()));
        //
        isTestSuccessful = true;
    }
}