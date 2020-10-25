package com.bridgelabz.censusanalyser;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public interface ICSVBuilder<E> {
    Iterator<E> getCSVFileIterator(Reader reader, Class<E> csvClass) throws CSVBuilderException;

    List<E> getCSVFileList(Reader reader, Class csvClass) throws CSVBuilderException;
}
