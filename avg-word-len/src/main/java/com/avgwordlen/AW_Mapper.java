package com.avgwordlen;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class AW_Mapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private final static IntWritable one = new IntWritable(1);
    private final static Text SUM = new Text("sum");
    private final static Text COUNT = new Text("count");

    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
            throws IOException, InterruptedException {
        String line = value.toString();
        String[] words = line.split("\\s+");

        for (String word : words) {
            word.replaceAll("[^a-zA-z]", ""); // remove all the punctuation
            if (!word.isEmpty()) {
                context.write(COUNT, one);
                context.write(SUM, new IntWritable(word.length()));
            }
        }
    }

}
