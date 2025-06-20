package com.dslab;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class LW_Mapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
            throws IOException, InterruptedException {
        // String line = value.toString();
        String line = value.toString().toLowerCase();
        String[] words = line.split("\\s+");
        for (String word : words) {
            word.replaceAll("[^a-zA-z]", "");
            if (!word.isEmpty()) {
                context.write(new Text(word), new IntWritable(word.length()));
            }
        }
    }
}
