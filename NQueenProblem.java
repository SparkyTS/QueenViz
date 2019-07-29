package queensProblem;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class Coords
{
    int row,col;
    public Coords(int row,int col){
        this.row = row;
        this.col = col;
    }
}


public class NQueenProblem {

    enum IMG{
            QUEEN,DANGER
    }

    //all variables and constant that we will ues to handle actual logic behind the N queen problem.
    private static int N = 4;
    private static int delay = 1000;
    private static int count = 0;
    private static final boolean PLACE_QUEEN =  true ;
    private static final boolean REMOVE_QUEEN = false;
    private static boolean chessBoard[][] = new boolean[N][N];
    private static final ArrayList<Coords> queensOnBoard = new ArrayList<>();

    // all variables that will handle our GUI
    private static JLabel jLabels[] = new JLabel[N*N];
    private static JFrame frame = new JFrame(N + " - Queen Problem Visualization");
    private static JPanel jPanel = new JPanel(new GridLayout(N,N));
    private static ImageIcon imageIconQueen;     // will be the final icon that which will be render on jLabel.
    private static ImageIcon imageIconDanger;

    public static void main(String[] args) throws Exception {
        prepareGUI();
        placeQueen(0);
        System.out.println("Total No of solution found : " + count);
    }


    private static void getTheDangerImage() throws IOException {
        BufferedImage rawImg = ImageIO.read(new File("GUI_BASED\\src\\queensProblem\\deathSkull.jpg"));

        // will contain resized image
        Image queen = rawImg.getScaledInstance(jLabels[0].getWidth(), jLabels[0].getHeight(),
                Image.SCALE_SMOOTH);
        imageIconDanger = new ImageIcon(queen);
    }

    private static void getTheQueenImage() throws IOException {
        // using bufferedImage so can be resized easily
        BufferedImage rawImg = ImageIO.read(new File("GUI_BASED\\src\\queensProblem\\queen.jpg"));

        // will contain resized image
        Image queen = rawImg.getScaledInstance(jLabels[0].getWidth(), jLabels[0].getHeight(),
                Image.SCALE_SMOOTH);
        imageIconQueen = new ImageIcon(queen);
    }

    private static void prepareGUI() throws IOException {
        for(int i = 0 ; i < N*N ; i++){
            jLabels[i] = new JLabel();
            jLabels[i].setBorder(BorderFactory.createLineBorder(Color.black));
            jPanel.add(jLabels[i]);
        }


        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setSize(700,500);
        frame.add(jPanel);
        getTheQueenImage();
        getTheDangerImage();
        //refresh();
        frame.repaint();
    }

    private static boolean placeQueen(int col) throws Exception {
        if(col == N ){
            printBoard();
            return true;
        }

        //trying to place the queens on the different rows.
        for(int row = 0 ; row < N ; row++){
            //checking to see if position is safe to place the queen or not.
            if(isSafe(row,col)){
                //if it is safe to place queen then place it on the board.
                chessBoard[row][col] = PLACE_QUEEN;
                addToGUI(row,col,IMG.QUEEN);
                //add the coordinates on which queen is placed.
                queensOnBoard.add(new Coords(row,col));
                //if(placeQueen(col+1)) return true;
                //now try to place the queens on the next columns.
                placeQueen(col+1);
                //now remove the queen from the last position to try different combinations.
                chessBoard[row][col] = REMOVE_QUEEN;
                removeFromGUI(row,col);
                //remove the coordinates from the list after removing the queen.
                queensOnBoard.remove(queensOnBoard.size()-1);
            }
        }
        return false;
    }



    public static void addToGUI(int row, int col,IMG image) throws InterruptedException {
        ImageIcon icon = imageIconQueen;
        if(image == IMG.DANGER)
            icon = imageIconDanger;
        jLabels[row*N + col].setIcon(icon);
        frame.repaint();
        Thread.sleep(delay);
    }

    private static void removeFromGUI(int row,int col) throws InterruptedException {
        Thread.sleep(delay);
        jLabels[row*N + col].setIcon(null);
        frame.repaint();
    }

    private static boolean isSafe(int row, int col) throws InterruptedException {
        for(Coords queen : queensOnBoard){
            //if two queen is there on the same row.
            if(queen.row == row || Math.abs(queen.row - row ) == Math.abs(queen.col - col)){
                int temp = delay;
                delay/=2;
                addToGUI(row,col,IMG.DANGER);
                removeFromGUI(row,col);
                delay=temp;
                return false;
            }
        }
        return true;
    }

    private static void printBoard() {
        if(JOptionPane.showConfirmDialog(frame,"Want Another Solution ? ","Choice",JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION)
            System.exit(0);

//        for(int i=0;i<=(N*5);i++)
//            System.out.print('-');
//        System.out.println();
//        for(var res : chessBoard)
//        {
//            for(var elem : res)
//                System.out.print((elem? " Q ": "   ") + " |");
//            System.out.println();
//            for(int i=0;i<=(N*5);i++) System.out.print('-');
//            System.out.println();
//        }
//        System.out.println();
        count++;
    }
}
