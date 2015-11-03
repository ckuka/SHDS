Start Flume:
HADOOP_PREFIX=~/bin/hadoop ./bin/flume-ng agent --conf conf -f conf/flume-conf.properties -n agent -Dflume.root.logger=DEBUG,console
