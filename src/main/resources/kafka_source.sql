CREATE TABLE kafka_table
(
    `user` STRING,
    `time` STRING,
    `url`  STRING
) WITH (
      'connector' = 'kafka',
      'topic' = 'pk-2-2',
      'properties.bootstrap.servers' = 'localhost:9092',
      'properties.group.id' = 'testGroup',
      'scan.startup.mode' = 'latest-offset',
      'format' = 'json'
      )