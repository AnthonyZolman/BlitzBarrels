package com.example.blaznbarrels;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GameUI extends Application {
    private User player;
    private Barrel barrel;
    private int bulletsRemaining;
    private int score;
    private Label playerInfo;
    private Label roundInfo;
    private GridPane barrelGrid;
    private VBox mainLayout;
    private int currentRound = 1;
    private final int totalRounds = 3;
    private int selectedDifficulty = 0; // Variable to store selected difficulty

    @Override
    public void start(Stage primaryStage) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Player Name");
        dialog.setHeaderText("Enter Player Name");
        dialog.setContentText("Please enter your name:");
        String playerName = dialog.showAndWait().orElse("");
        if (playerName.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("You must enter a player name");
            alert.showAndWait();
            start(primaryStage);
            return;
        }

        player = new User(playerName, 0);
        barrel = new Barrel();
        bulletsRemaining = 0;
        score = 0;

        Label titleLabel = new Label("Blazn' Squad");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        playerInfo = new Label("Game by: Anthony Zolman & Minh Phan");
        playerInfo.setStyle("-fx-font-size: 14px;");

        roundInfo = new Label("Round: " + currentRound + "/" + totalRounds);
        roundInfo.setStyle("-fx-font-size: 14px;");
        roundInfo.setVisible(false);

        barrelGrid = createBarrelGrid();
        barrelGrid.setVisible(false);

        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> showDifficultyOptions(primaryStage));

        mainLayout = new VBox(20, titleLabel, playerInfo, roundInfo, barrelGrid, startButton);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(20));

        Scene scene = new Scene(mainLayout, 600, 400);
        primaryStage.setTitle("Blazn' Barrels!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane createBarrelGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        grid.setStyle("-fx-border-color: black; -fx-border-width: 2px;");

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                Button cell = new Button(" ");
                cell.setMinSize(50, 50);
                int position = row * 4 + col + 1;
                cell.setOnAction(e -> handleShot(cell, position));
                grid.add(cell, col, row);
            }
        }
        return grid;
    }

    private void resetBarrelGrid() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                Button cell = (Button) barrelGrid.getChildren().get(row * 4 + col);
                cell.setText(" ");
                int finalRow = row;
                int finalCol = col;
                cell.setOnAction(e -> handleShot(cell, finalRow * 4 + finalCol + 1));
            }
        }
    }

    private void showDifficultyOptions(Stage primaryStage) {
        mainLayout.getChildren().clear();

        Button easyButton = new Button("Easy");
        easyButton.setOnAction(e -> startGame(3));

        Button mediumButton = new Button("Medium");
        mediumButton.setOnAction(e -> startGame(2));

        Button hardButton = new Button("Hard");
        hardButton.setOnAction(e -> startGame(1));

        Button quitButton = new Button("Quit");
        quitButton.setOnAction(e -> primaryStage.close());

        Button highScoreButton = new Button("High Score");
        highScoreButton.setOnAction(e -> {
            int highscore = player.getHighScore();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("High Score");
            alert.setHeaderText(null);
            alert.setContentText("High Score: " + highscore);
            alert.showAndWait();
        });

        HBox difficultyButtons = new HBox(10, easyButton, mediumButton, hardButton, highScoreButton, quitButton);
        difficultyButtons.setAlignment(Pos.CENTER);

        mainLayout.getChildren().addAll(new Label("Select Difficulty:"), difficultyButtons);
    }

    private void startGame(int difficulty) {
        selectedDifficulty = difficulty;
        bulletsRemaining = difficulty;
        roundInfo.setVisible(true);

        barrel.clearBarrel();
        barrel.fishAdd(bulletsRemaining);
        resetBarrelGrid();

        playerInfo.setText("Bullets: " + bulletsRemaining + " | Score: " + score);
        roundInfo.setText("Round: " + currentRound + "/" + totalRounds);
        barrelGrid.setVisible(true);
        mainLayout.getChildren().clear();
        mainLayout.getChildren().addAll(playerInfo, roundInfo, barrelGrid);
    }

    private void handleShot(Button cell, int position) {
        if (bulletsRemaining > 0) {
            bulletsRemaining--;
            boolean hit = barrel.getBarrel().contains(position);
            if (hit) {
                int points;
                switch(selectedDifficulty){
                    case 3: //Easy
                        points = 10;
                        break;
                        case 2: //Medium
                            points = 20;
                            break;
                            case 1: //Hard
                                points = 30;
                                break;

                                default:
                                    points = 10;
                }
                score += points;
                cell.setText("Fish!");
            } else {
                cell.setText("Miss!");
            }
            playerInfo.setText("Bullets: " + bulletsRemaining + " | Score: " + score);

            if (bulletsRemaining == 0) {
                if (currentRound < totalRounds) {
                    currentRound++;
                    startGame(selectedDifficulty);
                } else {
                    player.setHighScore(Math.max(player.getHighScore(), score));
                    showEndGameOptions();
                }
            }
        }
    }

    private void showEndGameOptions() {
        mainLayout.getChildren().clear();

        //Check if player has beaten their high score
        boolean isNewHighScore = score > player.getHighScore();

        //update the highscore
        if(isNewHighScore){
            player.setHighScore(score);
        }

        // Create end game message
        String endGameMessage = "Game Over! Your final score is " + score;
        if(isNewHighScore){
            endGameMessage += "\nNew High Score!";
        }
        else{
            endGameMessage += "\nHigh Score: " + player.getHighScore();
        }

        Button retryButton = new Button("Main Menu");
        retryButton.setOnAction(e -> {
            currentRound = 1;
            score = 0;
            showDifficultyOptions(new Stage());
        });

        Button quitButton = new Button("Quit");
        quitButton.setOnAction(e -> System.exit(0));

        HBox endGameButtons = new HBox(10, retryButton, quitButton);
        endGameButtons.setAlignment(Pos.CENTER);

        mainLayout.getChildren().addAll(new Label(endGameMessage, endGameButtons));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
