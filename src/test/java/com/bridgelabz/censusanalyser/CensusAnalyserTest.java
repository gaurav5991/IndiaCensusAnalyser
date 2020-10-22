package com.bridgelabz.censusanalyser;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_TYPE_PATH = "./src/test/resources/IndiaStateCensusData.txt";
    private static final String WRONG_FILE_TYPE_PATH = "./src/test/resources/IndiaCensus.csv";
    private static final String WRONG_FILE_HEADER_PATH = "./src/test/resources/IndiaNewCensus.csv";
    private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_CSV_FILE_STATE = "./src/main/resources/IndiaStateCode.csv";
    private static final String WRONG_CSV_FILE_TYPE_STATE = "./src/test/resources/IndiaStateCode.txt";
    private static final String WRONG_CSV_FILE_DELIMITER_STATE = "./src/test/resources/IndianStateCode.csv";
    private static final String WRONG_CSV_FILE_HEADER_STATE = "./src/test/resources/NewIndianStateCode.csv";

    /*Test Method for Welcome Message*/
    @BeforeClass
    public static void beforeClass() {
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
        } catch (CensusAnalyserException e) { }
    }

    /*Sad Test Case to return Custom Exception if file is incorrect*/
    @Test
    public void givenIndiaCensusDataWithWrongFileShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    /*Sad Test Case to return Custom Exception if file type is incorrect*/
    @Test
    public void givenIndiaCensusDataWithWrongTypeShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_TYPE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    /*Sad Test Case to return Custom Exception if file Delimiter is incorrect*/
    @Test
    public void givenIndiaCensusDataWithWrongDelimiterShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_FILE_TYPE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    /*Sad Test Case to return Custom Exception if CSV Header is incorrect*/
    @Test
    public void givenIndiaCensusDataWithWrongHeaderShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_FILE_HEADER_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    /*Test Case to validate number of entries in state Code csv file*/
    @Test
    public void givenStatesReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaStateData(INDIA_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(37, numOfRecords);
        } catch (CensusAnalyserException e) { }
    }

    /*Sad Test Case to return Custom Exception if StateCodeCSV file is incorrect or do not exists*/
    @Test
    public void givenWrongStateCodeFileReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaStateData(WRONG_CSV_FILE_STATE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.STATE_FILE_PROBLEM, e.type);
        }
    }

    /*Sad Test Case to return Custom Exception if StateCodeCSV file type is incorrect*/
    @Test
    public void givenWrongStateCodeFileTypeReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaStateData(WRONG_CSV_FILE_TYPE_STATE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.STATE_FILE_PROBLEM, e.type);
        }
    }

    /*Sad Test Case to return Custom Exception if StateCodeCSV file Delimiter is incorrect*/
    @Test
    public void givenWrongStateCodeFileWithWrongDelimiterReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaStateData(WRONG_CSV_FILE_DELIMITER_STATE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.STATE_FILE_PROBLEM, e.type);
        }
    }

    /*Sad Test Case to return Custom Exception if StateCode CSV Header is incorrect*/
    @Test
    public void givenStateCodeFileWithWrongHeaderReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaStateData(WRONG_CSV_FILE_HEADER_STATE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.STATE_FILE_PROBLEM, e.type);
        }
    }

}
