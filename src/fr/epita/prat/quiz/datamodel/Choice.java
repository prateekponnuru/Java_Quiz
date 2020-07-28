package fr.epita.prat.quiz.datamodel;

import fr.epita.prat.quiz.lib.ChoiceDAO;

public class Choice {
    private String _id;
    private String question_id;
    private String label;
    private String choice;
    private static ChoiceDAO dao = ChoiceDAO.getInstance();
//    private Media media;
    public String get_id() {
    return this._id;
}
    public String getChoice() {
        return this.choice;
    }
    public String getLabel() {
        return this.label;
    }
    public String getQuestionId() {
        return this.question_id;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public Choice(String choice, String label, MCQ q) {
        this.choice = choice;
        this.label = label;
        this.question_id = q.get_id();
    }
    public Choice(String _id, String choice, String label, String question_id) {
        this._id = _id;
        this.choice = choice;
        this.label = label;
        this.question_id = question_id;
    }
    public void Insert(){
        if(this._id == null){this._id = dao.create(this);}
    }
}
