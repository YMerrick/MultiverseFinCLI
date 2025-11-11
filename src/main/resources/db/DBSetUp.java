import javax.swing.plaf.nimbus.State;
import java.sql.*;

public class DBSetUp {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:app.db";
        createCredentialTable(url);
    }

    private static void createCredentialTable(String url) {
        String sql = """
                CREATE TABLE IF NOT EXISTS credentials (
                    id INTEGER PRIMARY KEY,
                    userId text NOT NULL,
                    username text NOT NULL,
                    passwordHash text NOT NULL
                    );""";

        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}