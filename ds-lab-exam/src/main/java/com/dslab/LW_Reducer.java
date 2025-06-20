package com.dslab;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class LW_Reducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    private int N = 20;
    private final Map<String, Integer> wordList = new HashMap<>();

    @Override
    protected void reduce(Text arg0, Iterable<IntWritable> arg1,
            Reducer<Text, IntWritable, Text, IntWritable>.Context arg2) throws IOException, InterruptedException {

        int len = 0;
        for (IntWritable intWritable : arg1) {
            len = intWritable.get();
        }
        wordList.put(arg0.toString(), len);
    }

    @Override
    protected void cleanup(Reducer<Text, IntWritable, Text, IntWritable>.Context context)
            throws IOException, InterruptedException {
        PriorityQueue<Map.Entry<String, Integer>> longestNWord = new PriorityQueue<>(
                Comparator.comparingInt(Map.Entry::getValue));

        for (Map.Entry<String, Integer> entry : wordList.entrySet()) {
            longestNWord.offer(entry);

            if (longestNWord.size() > N) {
                longestNWord.poll();
            }
        }

        List<Map.Entry<String, Integer>> result = new ArrayList<>(longestNWord);
        result.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        int maxsize = 0;
        for (Map.Entry<String, Integer> entry : result) {
            if (maxsize <= entry.getValue()) {
                maxsize = entry.getValue();
                context.write(new Text(entry.getKey()), new IntWritable(entry.getValue()));
            }
        }
    }
}
