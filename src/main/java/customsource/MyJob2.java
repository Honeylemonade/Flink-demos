package customsource;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class MyJob2 {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<Integer> integerDataStreamSource = env.addSource(new MyStreamingSource());

//        integerDataStreamSource.forward().reduce

//        transformers.addSink(new PrintSinkFunction<>());

        env.execute();
    }
}
