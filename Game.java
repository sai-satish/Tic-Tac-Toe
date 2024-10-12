// import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class Game{
    public static void main(String[] args) {
        new Board();
    }
}
class ResetButton extends JButton implements MouseListener{
    JFrame currentGame;
    JButton button;
    ResetButton(String buttonName, JFrame currentGame){
        button = new JButton();
        button.setText(buttonName);
        button.addMouseListener(this);
        this.currentGame = currentGame;
        
    }
    public void mouseMoved(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseClicked(MouseEvent e){
        System.out.println("clicked Reset Button");
        //creating new game
        currentGame.dispose();
        new Board();

        
    }
    public void mouseEntered(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    
    
}
class Board extends JFrame implements MouseListener{
    GridInfo[][] slot = new GridInfo[3][3];
    GridInfo ref = new GridInfo();
    JTextField instructUser = new JTextField(25);
    String[] userNameField = {"X","O"};
    int ActiveUser = 0;
    checkWin winref = new checkWin();
    public int usedCount =0;
    Board(){
        
        JFrame design = new JFrame();
        // System.out.println("design:\t"+design);
        
        
        design.setTitle("Tic-Tac-Toe");
        design.setLayout(new GridBagLayout());
        design.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                // Close the window when the close button is pressed
                design.dispose();
            }
        });

        //instructions field
        JPanel directionsToUser = new JPanel();
        //directionsToUser.setBounds(50,25,300,100);
        
        instructUser.setText("X's choice");
        directionsToUser.add(instructUser);
        // System.out.println(this.toString());
        ResetButton resetButton = new ResetButton("reset", design);

        
        directionsToUser.add(resetButton.button);

        GridBagConstraints instructFieldConstraints = setConstraints(30, 30, 100, 70, 5.0 ,0);
        design.add(directionsToUser,instructFieldConstraints );
        
        //User-Activity field
        JPanel Grids = new JPanel(new GridLayout(3, 3));
        Grids.setBounds(25,200,300,300);
        for(int i=0; i<3;i++){
            for(int j =0; j<3;j++)
            {
                slot[i][j]=new GridInfo();
                JButton temp = slot[i][j].getButton();
                temp.addMouseListener(this);
                Grids.add(temp);
            }
        }
        GridBagConstraints GridFieldConstraints = setConstraints(0, 100, 300, 300, 1.0 ,1.0);
        design.add(Grids,GridFieldConstraints );
        design.pack();
        design.setLocation(0,0);
        design.setSize(380, 400);
        design.setVisible(true);
        
    }
    public GridBagConstraints setConstraints(int x, int y, int width, int height, double weightx, double weighty){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = width;
        constraints.gridheight = height;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = weightx;
        constraints.weighty = weighty;
        return constraints;
    }
    public boolean successfulClick = false;
    public Integer[] user = {1,2};
    public void mouseMoved(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseClicked(MouseEvent e){
        usedCount++;
        // System.out.println(usedCount);
        
        //GridInfo ref = e.getSource();
        JButton source = (JButton)e.getSource();
        int i=0, j=0;
        ab:
        for(i=0; i<3;i++){
            for(j=0; j<3;j++){
                if(slot[i][j].getButton()==source){
                    //System.out.println(i+""+j);
                    break ab;
                }
            }
        }
        if(!ref.checkClick(slot[i][j])){
            if(successfulClick){                
                if(ActiveUser==2){
                    ActiveUser=0;
                }
            }
            successfulClick=false;
            ref.setAsUsed(slot[i][j]);
            int temp = ActiveUser;
            if(temp==0){
                temp=1;
            }
            else{
                temp=0;
            }

            instructUser.setText(userNameField[temp]+"'s chance");
            source.setText(userNameField[ActiveUser]);
            ref.setUser(slot[i][j], userNameField[ActiveUser]);
            source.setBackground(Color.GRAY);
            Font font = new Font("Arial", Font.PLAIN, 48);
            source.setFont(font);
            //instructUser.setText(""+ref.usedCount);
            successfulClick=true;
            if(winref.DecideWin(slot, usedCount, i, j, userNameField[ActiveUser])){
                instructUser.setText("\t"+userNameField[ActiveUser]+" Won\t");
                for(int i1=0; i1<3;i1++){
                    for(int j1=0; j1<3;j1++){
                        slot[i1][j1].getButton().removeMouseListener(this);
                    }
                }
            }
            if(usedCount>=9){
                instructUser.setText("Draw Match");
                for(int i1=0; i1<3;i1++){
                    for(int j1=0; j1<3;j1++){
                        slot[i1][j1].getButton().removeMouseListener(this);
                    }
                }
                return;
            }
            ActiveUser++;

        }
        else{
            instructUser.setText("Already Used the slot");
            
            successfulClick = false;
        }
    }
    public void mouseEntered(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    
    
}
class GridInfo{
    private JButton button = new JButton();
    //public static int usedCount=0;
    private boolean used=false;
    
    private String user=new String();
    
    public JButton getButton(){
        return button;
    }
    public boolean checkClick(GridInfo obj){
        return obj.used;
    }
    public void setAsUsed(GridInfo obj){
        obj.used=true;
    }
    public String getUser(GridInfo obj){
        return obj.user;
    }
    public void setUser(GridInfo obj,String userName){
        obj.user = userName;
    }

    


}
class checkWin{
    public boolean win = false;
    public int high = 3;
    GridInfo ref = new GridInfo();
    public boolean DecideWin(GridInfo[][] gameSituation, int usedSlots, int i, int j, String CurrentUser){
        if(usedSlots>=4){
            //checking for horizontal line
            int consequtiveSlots=0;
            for(int tempJ=0;tempJ<3;tempJ++){
                if(ref.getUser(gameSituation[i][tempJ])==CurrentUser){
                    consequtiveSlots++;
                }
            }
            if(consequtiveSlots==3){
                return true;
            }
            consequtiveSlots=0;

            //checking for vertical line
            for(int tempI=0;tempI<3;tempI++){
                if(ref.getUser(gameSituation[tempI][j])==CurrentUser){
                    consequtiveSlots++;
                }
            }
            if(consequtiveSlots==3){
                return true;
            }
            consequtiveSlots=0;
            
            //checking for cross line
            //||(i==2 && j==0)||(i==2 && j==2)){
            if((i==0 && j==0)||(i==2 && j==2)||(i==1 && j==1)){
                for(int tempI=0, tempJ=0; tempI<3;tempJ++,tempI++){
                    if(ref.getUser(gameSituation[tempI][tempJ])==CurrentUser){
                        consequtiveSlots++;
                    }
                }
            }
            if(consequtiveSlots==3){
                return true;
            }
            consequtiveSlots=0;

            if((i==1 && j==1)||(i==2 && j==0)||(i==0 && j==2)){
                for(int tempI=2,tempJ=0;tempI>=0;tempI--,tempJ++){
                    if(ref.getUser(gameSituation[tempI][tempJ])==CurrentUser){
                        consequtiveSlots++;
                    }
                    else{
                        return false;
                    }
                }
            }
            if(consequtiveSlots==3){
                return true;
            
            }
            consequtiveSlots=0;
        }
        
        return false;
    }
}
