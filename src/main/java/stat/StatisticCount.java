package stat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.PrintSink;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StatisticCount {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> source = env.readTextFile("/Users/bytedance/IdeaProjects/flink-demo/src/main/resources/ua.log");
        source.map((MapFunction<String, Access>) value -> new ObjectMapper().readValue(value, Access.class))
                .map(new MapFunction<Access, Tuple3<String, String, Integer>>() {
                    @Override
                    public Tuple3<String, String, Integer> map(Access value) throws Exception {
                        String date = new SimpleDateFormat("yyyyMMdd").format(new Date(value.getTs()));
                        return Tuple3.of(value.getName(), date, 1);
                    }
                })
                .keyBy(value -> value.f0)
                .sum(2)
                .sinkTo(new PrintSink<>());

        env.execute();
    }
}
