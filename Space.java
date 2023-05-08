import java.util.Arrays;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
public class Space{
    private boolean EmptySpace;
    private boolean PlayerOneSpace;
    private boolean PlayerTwoSpace;

    public Space() {
        this.EmptySpace = true;
        this.PlayerOneSpace = false;
        this.PlayerTwoSpace = false;
    }

    public String toString(){
        if (PlayerOneSpace == true){
            return " X ";
        }
        if (PlayerTwoSpace == true){
            return " O ";
        }
        return "   ";

    }

    public void setToPlayer1(){
        this.setEmptySpace(false);
        this.setPlayerTwoSpace(false);
        this.setPlayerOneSpace(true);

    }
    public void setToPlayer2(){
        this.setEmptySpace(false);
        this.setPlayerTwoSpace(true);
        this.setPlayerOneSpace(false);

    }

    public static void main(String[] args) {
        Space a = new Space();
        a.setPlayerOneSpace(true);
        System.out.println(a);
    }

    public boolean isEmptySpace() {
        return EmptySpace;
    }

    public void setEmptySpace(boolean emptySpace) {
        EmptySpace = emptySpace;
    }

    public boolean isPlayerOneSpace() {
        return PlayerOneSpace;
    }

    public void setPlayerOneSpace(boolean playerOneSpace) {
        PlayerOneSpace = playerOneSpace;
    }

    public boolean isPlayerTwoSpace() {
        return PlayerTwoSpace;
    }

    public void setPlayerTwoSpace(boolean playerTwoSpace) {
        PlayerTwoSpace = playerTwoSpace;
    }
}
