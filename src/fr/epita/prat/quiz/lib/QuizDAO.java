package fr.epita.prat.quiz.lib;

import fr.epita.prat.quiz.datamodel.Quiz;

import java.sql.*;

public class QuizDAO {
    private static QuizDAO dao;
    public static QuizDAO getInstance(){
        if (dao == null){
            dao = new QuizDAO();
        }
        return dao;
    }

    public String insert(Quiz quiz) {
        String _id = "";
        try {
            String SQL_QUERY = "INSERT INTO QUIZ(difficulty, participant, score) VALUES(?, ?, ?);";
            Connection connection = Database.getInstance().getConnection();
            PreparedStatement pstmt = connection.prepareStatement(SQL_QUERY, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, Integer.valueOf(quiz.getDifficulty()));
            pstmt.setString(2, quiz.getParticipant());
            pstmt.setInt(3, 0);
            pstmt.executeUpdate();
            ResultSet keys = pstmt.getGeneratedKeys();
            if(keys.next()){
                _id = keys.getString("_id");
            }
//            pstmt.close();
//            connection.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return _id;
    }
}
