
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

/**
 * credit goes to shellshock Live for inspiring me to program a similar game
 *
 * @author micla1676
 */
public class tankGameRough extends JComponent implements KeyListener {

    // Height and Width of our game
    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000) / desiredFPS;
    //create boolean ready
    boolean ready = true;
    //create tank
    Rectangle tank = new Rectangle(100, 400, 20, 20);
    
    //create shadow version of tank and missile
    //create tank
    Rectangle eTank = new Rectangle(700, 400, 20, 20);
    //create projectile
    Rectangle missile = new Rectangle(tank.x + 10, tank.y, 10, 10);
    //create shadow projectile
    //create ghost rectangle
    Rectangle eMissile = new Rectangle(eTank.x, eTank.y, 10, 10);
    //create a slider to determine angle
    JSlider angle = new JSlider(0, 180, 90);
    //create a slider to determine power
    JSlider power = new JSlider(1, 22, 11);
    
    //create variables for enemy targeting
    int randAngle = 0;
    int randPower = 0;
    //create a slider to determine movement
    JSlider movement = new JSlider(0, 2, 1);
    //create a button to test if player is ready
    JButton readyB = new JButton("Ready");
    //make gravity
    int gravity = 1;
    //difference in y
    double dy = 0;
    //change in x
    double dx = 0;
    //create variable to test if character is dead or not
    boolean dead = false;
    //create boolean to test if enemy is dead
    boolean eDead = false;
    //enemy fired variable
    boolean eLaunch = false;
    //boolean to toggle between calculating enemy trajectory and not
    boolean eTraj = true;
    
    boolean lv1 = false;
    boolean lv2 = false;
    boolean lv3 = false;
    
    
    //create a variable to store fuel
    int fuel = 250;
    //convert int fuel into a string
    String fuelS = Integer.toString(fuel);
    //create a textfield to display fuel levels
    JTextField fuelD = new JTextField(fuelS);
    //convert the value of the angle slider into a string
    String angleS = Integer.toString(angle.getValue());
    //create a textfield to display angle
    JTextField angleD = new JTextField(angleS);
    //convert the value of the power slider into a string
    String powerS = Integer.toString(power.getValue());
    //create a textfield to display power levels (which are over 9000)
    JTextField powerD = new JTextField(powerS);
    //code for loading backround image
    // BufferedImage bg = loadImage("stock_backround.jpg");
    int x = 100;
    int y = 100;

    public tankGameRough() {
        //set layout
        this.setLayout(null);

        //add angle
        this.add(angle);
        //add power
        this.add(power);
        //add ready button
        this.add(readyB);
        //add fuel display
        this.add(fuelD);
        //display anglle
        this.add(angleD);
        //display power levels
        this.add(powerD);
       
        //remove focus from the sliders, textboxes and buttons to allow key commands to work
        angle.setFocusable(false);
        power.setFocusable(false);
        readyB.setFocusable(false);
        fuelD.setFocusable(false);
        angleD.setFocusable(false);
        powerD.setFocusable(false);

        //set the bounds for the sliders
        angle.setBounds(0, 0, 200, 20);
        power.setBounds(200, 0, 200, 20);
        //set bounds for the ready button
        readyB.setBounds(450, 50, 100, 20);
        //set bounds for the text boxes
        fuelD.setBounds(650, 50, 100, 20);
        angleD.setBounds(0, 20, 100, 20);
        powerD.setBounds(200, 20, 100, 20);
    }

    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g) {
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);

        // GAME DRAWING GOES HERE 

        //change background color according to the sliders
        // g.setColor(new Color(angle.getValue(), power.getValue(), 255));

        //fill screen
        g.fillRect(0, 0, WIDTH, HEIGHT);

        //code for loading backround image
        //g.drawImage(bg, 0, 0, WIDTH, HEIGHT, null);


        // create tank
        g.setColor(Color.red);
        g.fillRect(tank.x, tank.y, tank.width, tank.height);

        //missile when in motion
        g.setColor(Color.gray);
        g.fillRect(missile.x, missile.y, missile.width, missile.height);
        
        //create enemy tank
        g.setColor(Color.yellow);
        g.fillRect(eTank.x, eTank.y, eTank.width, eTank.height);
        //enemy missile
        g.setColor(Color.gray);
        g.fillRect(eMissile.x, eMissile.y, eMissile.width, eMissile.height);

        // GAME DRAWING ENDS HERE
    }

    //code for loading backround image
    
    if(lv1 == true){
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
    
    public void reset() {
                //reset the game
                missile.x = tank.x + 10;
                missile.y = tank.y;
                eMissile.x = eTank.x;
                eMissile.y = eTank.y;
                dy = 0;
                dx = 0;
                ready = true;
                eLaunch = false;
                dead = false;
                eTraj = true;
    }
    public void player(){
        if(missile.y < 590){
        //if the player is not dead, and the ready button is pressed
            if (!dead && !ready) {
                //get the missile to fall
                //apply gravity
                dy = (dy - gravity);
                //apply change in y to the missile
                missile.y += (int) -dy;
                

                //apply changes in x to the missile
                missile.x += (int) -dx;
               
            }
            //convert fuel into a string
            fuelS = Integer.toString(fuel);
            //update textbox
            fuelD.setText(fuelS);
            
            //convert the power levels into a string
            powerS = Integer.toString(power.getValue());
            //update text box
            powerD.setText(powerS);
            
            //convert the angle value into a string
            angleS = Integer.toString(angle.getValue());
            //update textboxes
            angleD.setText(angleS);
    }
    }
    
    public void enemy(int randAngle,int randPower){
        
        if(eTraj){
        //multiply the sin of the angle by 2x the power slider
        dy = (Math.sin(Math.toRadians(randAngle)) * 1) * (2 * randPower);
        
        //multiply the cos of the angle by 2x the power slider
        dx = (Math.cos(Math.toRadians(randAngle)) * 1) * (2 * randPower);
        eTraj = false;
        }
        //if the player is not dead, and the ready button is pressed
            if (!eDead && !ready) {
                //get the missile to fall
                //apply gravity
                dy = (dy - gravity);
                //apply change in y to the missile
                eMissile.y += (int) -dy;
                

                //apply changes in x to the missile
               eMissile.x += (int) -dx;
               
            }
    }
            
// In here is where all the logic for my game will go
    public void run() {

        //set the textboxes as uneditable
        fuelD.setEditable(false);
        angleD.setEditable(false);
        powerD.setEditable(false);

        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;

        // the main game loop section
        // game will end if you set done = false;
        boolean done = false;
        while (!done) {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();

            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 


            //get tank to move
            //stop if the slider is at position 1 or if fuel is 0
            if (movement.getValue() == 1 || fuel == 0) {
            }
            //if movement value is greater then 1 and fuel value is greater then 0
            if (movement.getValue() > 1 && fuel > 0) {
                //add 1 to x
                tank.x = tank.x + 1;
                missile.x = missile.x + 1;
                //subtract 1 from fuel
                fuel = fuel - 1;
            }
            //if movement value is less then 1 and fuel value is greater then 0
            if (movement.getValue() < 1 && fuel > 0) {
                //subtract from x
                tank.x = tank.x - 1;
                missile.x = missile.x - 1;
                //subtract from fuel
                fuel = fuel - 1;
            }

            //when button is clicked
            readyB.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (ready) {
                        //set ready to false
                        ready = false;
                        
                        //multiply the sin of the angle by 2x the power slider
                        dy = (Math.sin(Math.toRadians(angle.getValue())) * 1) * (2 * power.getValue());
                        
                        //multiply the cos of the angle by 2x the power slider
                        dx = (Math.cos(Math.toRadians(angle.getValue())) * 1) * (2 * power.getValue());

                    }
                }
            });
            
            player();
            
            
            
            
            //test if the missile hit the ground
            if (!ready && missile.y > 590 && !eDead) {
                if(!eLaunch){
                //generate a random number for the power
                randAngle = (int)(Math.random()*(90 - 1 + 1))+ 1;
                randPower = (int)(Math.random()*(22 - 1 + 1))+ 1;
                eLaunch = true;
                }
                enemy(randAngle,randPower);
                
            }
            
            if (eMissile.y > 590){
                reset();
            }
            // GAME LOGIC ENDS HERE 

            // update the drawing (calls paintComponent)
            repaint();



            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            if (deltaTime > desiredTime) {
                //took too much time, don't wait
            } else {
                try {
                    Thread.sleep(desiredTime - deltaTime);
                } catch (Exception e) {
                };
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
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));

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
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_A) {
            movement.setValue(0);
        }
        if (key == KeyEvent.VK_D) {
            movement.setValue(2);
        }
        if (key == KeyEvent.VK_LEFT) {
            movement.setValue(0);
        }
        if (key == KeyEvent.VK_RIGHT) {
            movement.setValue(2);
        }
        if (key == KeyEvent.VK_1) {
            lv1 = true;
            lv2 = false;
            lv3 = false;        
        }
        if (key == KeyEvent.VK_2) {
            lv1 = false;
            lv2 = true;
            lv3 = false;  
        }
        if (key == KeyEvent.VK_3) {
            lv1 = false;
            lv2 = false;
            lv3 = true;  
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_A) {
            movement.setValue(1);
        }
        if (key == KeyEvent.VK_D) {
            movement.setValue(1);
        }

        if (key == KeyEvent.VK_LEFT) {
            movement.setValue(1);
        }
        if (key == KeyEvent.VK_RIGHT) {
            movement.setValue(1);
        }


    }
}
