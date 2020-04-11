package sample;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Delayed;
class Plant {
    private String plant_name;
    @FXML
    private ImageView plant_image,anotherimage;
    private int hp;

    public Plant(String p_name,ImageView p_image,int hp,ImageView another){
        plant_name=p_name;
        anotherimage=another;
        plant_image=p_image;
        this.hp=hp;

    }
    public ImageView getanotherr(){
        return  anotherimage;
    }
    public void setHp(int hp) {
        this.hp = hp;
    }

    public ImageView getPlant_image() {
        return plant_image;
    }

    public void setPlant_image(ImageView plant_image) {
        this.plant_image = plant_image;
    }

    public String getPlant_name() {
        return plant_name;
    }

    public void setPlant_name(String plant_name) {
        this.plant_name = plant_name;
    }

    public int getHp() {
        return hp;
    }
    public boolean contains(){
        return (plant_image==null);
    }
}
class Zombies{
    public int level;
    public int counter;
    ImageView own;
    public int time;
    public Zombies(int i,int c,ImageView j)
    {
        level=i;
        counter=c;
        own=j;
    }
    public boolean chcounter(){
        return(3*level==counter);
    }
}
public class Second {
    static int level=1;
    static int counter;
    volatile static boolean[][] occupied;
    static LinkedList<ImageView> zombietype;
   volatile static LinkedList<Zombies> allzombies[];
    static Plant[][] allplants=new Plant[9][5];;
    static ImageView [] alllawnsmovers;
    //static LinkedList<ImageView> allzombies[];
    private boolean selectsun,selectpea,selectcheery,selectpotato;
    @FXML
    private AnchorPane main_screen;
    @FXML
    private AnchorPane level_screen;
    @FXML
    private  ImageView menu_image;
    @FXML
    private  Text level_text1;
    @FXML
    private  Text level_text2;
    @FXML
    private  Button level_continue;
    @FXML
    private  Button game_exit;
    @FXML
    private  Button level_exit;
    @FXML
    private  Button game_continue;
    @FXML
    private  Button save_game;
    @FXML
    private javafx.scene.layout.GridPane GridPane;
    @FXML
    private ImageView SunPlant;
    @FXML
    private ImageView CherryPlant;
    @FXML
    private ImageView PotatoPlant;
    @FXML
    private ImageView PeaPlant;
    @FXML
    private ImageView background;
    @FXML
    private Text time;
    @FXML
    private Text SunToken;
    @FXML
    private ImageView zombie;
    @FXML
    private ImageView lawn_mover;
    @FXML
    private ImageView lawn_mover1;
    @FXML
    private ImageView lawn_mover2;
    @FXML
    private ImageView lawn_mover3;
    @FXML
    private ImageView lawn_mover4;
    @FXML
    private ImageView lawn_mover5;
    @FXML
    private ImageView pea;

