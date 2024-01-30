package com.example.lab1b;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.lab1b.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private int playerScore = 0;
    private int opponentScore = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.rockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playGame(Weapon.ROCK);
                String playerText = "Player's Weapon: " + Weapon.ROCK;
                binding.playerChoice.setText(playerText);
            }
        });

        binding.paperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playGame(Weapon.PAPER);
                String playerText = "Player's Weapon: " + Weapon.PAPER;
                binding.playerChoice.setText(playerText);
            }
        });

        binding.scissorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playGame(Weapon.SCISSORS);
                String playerText = "Player's Weapon: " + Weapon.SCISSORS;
                binding.playerChoice.setText(playerText);
            }
        });

        updateScores();
    }

    private void playGame(Weapon userChoice) {
        //Function that plays the game of Rock Paper Scissors out
        Weapon opponentChoice = getOpponentChoice();

        //Setting the choice the player made
        String playerText = getString(R.string.player_weapon, userChoice.toString());
        binding.playerChoice.setText(playerText);

        //Setting the choice the opponent made
        String opponentText = getString(R.string.computer_weapon, opponentChoice.toString());
        binding.computerChoice.setText(opponentText);

        //Using the function that determines the winner, then outputting the result
        String result = determineWinner(userChoice, opponentChoice);
        binding.resultMessage.setText(result);
    }

    private Weapon getOpponentChoice() {
        //Get a random weapon for the opponent
        Weapon[] values = Weapon.values();
        int randomIndex = (int) (Math.random() * values.length);

        // Update the opponent's weapon text
        String opponentText = getString(R.string.computer_weapon, values[randomIndex].toString());
        binding.computerChoice.setText(opponentText);

        return values[randomIndex];
    }

    private void updateScores() {
        //Function to update the string that shows the score through multiple games
        String scoreText = "Player: " + playerScore + ", Computer: " + opponentScore;
        binding.score.setText(scoreText);
    }

    private String determineWinner(Weapon userChoice, Weapon opponentChoice) {
        String resultMessage;

        if (userChoice == opponentChoice) {
            resultMessage = getString(R.string.draw_message);
        } else if ((userChoice == Weapon.ROCK && opponentChoice == Weapon.SCISSORS) ||
                (userChoice == Weapon.PAPER && opponentChoice == Weapon.ROCK) ||
                (userChoice == Weapon.SCISSORS && opponentChoice == Weapon.PAPER)) {
            //User wins
            playerScore++;
            updateScores();
            resultMessage = getWinMessage("Player", userChoice, opponentChoice);
        } else {
            //Opponent wins
            opponentScore++;
            updateScores();
            resultMessage = getWinMessage("Computer", opponentChoice, userChoice);
        }

        return resultMessage;
    }

    private String getWinMessage(String winnerLabel, Weapon winner, Weapon loser) {
        //Defining the string resources for all the different win messages
        switch (winner) {
            case ROCK:
                return getString(R.string.rock_beats_scissors, winnerLabel, winner.toString(), loser.toString());
            case PAPER:
                return getString(R.string.paper_covers_rock, winnerLabel, winner.toString(), loser.toString());
            case SCISSORS:
                return getString(R.string.scissors_cut_paper, winnerLabel, winner.toString(), loser.toString());
            default:
                return getString(R.string.default_win_message);
        }
    }
}