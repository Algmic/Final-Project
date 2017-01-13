
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
    //create projectile
    Rectangle missile = new Rectangle(tank.x + 10, tank.y, 10, 10);
    //create shadow version of tank and missile
    //create tank
    Rectangle stank = new Rectangle(100, 400, 20, 20);
    //create shadow projectile
    Rectangle smissile = new Rectangle(tank.x + 10, tank.y, 10, 10);
    //create shadow projectile
    //create ghost rectangle
    Rectangle gmissile = new Rectangle(tank.x + 10, tank.y, 10, 10);
    //create a slider to determine angle
    JSlider angle = new JSlider(0, 180, 180);
    //create a slider to determine power
    JSlider power = new JSlider(0, 50, 25);
    //create a slider to determine movement
    JSlider movement = new JSlider(0, 2, 1);
    //create a button to test if player is ready
    JButton readyb = new JButton("Ready");
    //create a variable to store fuel
    int fuel = 250;
    //make gravity
    int gravity = 1;
    //difference in y
    double dy = 0;
    //change in x
    double dx = 0;
    //create variable to test if character is dead or not
    boolean dead = false;
    //jump key variable
    boolean launch = false;
    //code for loading backround image
    // BufferedImage bg = loadImage("stock_backround.jpg");
    int x = 100;
    int y = 100;

    public tankGameRough() {
        this.setLayout(null);

        //add angle, and power,
        this.add(angle);
        this.add(power);
        this.add(readyb);

        //remove focus from the sliders to allow key commands to work
        angle.setFocusable(false);
        power.setFocusable(false);
        readyb.setFocusable(false);

        //set the bounds for the slider
        angle.setBounds(50, 50, 100, 20);
        power.setBounds(250, 50, 100, 20);
        readyb.setBounds(450, 50, 100, 20);


    }

    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g) {
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);

        // GAME DRAWING GOES HERE 

        // g.setColor(new Color(angle.getValue(), power.getValue(), 255));

        g.fillRect(0, 0, WIDTH, HEIGHT);

        //code for loading backround image
        //g.drawImage(bg, 0, 0, WIDTH, HEIGHT, null);


        //tank
        g.setColor(Color.red);
        g.fillRect(tank.x, tank.y, tank.width, tank.height);



        //missile when in motion
        g.setColor(Color.gray);
        g.fillRect(smissile.x, smissile.y, missile.width, missile.height);

        //ghost missile
        //g.setColor(Color.gray);
        // g.fillRect(tank.x + 10, tank.y, missile.width, missile.height);

        // GAME DRAWING ENDS HERE
    }

    //code for loading backround image
    /*
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
     */
    // The main game loop
    // In here is where all the logic for my game will go
    public void reset() {
        //reset the bird
        smissile.x = tank.x + 10;
        smissile.y = tank.y;
        dy = 0;
        dx = 0;
        ready = true;
        dead = false;
    }

    public void run() {




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
                //subtract 1 from fuel
                fuel = fuel - 1;
            }
            //if movement value is less then 1 and fuel value is greater then 0
            if (movement.getValue() < 1 && fuel > 0) {
                //subtract from x
                tank.x = tank.x - 1;
                //subtract from fuel
                fuel = fuel - 1;
            }


            readyb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (ready) {
                        ready = false;
                        //get the value of the power slider
                        int missileVelocity = power.getValue() * -1;
                        //set dy to be equal to missile velocity
                        dy = missileVelocity;

                        dx = (Math.cos(Math.toRadians(angle.getValue())) /* power.getValue()*/ * 1000);
                    }
                }
            });


            //if the player is not dead, and the ready button is pressed
            if (!dead && !ready) {
                //get the missile to fall
                //apply gravity
                dy = dy + gravity; //* ((Math.sin(Math.toRadians(angle.getValue())))) * 100;
                //apply change in y to the bird
                smissile.y = smissile.y + (int) dy;
                System.out.println("y = " + smissile.y);


                smissile.x += -dx;
                System.out.println("x = " + smissile.x);
            }

            //test if the missile hit the ground
            if (smissile.y > 590) {
                //set the missile to a height of 590
                smissile.y = 590;
                smissile.x = tank.x + 10;
                smissile.y = tank.y;
                dy = 0;
                dx = 0;
                ready = true;
                dead = false;
                //reset();
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
