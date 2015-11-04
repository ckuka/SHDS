In this directory you find the necessary configuration for flume.

To start Flume issue the following command:
HADOOP_PREFIX=/usr/local/bin/hadoop /usr/local/bin/flume-ng agent --conf conf -f conf/flume-conf.properties -n agent -Dflume.root.logger=DEBUG,console
