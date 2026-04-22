import com.lab7.db.DatabaseManager;
import java.sql.*;

public class H2Smoke {
  public static void main(String[] args) throws Exception {
    DatabaseManager.connectEmbedded();
    try (Statement st = DatabaseManager.getConnection().createStatement()) {
      try (ResultSet rs = st.executeQuery("select count(*) from users")) { rs.next(); System.out.println("users="+rs.getInt(1)); }
      try (ResultSet rs = st.executeQuery("select count(*) from analyses")) { rs.next(); System.out.println("analyses="+rs.getInt(1)); }
    }
    DatabaseManager.disconnect();
  }
}
