package com.bridgelabz.censusanalyser;

import org.junit.Assert;
import org.junit.BeforeClass;

public class CensusAnalyserTest {
    /*Test Method for Welcome Message*/
    @BeforeClass
    public static void beforeClass() throws Exception {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        boolean message = censusAnalyser.printWelcomeMessage();
        Assert.assertTrue(message);
    }
}
