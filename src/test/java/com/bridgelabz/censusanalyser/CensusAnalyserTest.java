package com.bridgelabz.censusanalyser;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CensusAnalyserTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";

    /*Test Method for Welcome Message*/
    @BeforeClass
    public static void beforeClass() throws Exception {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        boolean message = censusAnalyser.printWelcomeMessage();
        Assert.assertTrue(message);
    }
    /*Test Method to check number of entries*/
    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
}
