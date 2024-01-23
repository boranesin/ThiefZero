import java.util.Random;
public class Snake extends Obstacle {


    public Snake(int damage) {
        super(4, damage,12, "yılan", 4);
    }

    public static int randDamage(){
        Random r=new Random();
        int damage=r.nextInt(3,7);

        return damage;
    }

}