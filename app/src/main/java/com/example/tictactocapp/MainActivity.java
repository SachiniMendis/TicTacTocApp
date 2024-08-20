package com.example.tictactocapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tictactocapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private final List<int[]> combinationList = new ArrayList<>();
    private int[] boxPositions = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int playerTurn = 1;
    private int totalSelectedBoxes = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        combinationList.add(new int[]{0, 1, 2});
        combinationList.add(new int[]{3, 4, 5});
        combinationList.add(new int[]{6, 7, 8});
        combinationList.add(new int[]{0, 3, 6});
        combinationList.add(new int[]{1, 4, 7});
        combinationList.add(new int[]{2, 5, 8});
        combinationList.add(new int[]{0, 4, 8});
        combinationList.add(new int[]{2, 4, 6}); // Added missing combination

        String getPlayerOneName = getIntent().getStringExtra("playerOne");
        String getPlayerTwoName = getIntent().getStringExtra("playerTwo");

        binding.playerOneName.setText(getPlayerOneName);
        binding.playerTwoName.setText(getPlayerTwoName);

        setClickListener(binding.image1, 0);
        setClickListener(binding.image2, 1);
        setClickListener(binding.image3, 2);
        setClickListener(binding.image4, 3);
        setClickListener(binding.image5, 4);
        setClickListener(binding.image6, 5);
        setClickListener(binding.image7, 6);
        setClickListener(binding.image8, 7);
        setClickListener(binding.image9, 8);
    }

    private void setClickListener(ImageView imageView, int boxPosition) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(boxPosition)) {
                    performAction(imageView, boxPosition);
                }
            }
        });
    }

    private boolean performAction(ImageView imageView, int selectedBoxPosition) {
        boxPositions[selectedBoxPosition] = playerTurn;

        if (playerTurn == 1) {
            imageView.setImageResource(R.drawable.ximage);
            if (checkResults()) {
                ResultDialog resultDialog = new ResultDialog(MainActivity.this, binding.playerOneName.getText().toString()
                        + " is a Winner!", MainActivity.this);
                resultDialog.setCancelable(false);
                resultDialog.show();
                return true; // To stop the function after showing the result
            }
        } else {
            imageView.setImageResource(R.drawable.o_image); // Changed to O image
            if (checkResults()) {
                ResultDialog resultDialog = new ResultDialog(MainActivity.this, binding.playerTwoName.getText().toString()
                        + " is a Winner!", MainActivity.this);
                resultDialog.setCancelable(false);
                resultDialog.show();
                return true; // To stop the function after showing the result
            }
        }

        totalSelectedBoxes++;
        if (totalSelectedBoxes == 9) {
            ResultDialog resultDialog = new ResultDialog(MainActivity.this, "Match Draw", MainActivity.this);
            resultDialog.setCancelable(false);
            resultDialog.show();
        } else {
            changePlayerTurn(playerTurn == 1 ? 2 : 1);
        }
        return false;
    }

    private void changePlayerTurn(int currentPlayerTurn) {
        playerTurn = currentPlayerTurn;
        if (playerTurn == 1) {
            binding.playerOneLayout.setBackgroundResource(R.drawable.black_border);
            binding.playerTwoLayout.setBackgroundResource(R.drawable.white_box);
        } else {
            binding.playerTwoLayout.setBackgroundResource(R.drawable.black_border);
            binding.playerOneLayout.setBackgroundResource(R.drawable.white_box);
        }
    }

    private boolean checkResults() {
        for (int[] combination : combinationList) {
            if (boxPositions[combination[0]] == playerTurn &&
                    boxPositions[combination[1]] == playerTurn &&
                    boxPositions[combination[2]] == playerTurn) {
                return true;
            }
        }
        return false;
    }

    private boolean isBoxSelectable(int boxPosition) {
        return boxPositions[boxPosition] == 0;
    }

    public void restartMatch() {
        boxPositions = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        playerTurn = 1;
        totalSelectedBoxes = 1;

        binding.image1.setImageResource(R.drawable.white_box);
        binding.image2.setImageResource(R.drawable.white_box);
        binding.image3.setImageResource(R.drawable.white_box);
        binding.image4.setImageResource(R.drawable.white_box);
        binding.image5.setImageResource(R.drawable.white_box);
        binding.image6.setImageResource(R.drawable.white_box);
        binding.image7.setImageResource(R.drawable.white_box);
        binding.image8.setImageResource(R.drawable.white_box);
        binding.image9.setImageResource(R.drawable.white_box);
    }
}
