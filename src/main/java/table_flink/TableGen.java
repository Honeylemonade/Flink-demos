package table_flink;

import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.TableResult;

public class TableGen {
    public static void main(String[] args) {
        String sql = "CREATE TABLE datagen (\n" +
                " id INT,\n" +
                " name STRING,\n" +
                " age INT,\n" +
                " ts AS localtimestamp,\n" +
                " WATERMARK FOR ts AS ts\n" +
                ") WITH (\n" +
                " 'connector' = 'datagen',\n" +
                " 'rows-per-second'='5',\n" +
                " 'fields.id.min'='1',\n" +
                " 'fields.id.max'='50',\n" +
                " 'fields.age.min'='1',\n" +
                " 'fields.age.max'='150',\n" +
                " 'fields.name.length'='10'\n" +
                ")\n";
        System.out.println(sql);
        EnvironmentSettings settings = EnvironmentSettings
                .newInstance()
                .inStreamingMode()
                .build();

        TableEnvironment tEnv = TableEnvironment.create(settings);
        TableResult tableResult = tEnv.executeSql(sql);
        tableResult.print();


        Table datagen = tEnv.from("datagen");
        datagen.execute().print();
    }
}
