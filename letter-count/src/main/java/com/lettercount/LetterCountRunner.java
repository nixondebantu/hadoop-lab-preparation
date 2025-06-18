package com.lettercount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class LetterCountRunner {

    public static void main(String[] args) throws Exception {
        // Check for the correct number of arguments: input path and output path
        if (args.length != 2) {
            System.err.println("Usage: LetterCountRunner <input path> <output path>");
            System.exit(-1);
        }

        // Create a new Hadoop configuration
        Configuration conf = new Configuration();

        // Create a new Job instance
        Job job = Job.getInstance(conf, "Letter Count");

        // Set the main class of the job, so Hadoop knows which JAR to ship
        job.setJarByClass(LetterCountRunner.class);

        // Set the Mapper and Reducer classes
        job.setMapperClass(LetterCountMapper.class);
        // We can also set a Combiner, which is often the same as the Reducer for
        // efficiency
        job.setCombinerClass(LetterCountReducer.class);
        job.setReducerClass(LetterCountReducer.class);

        // Set the final output key and value classes
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // Set the input and output paths from the command-line arguments
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // Wait for the job to complete and exit with a status code
        // 0 for success, 1 for failure
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
