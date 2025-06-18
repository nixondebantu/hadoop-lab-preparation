package com.lettercount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class LetterCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    // Reusable object for the result
    private IntWritable result = new IntWritable();

    /**
     * The reduce method is called once for each unique key (letter).
     * 
     * @param key     The letter (e.g., 'a').
     * @param values  An iterable of all the counts for that letter (e.g., [1, 1, 1,
     *                ...]).
     * @param context The context object to write the final output to.
     */
    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {
        int sum = 0;
        // Iterate through all the values (the 1s) for the given key and sum them up
        for (IntWritable val : values) {
            sum += val.get();
        }
        // Set the final sum as the result
        result.set(sum);
        // Write the final key-value pair (e.g., <'a', 15>) to the context
        context.write(key, result);
    }
}
