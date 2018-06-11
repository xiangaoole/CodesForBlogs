package com.mindle.androidtest;


import java.util.Arrays;
import java.util.Random;

public class Creatures {
    private int num;
    private static final double INTIAL_SURVIVAL_RATE = 0.50;
    private int[][] livingStatus;

    public Creatures(int num){
        this.num = num;
        livingStatus = new int[num][num];
        Random random = new Random();
        for (int row = 0; row < num; row++) {
            for (int col = 0; col < num; col++) {
                livingStatus[row][col] = random.nextDouble() < INTIAL_SURVIVAL_RATE ? 1 : 0;
            }
        }
    }

    public void nextTime() {
        int[][] nodesTmp = new int[num][num];

        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                switch (countLivingsAround(i, j)) {
                    case 2:
                        if (livingStatus[i][j] != 0) {
                            nodesTmp[i][j] = livingStatus[i][j] + 1;
                        } else {
                            nodesTmp[i][j] = 0;
                        }
                        break;
                    case 3:
                        if (livingStatus[i][j] != 0) {
                            nodesTmp[i][j] = livingStatus[i][j] + 1;
                        } else {
                            nodesTmp[i][j] = 1;
                        }
                        break;
                    default:
                        nodesTmp[i][j] = 0;
                }
            }
        }


        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                livingStatus[i][j] = nodesTmp[i][j];
            }
        }
    }

    private int countLivingsAround(int row, int col) {
        int sum = 0;
        if (row >= 1) {
            sum += (livingStatus[row - 1][col] != 0) ? 1 : 0;
            if (col - 1 >= 0 && livingStatus[row - 1][col - 1] != 0) {
                sum++;
            }
            if (col + 1 < num && livingStatus[row - 1][col + 1] != 0) {
                sum++;
            }
        }
        if (row + 1 < num) {
            sum += (livingStatus[row + 1][col] != 0) ? 1 : 0;
            if (col - 1 >= 0 && livingStatus[row + 1][col - 1] != 0) {
                sum++;
            }
            if (col + 1 < num && livingStatus[row + 1][col + 1] != 0) {
                sum++;
            }
        }
        if (col - 1 >= 0 && livingStatus[row][col - 1] != 0) {
            sum++;
        }
        if (col + 1 < num && livingStatus[row][col + 1] != 0) {
            sum++;
        }
        return sum;
    }

    public int[][] getLivingStatus() {
        return livingStatus;
    }

    public static void main(String[] args) {
        Creatures creatures = new Creatures(4);
        int[][] matrix = {{0,0,0,3},{0,0,3,0},{0,0,3,0},{0,0,0,3}};
        creatures.livingStatus = matrix;
        creatures.nextTime();
        matrix = creatures.getLivingStatus();
        for (int i = 0; i < 4; i++) {
            System.out.println(Arrays.toString(matrix[i]));
        }
    }
}
