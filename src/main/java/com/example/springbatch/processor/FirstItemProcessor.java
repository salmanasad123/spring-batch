package com.example.springbatch.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

// ItemProcessor<Integer, Integer> need to provide 2 values one for processing and second for output.
// Output of itemReader is input for item processor.
@Component
public class FirstItemProcessor implements ItemProcessor<Integer, Long> {

    @Override
    public Long process(Integer integer) throws Exception {
        System.out.println("Inside item processor");
        return Long.valueOf(integer + 20);
    }
}