    @FXML
    private ImageView sun;
    @FXML
    void initialize()
    {   zombietype=new LinkedList<>();
        zombie=new ImageView(new Image("/zombie_normal.gif"));
        zombietype.add(zombie);
        ImageView zombie2=new ImageView(new Image("/level2.gif"));
        ImageView zombie3=new ImageView(new Image("/level3.gif"));
        ImageView zombie4=new ImageView(new Image("/level4.gif"));
        zombietype.add(zombie2);
        zombietype.add(zombie3);
        zombietype.add(zombie4);
        zombietype.get(1).setFitWidth(100);
        zombietype.get(1).setFitHeight(100);

        zombietype.get(2).setFitWidth(100);
        zombietype.get(2).setFitHeight(100);

        zombietype.get(3).setFitWidth(100);
        zombietype.get(3).setFitHeight(100);
        zombietype.add(zombie3);
        zombietype.add(zombie4);
    SunPlant=new ImageView(new Image("/sun_flower.gif"));
    PeaPlant=new ImageView(new Image("/pea_shooter.gif"));
    pea=new ImageView(new Image("/Pea.png"));
   CherryPlant=new ImageView(new Image("/Cherrybomb.png"));
   PotatoPlant=new ImageView(new Image("/Walnut.png"));
    lawn_mover=new ImageView(new Image("/lawn_mower.gif"));
    sun=new ImageView(new Image("/sun.gif"));
    allzombies=new LinkedList[5];
        alllawnsmovers=new ImageView[5];
        alllawnsmovers[0]=lawn_mover1;
        alllawnsmovers[1]=lawn_mover2;
        alllawnsmovers[2]=lawn_mover3;
        alllawnsmovers[3]=lawn_mover4;
        alllawnsmovers[4]=lawn_mover5;
    for(int i=0;i<5;i++)
    {
        allzombies[i]=new LinkedList<>();
    }
        Random rand=new Random();
        ImageView dropsun=new ImageView(sun.getImage());
        dropsun.setFitHeight(24);
        dropsun.setFitWidth(24);
        occupied=new boolean[9][5];
        time.setText(Integer.toString(Second.level*6));
        Timer timer = new Timer();
        //Timer sun_drop= new Timer();
        Second.counter=0;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Second.counter++;
                if(Second.counter==10){
                    Platform.runLater(() -> {
                        if(GridPane.getChildren().contains(dropsun))
                        {
                            GridPane.getChildren().remove(dropsun);
                        }
                        int xpos=rand.nextInt(8)+1;
                        int ypos=rand.nextInt(4)+1;
                        GridPane.add(dropsun,xpos,ypos);
                        TranslateTransition suntransition=new TranslateTransition();
                        suntransition.setNode(dropsun);
                        suntransition.setFromY(-500);
                        suntransition.setToY(ypos*9);
                        suntransition.setCycleCount(1);
                        suntransition.setDuration(Duration.seconds(2));
                        suntransition.play();
                        dropsun.setOnMouseClicked((MouseEvent e)->{
                            int  suntokens=Integer.parseInt(SunToken.getText());
                            suntokens+=10;
                            SunToken.setText(String.valueOf(suntokens));
                            GridPane.getChildren().remove(dropsun);
                        } );
                    });
                    Second.counter=0;
                }
                int sec=Integer.parseInt(time.getText());
                if(sec>0)
                    sec--;
                if(sec==0)
                    //level_screen.setDisable(false);
                    //sec=20;
                    level_screen.setVisible(true);
                level_screen.setOpacity(1);
                level_text1.setVisible(true);
                level_text2.setVisible(true);
                level_continue.setVisible(true);
                level_exit.setVisible(true);
                if(sec==0) {
                    menu_image.setVisible(false);
                    menu_image.setOpacity(0);
                    game_continue.setVisible(false);
                    game_exit.setVisible(false);
                    save_game.setVisible(false);
                    game_continue.setDisable(true);
                    game_exit.setDisable(true);
                    save_game.setDisable(true);
                    level_text1.setOpacity(1);
                    level_text2.setOpacity(1);
                    level_exit.setOpacity(1);
                    level_continue.setOpacity(1);
                    level_continue.setDisable(false);
                    level_exit.setDisable(false);
                    level_text1.setText("You completed level " +Second.level+" move to level "+(Second.level+1));
                    String unlocked=" ";
                    if(level==1)
                        unlocked="Sunflower";
                    else if(level==2)
                        unlocked="Walnut";
                    else if(level==3)
                        unlocked="Cherry";

                    level_text2.setText("You unlocked new plant: "+unlocked);
                }
                time.setText(String.valueOf(sec));

            }
        }, 0, 1000);
        /*Random rand=new Random();
        ImageView dropsun=new ImageView(sun.getImage());
        dropsun.setFitHeight(24);
        dropsun.setFitWidth(24);
       //GridPane.add(dropsun,4,3);
        sun_drop.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(GridPane.getChildren().contains(dropsun))
                    {
                        GridPane.getChildren().remove(dropsun);
                    }
                    int xpos=rand.nextInt(8)+1;
                    int ypos=rand.nextInt(4)+1;
                    GridPane.add(dropsun,xpos,ypos);
                    TranslateTransition suntransition=new TranslateTransition();
                    suntransition.setNode(dropsun);
                    suntransition.setFromY(-500);
                    suntransition.setToY(ypos*9);
                    suntransition.setCycleCount(1);
                    suntransition.setDuration(Duration.seconds(2));
                    suntransition.play();
                    dropsun.setOnMouseClicked((MouseEvent e)->{
                        int  suntokens=Integer.parseInt(SunToken.getText());
                        suntokens+=10;
                        SunToken.setText(String.valueOf(suntokens));
                        GridPane.getChildren().remove(dropsun);
                    } );
                });
            }

        }, 0, 5000);*/
        zombiecreate(Second.level);
    }
    void zombiecreate(int level){

        Random rand=new Random();
        Timer Zombie=new Timer();
        Zombie.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    int zombielevel=rand.nextInt(Second.level);
                    ImageView copy=new ImageView(zombietype.get(zombielevel).getImage());
                    copy.setFitHeight(60);
                    copy.setFitWidth(50);
                    int ypos=rand.nextInt(5);
                    GridPane.add(copy,8,ypos);
                    allzombies[ypos].add(new Zombies(zombielevel+1,0,copy));
                    TranslateTransition zombietransition=new TranslateTransition();
                    zombietransition.setNode(copy);
                    zombietransition.setToX(-400);
                    zombietransition.setFromX(0);
                    zombietransition.setCycleCount(1);
                    zombietransition.setDuration(Duration.seconds(50));
                    zombietransition.play();
                    copy.translateXProperty().addListener((Observable ob) ->{if(allzombies[ypos].size()!=0 && allzombies[ypos].get(0).own==null){
                        allzombies[ypos].remove(0);
                        zombietransition.stop();
                    }
                        else if(allzombies[ypos].size()!=0 &&Second.occupied[8-(int)(Math.abs(allzombies[ypos].get(0).own.getTranslateX())/45)][ypos]){
                            //System.out.println(allplants[8-(int)(Math.abs(copy.getTranslateX())/45)][ypos].getPlant_name());
                            GridPane.getChildren().remove(allplants[8-(int)(Math.abs(allzombies[ypos].get(0).own.getTranslateX())/45)][ypos].getPlant_image());
                            if(allplants[8-(int)(Math.abs(allzombies[ypos].get(0).own.getTranslateX())/45)][ypos].getanotherr()!=null)
                            GridPane.getChildren().remove(allplants[8-(int)(Math.abs(allzombies[ypos].get(0).own.getTranslateX())/45)][ypos].getanotherr());
                            if(allplants[8-(int)(Math.abs(allzombies[ypos].get(0).own.getTranslateX())/45)][ypos].getPlant_name().compareToIgnoreCase("cherry")==0)
                            {
                                GridPane.getChildren().remove(allzombies[ypos].get(0).own);
                                zombietransition.stop();
                            }
                            allplants[8-(int)(Math.abs(allzombies[ypos].get(0).own.getTranslateX())/45)][ypos]=null;
                            Second.occupied[8-(int)(Math.abs(allzombies[ypos].get(0).own.getTranslateX())/45)][ypos]=false;
                        allzombies[ypos].remove(0);

                        }
                    else if(allzombies[ypos].size()!=0 && (int)(allzombies[ypos].get(0).own.getTranslateX())==-400 &&alllawnsmovers[ypos]!=null) {
                        TranslateTransition t1tt1=new TranslateTransition();
                        //t1tt1.setDelay(Duration.seconds(14.5));
                        t1tt1.setDuration(Duration.seconds(8));
                        t1tt1.setNode(alllawnsmovers[ypos]);
                        t1tt1.setToX(450);
                        //tt.setToY();
                        t1tt1.setCycleCount(1);
                        t1tt1.play();
                        t1tt1.setOnFinished(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                main_screen.getChildren().remove(alllawnsmovers[ypos]);
                                alllawnsmovers[ypos]=null;
                                //getPlotChildren().remove(candle);
                            }
                        });
                        for(int y=0;y<allzombies[ypos].size();y++){
                            GridPane.getChildren().remove(allzombies[ypos].get(y).own);
                        }
                        allzombies[ypos]=new LinkedList<>();
                        for(int x=0;x<9;x++){
                            if(Second.occupied[x][ypos]!=false){
                                Second.occupied[x][ypos]=false;
                                GridPane.getChildren().remove(allplants[x][ypos].getPlant_image());
                                if(allplants[x][ypos].getanotherr()!=null){
                                    GridPane.getChildren().remove(allplants[x][ypos].getanotherr());
                                }
                                allplants[x][ypos]=null;

                            }
                        }
                    }
                    else if(allzombies[ypos].size()!=0 && (int)(allzombies[ypos].get(0).own.getTranslateX())==-400 && alllawnsmovers[ypos]==null){
                        System.out.println("Zombie in the house");
                        System.exit(0);
                    }

                    });

                });
            }

        }, 0, 10000);
    }
    @FXML
    void ThirdScene(ActionEvent event) throws IOException {
        level_screen.setVisible(true);
        level_screen.setOpacity(1);
        //level_text1.setVisible(false);
        //level_text2.setVisible(false);
        //level_text1.setDisable(true);
        level_exit.setVisible(false);
        level_continue.setVisible(false);
        level_exit.setOpacity(0);
        //level_continue.setVisible(false);
        //level_exit.setVisible(false);
        menu_image.setVisible(true);
        menu_image.setOpacity(1);
        game_continue.setVisible(true);
        game_exit.setVisible(true);
        save_game.setVisible(true);
        game_continue.setDisable(false);
        game_exit.setDisable(false);
        save_game.setDisable(false);
        level_continue.setDisable(true);
        level_exit.setDisable(true);
        level_continue.setVisible(false);
        level_exit.setVisible(false);
        level_continue.setOpacity(0);
        level_exit.setOpacity(0);
        level_text1.setOpacity(0);
        level_text2.setOpacity(0);

    }
    @FXML
    void continue_game(ActionEvent event) throws IOException {
        level_screen.setOpacity(0);
        level_screen.setVisible(false);
        menu_image.setVisible(false);
        menu_image.setOpacity(0);
        game_continue.setVisible(false);
        game_exit.setVisible(false);
        save_game.setVisible(false);
        //time.setText("2");
        //initialize();
    }
    @FXML
    void continue_level(ActionEvent event) throws IOException {
        level_screen.setOpacity(0);
        level_screen.setVisible(false);
        level_text1.setVisible(false);
        level_text2.setVisible(false);
        level_continue.setVisible(false);
        level_exit.setVisible(false);
        Second.level++;
        time.setText(Integer.toString(Second.level*60));
        //initialize();
    }
    @FXML
    void exit_level(ActionEvent event) throws IOException {
        System.exit(0);
    }
    @FXML
    void SunTokenAdd(ActionEvent event) throws IOException {
        if(sun.getOpacity()==1){
            int  suntokens=Integer.parseInt(SunToken.getText());
            suntokens+=10;
            SunToken.setText(String.valueOf(suntokens));
            sun.setOpacity(0);
            FadeTransition sun_fade00 = new FadeTransition();
            sun_fade00.setDelay(Duration.seconds(3));
            sun_fade00.setDuration(Duration.millis(1000));
            sun_fade00.setFromValue(0);
            sun_fade00.setToValue(1);
            sun_fade00.setCycleCount(1);
            sun_fade00.setAutoReverse(true);
            sun_fade00.setNode(sun);
            sun_fade00.play();
        }
    }
    @FXML
    void BuyCherry(ActionEvent event) {
        selectpea=true;
        selectcheery=true;
        selectpotato=false;
        selectsun=false;
        System.out.println("Buying cherry");
    }

    @FXML
    void BuyPeaShooter(ActionEvent event) {
        selectpea=true;
        selectcheery=false;
        selectpotato=false;
        selectsun=false;
        System.out.println("Buying peasShooter");
    }

    @FXML
    void BuyPotato(ActionEvent event) {
        selectpea=false;
        selectcheery=false;
        selectpotato=true;
        selectsun=false;
        System.out.println("Buying potato");
    }

    @FXML
    void BuySunFlower(ActionEvent event) {
        selectpea=false;
        selectcheery=false;
        selectpotato=false;
        selectsun=true;
        System.out.println("Buying SunFlower");
    }
    @FXML
    void PlacePlant(MouseEvent event) throws FileNotFoundException {
        if(selectsun==true||selectpea==true||selectpotato==true||selectcheery==true) {
            double xpos = event.getX();
            double ypos = event.getY();
           // System.out.println(xpos + " " + ypos);
            ImageView cloned = new ImageView();
            GridPane.add(cloned, (int) xpos / 50, (int) ypos / 60);
            if (selectsun==true && !Second.occupied[(int)xpos/50][(int)ypos/60]) {
                cloned.setFitWidth(38);
                cloned.setFitHeight(38);
                Second.occupied[(int)xpos/50][(int)ypos/60]=true;
                //System.out.println((int)xpos/50+" "+(int)ypos/60);
                ImageView copiedsun=new ImageView(sun.getImage());
                allplants[(int)xpos/50][(int)ypos/60]=new Plant("Sunflower",cloned,1,copiedsun);
                copiedsun.setFitWidth(20);
                copiedsun.setFitHeight(20);
                GridPane.add(copiedsun,(int)xpos/60,(int)ypos/60);
                GridPane.setHalignment(copiedsun, HPos.RIGHT);
                //copiedsun.setY(ypos/60+200);
                cloned.setImage(SunPlant.getImage());
                Platform.runLater(() -> {;
                copiedsun.setOnMouseClicked((MouseEvent e)->{
                    int  suntokens=Integer.parseInt(SunToken.getText());
                    suntokens+=10;
                    SunToken.setText(String.valueOf(suntokens));
                    copiedsun.setOpacity(0);
                    FadeTransition sun_fade00 = new FadeTransition();
                    sun_fade00.setDelay(Duration.seconds(3));
                    sun_fade00.setDuration(Duration.millis(5000));
                    sun_fade00.setFromValue(0);
                    sun_fade00.setToValue(1);
                    sun_fade00.setCycleCount(1);
                    sun_fade00.setAutoReverse(true);
                    sun_fade00.setNode(copiedsun);
                    sun_fade00.play();
                } );});
            } else if (selectpotato==true&& !Second.occupied[(int)xpos/50][(int)ypos/60]) {
                cloned.setImage(PotatoPlant.getImage());
                cloned.setFitHeight(70);
                cloned.setFitWidth(70);
                Second.occupied[(int)xpos/50][(int)ypos/60]=true;
                //System.out.println((int)xpos/50+" "+(int)ypos/60);
                allplants[(int)xpos/50][(int)ypos/60]=new Plant("Potato",cloned,10,null);
            } else if (selectcheery==true&& !Second.occupied[(int)xpos/50][(int)ypos/60]) {
                Second.occupied[(int)xpos/50][(int)ypos/60]=true;
                System.out.println(Second.occupied[(int)xpos/50][(int)ypos/60]);
                cloned.setFitHeight(50);
                cloned.setFitWidth(50);
                cloned.setImage(CherryPlant.getImage());
                allplants[(int)xpos/50][(int)ypos/60]=new Plant("Cherry",cloned,1,null);
               // System.out.println(allplants[(int)xpos/50][(int)ypos/60].getPlant_name());
                //System.out.println((int)xpos/50+" "+(int)ypos/60);
                /*cloned.translateXProperty().addListener((Observable ob) ->{
                    System.out.println(cloned.getTranslateX());
                    if(allzombies[(int)ypos/60].size()!=0 && cloned.getLayoutX()+Math.abs(allzombies[(int)ypos/60].get(0).own.getTranslateX())>250)
                    {
                        GridPane.getChildren().remove(allzombies[(int)ypos/60].get(0).own);
                        allzombies[(int)ypos/60].remove(0);
                        GridPane.getChildren().remove(cloned);
                        occupied[(int)xpos/50][(int)ypos/60]=false;
                    }
                });*/
            } else if (selectpea==true&& !Second.occupied[(int)xpos/50][(int)ypos/60]) {
                Second.occupied[(int)xpos/50][(int)ypos/60]=true;
                InvalidationListener invalid;
                cloned.setFitWidth(38);
                cloned.setFitHeight(38);
                cloned.setImage(PeaPlant.getImage());
                //System.out.println((int)xpos/50+" "+(int)ypos/60);
                Platform.runLater(() -> {
                ImageView copypea=new ImageView();
                    allplants[(int)xpos/50][(int)ypos/60]=new Plant("PeaPlant",cloned,1,copypea);
                GridPane.add(copypea, (int)Math.ceil(xpos / 60), (int)ypos / 60);
                copypea.setImage(pea.getImage());
                copypea.setFitWidth(15);
                copypea.setFitHeight(15);
                TranslateTransition tt0=new TranslateTransition();
                tt0.setDuration(Duration.seconds(5));
                tt0.setNode(copypea);
                //System.out.println(cloned.getX());
                tt0.setFromX((int)Math.ceil(xpos / 60)*8);
                tt0.setToX(400);
                tt0.setCycleCount(1000);
                tt0.play();
                copypea.translateXProperty().addListener((Observable ob) ->{
                    //System.out.println("Peas:  "+copypea.getTranslateX());
                   //System.out.println("Zombie:  "+allzombies[(int)ypos/60].get(0).own.getTranslateX());
                    if(allzombies[(int)ypos/60].size()!=0 &&allzombies[(int)ypos/60].get(0).own!=null && copypea.getBoundsInParent().intersects(allzombies[(int)ypos/60].get(0).own.getBoundsInParent())){
                        tt0.playFromStart();
                        allzombies[(int)ypos/60].get(0).counter++;
                        if(allzombies[(int)ypos/60].get(0).chcounter()){
                            GridPane.getChildren().remove(allzombies[(int)ypos/60].get(0).own);
                            allzombies[(int)ypos/60].get(0).own=null;
                        //allzombies[(int)ypos/60].get(0).own=null;
                        //allzombies[(int)ypos/60].remove(0);
                            }
                    }
                } );
                });
            }
        }
        selectpea=false;
        selectcheery=false;
        selectpotato=false;
        selectsun=false;
    }


}
