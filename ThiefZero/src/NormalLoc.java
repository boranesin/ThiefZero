public class NormalLoc extends Location {
    public NormalLoc(Player player, String name) {
        super(player, name);
    }

    @Override
    public boolean onLocation() {
        System.out.println("Mağazaya hoşgeldiniz");
        return true;
    }


}