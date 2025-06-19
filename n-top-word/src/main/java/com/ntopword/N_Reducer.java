package com.ntopword;

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

public class N_Reducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    private int N = 10;
    private final Map<String, Integer> wordCounts = new HashMap<>();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        N = context.getConfiguration().getInt("top.n", N);
    }

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {
        int sum = 0;
        for (IntWritable val : values) {
            sum += val.get();
        }
        wordCounts.put(key.toString(), sum);
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        // Build a min-heap to keep top N
        PriorityQueue<Map.Entry<String, Integer>> topNWords = new PriorityQueue<>(
                Comparator.comparingInt(Map.Entry::getValue));

        for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
            topNWords.offer(entry);
            if (topNWords.size() > N) {
                topNWords.poll();
            }
        }

        List<Map.Entry<String, Integer>> result = new ArrayList<>(topNWords);
        result.sort((a, b) -> b.getValue().compareTo(a.getValue())); // descending

        for (Map.Entry<String, Integer> entry : result) {
            context.write(new Text(entry.getKey()), new IntWritable(entry.getValue()));
        }
    }
}
