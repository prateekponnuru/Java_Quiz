package fr.epita.prat.quiz.commands;

import fr.epita.prat.quiz.datamodel.Choice;
import fr.epita.prat.quiz.datamodel.MCQ;
import fr.epita.prat.quiz.lib.Config;
import fr.epita.prat.quiz.lib.Database;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Admin {
    public static Scanner scanner = new Scanner(System.in);

    public static String getDifficulty(){
        String input = "";
        while (!input.matches(("1|2|3"))) {
            if(!input.equals("")){
                System.out.println("Invalid Input");
            }
            System.out.println("Choose Difficulty: 1|2|3");
            System.out.println("1. Easy");
            System.out.println("2. Moderate");
            System.out.println("3. Hard");
            input = scanner.nextLine();
        }
        return input;
    }

    public static String getUserText(String prompt){
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
        return text;
    }

    public static String getTags(){
        System.out.println("Enter tags for the question, separated by spaces.");
        System.out.print("> ");
        return scanner.nextLine();
    }

    public static Boolean checkUserAgreement(String question){
        String input = "";
        while (!input.matches(("Y|N|y|n"))) {
            if(!input.equals("")){
                System.out.println("Invalid Input");
            }
            System.out.println(question);
            System.out.print("> ");
            input = scanner.nextLine();
        }
        return true ? input.matches("Y|y") : false;
    }

    public static void askChoices(MCQ q){
        Boolean exit = false;
        String[] labels = {"a", "b", "c", "d", "e"};
        int idx = 0;
        String l = labels[idx];
        while (!exit){
            String c = getUserText("Enter Choice, when finished type '::End' in a newline");
            Choice choice = new Choice(c, l, q);
            choice.Insert();
            q.addChoice(choice);
            try{
                l = labels[++idx];
                exit = !checkUserAgreement("Do you want to enter another choice? Y|N");
            }
            catch (IndexOutOfBoundsException e){
                exit = true;
            }
        }
    }

    public static String getCorrectAns(List<String> ids){
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
        return input;
    }

    public static void execute(){
//        System.out.println("Working Directory = " + System.getProperty("user.dir"));
//        System.setProperty("user.dir", "C:\\myProjects\\epita\\java\\exercises\\src\\fr\\epita\\prat\\quiz\\");
//        try (Stream<Path> walk = Files.walk(Paths.get("C:\\myProjects\\epita\\java\\exercises\\src\\fr\\epita\\prat\\quiz"))) {
//
//            List<String> result = walk.filter(Files::isRegularFile)
//                    .map(x -> x.toString()).collect(Collectors.toList());
//
//            result.forEach(System.out::println);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Config conf = Config.getInstance();
        Database db = Database.getInstance();
        db.setParams(conf.get("db.url"), conf.get("db.user"), conf.get("db.password"));
        System.out.println("Welcome to the quiz application!");
//        System.out.println("What do you want to do?");
//        System.out.println("1. Add a question");
//        System.out.println("2. Find a question");
        List<MCQ> questions = new ArrayList<>();
        Boolean exit = false;
        while(!exit){
            String question = getUserText("Enter a question..");
            String difficulty = getDifficulty();
            String tags = getTags();

            MCQ q = new MCQ(question, difficulty, tags);
            q.Insert();

            if (checkUserAgreement("Does the question have choices? Y|N")){
                askChoices(q);
                List<String> choice_labels = new ArrayList<>();
                for(Choice c: q.getChoices()){
                    String label = c.getLabel();
                    choice_labels.add(label);
                    System.out.println("choice: " + label);
                    System.out.println(c.getChoice());
                }
                System.out.println("Enter the label of the correct choice");
                q.insertAnswer(getCorrectAns(choice_labels));
            }
            questions.add(q);
            exit = !checkUserAgreement("Do you want to enter another question? Y|N");
        }
        for(MCQ q: questions){
            System.out.print(q.str());
        }

    }
}
