In this directory you find an example on how to perform a map/reduce job and store the result in another file in HDFS

First you have to compile the example using Maven. After that, you can start the map/reduce job issuing the following command:
HADOOP_PREFIX=/usr/local/bin/hadoop /usr/local/bin/hadoop jar src/shds-example/target/shds-example-0.0.1-SNAPSHOT.jar cc.kuka.hadoop.shds_example.SearchCount hdfs://localhost:9000/flume hdfs://localhost:9000/result

