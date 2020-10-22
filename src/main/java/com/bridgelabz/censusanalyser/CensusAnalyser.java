package com.bridgelabz.censusanalyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    /*Method to print Welcome message*/
    public boolean printWelcomeMessage() {
        System.out.println("Welcome to Census Analyser Problem");
        return true;
    }

    /* Method to load India Census Data*/
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            Iterator<IndiaCensusCSV> censusCSVIterator = new OpenCSVBuilder().getCSVFileIterator(reader, IndiaCensusCSV.class);
            return getCount(censusCSVIterator);
        } catch (IOException | RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    /*Method to load Indian State Code csv File*/
    public int loadIndiaStateData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            Iterator<IndiaStateCSV> StateCSVIterator = new OpenCSVBuilder().getCSVFileIterator(reader, IndiaStateCSV.class);
            return getCount(StateCSVIterator);
        } catch (IOException | RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.STATE_FILE_PROBLEM);
        }
    }

    /* Generic Method to extract Count */
    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> csvIterable = () -> iterator;
        int numOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numOfEntries;
    }
}
