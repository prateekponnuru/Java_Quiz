package fr.epita.prat.quiz.lib;

import fr.epita.prat.quiz.datamodel.Choice;
import fr.epita.prat.quiz.datamodel.MCQ;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChoiceDAO {

    private static ChoiceDAO dao;
    public static ChoiceDAO getInstance(){
        if (dao == null){
            dao = new ChoiceDAO();
        }
        return dao;
    }

    public String create(Choice choice) {
        String _id = "";
        try {
            String SQL_QUERY = "INSERT INTO CHOICE(choice, label, question_id) VALUES(?, ?, ?);";
            Connection connection = Database.getInstance().getConnection();
            PreparedStatement pstmt = connection.prepareStatement(SQL_QUERY, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, choice.getChoice());
            pstmt.setString(2, choice.getLabel());
            pstmt.setInt(3, Integer.valueOf(choice.getQuestionId()));
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

    public List<Choice> search(MCQ q) {
        List<Choice> choices = new ArrayList<>();
        Choice c;
        try {
            Connection connection = Database.getInstance().getConnection();
            Statement sql = connection.createStatement();
            ResultSet result = sql.executeQuery(String.format("SELECT _id, choice FROM %s where question_id=%d;", "QUESTION", Integer.valueOf(q.get_id())));

            while (result.next()) {
                c = new Choice(result.getString("_id"),
                        result.getString("choice"),
                        result.getString("label"),
                        result.getString("question_id"));
                choices.add(c);
            }
            sql.close();
            connection.close();
        }
        catch (SQLException e){
            System.out.println("Exception occurred while fetching all records from table.");
            e.printStackTrace();
        }

        return choices;
    }

}
