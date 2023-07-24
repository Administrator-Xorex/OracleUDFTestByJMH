package space.xorex.jmhdemo.oracle11g;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class HandleData {
    public static void main(String[] args) throws Exception {
        deleteData();
        prepareData();
    }
    public static void prepareData() throws Exception {
        String url = "jdbc:oracle:thin:@IP:Port:helowin";
        String username = "system";
        String password = "";

        Class.forName("oracle.jdbc.driver.OracleDriver");
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            try (PreparedStatement insert = connection.prepareStatement("insert into sbtest1 (id,c) values(?,?) ")) {
                for (int i = 1; i <= 100000; i++) {
                    insert.setInt(1, i);
                    insert.setString(2, String.valueOf(i));
                    insert.execute();
                }
            }
        }
    }

    public static void deleteData() throws Exception {
        String url = "jdbc:oracle:thin:@IP:Port:helowin";
        String username = "system";
        String password = "";

        Class.forName("oracle.jdbc.driver.OracleDriver");
        try(Connection connection = DriverManager.getConnection(url, username, password)) {
            try(PreparedStatement insert = connection.prepareStatement("delete from sbtest1")) {
                insert.execute();
            }
        }
    }

}
