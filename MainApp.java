import java.sql.*;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {

            Connection con = DBConnection.connect();
            System.out.println("Database Connected");

            System.out.print("Enter Voter ID: ");
            String voterId = sc.next();   // FIXED

            System.out.print("Enter Candidate ID: ");
            int candidateId = sc.nextInt();

            String check = "SELECT * FROM voters WHERE voter_id=?";
            PreparedStatement ps = con.prepareStatement(check);
            ps.setString(1, voterId);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                System.out.println("Invalid voter");
                return;
            }

            System.out.println("Voter Verified");

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
