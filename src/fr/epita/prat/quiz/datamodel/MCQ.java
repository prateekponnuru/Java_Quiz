package fr.epita.prat.quiz.datamodel;

import fr.epita.prat.quiz.lib.MCQDAO;

import javax.print.attribute.standard.Media;
import java.util.ArrayList;
import java.util.List;

public class MCQ extends Question {

    private String _id;
    private List<Choice> choices = new ArrayList<>();
    private String answer;
    public static MCQDAO dao = MCQDAO.getInstance();

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<Choice> getChoices() {
        return this.choices;
    }

    public void addChoice(String choice, String label, Boolean isAns) {
        Choice choice_ = new Choice(choice, label, this);
//        if(isAns){this.answer = choice_;}
        this.choices.add(choice_);
    }

    public void addChoice(Choice choice) {
        this.choices.add(choice);
    }


    public MCQ(String question, String difficulty, String tags) {
        super(question, difficulty, tags);
    }

    public MCQ(String _id, String question, String difficulty, String tags, String ans_id) {
        super(_id, question, difficulty, tags);
        this._id = _id;
        this.answer = ans_id;
    }

    public void Insert(){
        super.Insert();
        this._id = super.get_id();
        dao.insert(this);
    }

    public void insertAnswer(String answer) {
        this.answer = answer;
        dao.update(this);
    }

    public String str(){
        String s = "";
        s = String.format("question_id: %s\n%s\nDifficulty: %s\nTags: %s\n",
                this._id, this.getQuestion(), this.getDifficultyStr(), this.getTags());
//        [3m[0m
        if(!this.choices.isEmpty()){
            s += "\n";
            for (Choice c: this.choices){
                if(this.answer.equals(c.getLabel())){
                    s += String.format("%s.[CORRECT ANSWER]\n%s\n", c.getLabel(), c.getChoice());
                }
                else{
                    s += String.format("%s.\n%s\n", c.getLabel(), c.getChoice());
                }
            }
        }
        return s;
    }

    public String quizStr(Boolean showAns, int i){
        String s = "";
        s = String.format("question: %d\n%s\n", i, this.getQuestion());
        if(!this.choices.isEmpty()){
            s += "\n";
            for (Choice c: this.choices){
                if(this.answer.equals(c.getLabel()) && showAns){
                    s += String.format("%s.[CORRECT ANSWER]\n%s\n", c.getLabel(), c.getChoice());
//                    s += "\nCORRECT ANSWER";
                }
                else{
                    s += String.format("%s.\n%s\n", c.getLabel(), c.getChoice());
                }
            }
        }
        return s;
    }
}
