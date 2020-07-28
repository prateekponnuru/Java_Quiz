package fr.epita.prat.quiz.lib;

import fr.epita.prat.quiz.datamodel.MCQ;
import fr.epita.prat.quiz.datamodel.Choice;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MCQDAO {

    private static MCQDAO dao;
    public static MCQDAO getInstance(){
        if (dao == null){
            dao = new MCQDAO();
        }
        return dao;
    }

    public String insert(MCQ question) {
        String _id = "";
        try {
            String SQL_QUERY = "INSERT INTO QUESTION_EXT1(_id) VALUES(?);";
            Connection connection = Database.getInstance().getConnection();
            PreparedStatement pstmt = connection.prepareStatement(SQL_QUERY);
            pstmt.setString(1, question.get_id());
            pstmt.executeUpdate();
//            pstmt.close();
//            connection.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return _id;
    }

    public void update(MCQ question) {
        try {
            String SQL_QUERY = "UPDATE QUESTION_EXT1 SET correct_choice_label=? WHERE _ID=?;";
            Connection connection = Database.getInstance().getConnection();
            PreparedStatement pstmt = connection.prepareStatement(SQL_QUERY);
            pstmt.setString(1, question.getAnswer());
            pstmt.setInt(2, Integer.valueOf(question.get_id()));
            pstmt.executeUpdate();
//            pstmt.close();
//            connection.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public List<MCQ> search(String difficulty) {
//        List<MCQ>  = new ArrayList<>();
        HashMap<String, MCQ> questions = new HashMap<>();
        String mcqIds ="";
        MCQ q;
        try {
            Connection connection = Database.getInstance().getConnection();
            Statement sql = connection.createStatement();
            ResultSet result = sql.executeQuery(
                    String.format("SELECT a._id, a.question, a.difficulty, a.tags, b.correct_choice_label " +
                                    "FROM QUESTION a, QUESTION_EXT1 b " +
                            "WHERE a._id = b._id and a.DIFFICULTY= %d;", Integer.valueOf(difficulty)));

            while (result.next()) {
                q = new MCQ(result.getString(1), result.getString(2),
                        result.getString(3), result.getString(4),
                        result.getString(5));
                if (result.getString(5) != null){
                    mcqIds += q.get_id()+", ";
                }
                questions.put(q.get_id(), q);
            }
            if (mcqIds.matches(".*,\\s")){
                mcqIds = mcqIds.substring(0, mcqIds.lastIndexOf(","));
            }
            result = sql.executeQuery(String.format("SELECT _id, question_id, label, choice " +
                    "FROM choice WHERE question_id IN (%s) ORDER BY label", mcqIds));
            while (result.next()){
                questions.get(result.getString(2)).addChoice(
                        new Choice(result.getString(1),
                                result.getString(4),
                                result.getString(3),
                                result.getString(2)));
            }
        }
        catch (SQLException e){
            System.out.println("Exception occurred while fetching all records from table.");
            e.printStackTrace();
        }

        return new ArrayList<>(questions.values());
    }

}
