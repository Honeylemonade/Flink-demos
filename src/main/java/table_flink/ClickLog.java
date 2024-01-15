package table_flink;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class ClickLog {
    private String user;
    private String time;
    private String url;
}
