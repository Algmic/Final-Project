package tankgame;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

/**
 *
 * @author micla1676
 */


public class tankGameRough extends JComponent implements KeyListener{

    // Height and Width of our game
    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000)/desiredFPS;
    
    //generate a randum number
    int randNum = (int)(Math.random()*(255 - 0 + 1))+ 0;
    
    //create a slider to determine angle
    JSlider angle = new JSlider(0,180,180);
    //create a slider to determine power
    JSlider power = new JSlider(0,180,180);   
    //create a slider to determine movement
    JSlider movement = new JSlider(0,200,100); 
    
    //key commands
    //boolean left = false;
    //boolean right = false;
    
    BufferedImage bg = loadImage("stock_backround.jpg");
    
    int x = 100;
    int y = 100;
    
    public tankGameRough(){
        this.setLayout(null);
        
        this.add(angle);
        this.add(power);
        
        angle.setFocusCycleRoot(false);
        power.setFocusCycleRoot(false);
        
    
        angle.setBounds(50, 50, 100, 20);
        power.setBounds(250,50,100,20);
        

    }
    
    
    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g)
    {
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);
        
        // GAME DRAWING GOES HERE 
        
        g.setColor(new Color(angle.getValue(),power.getValue(),255));

        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        g.drawImage(bg, 0, 0, WIDTH, HEIGHT, null);
        
        g.setColor(Color.red);
        g.fillRect(x, y, 50, 50);
        // GAME DRAWING ENDS HERE
    }
    
    
    public BufferedImage loadImage(String filename){
        BufferedImage img = null;
        try{
            File file = new File(filename);
            img = ImageIO.read(file);
        }catch(Exception e){
            e.printStackTrace();
        }
        return img;
    }
    
    // The main game loop
    // In here is where all the logic for my game will go
    public void run()
    {
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;
        
        // the main game loop section
        // game will end if you set done = false;
        boolean done = false; 
        while(!done)
        {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();
            
            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 
            if(right){
                x = x + 1;
            }
            if(left){
                x = x - 1;
            }

            // GAME LOGIC ENDS HERE 
            
            // update the drawing (calls paintComponent)
            repaint();
            
            
            
            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            if(deltaTime > desiredTime)
            {
                //took too much time, don't wait
            }else
            {
                try
                {
                    Thread.sleep(desiredTime - deltaTime);
                }catch(Exception e){};
            }
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates a windows to show my game
        JFrame frame = new JFrame("Tank Game");
       
        // creates an instance of my game
        tankGameRough game = new tankGameRough();
        // sets the size of my game
        game.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        
        // adds the game to the window
        frame.add(game);
        frame.addKeyListener(game); 
        
        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);
        
        // starts my game loop
        game.run();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
