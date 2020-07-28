package fr.epita.prat.quiz.lib;

import fr.epita.prat.quiz.datamodel.MCQ;
import fr.epita.prat.quiz.datamodel.Quiz;
import fr.epita.prat.quiz.datamodel.QuizQuestion;

import java.sql.*;
import java.util.List;

public class QuizQuestionDAO {
    private static QuizQuestionDAO dao;
    public static QuizQuestionDAO getInstance(){
        if (dao == null){
            dao = new QuizQuestionDAO();
        }
        return dao;
    }

    public String insert(QuizQuestion question, Quiz quiz) {
        String _id = "";
        try {
            String SQL_QUERY = "INSERT INTO QUIZ_QUESTION(quiz_id, question_id) VALUES(?, ?);";
//            for (MCQ q: questions){
//                SQL_QUERY += String.format("INSERT INTO QUIZ_QUESTIONS(quiz_id, question_id) " +
//                                "VALUES(?, ?);\n", quiz.get_id(), q.get_id());
//            pstmt.close();
//            connection.close();
//            }
            Connection connection = Database.getInstance().getConnection();
            PreparedStatement pstmt = connection.prepareStatement(SQL_QUERY, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, quiz.get_id());
            pstmt.setString(2, question.get_id());
            pstmt.executeUpdate();
            ResultSet keys = pstmt.getGeneratedKeys();
            if(keys.next()){
                _id = keys.getString("_id");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return _id;
    }

    public void update(QuizQuestion question) {
        try {
            String SQL_QUERY = "UPDATE QUIZ_QUESTION SET USER_ANS=?, VALID_ANS=? WHERE _ID=?;";
            Connection connection = Database.getInstance().getConnection();
            PreparedStatement pstmt = connection.prepareStatement(SQL_QUERY);
            pstmt.setString(1, question.getUserAnswer());
            pstmt.setBoolean(2, question.getValidAns());
            pstmt.setInt(3, Integer.valueOf(question.get_id()));
            pstmt.executeUpdate();
//            pstmt.close();
//            connection.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
