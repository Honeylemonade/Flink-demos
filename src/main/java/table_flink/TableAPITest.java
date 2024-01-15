package table_flink;

import org.apache.flink.table.api.*;

import static org.apache.flink.table.api.Expressions.$;
import static org.apache.flink.table.api.Expressions.row;

public class TableAPITest {
    public static void main(String[] args) {
        EnvironmentSettings settings = EnvironmentSettings
                .newInstance()
                .inStreamingMode()
                .build();


        TableEnvironment tEnv = TableEnvironment.create(settings);

        // source
        Table sourceTable = tEnv.fromValues(DataTypes.ROW(
                        DataTypes.FIELD("user", DataTypes.STRING()),
                        DataTypes.FIELD("time", DataTypes.STRING()),
                        DataTypes.FIELD("url", DataTypes.STRING())
                ),
                row("Mary", "12:00:00", "./home"),
                row("Bob", "12:00:00", "./cart"),
                row("Mary", "12:00:05", "./prod?id=1"),
                row("Liz", "12:01:05", "./home"),
                row("Bob", "12:01:30", "./prod?id=3"),
                row("Mary", "12:01:40", "./prod?id=4")
        );


        //transform select user, count(1) cnt from xxx group by user;
        sourceTable.groupBy($("user"))
                .select($("user"), $("url").count().as("cnt"))
                .execute().print();

    }
}
