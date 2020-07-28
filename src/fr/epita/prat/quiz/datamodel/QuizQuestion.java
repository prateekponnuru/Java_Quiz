package fr.epita.prat.quiz.datamodel;

import fr.epita.prat.quiz.lib.QuizQuestionDAO;

public class QuizQuestion {
    private String _id;
    private MCQ question;
    private String userAnswer;
    private Boolean validAns = false;
    public static QuizQuestionDAO dao = QuizQuestionDAO.getInstance();

    public String get_id() {
        return _id;
    }

    public MCQ getQuestion() {
        return question;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public Boolean getValidAns() {
        return validAns;
    }

    public boolean setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
        if (this.question.getAnswer() == null || this.question.getAnswer().equals(userAnswer)){
            this.validAns = true;
        }else{
            this.validAns = false;
        }
        dao.update(this);
        return this.validAns;
    }

    public QuizQuestion(MCQ question, String ans){
        this.question = question;
        this.userAnswer = ans;
    }
    public QuizQuestion(MCQ question){
        this.question = question;
    }

    public void create(Quiz quiz){
        if(this._id == null){this._id = dao.insert(this, quiz);}
    }

}
