package table_flink;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.PrintSink;
import org.apache.flink.table.api.Schema;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;

public class FlinkTableWithDataStreamApp {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        SingleOutputStreamOperator<ClickLog> clickLogStream = env.fromElements(
                        "Mary,12:00:00,./home",
                        "Bob,12:00:00,./cart",
                        "Mary,12:00:05,./prod?id=1",
                        "Liz,12:01:00,./home",
                        "Bob,12:01:30,./prod?id=3",
                        "Mary,12:01:40,./prod?id=4")
                .map(new MapFunction<String, ClickLog>() {
                    @Override
                    public ClickLog map(String value) throws Exception {
                        String[] strings = value.split(",");
                        return new ClickLog(strings[0], strings[1], strings[2]);
                    }
                });

        Table table = tableEnv.fromDataStream(clickLogStream).as("user");

//        Table tableResult = table
//                .where($("user").isEqual("Mary"))
//                .select($("user"), $("time"), $("url"));


        table.printSchema();
        table.execute().print();
//        DataStream dataStream = tableEnv.toDataStream(table);

//        dataStream.sinkTo(new PrintSink<>());

//        env.execute();

    }
}
