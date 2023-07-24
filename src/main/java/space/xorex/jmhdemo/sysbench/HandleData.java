package space.xorex.jmhdemo.sysbench;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class HandleData {
    public static void main(String[] args) throws Exception {
        deleteData();
        prepareData();
    }

    public static void prepareData() throws Exception {
        String url = "jdbc:oracle:thin:@192.168.10.20:1522:LHRCDB";
        String username = "";
        String password = "";

        Class.forName("oracle.jdbc.driver.OracleDriver");
        try(Connection connection = DriverManager.getConnection(url, username, password)) {
            for (int i = 0; i < 10; i++) {
                int table = i + 1;
                String sql = "insert into sbtest" + table + " (id,k,c,pad) values(?,?,?,?)";
                try(PreparedStatement insert = connection.prepareStatement(sql)) {
                    for (int j = 1; j <= 1000000; j++) {
                        insert.setInt(1, j);
                        insert.setInt(2, j);
                        insert.setString(3,"68487932199-96439406143-93774651418-41631865787-96406072701-20604855487-25459966574-28203206787-41238978918-19503783441");
                        insert.setString(4,"22195207048-70116052123-74140395089-76317954521-98694025897");
                        insert.execute();
                    }
                }
            }

        }
    }


    public static void deleteData() throws Exception {
        String url = "jdbc:oracle:thin:@192.168.10.20:1522:LHRCDB";
        String username = "";
        String password = "";

        Class.forName("oracle.jdbc.driver.OracleDriver");
        try(Connection connection = DriverManager.getConnection(url, username, password)) {
            for (int i = 0; i < 10; i++) {
                int num = i+1;
                String sql = "delete from sbtest" + num;
                try(PreparedStatement delete = connection.prepareStatement(sql)) {
                    delete.execute();
                }

            }

        }
    }

}
