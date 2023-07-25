package space.xorex.jmhdemo.update;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
public class TestUDFPerformance {
    public static void main(String[] args) throws Exception {

        Options opt = new OptionsBuilder()
                .include(space.xorex.jmhdemo.update.TestUDFPerformance.class.getName())
                .forks(1)
                .threads(200)
                .warmupIterations(1)
                .warmupTime(new TimeValue(30,TimeUnit.SECONDS))
                .measurementIterations(2)
                .measurementTime(new TimeValue(60,TimeUnit.SECONDS))
                .build();
        new Runner(opt).run();

    }

    public PreparedStatement update;

    public Connection connection;

    @Setup(Level.Trial)
    public void setup() throws Exception {
        String url = "jdbc:oracle:thin:@192.168.10.20:1522:LHRCDB";
        String username = "system";
        String password = "";

        Class.forName("oracle.jdbc.driver.OracleDriver");
        connection = DriverManager.getConnection(url,username,password);

        update = connection.prepareStatement("update sbtest1 set c = simpleReturn(c) where id = ?");
    }

    @Benchmark
    public void ReadWrite() throws Exception {
        int num = (int)(Math.random()*1000000);
        update.setInt(1, num);
        update.executeUpdate();
    }

    @TearDown(Level.Trial)
    public void teardown() throws Exception {
        update.close();
        connection.close();
    }

}
