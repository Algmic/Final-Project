
import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory.Default;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
    long desiredFPS = 120;
    long desiredTime = (1000) / desiredFPS;
    //create boolean ready
    boolean ready = true;
    //create tank
    Rectangle tank = new Rectangle(100, 421, 20, 20);
    //create shadow version of tank and missile
    //create tank
    Rectangle eTank = new Rectangle(700, 421, 20, 20);
    //create projectile
    Rectangle missile = new Rectangle(tank.x + 10, tank.y - 10, 10, 10);
    //create shadow projectile
    //create ghost rectangle
    Rectangle eMissile = new Rectangle(eTank.x, eTank.y - 10, 10, 10);
    //create a slider to determine angle
    JSlider angle = new JSlider(0, 180, 90);
    //create a slider to determine power
    JSlider power = new JSlider(1, 22, 11);
    //create ground
    Rectangle ground = new Rectangle(0, 441, 800, 600);
    //create the wall
    Rectangle wall = new Rectangle(300, 130, 180, 400);
    //create variables for enemy targeting
    int randAngle = 0;
    int randPower = 0;
    //create a integer to store health
    int health = 100;
    int eHealth = 100;
    //set counter for amount of nukes
    int nukeC = 1;
    int eNuke = 1;
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
    // enemy difference in y
    double edy = 0;
    // enemy change in x
    double edx = 0;
    //variables to use when generating random power levels
    int Lamount = 1;
    int Gamount = 22;
    //create variable to test if character is dead or not
    boolean dead = false;
    //create a boolean to test if normal missile is true
    boolean nMissile = true;
    //create a boolean to test if nuke missile is true
    boolean nuke = false;
    //create boolean to test if enemy is dead
    boolean eDead = false;
    //enemy fired variable
    boolean eLaunch = false;
    //boolean to toggle between calculating enemy trajectory and not
    boolean eTraj = true;
    //reset game boolean
    boolean resetGame = false;
    //test if missiles hits the ground
    boolean missileGround = false;
    boolean eMissileGround = false;
    //booleans to load different level
    boolean lv1 = true;
    boolean lv2 = false;
    boolean lv3 = false;
    //allow the enemy to switch between weapons
    boolean eNukes = false;
    //create ground colour
    Color groundColour = new Color(255, 180, 0);
    //create a variable to store fuel
    int fuel = 250;
    //create a variable to store enemy randum numbers for nukes
    int randNukes = 0;
    //convert int fuel into a string
    String fuelS = Integer.toString(fuel);
    //display current weapon
    JTextField weapon = new JTextField("Current Weapon: Missile");
    //create a textfield to display fuel levels
    JTextField fuelD = new JTextField("Fuel: " + fuelS);
    //convert the value of the angle slider into a string
    String angleS = Integer.toString(angle.getValue());
    //create a textfield to display angle
    JTextField angleD = new JTextField("Angle:" + angleS);
    //convert the value of the power slider into a string
    String powerS = Integer.toString(power.getValue());
    //create a textfield to display power levels (which are over 9000)
    JTextField powerD = new JTextField("Power: " + powerS);
    //convert the value of the angle slider into a string
    String healthS = Integer.toString(health);
    //create a textfield to display angle
    JTextField healthD = new JTextField("Health: " + healthS);
    //convert the value of the angle slider into a string
    String eHealthS = Integer.toString(eHealth);
    //create a textfield to display angle
    JTextField eHealthD = new JTextField("Enemy Health: " + eHealthS);
    //display victory screen
    Font victoryFont = new Font("Arial", Font.BOLD, 60);
    //code for loading backround image
    BufferedImage bg1 = loadImage("Default.jpg");
    //BufferedImage bg2 = loadImage("Level2.jpg");
    // BufferedImage bg3 = loadImage("Level-3.jpg");
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
        //display health levels
        this.add(healthD);
        //display health levels
        this.add(eHealthD);
        //display health levels
        this.add(weapon);

        //remove focus from the sliders, textboxes and buttons to allow key commands to work
        angle.setFocusable(false);
        power.setFocusable(false);
        readyB.setFocusable(false);
        fuelD.setFocusable(false);
        angleD.setFocusable(false);
        powerD.setFocusable(false);
        healthD.setFocusable(false);
        eHealthD.setFocusable(false);
        weapon.setFocusable(false);

        //set the bounds for the sliders
        angle.setBounds(0, 0, 200, 20);
        power.setBounds(200, 0, 200, 20);
        //set bounds for the ready button
        readyB.setBounds(400, 00, 100, 20);
        //set bounds for the text boxes
        fuelD.setBounds(500, 0, 100, 20);
        angleD.setBounds(0, 20, 100, 20);
        powerD.setBounds(200, 20, 100, 20);
        eHealthD.setBounds(685, 250, 120, 20);
        healthD.setBounds(000, 250, 80, 20);
        weapon.setBounds(600, 00, 200, 20);
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
        //g.setColor(new Color(angle.getValue(), power.getValue(), 255));

        //fill screen
        g.fillRect(0, 0, WIDTH, HEIGHT);

        if (lv1) {
            //code for loading backround image
            g.drawImage(bg1, 0, 0, WIDTH, HEIGHT, null);
        }
        /*
         if(lv3){
         //code for loading backround image
         g.drawImage(bg3, 0, 0, WIDTH, HEIGHT, null);
         }
         */

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

        //if lv3 is true, draw a rectangle in the screen
        if (lv3) {
            g.setColor(groundColour);
            g.fillRect(wall.x, wall.y, wall.width, wall.height);
        }

        //if health is 0
        if (health <= 0) {
            //score
            g.setColor(Color.BLUE);
            g.setFont(victoryFont);
            g.drawString("YOU LOSE", WIDTH / 4 + 50, 300);
        }
        //if enemy health is 0
        if (eHealth <= 0) {
            //score
            g.setColor(Color.WHITE);
            g.setFont(victoryFont);
            g.drawString("YOU WIN", WIDTH / 4 + 50, 300);
        }
        // GAME DRAWING ENDS HERE
    }

    //code for loading backround image
    public BufferedImage loadImage(String filename) {
        BufferedImage img = null;
        try {
            File file = new File(filename);
            img = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return img;
    }

    // The main game loop
    public void reset() {
        //reset the game
        missile.x = tank.x + 10;
        missile.y = tank.y - 10;
        eMissile.x = eTank.x;
        eMissile.y = eTank.y - 10;
        dy = 0;
        dx = 0;
        edy = 0;
        edx = 0;
        ready = true;
        eLaunch = false;
        eTraj = true;
        // eMissileGround = false;
        missileGround = false;
    }

    public void player() {

        //if the player is not dead, and the ready button is pressed
        if ((!dead && !ready) && (nMissile || nukeC > 0)) {
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
        fuelD.setText("Fuel: " + fuelS);

        //convert the power levels into a string
        powerS = Integer.toString(power.getValue());
        //update text box
        powerD.setText("Power: " + powerS);

        //convert the angle value into a string
        angleS = Integer.toString(angle.getValue());
        //update textboxes
        angleD.setText("Angle: " + angleS);

        //convert fuel into a string
        healthS = Integer.toString(health);
        //update textbox
        healthD.setText("Health: " + healthS);

        //convert fuel into a string
        eHealthS = Integer.toString(eHealth);
        //update textbox
        eHealthD.setText("Enemy Health: " + eHealthS);

        //if missile hits the ground, set missily.y equal to ground height
        if (missile.intersects(ground)) {
            missile.y = 441;
            //set missileGround to true
            missileGround = true;
        }
        //if nuke is used
        if (nuke) {
            //subtract 1 from nuke
            nukeC--;
        }

        /*
         //code to prevent missile falling through ground
         //if color tank is currently on matches ground colour, move it back 1 pixel
         if (bg1.getRGB((int) missile.x + 10, (int) missile.y + 10) == groundColour.getRGB() 
         || bg1.getRGB((int) missile.x + 10, (int) missile.y - 10) == groundColour.getRGB()
         || bg1.getRGB((int) missile.x - 10, (int) missile.y + 10) == groundColour.getRGB()
         || bg1.getRGB((int) missile.x + 10, (int) missile.y - 10) == groundColour.getRGB()){
         missile.y = 441;
         missileGround = true;
         }*/
    }

    public void enemy(int randAngle, int randPower) {
        //if eTrag is true
        if (eTraj) {
            //multiply the sin of the angle by 2x the power slider
            edy = (Math.sin(Math.toRadians(randAngle)) * 1) * (2 * randPower);

            //multiply the cos of the angle by 2x the power slider
            edx = (Math.cos(Math.toRadians(randAngle)) * 1) * (2 * randPower);
            eTraj = false;
        }
        //if the player is not dead, and the ready button is pressed
        if (!eDead && !ready) {
            //get the missile to fall
            //apply gravity
            edy = (edy - gravity);
            //apply change in y to the missile
            eMissile.y += (int) -edy;


            //apply changes in x to the missile
            eMissile.x += (int) -edx;

        }
        /*
         //code to prevent missile falling through ground
         //if color tank is currently on matches ground colour, move it back 1 pixel
         if (bg1.getRGB((int) eMissile.x + 10, (int) eMissile.y + 10) == groundColour.getRGB() 
         || bg1.getRGB((int) eMissile.x + 10, (int) eMissile.y - 10) == groundColour.getRGB()
         || bg1.getRGB((int) eMissile.x- 10, (int) eMissile.y + 10) == groundColour.getRGB()
         || bg1.getRGB((int) eMissile.x + 10, (int) eMissile.y - 10) == groundColour.getRGB())
         {
         eMissile.y = 441;
         eMissileGround = true;
         */
        //}


    }

    public void gameReset() {
        if (resetGame == true) {
            //reset the game
            //reset x coordinate of tanks
            tank.x = 100;
            eTank.x = 700;
            //reset missiles
            missile.x = tank.x + 10;
            missile.y = tank.y - 10;
            eMissile.x = eTank.x;
            eMissile.y = eTank.y - 10;
            //reset directional momentum
            dy = 0;
            dx = 0;
            edy = 0;
            edx = 0;
            //reset ready buttons
            ready = true;
            eLaunch = false;
            eTraj = true;
            // eMissileGround = false;
            //reset missileGround
            missileGround = false;
            //reset health
            health = 100;
            eHealth = 100;
            //reset fuel
            fuel = 250;
            //reset game
            resetGame = false;
            //reset deaths
            eDead = false;
            dead = false;
            //reset weapons
            nukeC = 1;
        }
    }
// In here is where all the logic for my game will go

    public void run() {

        //set the textboxes as uneditable
        fuelD.setEditable(false);
        angleD.setEditable(false);
        powerD.setEditable(false);
        healthD.setEditable(false);
        eHealthD.setEditable(false);
        weapon.setEditable(false);



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
            // GAME LOGIC STARTS HERE \
            //reset full game
            gameReset();

            // dead and eDead are false
            if (!dead && !eDead) {
                //set bounds of the ground
                ground.setBounds(-1000, 441, 3200, 200);
                //set bounds of the enemy tank
                eTank.setBounds(eTank.x, eTank.y, 20, 20);
                //set bounds of the tank
                tank.setBounds(tank.x, tank.y, 20, 20);
                //set bounds of the missile
                missile.setBounds(missile.x, missile.y, 10, 10);
                //set bounds of the enemy missile
                eMissile.setBounds(eMissile.x, eMissile.y, 10, 10);
                //if lv3 is true
                if (lv3) {
                    //set bounds of the wall
                    wall.setBounds(wall.x, wall.y, wall.width, wall.height);
                }
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


                /*
                 //apply gravity to tanks
                 tank.y = tank.y + gravity;
                 eTank.y = eTank.y + gravity;
                 */


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

                //if nMissile is true
                if (nMissile) {
                    //display current weapon
                    weapon.setText("Current Weapon: missile");
                } //if nuke is true
                else if (nuke) {
                    //display current weapon
                    weapon.setText("Current Weapon: Nuke");
                }

                //if the missile has not hit the ground, activate the player function
                if (!missileGround) {
                    player();
                }

                //test if the missile hit the ground
                if (!ready && missileGround && !eDead) {
                    if (!eLaunch) {
                        //generate a random number for the power
                        randAngle = (int) (Math.random() * (90 - 0 + 1)) + 0;
                        randPower = (int) (Math.random() * (Gamount - Lamount + 1)) + Lamount;
                        //set eLaunch equal to true
                        eLaunch = true;
                    }
                    //generate a random number betweeen 0 and 1
                    randNukes = (int) (Math.random() * (1 - 0 + 1)) + 0;
                    //if rand Nukes = 0
                    if (randNukes == 0) {
                        //set eNukes to false
                        eNukes = false;
                    }
                    //if randNukes equals 1
                    if (randNukes == 1) {
                        //set eNukes to true
                        eNukes = true;
                    }
                    //pass randAngle and randPower into the enemy function
                    enemy(randAngle, randPower);

                }
                //if level 3 is true
                if (lv3) {
                    //if missile intersects with the wall
                    if (missile.intersects(wall)) {
                        //set horizontal momententum equal to zero
                        dx = 0;
                    }
                    //if enemy missile intersects with the wall
                    if (eMissile.intersects(wall)) {
                        //set enemy horizontal momentum down to 0
                        edx = 0;
                    }
                    // if the tank intersects with the wall
                    if (tank.intersects(wall)) {
                        //set tank back a pixel
                        tank.x = tank.x - 1;
                    }
                }
                ////test if missile hit enemy tank or the ground underneath the enemy tank
                if (missile.intersects(eTank) || (((missile.x >= eTank.x && missile.x <= eTank.x + 20) && missile.y == 441))) {
                    //if nMissile is true
                    if (nMissile) {
                        //subtract 20 from enemy health
                        eHealth = eHealth - 20;
                        //reset missile position
                        missile.x = tank.x + 10;
                        missile.y = tank.y - 10;
                    } //if nuke is true
                    else if (nuke) {
                        //set enemy helath to 0
                        eHealth = 0;
                    }
                }

                //if enemy missile hits player tank
                if (eMissile.intersects(tank)) {
                    //if eNukes is falsse
                    if (!eNukes) {
                        //subtract 20 from player health
                        health = health - 20;
                    } //eNukes is true
                    else if (eNukes) {
                        //set health to 0
                        health = 0;
                    }
                }
                //if enemy missile hits enemy tank
                if (eMissile.intersects(eTank)) {
                    //if eNukes is false
                    if (!eNukes) {
                        //subtract 20 from enemy health
                        eHealth = eHealth - 20;
                    } //if eNukes is true
                    else if (eNukes) {
                        //set eHealth equal to 0
                        eHealth = 0;
                    }
                }
                //test if missile hit player tank or the ground underneath the players tank
                if (missile.intersects(tank) || (((missile.x >= tank.x && missile.x <= tank.x + 20) && missile.y == 441))) {
                    //if nMissile is true
                    if (nMissile) {
                        //subtract 20 from health
                        health = health - 20;
                        //reset missile position
                        missile.x = tank.x + 10;
                        missile.y = tank.y - 10;
                    } //if nuke is true
                    else if (nuke) {
                        //set health to 0
                        health = 0;
                    }
                }

                //if the enemy missile passes y co-ordiante 441 (hits ground)
                if (eMissile.y > 441) {
                    //if enemy missile x co-ordinate is greater then tank.x
                    if (eMissile.x > tank.x) {
                        //add 1 to Lamount
                        Lamount = Lamount + 1;
                    } //if enemy missile x co ordiante is less then tank.x
                    else if (eMissile.x < tank.x) {
                        //subtract 1 from Gamount
                        Gamount = Gamount - 1;
                    }

                    //convert fuel into a string
                    healthS = Integer.toString(health);
                    //update textbox
                    healthD.setText("Health: " + healthS);

                    //convert fuel into a string
                    eHealthS = Integer.toString(eHealth);
                    //update textbox
                    eHealthD.setText("Enemy Health: " + eHealthS);


                    //if health is equal to 0
                    if (health == 0) {
                        //set dead equal to true
                        dead = true;
                        //reset full game
                        gameReset();
                    } //if enemy health is equal to 0
                    else if (eHealth == 0) {
                        //set eDead equal to true
                        eDead = true;
                        //reset full game
                        gameReset();
                    }
                    //reset back to starting positions
                    reset();
                }
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
        //set value of movement slider to 0 when A is pressed
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_A) {
            movement.setValue(0);
        }
        //set value of movement slider to 0 when D is pressed
        if (key == KeyEvent.VK_D) {
            movement.setValue(2);
        }

        //set value of movement slider to 2 when left arrow is pressed
        if (key == KeyEvent.VK_LEFT) {
            movement.setValue(0);
        }
        //set value of movement slider to 2 when right arrow is pressed
        if (key == KeyEvent.VK_RIGHT) {
            movement.setValue(2);
        }
        //set lv1 equal to true if 1 is pressed
        if (key == KeyEvent.VK_1) {
            lv1 = true;
            lv2 = false;
            lv3 = false;
        }
        //set resetGame equal to true if space is pressed
        if (key == KeyEvent.VK_SPACE) {
            resetGame = true;
        }
        /*
         if (key == KeyEvent.VK_2) {
         lv1 = false;
         lv2 = true;
         lv3 = false;  
         }
         */
        //set lv3 equal to true if 3 is pressed
        if (key == KeyEvent.VK_3) {
            lv2 = false;
            lv3 = true;
        }
        //set normal missile to true and nuke to false
        if (key == KeyEvent.VK_N) {
            nMissile = true;
            nuke = false;
        }
        //set normal missile to false and nuke to true
        if (key == KeyEvent.VK_B) {
            nuke = true;
            nMissile = false;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        //set movement to 1 if A is released
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_A) {
            movement.setValue(1);
        }
        //set movement to 1 if D is released
        if (key == KeyEvent.VK_D) {
            movement.setValue(1);
        }
        //set movement to 1 if left arrow is released
        if (key == KeyEvent.VK_LEFT) {
            movement.setValue(1);
        }
        //set movement to 1 if right arrow is released
        if (key == KeyEvent.VK_RIGHT) {
            movement.setValue(1);
        }
        //set resetGame to false if space is released
        if (key == KeyEvent.VK_SPACE) {
            resetGame = false;
        }



    }
}
