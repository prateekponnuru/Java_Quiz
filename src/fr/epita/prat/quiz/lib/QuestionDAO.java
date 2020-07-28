package fr.epita.prat.quiz.lib;

import fr.epita.prat.quiz.datamodel.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionDAO {
    private static QuestionDAO dao;
    public static QuestionDAO getInstance(){
        if (dao == null){
            dao = new QuestionDAO();
        }
        return dao;
    }

    //CRUD : CREATE, READ, UPDATE, DELETE
    public String create(Question question) {
        String _id = "";
        try {
            String SQL_QUERY = "INSERT INTO QUESTION(question, difficulty, tags) VALUES(?, ?, ?);";
            Connection connection = Database.getInstance().getConnection();
            PreparedStatement pstmt = connection.prepareStatement(SQL_QUERY, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, question.getQuestion());
            pstmt.setInt(2, Integer.valueOf(question.getDifficulty()));
            pstmt.setString(3, String.join(",", question.getTags()));
            pstmt.executeUpdate();
            ResultSet keys = pstmt.getGeneratedKeys();
            if(keys.next()){
                 _id = keys.getString("_id");
            }
//            System.out.println(pstmt.getResultSet());
//            pstmt.close();
//            connection.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return _id;
    }

    public List<Question> search() {
        List<Question> questionResult = new ArrayList<>();
        Question q;
        try {
            Connection connection = Database.getInstance().getConnection();
            Statement sql = connection.createStatement();
            ResultSet result = sql.executeQuery(String.format("SELECT _id, question, difficulty, tags FROM %s;", "QUESTION"));

            while (result.next()) {
                q = new Question(result.getString(1), result.getString(2), result.getString(3), result.getString(4));
                questionResult.add(q);
            }
//            sql.close();
//            connection.close();
        }
        catch (SQLException e){
            System.out.println("Exception occurred while fetching all records from table.");
            e.printStackTrace();
        }

        return questionResult;
    }

    public void update(Question question) {

    }

    public void delete(Question question) {

    }


}

