import java.sql.*;

public class DatabaseCommands {
    public void createNewDatabase(String fileName) {

        //String url = "jdbc:sqlite:src/db/" + fileName;
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/member", "root", "1234")) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createNewTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:src/db/tests.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS member (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	sdate text NOT NULL,\n"
                + "	edate text NOT NULL\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Connection connect() {
        // SQLite connection string
        //String url = "jdbc:sqlite:src/db/tests.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/member", "root", "1234");
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
 */
/*REFERENCE FROM OLD BOT
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
    public boolean insert(String name, String sdate, String edate) {
        String sql = "INSERT INTO memberlist(name,sdate,edate) VALUES(?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, sdate);
            pstmt.setString(3, edate);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean delete(String id) {
        String sql = "DELETE FROM member WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
