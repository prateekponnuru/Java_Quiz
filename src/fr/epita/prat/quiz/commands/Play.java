package fr.epita.prat.quiz.commands;

import fr.epita.prat.quiz.datamodel.Choice;
import fr.epita.prat.quiz.datamodel.MCQ;
import fr.epita.prat.quiz.datamodel.Quiz;
import fr.epita.prat.quiz.datamodel.QuizQuestion;
import fr.epita.prat.quiz.lib.Config;
import fr.epita.prat.quiz.lib.Database;
import fr.epita.prat.quiz.lib.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Play {
    public static Scanner scanner = new Scanner(System.in);

    public static String getUserText(String prompt){
//        Scanner scanner = new Scanner(System.in);
        System.out.println(prompt);
        String text = "";
        String input = "";
        boolean nextline = true;
//        input.matches(".*\\\\") | input.equals("")
        while (nextline) {
            System.out.print("> ");
            input = scanner.nextLine();
            int linebreak = input.lastIndexOf("\\");
            if(input.matches(".*\\\\")){
                input = input.substring(0, input.lastIndexOf("\\")) + "\n";
            }
            else{
                nextline = false;
            }
            text += input;
        }
//        scanner.close();
        return text;
    }

    public static String getDifficulty(){
//        Scanner scanner = new Scanner(System.in);
        String input = "";
        while (!input.matches(("1|2|3"))) {
            if(!input.equals("")){
                System.out.println("Invalid Input");
            }
            System.out.println("Choose Difficulty: 1|2|3");
            System.out.println("1. Easy");
            System.out.println("2. Moderate");
            System.out.println("3. Hard");
            System.out.print("> ");
            input = scanner.nextLine();
        }
//        scanner.close();
        return input;
    }

    public static Boolean checkUserAgreement(String question){
//        Scanner scanner = new Scanner(System.in);
        String input = "";
        while (!input.matches(("Y|N|y|n"))) {
            if(!input.equals("")){
                System.out.println("Invalid Input");
            }
            System.out.println(question);
            System.out.print("> ");
            input = scanner.nextLine();
        }
//        scanner.close();
        return true ? input.matches("Y|y") : false;
    }

    public static String getCorrectAns(List<String> ids){
//        Scanner scanner = new Scanner(System.in);
        String input = "";
        int tries = 0;
        while (!ids.contains(input)) {
            if(!input.equals("")){
                System.out.println("Invalid Input");
            }
//            System.out.println(question);
            System.out.print("> ");
            input = scanner.nextLine();
        }
//        scanner.close();
        System.out.println("");
        return input;
    }

    public static void playQuiz(Quiz quiz) {
//        Utils utils = Utils.getInstance();
        int i = 0;
        for (QuizQuestion question : quiz.getQuestions()) {
            MCQ q = question.getQuestion();
            System.out.println(q.quizStr(false, ++i));
            if (q.getChoices().isEmpty()) {
                question.setUserAnswer(getUserText("Enter you answer.."));
            }else{
                List<String> choice_labels = new ArrayList<>();
                for(Choice c: q.getChoices()){
                    choice_labels.add(c.getLabel());
                }
                System.out.println("Type the label of your answer..");
                if (question.setUserAnswer(getCorrectAns(choice_labels))){
                    quiz.setScore(quiz.getScore()+1);
                }
            }
        }
    }

    public static void showResults(Quiz quiz) {
        Utils utils = Utils.getInstance();
        System.out.println("\nYou have scored: "+quiz.getScore());
        int i = 0;
        for (QuizQuestion question : quiz.getQuestions()) {
            MCQ q = question.getQuestion();
            System.out.println("Question: "+String.valueOf(++i));
            System.out.println(q.getQuestion());
            for (Choice c: q.getChoices()){
                System.out.print(c.getLabel());
                if (q.getAnswer().equals(c.getLabel())){
                    System.out.println(" [CORRECT ANSWER]");
                }else{
                    System.out.print("\n");
                }
                System.out.println(c.getChoice());
            }
            System.out.println("Your Answer: "+question.getUserAnswer()+"\n");
        }
    }

    public static void execute() {
        Config conf = Config.getInstance();
        Database db = Database.getInstance();
        db.setParams(conf.get("db.url"), conf.get("db.user"), conf.get("db.password"));
        System.out.println("Welcome to the quiz application!");
        Utils utils = Utils.getInstance();
        String participant = getUserText("Enter a display name..");
//        String participant = "";
        Boolean exit = false;
        while (!exit) {
            String difficulty = getDifficulty();
            Quiz quiz = new Quiz(difficulty, participant);
            quiz.createQuiz(false, conf.get("quiz.questions"));
            playQuiz(quiz);
            showResults(quiz);
            exit = !checkUserAgreement("Would you like to take a new quiz?");
        }
    }
}
