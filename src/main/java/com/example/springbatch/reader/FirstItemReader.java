package com.example.springbatch.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

// responsible for reading the data from the source, the source can be anything file, database, lists etc
@Component
public class FirstItemReader implements ItemReader<Integer> {

    List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    int i = 0;

    // this method will be called by spring batch and we need to return one value , this method will be
    // called multiple times by spring batch till all the data has been read.
    // this read method will be called 3 times because we have set chunk size as 3
    @Override
    public Integer read() throws Exception, UnexpectedInputException, ParseException,
            NonTransientResourceException {

        System.out.println("Inside item reader");

        Integer item;
        if (i < integerList.size()) {
            item = integerList.get(i);
            i++;
            return item;
        }
        i = 0;
        // when null is returned item reading will get to know that there are no more records to read from source.
        return null;
    }
}
