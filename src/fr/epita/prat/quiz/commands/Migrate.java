package fr.epita.prat.quiz.commands;
import fr.epita.prat.quiz.lib.Config;
import fr.epita.prat.quiz.lib.Database;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Migrate {
    public static void execute(Boolean drop){
        Config conf = Config.getInstance();
        Database db = Database.getInstance();
        db.setParams(conf.get("db.url"), conf.get("db.user"), conf.get("db.password"));

        if (drop) {
//            if (args[0].equals("-drop") || args[0].equals("-d")) {
            List<String> tables = new ArrayList<>();
            tables.add("QUESTION");
            tables.add("CHOICE");
            tables.add("QUESTION_EXT1");
            tables.add("QUIZ");
            tables.add("QUIZ_QUESTION");
            db.dropTables(tables);
//            }
        }

        String table = "QUESTION";
        Map<String, String> columns = Map.of(
                "_id", "bigint auto_increment PRIMARY KEY",
                "question", "varchar(2000)",
                "difficulty", "Int",
                "tags", "varchar(500)"
        );
        db.createTable(table, columns);
//        db.selectAll(table);
        table = "CHOICE";
        columns = Map.of(
                "_id", "bigint auto_increment PRIMARY KEY",
                "question_id", "bigint",
                "label", "varchar(2)",
                "choice", "varchar(1000)"
        );
        db.createTable(table, columns);

        table = "QUESTION_EXT1";
        columns = Map.of(
                "_id", "bigint PRIMARY KEY",
                "correct_choice_label", "varchar(2)"
        );
        db.createTable(table, columns);

        table = "QUIZ";
        columns = Map.of(
                "_id", "bigint auto_increment PRIMARY KEY",
                "difficulty", "Int",
                "participant", "varchar(100)",
                "score", "Int"
        );
        db.createTable(table, columns);

        table = "QUIZ_QUESTION";
        columns = Map.of(
                "_id", "bigint auto_increment PRIMARY KEY",
                "quiz_id", "bigint",
                "question_id", "bigint",
                "user_ans", "varchar(500)",
                "valid_ans", "boolean"
        );
        db.createTable(table, columns);
    }
}
