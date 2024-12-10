package com.example.blaznbarrels;

import java.util.*;

public class Game {

    public static void main(String[] args){
        User user = new User(null,0);
        Barrel barrel = new Barrel();
        Scanner scan = new Scanner(System.in);
        ArrayList<User> users = new ArrayList<>();

        int exit = 0, menu = 0, difficulty = 0, numFish, highScore = 0;
        String userName = "";

        //While statement to recieve the player's username
        while (userName.isBlank()){
            System.out.println("Please enter your user-name!: ");
            userName = scan.nextLine();
            user.setUserName(userName);
            System.out.println("Your name is: " + user.getUserName());
        }

        do{
            System.out.println("Hello! Welcome to Blazn' Barrels!");

            System.out.println("\nPlease choose one of the following options: ");
            System.out.println("1.Play Game\n2.View High-Score\n3.View Credits\n4.Exit Game");
            menu = scan.nextInt();
            scan.nextLine();
            
            switch (menu) {
                case 1:
                    while (difficulty != 1 && difficulty != 2 && difficulty != 3){
                        System.out.println("Choose your difficulty: \n1.Easy\n2.Medium\n3.Hard");
                        difficulty = scan.nextInt();
                    }
                    numFish = barrel.setDifficulty(difficulty);

                    System.out.println("Adding the fish...");
                    int totalPoints = barrel.fishAdd(numFish);
                    
                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:
                    System.out.println("Thank you for playing!\nHigh-Score: ");
                    exit = 1;
                    break;
                default:
                    System.out.println("That was not an option, please try again!");
                    break;
            }
                    










        }while(exit == 0);


    }
}
