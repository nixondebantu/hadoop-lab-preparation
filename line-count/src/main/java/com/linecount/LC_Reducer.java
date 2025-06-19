package com.linecount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class LC_Reducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    @Override
    protected void reduce(Text arg0, Iterable<IntWritable> arg1,
            Reducer<Text, IntWritable, Text, IntWritable>.Context arg2) throws IOException, InterruptedException {
        int lineCount = 0;
        for (IntWritable val : arg1) {
            lineCount += val.get();
        }
        arg2.write(arg0, new IntWritable(lineCount));
    }
}
