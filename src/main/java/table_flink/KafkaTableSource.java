package table_flink;

import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.apache.flink.table.api.Expressions.$;

public class KafkaTableSource {
    public static void main(String[] args) throws IOException {
        EnvironmentSettings settings = EnvironmentSettings
                .newInstance()
                .inStreamingMode()
                .build();

        TableEnvironment tEnv = TableEnvironment.create(settings);
        byte[] bytes = Files.readAllBytes(Path.of("/Users/bytedance/IdeaProjects/flink-demo/src/main/resources/kafka_source.sql"));
        String sql = new String(bytes);
        System.out.println(sql);

        tEnv.executeSql(sql);
        Table kafkaTable = tEnv.from("kafka_table");
        Table select = kafkaTable.groupBy($("user"))
                .select($("user"), $("user").count().as("click count"));

        select.execute().print();
    }
}
