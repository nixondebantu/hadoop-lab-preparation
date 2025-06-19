package com.avgwordlen;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class AW_Reducer extends Reducer<Text, IntWritable, Text, Text> {
    private int totalCount = 0;
    private int totalLength = 0;

    @Override
    protected void cleanup(Reducer<Text, IntWritable, Text, Text>.Context context)
            throws IOException, InterruptedException {
        if (totalCount != 0) {
            double avg = (double) totalLength / totalCount;
            context.write(new Text("Average Word Length"), new Text(String.format("%.2f", avg)));
        } else {
            context.write(new Text("Average Word Length"), new Text("0.00"));
        }
    }

    @Override
    protected void reduce(Text arg0, Iterable<IntWritable> arg1, Reducer<Text, IntWritable, Text, Text>.Context arg2)
            throws IOException, InterruptedException {
        int sum = 0;
        for (IntWritable val : arg1) {
            sum += val.get();
        }

        if (arg0.toString().equals("sum")) {
            totalLength = sum;
        } else if (arg0.toString().equals("count")) {
            totalCount = sum;
        }
    }

}
