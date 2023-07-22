import java.sql.*;

public class DatabaseCommands {
    private Connection connect() {
        // SQLite connection string
        //String url = "jdbc:sqlite:src/db/tests.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/items", "root", "1234");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
/*REFERENCE FROM OLD BOT
    public ArrayList<Member> selectAll() throws ParseException{
        String sql = "SELECT id, name, sdate, edate FROM memberlist";

        try (Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            ArrayList<Member> mlist = new ArrayList<Member>();
            while (rs.next()) {
                mlist.add(new Member(rs.getInt("id"), rs.getString("name"), rs.getString("sdate"), rs.getString("edate")));
            }
            return mlist;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public ArrayList<Member> selectName(String name){
        String sql = "SELECT id, name, sdate, edate FROM memberlist WHERE name = ?";

        try (Connection conn = this.connect()) {
            PreparedStatement pstmt  = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            ArrayList<Member> mlist = new ArrayList<Member>();
            // loop through the result set
            while (rs.next()) {
                mlist.add(new Member(rs.getInt("id"), rs.getString("name"), rs.getString("sdate"), rs.getString("edate")));
            }
            if (mlist.isEmpty()) {
                return null;
            }
            return mlist;
        } catch (SQLException | ParseException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
 */
    public boolean insert(Integer id, String EN, String DE, String JSON) {
        String sql = "INSERT INTO items(id,EN,DE,JSON) VALUES(?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, EN);
            pstmt.setString(3, DE);
            pstmt.setString(4, JSON);
            pstmt.executeUpdate();
            conn.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean delete(String id) {
        String sql = "DELETE FROM items WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
            conn.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
