package minesweeper.UI;

import minesweeper.logic.MineSweeper;

import java.util.Scanner;

public class UserInterface {
    private MineSweeper mineSweeper;
    private Scanner scanner;

    public UserInterface(Scanner scanner) {
        this.mineSweeper = new MineSweeper();
        this.scanner = scanner;
    }

    public void start(){
        System.out.println("How many mines do you want on the field?");
        int x = Integer.parseInt(scanner.nextLine());
        mineSweeper.generateMines(x);
        play();
    }

    private void play(){
        boolean allHit=false;
        while (!allHit){
            mineSweeper.print(false);
            System.out.println("Set/delete mines marks (x and y coordinates):");
            int y = scanner.nextInt()-1;
            int x = scanner.nextInt()-1;
            String input = scanner.next();
            mineSweeper.mark(x,y, input);
            if(mineSweeper.getMines()==null){
                break;
            }
            allHit=mineSweeper.check();
        }
        System.out.println(allHit? "Congratulations! You found all mines!":"You stepped on a mine and failed!");
    }
}
