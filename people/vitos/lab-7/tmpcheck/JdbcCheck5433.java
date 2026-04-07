import java.sql.*;
public class JdbcCheck5433 {
  public static void main(String[] args) {
    try (Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5433/trading_bot", "trading_bot", "devpassword")) {
      try (Statement s = c.createStatement(); ResultSet rs = s.executeQuery("select current_user, current_database()")) {
        rs.next();
        System.out.println("OK -> " + rs.getString(1) + "/" + rs.getString(2));
      }
    } catch (Exception e) {
      System.out.println("FAIL -> " + e.getMessage());
      System.exit(1);
    }
  }
}
