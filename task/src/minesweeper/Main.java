package minesweeper;

import minesweeper.UI.UserInterface;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // write your code here
        Scanner scanner = new Scanner(System.in);
        new UserInterface(scanner).start();
    }
}
