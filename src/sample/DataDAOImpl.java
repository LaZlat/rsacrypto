package sample;

import java.sql.*;
import java.util.ArrayList;

public class DataDAOImpl implements DataDAO {

    private Connection connect() {
        String url = "jdbc:sqlite:C:/sqlite/rsaTask.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private void createNewTable() {
        Connection conn = this.connect();

        String sql = "CREATE TABLE IF NOT EXISTS rsadata (\n"
                + " id integer PRIMARY KEY,\n"
                + " publicKeyOne text NOT NULL,\n"
                + " publicKeyTwo text NOT NULL,\n"
                + " allText Text\n"
                + ");";

        try{
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void insert(int publicKeyOne, int publicKeyTwo, String allText) {
        String sql = "INSERT INTO rsadata(publicKeyOne, publicKeyTwo, allText) VALUES(?,?,?)";

        this.createNewTable();

        try{
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, publicKeyOne);
            pstmt.setInt(2, publicKeyTwo);
            pstmt.setString(3, allText);
            pstmt.executeUpdate();
            System.out.println("success on insert");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String fetchText() {
        Statement stmt = null;
        String allText = "error";

        try{
            stmt = this.connect().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT allText FROM rsadata WHERE id == 1");

            while (rs.next()){
                allText = rs.getString("allText");
            }

            rs.close();
            stmt.close();

            return allText;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return allText;
    }

    @Override
    public void delete() {
        String sql = "DELETE FROM rsadata where id = 1";

        this.createNewTable();

        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            System.out.println("success on delete");
        } catch (SQLException e) {
            System.out.println("it's empty!");
        }
    }

    @Override
    public ArrayList<Integer> fetchPublicKey() {
        Statement stmt = null;
        ArrayList<Integer> publicKey = new ArrayList<>();

        try{
            stmt = this.connect().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT publicKeyOne, publicKeyTwo FROM rsadata");

            while (rs.next()){
                publicKey.add(rs.getInt("publicKeyOne"));
                publicKey.add(rs.getInt("publicKeyTwo"));
            }

            rs.close();
            stmt.close();

            return publicKey;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return publicKey;
    }
}
