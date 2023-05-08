import java.util.Arrays;
import java.util.Scanner;

import javax.xml.transform.Source;

import java.util.HashMap;
import java.util.ArrayList;

public class Board {
    private static int turn;
    private Space[][] board;
    private static int CurrentXvalue;
    private static int CurrentYvalue;

   

    public Board() {
        board = new Space[8][8];
        for (int row = 0; row < board.length; row ++){
            for (int col = 0; col < board[row].length; col ++){
                this.board[row][col] =new Space();
            }
        }
        board[3][3].setToPlayer1();
        board[4][4].setToPlayer1();
        board[3][4].setToPlayer2();
        board[4][3].setToPlayer2();
        CurrentXvalue = 0;
        CurrentYvalue = 0;
        turn = 1;
    }

    public static void PvPGame(Scanner input){
        Board game = new Board();
        Space[][] board = game.getBoard();
        while(isBoardFull(board) == false){
            PrintGame(game);
            Playmove(input, board);
            if(isBoardFull(board) == true){
                break;
            }
            if(getTurn() == 1){
                System.out.println("hui");
                setTurn(2);
            }
            else{
                setTurn(1);
            }
            System.out.println(getTurn());
        }
    }
    public static void Playmove(Scanner input, Space[][] board){
        while(isPlayableSpace(board) == false){
            if(getTurn() == 1){
                System.out.println("Player 1s turn Enter a space");
            }
            else{
                System.out.println("Player 2s turn Enter a space");
            }
            String play = input.nextLine();
            checkvalidmove(play, board);
        } 
        makemove(board);
    }

    

    public static void makemove(Space[][] board){
        String a = FindSurrounding(board);
        String b = SwitchableCoordinates(a, board);
        SwitchColour(b, board);
    }
    public static void checkvalidmove(String play, Space[][] board){
        if(play.matches("[a-h][1-8]")){
            setCurrentXvalue(Integer.valueOf(getRow(play.substring(1))));
            setCurrentYvalue(Integer.valueOf(getColumn(play.substring(0,1))));
            if(board[getX()][getY()].isPlayerOneSpace()==true|board[getX()][getY()].isPlayerTwoSpace()==true){
                setCurrentXvalue(3);
                setCurrentYvalue(3);
            }
            if(board[getX()][getY()].isEmptySpace() == true && getTurn() == 1){
                board[getX()][getY()].setEmptySpace(false);
                board[getX()][getY()].setPlayerOneSpace(true);
                if(isPlayableSpace(board) == false){
                    board[getX()][getY()].setEmptySpace(true);
                    board[getX()][getY()].setPlayerOneSpace(false);
                }
            }
            if(board[getX()][getY()].isEmptySpace() == true && getTurn() == 2){
                if(board[getX()][getY()].isEmptySpace() == true){
                    board[getX()][getY()].setEmptySpace(false);
                    board[getX()][getY()].setPlayerTwoSpace(true);
                    if(isPlayableSpace(board) == false){
                            board[getX()][getY()].setEmptySpace(true);
                            board[getX()][getY()].setPlayerTwoSpace(false);
                    }      
                }
            }
        }
    }

    public static void PrintGame(Board board){
        System.out.println("\n\n\n\n"+board);
        System.out.println("\n\nPlayer 1 is X Player 2 is O");
        int[] score = board.score();
        System.out.println("\n\nCurrent score\nPlayer One: "+ score[0]+"    Player Two: "+ score[1]);
    }

    public static boolean isPlayableSpace(Space[][] board){
        if(board[getX()][getY()].isEmptySpace() == true){
            return false;
        }
        if (SurroundingHasOppositePlayer(board) == false){
            return false;
        } 
        String coordinates = FindSurrounding(board);
        String switchable = SwitchableCoordinates(coordinates, board);
        if(switchable.equals("")){
            return false;
        }
        return true;
    } 

    public static boolean SurroundingHasOppositePlayer(Space[][] board){
        for(int i = -1; i<2;i++ ){
            for(int a = -1; a<2;a++){
                if(board[getX()][getY()].isPlayerOneSpace() == true){
                    if(i==0 &&a==0){
                    }
                    if(getX()+i == 8|getX()+i == -1|getY()+a == 8|getY()+a == -1){   
                    }
                    else{
                        if(board[getX()+i][getY()+a].isPlayerTwoSpace()==true){
                            return true;
                        }                      
                    }
                }
                if(board[getX()][getY()].isPlayerTwoSpace() == true){
                    if(i==0 &&a==0){
                    }
                    if(getX()+i == 8|getX()+i == -1|getY()+a == 8|getY()+a == -1){   
                    }
                    else{ 
                        if(board[getX()+i][getY()+a].isPlayerOneSpace()==true){
                            return true;
                        }                      
                    }
                }
            }
        }
        return false;
    }

