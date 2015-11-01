/**
 * 
 */
package cc.kuka.hadoop.shds_example;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

/**
 * Hadoop Map/Reduce example for the Shanghai Data Science Meetup
 * 
 * @author Karthik Rajasethupathy
 * @author Christian Kuka
 *
 */
public class SearchCount {

	/**
	 * 
	 * Mapper to extract the search URL
	 *
	 */
	public static class Map extends Mapper<LongWritable, BytesWritable, Text, IntWritable> {

		@Override
		public void map(LongWritable key, BytesWritable value, Context context)
				throws IOException, InterruptedException {
			String line = new String(value.getBytes());
			Text word = new Text();

			// Extract the URL of the web access log line
			word.set(line.split(" ")[6]);
			context.write(word, new IntWritable(1));
		}
	}

	/**
	 * 
	 * Reducer to count the search term frequency
	 *
	 */
	public static class Reduce extends Reducer<Text, IntWritable, Text, IntWritable> {

		@Override
		public void reduce(Text key, Iterable<IntWritable> values, Context context)
				throws IOException, InterruptedException {
			int sum = 0;
			for (IntWritable val : values) {
				sum += val.get();
			}
			context.write(key, new IntWritable(sum));
		}
	}

	/**
	 * 
	 * @param args
	 *            0: The input sequence file, 1: The output file
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();

		Job job = new Job(conf, "searchcount");

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		job.setMapperClass(Map.class);
		job.setReducerClass(Reduce.class);

		job.setInputFormatClass(SequenceFileInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.waitForCompletion(true);
	}

}
