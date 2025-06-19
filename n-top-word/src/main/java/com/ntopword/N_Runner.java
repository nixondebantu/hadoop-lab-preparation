package com.ntopword;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class N_Runner {
    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            System.err.println("Usage: TopNDriver <input path> <output path> <N>");
            System.exit(-1);
        }

        Configuration conf = new Configuration();
        conf.setInt("top.n", Integer.parseInt(args[2]));

        Job job = Job.getInstance(conf, "Top N Word Count");
        job.setJarByClass(N_Runner.class);

        job.setMapperClass(N_Mapper.class);
        job.setReducerClass(N_Reducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
