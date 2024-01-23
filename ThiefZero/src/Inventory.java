public class Inventory {
    private boolean water,food,firewood;
    private Weapon weapon;
    private Armor armor;

    public Inventory() {
        this.food=false;
        this.firewood=false;
        this.water=false;
        this.armor=new Armor(-1,"Paçavra",0,0);
        this.weapon =new Weapon("Yumruk",-1,0,0);
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Armor getArmor() {
        return armor;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    public boolean isWater() {
        return water;
    }

    public void setWater(boolean water) {
        this.water = water;
    }

    public boolean isFood() {
        return food;
    }

    public void setFood(boolean food) {
        this.food = food;
    }

    public boolean isFirewood() {
        return firewood;
    }

    public void setFirewood(boolean firewood) {
        this.firewood = firewood;
    }
}