    public static String FindSurrounding(Space[][] board){  
        String Spaces = "";
        int coordinate = 1;
        for(int i = -1; i<2;i++ ){
            for(int a = -1; a<2;a++ ){
                if(i==0 &&a==0){
                }
                else if(getX()+i == 8|getX()+i == -1|getY()+a == 8|getY()+a == -1){ 
                    coordinate++;
                }
                else{
                    if(checkOppositePlayer(board[getX()][getY()], board[getX()+i][getY()+a]) == true && board[getX()+i][getY()+a].isEmptySpace() == false){
                        Spaces += Integer.toString(coordinate);   
                    }
                    coordinate++; 
                }
                
            }
        }
        return Spaces;   
    }

    public static String SwitchableCoordinates(String coordinates,Space[][] board){
        String check = "";
        for(int i = 0;i < coordinates.length();i++){
            String current = coordinates.substring(i, i+1);
            check += getswitchFactor(current, board);
        }
        return check;
    }

    public static String getswitchFactor(String current, Space[][] board){
        if(current.equals("1")){
            if(checkSwitchable(-1,-1,current, board) == true){
                return current;
            }
        }
        if(current.equals("2")){
            if(checkSwitchable(-1,0,current, board) == true){
                return current;
            }
        }if(current.equals("3")){
            if(checkSwitchable(-1,1,current, board) == true){
                return current;
            }
        }if(current.equals("4")){
            if(checkSwitchable(0,-1,current, board) == true){
                return current;
            }
        }if(current.equals("5")){
            if(checkSwitchable(0,1,current, board) == true){
                return current;
            }
        }if(current.equals("6")){
            if(checkSwitchable(1,-1,current, board) == true){
                return current;
            }
        }if(current.equals("7")){
            if(checkSwitchable(1,0,current, board) == true){
                return current;
            }
        }if(current.equals("8")){
            if(checkSwitchable(1,1,current, board) == true){
                return current;
            }
        }
        return"";
    }

    public static void SwitchColour(String coordinates,Space[][] board){
        for(int i = 0;i < coordinates.length();i++){
            String current = coordinates.substring(i, i+1);
            if(current.equals("1")){
                Switch(-1,-1,current, board);
            }  
            if(current.equals("2")){
                Switch(-1,0,current, board);
                
            }if(current.equals("3")){
                Switch(-1,1,current, board);
            }if(current.equals("4")){
                Switch(0,-1,current, board);
            }if(current.equals("5")){
                Switch(0,1,current, board);
            }if(current.equals("6")){
                Switch(1,-1,current, board);

            }if(current.equals("7")){
                Switch(1,0,current, board);

            }if(current.equals("8")){
                Switch(1,1,current, board);

            }
        }
    }

    public static  boolean checkOppositePlayer(Space space1, Space space2){
        if (space1.isPlayerOneSpace() && space2.isPlayerOneSpace()){
            return false;
        } else if (space1.isPlayerTwoSpace() && space2.isPlayerTwoSpace()){
            return false;
        } else {
            return true;
        }
    }
    
    public static boolean checkSwitchable(int one, int two, String place, Space[][] board){
        while(true){
            if(getX()+one >= 8|getX()+one <= -1 && getY()+ two >= 8|getY()+two <= -1){
                return false;
            }
           
            if (board[getX() + one][getY()+two].isEmptySpace() == true){
                    return false;
                }
            if (checkOppositePlayer(board[getX()][getY()], board[getX()+one ][getY()+two]) == false ){
                    return true;
                }
                if(one!=0){
                    if(one<0){
                        one--;
                    }
                    else{
                        one++;
                    }                
                }
                if(two!=0){
                    if(two<0){
                        two--;
                    }
                    else{
                    two++;
                    }
                }              
            }
    }

    
    public static void Switch(int one, int two, String place, Space[][] board){
        while(getX()+one != 8|getX()+one != -1|getY()+ two != 8|getY()+two != -1){
            if (checkOppositePlayer(board[getX()][getY()], board[getX() +one ][getY()+two]) == false ){
                break;
            }
            else{
                SwitchPlayerColour(board[getX()][getY()], board[getX() +one][getY()+two]);
            }
            if(one!=0){
                if(one<0){
                    one--;
                }
                else{
                    one++;
                }                
            }
            if(two!=0){
                if(two<0){
                    two--;
                }
                else{
                two++;
                }
            }

        }
    }

