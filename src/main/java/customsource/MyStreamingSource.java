package customsource;

import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.Random;

public class MyStreamingSource implements SourceFunction<Integer> {

    public boolean isRunning = true;

    @Override
    public void run(SourceContext<Integer> ctx) throws Exception {
        while (isRunning) {
            ctx.collect(new Random().nextInt(100));
            Thread.sleep(100);
        }
    }

    @Override
    public void cancel() {
        isRunning = false;
    }
}
