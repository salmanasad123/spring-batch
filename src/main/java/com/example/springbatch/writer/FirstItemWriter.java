package com.example.springbatch.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FirstItemWriter implements ItemWriter<Long> {

    // the length of list be based on chunk size that we have defined for example if chunk size is 3
    // the size of list will be 3.
    // we will not get items one by one in writer.
    @Override
    public void write(List<? extends Long> list) throws Exception {

        System.out.println("Inside item writer");

        list.forEach((Long value) -> {
            System.out.println(value);
        });
    }
}
