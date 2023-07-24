package space.xorex.jmhdemo.oracle11g;

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
    public static void main(String[] args) throws RunnerException {

        Options opt = new OptionsBuilder()
                .include(TestUDFPerformance.class.getName())
                .forks(1)
                .threads(200)
                .warmupIterations(1)
                .warmupTime(new TimeValue(30,TimeUnit.SECONDS))
                .measurementIterations(2)
                .measurementTime(new TimeValue(120,TimeUnit.SECONDS))
                .build();
        new Runner(opt).run();
    }

    public PreparedStatement read;

    public PreparedStatement update;

    public Connection connection;

    @Setup(Level.Trial)
    public void setup() throws Exception {
        String url = "jdbc:oracle:thin:@IP:Port:helowin";
        String username = "system";
        String password = "";

        Class.forName("oracle.jdbc.driver.OracleDriver");
        connection = DriverManager.getConnection(url,username,password);

        read = connection.prepareStatement("select udf_unprefix(c) from sbtest1 where id = ?");
        update = connection.prepareStatement("update sbtest1 set c = udf_prefix(c) where id = ?");
    }

    @Benchmark
    public void ReadWrite() throws Exception {
        int num = (int)(Math.random()*100000);
        update.setInt(1, num);
        update.executeUpdate();
        read.setInt(1, num);
        read.executeQuery();
    }

    @TearDown(Level.Trial)
    public void teardown() throws Exception {
        read.close();
        update.close();
        connection.close();
    }

}