    public static void SwitchPlayerColour(Space one, Space two ){
        if(one.isPlayerOneSpace()){
           two.setToPlayer1();
        }
        if(one.isPlayerTwoSpace()){
            two.setToPlayer2();
        }  
    }

    public static boolean isBoardFull(Space[][] other){
        for (int row = 0; row < other.length; row ++){
            for (int col = 0; col < other[row].length; col ++){
                if(other[row][col].isEmptySpace() == true){
                    return false;
                }
            }
        }
        return true;
    }
    
       

    public static String getColumn(String other){
        if(other.equals("a")){
            return "0";
        }
        if(other.equals("b")){
            return "1";
        }
        if(other.equals("c")){
            return "2";
        }
        if(other.equals("d")){
            return "3";
        }
        if(other.equals("e")){
            return "4";
        }
        if(other.equals("f")){
            return "5";
        }
        if(other.equals("g")){
            return "6";
        }
        return "7";
    }
    public static String getRow(String other){
        if(other.equals("1")){
            return "0";
        }
        if(other.equals("2")){
            return "1";
        }
        if(other.equals("3")){
            return "2";
        }
        if(other.equals("4")){
            return "3";
        }
        if(other.equals("5")){
            return "4";
        }
        if(other.equals("6")){
            return "5";
        }
        if(other.equals("7")){
            return "6";
        }
        return "7";
    }
    

    public String toString(){
        String art ="  "+"-".repeat(33);
        art += "\n  | A | B | C | D | E | F | G | H |";
        int i = 1;
            for (int row = 0; row < board.length; row ++){
                art+= "\n  |---|---|---|---|---|---|---|---|\n"+Integer.valueOf(i)+" ";
                for (int col = 0; col < board[row].length; col ++){
                   art += "|"+(board[row][col]).toString();
                }
                art+="|";
                i++;
            }
            art+="\n  "+"-".repeat(33);
        return art;
    }

    public int[] score(){
        int PlayerOne = 0;
        int PlayerTwo = 0;
        for (int row = 0; row < board.length; row ++){
            for (int col = 0; col < board[row].length; col ++){
                if(board[row][col].isPlayerOneSpace() == true){
                    PlayerOne++;
                }
                if(board[row][col].isPlayerTwoSpace() == true){
                    PlayerTwo++;
                } 
            }
        }
        int[] score = {PlayerOne,PlayerTwo};
        return score;
    }
       
    public static void run(Scanner input) {
        String play = "";
        while(true){
            System.out.println("Enter 1 to play Orthello against another player, Enter 2 to play against a computer or enter QUIT to quit the program.");
            play = input.nextLine();
            if (play.equals("QUIT")){
                break;
            }
            else if(play.equals("1")){
                PvPGame(input);
            }
            else if(play.equals("2")){
                
            }
            else{
                System.out.println("Enter a valid input");
            }
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        test();
    }


    public Space[][] getBoard() {
        return board;
    }


    public void setBoard(Space[][] board) {
        this.board = board;
    }

    public static int getX() {
        return CurrentXvalue;
    }

    public static void setCurrentXvalue(int currentXvalue) {
        CurrentXvalue = currentXvalue;
    }

    public static int getY() {
        return CurrentYvalue;
    }
    

    public static void setCurrentYvalue(int currentYvalue) {
        CurrentYvalue = currentYvalue;
    }

    public static void setTurn(int turn) {
        Board.turn = turn;
    }
    public static int getTurn() {
        return turn;
    } 
    public static void test(){
        Board game = new Board();
        Space[][] board = game.getBoard();
        int a = 0;
        String[] move = moves();
        while(isBoardFull(board) == false){
            PrintGame(game);
            Playmovetest(move,a, board);
            if(isBoardFull(board) == true){
                break;
            }
            if(getTurn() == 1){
                System.out.println("hui");
                setTurn(2);
            }
            else{
                setTurn(1);
            }
            System.out.println(getTurn());
            a++;
        }
    
        
    }
    public static void Playmovetest(String[] move,int other, Space[][] board){
        while(isPlayableSpace(board) == false){
            if(getTurn() == 1){
                System.out.println("Player 1s turn Enter a space");
            }
            else{
                System.out.println("Player 2s turn Enter a space");
            }
            String play = move[other];
            checkvalidmove(play, board);
        } 
        makemove(board);
    }
    public static String[] moves(){
        String[] a = {"e3", "f3", "f4",  "f5", "g3", "c4", "e6", "d7", "f6", "g4", "g5", "h5", "e7", "h4", "g6", "f7", "d7", "e7", "b7", "a6", "a7", "g6", "b5", "h6", "f8", "d8", "e8", "g8", "c8"};


        return a;

    }
}


