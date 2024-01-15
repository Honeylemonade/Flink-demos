package customsource;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.connector.jdbc.JdbcConnectionOptions;
import org.apache.flink.connector.jdbc.JdbcSink;
import org.apache.flink.streaming.api.datastream.*;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.streaming.api.functions.co.CoMapFunction;
import org.apache.flink.streaming.api.functions.sink.PrintSinkFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

public class MyJob2 {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        final OutputTag<String> outputTag = new OutputTag<String>("Even number side-output") {
        };
        DataStreamSource<Integer> source = env.fromElements(1, 2, 3, 4, 5, 6);
        source.addSink(JdbcSink.sink(
                "insert into user (user_id, name, age) values (?,?,?)",
                (ps, t) -> {
                    ps.setInt(1, t);
                    ps.setString(2, "xyp" + t);
                    ps.setInt(3, t * 10);
                },
                new JdbcConnectionOptions.JdbcConnectionOptionsBuilder()
                        .withUrl("jdbc:mysql://localhost:3306/xyp_test")
                        .withUsername("root")
                        .withPassword("qwe159852")
                        .withDriverName("com.mysql.cj.jdbc.Driver")
                        .build()));


        env.execute();
    }
}
