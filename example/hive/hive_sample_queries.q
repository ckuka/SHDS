#Create table statement.
#Specifies fields types, quote and field delimiters
#Specifies expected filetype as sequence file
#Change LOCATION with the path to your apache access logs on hdfs (check the location where the flume agent wrote out these files in hdfs) location. 
#For example, on my hadoop server, this is - hdfs://localhost:9000/user/kr/tmp/
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
LOCATION 'hdfs://<hdfs_server/path/to/apache/files/';

#Wordcount Query -- Parses request line by creating an example url, and using builtin parse url function to extract the QUERY parameter q.
select parse_url(concat("http://www.some_example.com",split(requestline,' ')[1]),'QUERY','q') as query, count(*) co from apachelog6 group by parse_url(concat("http://www.some_example.com",split(requestline,' ')[1]),'QUERY','q');

