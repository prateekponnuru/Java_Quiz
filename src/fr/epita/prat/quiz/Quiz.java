package fr.epita.prat.quiz;

import fr.epita.prat.quiz.commands.Admin;
import fr.epita.prat.quiz.commands.Export;
import fr.epita.prat.quiz.commands.Migrate;
import fr.epita.prat.quiz.commands.Play;

public class Quiz {
    public static void main(String[] args){
        if (args.length == 0){
            System.out.println("Please use one of the commands: -migrate -manage -play -export");
        }
        else if(args[0].equals("-migrate")) {
            try {
                if (args[1].equals("-d") || args[1].equals("-drop")) {
                    Migrate.execute(true);
                }
            } catch (IndexOutOfBoundsException e) {
                Migrate.execute(false);
            }
        }
        else if(args[0].equals("-manage")){
            Admin.execute();
        }
        else if(args[0].equals("-play")){
            Play.execute();
        }
        else if(args[0].equals("-export")){
            Export.execute();
        }
    }
}
