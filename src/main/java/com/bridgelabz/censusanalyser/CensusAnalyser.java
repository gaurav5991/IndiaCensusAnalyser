package com.bridgelabz.censusanalyser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    List<IndiaCensusCSV> censusCSVList = null;
    List<IndiaStateCSV> stateCSVList = null;

    /*Method to print Welcome message*/
    public boolean printWelcomeMessage() {
        System.out.println("Welcome to Census Analyser Problem");
        return true;
    }

    /* Method to load India Census Data*/
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        checkFile(csvFilePath);
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            censusCSVList = CSVBuilderFactory.createCSVBuilder().getCSVFileList(reader, IndiaCensusCSV.class);
            return censusCSVList.size();
        } catch (IOException | CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    /*Method to load Indian State Code csv File*/
    public int loadIndiaStateData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            stateCSVList = CSVBuilderFactory.createCSVBuilder().getCSVFileList(reader, IndiaStateCSV.class);
            return stateCSVList.size();
        } catch (IOException | CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    /*Method to implement CommonCSV to load state and india census data*/
    public int loadIndiaStateOrCensusDataUsingCommonsCSV(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim()
                    .parse(reader)
                    .iterator();
            return this.getCount(records);
        } catch (IOException | RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    /*Method To Return JSON File Of States In Alphabetical Order*/
    public String SortedStateWiseCensusData() throws CensusAnalyserException {
        try (Writer writer = new FileWriter("./src/test/resources/IndiaStateCensusDataJson.json")) {
            if (censusCSVList == null || censusCSVList.size() == 0) {
                throw new CensusAnalyserException("No data", CensusAnalyserException.ExceptionType.NO_DATA);
            }
            Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.state);
            this.sort(censusComparator);
            String json = new Gson().toJson(censusCSVList);
            Gson gson = new GsonBuilder().create();
            gson.toJson(censusCSVList, writer);
            return json;

        } catch (RuntimeException | IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.FILE_OR_HEADER_PROBLEM);
        }
    }
    /*Method to sort based on comparator*/
    public void sort(Comparator<IndiaCensusCSV> censusComparator) {
        for (int firstcounter = 0; firstcounter < censusCSVList.size() - 1; firstcounter++) {
            for (int secondcounter = 0; secondcounter < censusCSVList.size() - firstcounter - 1; secondcounter++) {
                IndiaCensusCSV census1 = censusCSVList.get(secondcounter);
                IndiaCensusCSV census2 = censusCSVList.get(secondcounter + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    censusCSVList.set(secondcounter, census2);
                    censusCSVList.set(secondcounter + 1, census1);
                }
            }
        }
    }
    /*Method To Return JSON File Of States According to State Code*/
    public String StateCodeWiseSortedCensusData() throws CensusAnalyserException {
        try (Writer writer = new FileWriter("./src/test/resources/IndiaStateCodeDataJson.json")) {
            if (stateCSVList== null || stateCSVList.size() == 0) {
                throw new CensusAnalyserException("No data", CensusAnalyserException.ExceptionType.NO_DATA);
            }
            Comparator<IndiaStateCSV> censusComparator = Comparator.comparing(state -> state.stateCode);
            this.sortState(censusComparator);
            String json = new Gson().toJson(stateCSVList);
            Gson gson = new GsonBuilder().create();
            gson.toJson(stateCSVList, writer);
            return json;

        } catch (RuntimeException | IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.FILE_OR_HEADER_PROBLEM);
        }
    }
    private void sortState(Comparator<IndiaStateCSV> censusComparator) {
        for (int firstcounter = 0; firstcounter < stateCSVList.size() - 1; firstcounter++) {
            for (int secondcounter = 0; secondcounter < stateCSVList.size() - firstcounter - 1; secondcounter++) {
                IndiaStateCSV census1 = stateCSVList.get(secondcounter);
                IndiaStateCSV census2 = stateCSVList.get(secondcounter + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    stateCSVList.set(secondcounter, census2);
                    stateCSVList.set(secondcounter + 1, census1);
                }
            }
        }
    }
    /* Generic Method to extract Count */
    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> csvIterable = () -> iterator;
        int numOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numOfEntries;
    }
    /*Method to check file type*/
    public static void checkFile(String path) throws CensusAnalyserException {
        String pattern = "^[A-za-z]*.(csv)$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(path);
        if (!m.matches())
            throw new CensusAnalyserException("Incorrect file type",
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
    }
}
