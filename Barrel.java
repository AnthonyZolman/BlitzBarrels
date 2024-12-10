package com.example.blaznbarrels;

import java.util.*;

public class Barrel {
    private Random rand = new Random();
    private ArrayList<Integer> barrel;  // Stores fish positions in the barrel

    public Barrel(){
        barrel = new ArrayList<Integer>();
    }

    // Method to return the barrel
    public ArrayList<Integer> getBarrel() {
        return barrel;
    }

    public void clearBarrel(){
        barrel.clear();  // Clear the barrel after each round
    }

    public int fishAdd(int numFish){
        int point = 0;

        // Place fish in random positions in the barrel
        for(int i = 0; i < numFish; i++){
            int fish = rand.nextInt(12) + 1; // Random position between 1 and 12
            barrel.add(fish);
        }

        return point;
    }

    public int setDifficulty(int difficulty){
        int numFish = 0;
        switch (difficulty) {
            case 1:
                numFish = 3;
                break;
            case 2:
                numFish = 2;
                break;
            case 3:
                numFish = 1;
                break;
            default:
                break;
        }
        return numFish;
    }
}
