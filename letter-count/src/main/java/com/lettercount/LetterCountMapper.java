package com.lettercount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class LetterCountMapper extends Mapper<Object, Text, Text, IntWritable> {

    // Reusable objects to avoid creating new objects for every record
    private final static IntWritable one = new IntWritable(1);
    private Text letter = new Text();

    /**
     * The map method is called for each line in the input file.
     * 
     * @param key     The byte offset of the line in the file (we ignore this).
     * @param value   The line of text.
     * @param context The context object to write the output to.
     */
    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        // Convert the line of text to a string and make it lowercase
        String line = value.toString().toLowerCase();

        // Iterate over each character in the line
        for (char c : line.toCharArray()) {
            // Check if the character is an alphabet letter
            if (Character.isLetter(c)) {
                // Set the character as the key for our output
                letter.set(String.valueOf(c));
                // Write the key-value pair (e.g., <'a', 1>) to the context
                context.write(letter, one);
            }
        }
    }
}
