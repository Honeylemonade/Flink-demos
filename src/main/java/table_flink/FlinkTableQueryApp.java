package table_flink;

import org.apache.flink.table.api.*;

import static org.apache.flink.table.api.Expressions.$;
import static org.apache.flink.table.api.Expressions.row;

public class FlinkTableQueryApp {
    public static void main(String[] args) {
        EnvironmentSettings settings = EnvironmentSettings
                .newInstance()
                .inStreamingMode()
                .build();

        TableEnvironment tEnv = TableEnvironment.create(settings);

        // source
        Table sourceTable = tEnv.fromValues(DataTypes.ROW(
                        DataTypes.FIELD("id", DataTypes.INT()),
                        DataTypes.FIELD("name", DataTypes.STRING())
                ),
                row(1, "PK哥"),
                row(2, "张兰")
        );
        tEnv.createTemporaryView("sourceTable", sourceTable);


        //transform
        Table resultTable = tEnv.from("sourceTable")
                .select($("id"), $("name"));


        // sink
        tEnv.createTemporaryTable("sinkTable", TableDescriptor.forConnector("print")
                .schema(Schema.newBuilder()
                        .column("id", DataTypes.INT())
                        .column("name", DataTypes.STRING())
                        .build())
                .build());

        resultTable.executeInsert("sinkTable");

        sourceTable.printSchema();
        sourceTable.execute().print();
    }
}
