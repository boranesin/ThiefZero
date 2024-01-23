import java.util.Random;

public class Mine extends BattleLoc{
    public Mine(Player player) {
        super(player, "Maden",new Snake(Snake.randDamage()),"Eşya",5);
    }


}