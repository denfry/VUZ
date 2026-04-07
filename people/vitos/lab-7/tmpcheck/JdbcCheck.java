import java.sql.*;
public class JdbcCheck {
  static void test(String url) {
    try (Connection c = DriverManager.getConnection(url, "trading_bot", "devpassword")) {
      try (Statement s = c.createStatement(); ResultSet rs = s.executeQuery("select current_user, current_database()")) {
        rs.next();
        System.out.println("OK " + url + " -> " + rs.getString(1) + "/" + rs.getString(2));
      }
    } catch (Exception e) {
      System.out.println("FAIL " + url + " -> " + e.getMessage());
    }
  }
  public static void main(String[] args) {
    test("jdbc:postgresql://localhost:5432/trading_bot");
    test("jdbc:postgresql://127.0.0.1:5432/trading_bot");
    test("jdbc:postgresql://[::1]:5432/trading_bot");
  }
}
