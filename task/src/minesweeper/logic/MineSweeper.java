package minesweeper.logic;

import minesweeper.domain.Mine;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MineSweeper {
    private String [][] minesSweeperBoard;
    private String [][] dispalyBoard;
    private final Random random;
    private ArrayList<Mine> mines;

    {
        this.dispalyBoard = new String[9][9];
        for(int r = 0;r<9;r++){
            for(int c =0;c<9;c++){
                dispalyBoard[r][c]=".";
            }
        }
    }

    public MineSweeper() {
        minesSweeperBoard = new String[9][9];
        this.mines=new ArrayList<>();
        this.random = new Random();
    }

    public ArrayList<Mine> getMines() {
        return mines;
    }

    public void generateMines(int minesAmount){
        for(int i = 0 ;i<minesAmount;i++){
            int x = random.nextInt(9);
            int y = random.nextInt(9);
            Mine mine = new Mine(x,y);
            while (mines.contains(mine)){
                x = random.nextInt(9);
                y = random.nextInt(9);
                mine = new Mine(x,y);
            }
            mines.add(mine);
        }
        placeMines();
    }

    private void placeMines(){
       for(Mine m : mines){
          minesSweeperBoard[m.getX()][m.getY()]="X";
        }
    }


    private void checkSurroundings(int x,int y){
        for(int r=x-1;r<x+2;r++){
            for(int c=y-1;c<y+2;c++){
                try {
                    if(".".equals(dispalyBoard[r][c])&&!mines.contains(new Mine(r,c))){
                        int count = count(r,c);
                        if(count>0){
                            dispalyBoard[r][c]= String.valueOf(count);
                        }else{
                            dispalyBoard[r][c]="/";
                            checkSurroundings(r,c);
                        }
                    }else if("*".equals(dispalyBoard[r][c])&&!mines.contains(new Mine(r,c))){
                        String[][] tempArray = surroundingArray(r,c, dispalyBoard);
                        for(String[] row: tempArray){
                            for (String s: row) {
                                if("/".equals(s)){
                                    int count = count(r,c);
                                    dispalyBoard[r][c]=count>0?String.valueOf(count):"/";
                                    checkSurroundings(r,c);
                                    break;
                                }
                            }
                        }
                    }
                } catch (Exception ignored) {}
            }
        }
    }

    private int count(int r , int c){
        String[][] tempArray = surroundingArray(r,c,minesSweeperBoard);
        int count =0;
        for(String[] row: tempArray){
            for (String s: row){
                if("X".equals(s)){
                    count++;
                }
            }
        }
        return count;
    }

    private String[][] surroundingArray(int x, int y, String [][]array){
        String[][] tempArray = new String[3][3];
        int i =0;
        for(int r=x-1;r<x+2;r++){
            if(r<0||r>8) continue;
            try {
                tempArray[i] = Arrays.copyOfRange(array[r],y-1,y+2);
                i++;
            } catch (Exception e) {
                tempArray[i] = Arrays.copyOfRange(array[r],y,y+2);
                i++;
            }
        }
        return tempArray;
    }

    public void print(boolean gameOver){
        System.out.println(" |123456789|");
        System.out.println("-|---------|");
        int i=1;
        for (String [] row: gameOver? minesSweeperBoard: dispalyBoard){
            System.out.print(i+"|");
            i++;
            for(String s: row){
                System.out.print(s==null? ".":s);
            }
            System.out.println("|");

        }
        System.out.println("-|---------|");
    }

    public void mark(int x, int y,String type){

        if(dispalyBoard[x][y].matches("\\d")) {
            System.out.println("There is a number here!");
        }
        Mine mine= new Mine(x,y);

        switch (type){
            case "mine":
                if("*".equals(dispalyBoard[x][y])){
                    dispalyBoard[x][y]=".";
                    if(mines.contains(mine)){
                        mines.get(mines.indexOf(mine)).setMarked(false);
                    }
                }else if("/".equals(dispalyBoard[x][y])) {
                    System.out.println("Marked as empty already");
                }
                else {
                    dispalyBoard[x][y] ="*";
                    if(mines.contains(mine)){
                        mines.get(mines.indexOf(mine)).setMarked(true);
                    }
                }
                break;
            case "free":
                if(mines.contains(mine)){
                    print(true);
                    mines = null;
                }else {
                    checkSurroundings(x,y);
                }
                break;
        }
    }

    public boolean check() {
        int number = 0;
        for(Mine m : mines){
            if(m.isMarked()){
                number++;
            }
        }
        return number==mines.size();
    }
}

