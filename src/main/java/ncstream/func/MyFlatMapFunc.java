package ncstream.func;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

public class MyFlatMapFunc implements FlatMapFunction<String, String> {
    @Override
    public void flatMap(String line, Collector<String> lineRes) throws Exception {
        String[] strings = line.split(",");
        for (String s : strings) {
            lineRes.collect(s);
        }
    }
}
