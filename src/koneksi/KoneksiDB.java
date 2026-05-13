package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KoneksiDB {

    private static final String URL = "jdbc:mysql://localhost:3306/db_ekspedisi";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            throw new SQLException("Driver MySQL tidak ditemukan. "
                    + "Tambahkan mysql-connector-java ke Libraries project.", ex);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
