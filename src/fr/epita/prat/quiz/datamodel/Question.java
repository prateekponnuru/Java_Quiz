package fr.epita.prat.quiz.datamodel;

import fr.epita.prat.quiz.lib.QuestionDAO;

import javax.print.attribute.standard.Media;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Question {
    private String _id;

    public String get_id() {
        return _id;
    }

    private String question;
//    private Media media;
//    private static HashMap<String, Difficulty> listDifficulties = new HashMap<>();
//    public enum Difficulty{
//        HARD(3),
//        MODERATE(2),
//        EASY(1);
//
//        private int val;
//        Difficulty(int v){
//            Question.listDifficulties.put(String.valueOf(v), this);
//            val = v;
//        }
//
//        public int getVal(){return val;}
//    }

//    public static Difficulty difficulty(String val){
//        return listDifficulties.get(val);
//    }

    private String difficulty;

    private List<String> tags;

    private static QuestionDAO dao = QuestionDAO.getInstance();

    public List<String> getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = Arrays.asList(tags.split("\\s|,|;"));
        List<String> invalidTags = new ArrayList<String>();
        Boolean valid = true;
        for(String tag: this.tags){
            if (tag.length() > 25){
                invalidTags.add(tag);
                valid = false;
            }
        }
        if(!valid){
            System.out.printf("Invalid Tags: %s", new Object[] {String.join(",", invalidTags)});
        }
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDifficulty() { return this.difficulty;}

    public String getDifficultyStr() {
        switch (this.difficulty){
            case "1":
                return "Easy";
            case "2":
                return "Moderate";
            default:
                return "Hard";
        }
    }

    public void setDifficulty(String d) {
//        switch (d){
//            case "1":
//                this.difficulty = Difficulty.EASY;
//                break;
//            case "2":
//                this.difficulty = Difficulty.MODERATE;
//                break;
//            case "3":
//                this.difficulty = Difficulty.HARD;
//                break;
//            default:
//                System.out.println("Something went wrong!");
//        }
        this.difficulty = d;
    }


    public Question(String question, String difficulty, String tags) {
        this.question = question;
        this.setDifficulty(difficulty);
        this.setTags(tags);
    }
    public Question(String _id, String question, String difficulty, String tags) {
        this._id = _id;
        this.question = question;
        this.setDifficulty(difficulty);
        this.setTags(tags);
    }

    public void Insert(){
        if(this._id == null){this._id = dao.create(this);}
    }
}
