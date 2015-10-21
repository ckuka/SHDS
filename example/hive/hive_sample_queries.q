#Create table statement.
#Specifies fields types, quote and field delimiters
#Specifies expected filetype as sequence file
#CHANGE <hadoop_server> with your hdfs server location. For example, if your hadoop server is running locally, this would be localhost:9000
CREATE EXTERNAL TABLE apache_log (
   ipaddress STRING, 
   identd STRING, 
   user1 STRING,
   finishtime STRING,
   requestline string, 
   returncode string, 
   size string)
ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.dynamic_type.DynamicSerDe'
WITH SERDEPROPERTIES (
'serialization.format'='org.apache.hadoop.hive.serde2.thrift.TCTLSeparatedProtocol',
'quote.delim'='("|\\[|\\])',
'field.delim'=' ',
'serialization.null.format'='-')
stored as sequencefile
LOCATION 'hdfs://localhost:9000/user/kr/tmp/';

#
select parse_url(concat("http://www.some_example.com",split(requestline,' ')[1]),'QUERY','q') as query, count(*) co from apachelog6 group by parse_url(concat("http://www.some_example.com",split(requestline,' ')[1]),'QUERY','q');