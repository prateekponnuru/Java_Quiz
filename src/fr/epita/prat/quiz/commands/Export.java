package fr.epita.prat.quiz.commands;

import fr.epita.prat.quiz.datamodel.Choice;
import fr.epita.prat.quiz.datamodel.MCQ;
import fr.epita.prat.quiz.datamodel.Quiz;
import fr.epita.prat.quiz.datamodel.QuizQuestion;
import fr.epita.prat.quiz.lib.Config;
import fr.epita.prat.quiz.lib.Database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Export {

    public static Scanner scanner = new Scanner(System.in);

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

    public static void export(Quiz quiz, String loc){
        PrintWriter writer;
            try {
                writer = new PrintWriter(new File(loc+"/quiz_export.txt"));
            } catch (FileNotFoundException e) {
                System.out.println("not able to open the file in write mode : " + e.getMessage());
                System.out.println("exiting...");
                return;
            }

            for (QuizQuestion q : quiz.getQuestions()) {
                int i = 0;
                MCQ mcq = q.getQuestion();
                System.out.println(++i+".");
                System.out.println(mcq.getQuestion());
                for (Choice c: mcq.getChoices()){
                    System.out.println(c.getLabel());
                    System.out.println(c.getChoice());
                }
                System.out.print("\n");
            }
            writer.flush();
            writer.close();
    }

    public static void execute(){
        Config conf = Config.getInstance();
        Database db = Database.getInstance();
        db.setParams(conf.get("db.url"), conf.get("db.user"), conf.get("db.password"));
        System.out.println("Welcome to the quiz application!");
        String difficulty = getDifficulty();
        Quiz quiz = new Quiz(difficulty);
        quiz.createQuiz(true, conf.get("quiz.questions"));
        export(quiz, conf.get("quiz.export.to"));
    }

}
