import java.sql.*;
import java.util.Scanner;

public class VotingDAO {

    Connection con = DBConnection.connect();

    public void showCandidates() {

        try {

            String query = "SELECT * FROM candidates";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            System.out.println("\nCandidates List");

            while (rs.next()) {

                System.out.println(
                        rs.getInt("candidate_id") + " - " +
                        rs.getString("candidate_name") +
                        " Votes: " +
                        rs.getInt("votes"));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean validateVoter(int voterId) {

        try {

            String query = "SELECT * FROM voters WHERE voter_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, voterId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                if (rs.getBoolean("has_voted")) {
                    System.out.println("You already voted");
                    return false;
                }

                return true;
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("Invalid Voter ID");
        return false;
    }

    public void castVote(int voterId, int candidateId) {

        try {

            String voteQuery =
                    "UPDATE candidates SET votes = votes + 1 WHERE candidate_id=?";

            PreparedStatement ps = con.prepareStatement(voteQuery);
            ps.setInt(1, candidateId);
            ps.executeUpdate();

            String voterQuery =
                    "UPDATE voters SET has_voted = true WHERE voter_id=?";

            PreparedStatement ps2 = con.prepareStatement(voterQuery);
            ps2.setInt(1, voterId);
            ps2.executeUpdate();

            System.out.println("Vote Cast Successfully");

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
