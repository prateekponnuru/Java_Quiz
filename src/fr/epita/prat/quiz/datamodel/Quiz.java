package fr.epita.prat.quiz.datamodel;

import fr.epita.prat.quiz.lib.QuizDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Quiz {
    private String _id;
    private String difficulty;
    private List<QuizQuestion> questions = new ArrayList<>();
    private String participant;
    private int score;
    public static QuizDAO dao = QuizDAO.getInstance();

    public String get_id() {
        return _id;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getParticipant() {
        return participant;
    }

    public List<QuizQuestion> getQuestions() {
        return questions;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<MCQ> getRandom(List<MCQ> questions, int num){
        Random rand = new Random();
        List<MCQ> newList = new ArrayList<>();
        for (int i=0; i < num; i++){
            int idx = rand.nextInt(questions.size());
            newList.add(questions.get(idx));
            questions.remove(idx);
        }
        return newList;
    }

    public void createQuiz(Boolean export, String num_questions){
        List<MCQ> questions = MCQ.dao.search(this.difficulty);
        questions = getRandom(questions, Integer.valueOf(num_questions));
        if(!export){this._id = dao.insert(this);}
        for (MCQ q: questions){
            QuizQuestion quiz_q = new QuizQuestion(q);
            if(!export){quiz_q.create(this);}
            this.questions.add(quiz_q);
        }

    }

    public Quiz(String difficulty, String name){
//        this.difficulty = MCQ.difficulty(difficulty);
        this.difficulty = difficulty;
        this.participant = name;
        this.score = 0;
    }

    public  Quiz(String difficulty){
        this.difficulty = difficulty;
    }

}
