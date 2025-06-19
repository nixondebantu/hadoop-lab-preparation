package com.charfreq;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class CF_Reducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    @Override
    protected void reduce(Text arg0, Iterable<IntWritable> arg1,
            Reducer<Text, IntWritable, Text, IntWritable>.Context arg2) throws IOException, InterruptedException {
        int sum = 0;
        for (IntWritable value : arg1) {
            sum += value.get();
        }

        arg2.write(arg0, new IntWritable(sum));
    }

}
