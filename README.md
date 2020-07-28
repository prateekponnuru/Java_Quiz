# Java_Quiz
Quiz Application - Final Assignment

Follow the below steps to test the application:

    Clone the repository
    Compile and Execute the fr.prat.quiz.Quiz class

You can use the following commands while executing to perform various actions:

    java quiz -migrate         //to create the necessary tables
    
    java quiz -migrate -drop   //to drop and recreate all the tables
    
    java quiz -manage          //to add questions to the database
    
    java quiz -play            //to take a quiz. Before doing this, 
                               //you will need questions to the db.
                               //The quiz.questions parameter in conf file
                               //determines the minimum number of questions 
                               //required of a particular difficulty.
                               
    java quiz -export          //to export a quiz as text, 
                               //change the address in the conf file
