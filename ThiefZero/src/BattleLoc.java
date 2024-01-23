import java.util.Random;

public class BattleLoc extends Location{
    private Obstacle obstacle;
    private String award;
    private int maxObstacle;
    private int obsCount=0;
    public BattleLoc(Player player, String name,Obstacle obstacle,String award,int maxObstacle) {

        super(player, name);
        this.obstacle=obstacle;
        this.award=award;
        this.maxObstacle=maxObstacle;
    }

    @Override
    public boolean onLocation() {

        int obsNumber=this.randomObstacleNumber();
        System.out.println("Şuan buradasınız : "+this.getName());
        System.out.println("Dikkatli ol! Burada "+obsNumber+" tane "+this.getObstacle().getName()+" yaşıyor !");
        System.out.println("<S>avaş veya <K>aç : ");
        String selectCase=input.nextLine();
        selectCase=selectCase.toUpperCase();
        if(selectCase.equals("S")&&combat(obsNumber)){
            if(combat(obsNumber)){
                System.out.println(this.getName()+"bölgesindeki tüm düşmanları yendiniz !");
                if(this.award.equals("Food")&&player.getInventory().isFood()==false){
                    System.out.println(this.award+ " Kazandınız! ");
                    player.getInventory().setFood(true);
                }else if(this.award.equals("Water")&&player.getInventory().isWater()==false){
                    System.out.println(this.award+ " Kazandınız! ");
                    player.getInventory().setWater(true);
                }else if(this.award.equals("Firewood")&&player.getInventory().isFirewood()==false){
                    System.out.println(this.award+ " Kazandınız! ");
                    player.getInventory().setFirewood(true);
                }
                return true;
            }
        }
        if(this.getPlayer().getHealth()<=0){
            System.out.println("Öldünüz !");
            return false;
        }


        return true;
    }


    public int randomObstacleNumber(){
        Random r=new Random();
        return r.nextInt(this.getMaxObstacle())+1;
    }
    public boolean isPlayerStart(){//kimin önce başlayacağının random seçim metodu
        //true:oyuncu önce başlayacak
        //false:canavar önce başlayacak.
        double rondomStartNumber=Math.random();
        return rondomStartNumber<=0.5;
    }
    public boolean combat(int obsNumber) {

        for (int i = 1; i <= obsNumber; i++) {
            this.getObstacle().setHealth(this.getObstacle().getOrjinalHealth());
            playerStats();
            obstacleStats(i);
            if(isPlayerStart()){
                System.out.println("Siz canavara vurdunuz !");
                this.getObstacle().setHealth(this.getObstacle().getHealth() - this.getPlayer().getTotalDamage());
                afterHit();
            }else{
                System.out.println("Canavar size vurdu !");
                int obstacleDamage = this.getObstacle().getDamage() - this.getPlayer().getInventory().getArmor().getBlock();
                if (obstacleDamage < 0) {
                    obstacleDamage = 0;
                }
                this.getPlayer().setHealth(this.getPlayer().getHealth() - obstacleDamage);
                afterHit();

            }
            while (this.getPlayer().getHealth() > 0 && this.getObstacle().getHealth() > 0) {
                System.out.println("<V>ur vaya <K>aç : ");
                String selectCombat = input.nextLine().toUpperCase();
                if (selectCombat.equals("V")) {
                    System.out.println("Siz vurdunuz !");
                    this.getObstacle().setHealth(this.getObstacle().getHealth() - this.getPlayer().getTotalDamage());
                    afterHit();
                    if (this.getObstacle().getHealth() > 0) {
                        System.out.println();
                        System.out.println("Canavar size vurdu !");
                        int obstacleDamage = this.getObstacle().getDamage() - this.getPlayer().getInventory().getArmor().getBlock();
                        if (obstacleDamage < 0) {
                            obstacleDamage = 0;
                        }
                        this.getPlayer().setHealth(this.getPlayer().getHealth() - obstacleDamage);
                        afterHit();
                    }
                }else {
                    return false;
                }

            }

            if(this.getObstacle().getHealth()<this.getPlayer().getHealth()){
                System.out.println("Düşmanı Yendiniz !");
                System.out.println(this.getObstacle().getAward()+ " para kazandınız !");
                this.getPlayer().setMoney(this.getPlayer().getMoney()+this.getObstacle().getAward());

                this.obsCount++;//öldürülen obstacle sayısı
                if(this.obsCount==obsNumber){

                    System.out.println("Bu bölgenin ödülü : "+this.getAward());
                    if(this.obstacle.getName().equals("Zombi")){
                        player.getInventory().setFood(true);
                    }
                    if(this.obstacle.getName().equals("Vampir")){
                        player.getInventory().setFirewood(true);
                    }
                    if(this.obstacle.getName().equals("Ayı")){
                        player.getInventory().setWater(true);
                    }
                    if(player.getInventory().isFirewood()&&player.getInventory().isFood()&&player.getInventory().isWater()){
                        System.out.println("Tebrikler tüm ödülleri topladınız." +
                                "\nÇıkmak isterseniz Güvenli Ev'e dönüş yapabilirsiniz." +
                                "\nDevam etmek için herhangi bir tuşa basınız.");

                        int select=input.nextInt();
                        Location location=null;
                        switch (select){
                            case 1:
                                location =new SafeHouse(player);

                                break;
                            default:
                                player.getInventory().setWater(false);
                                player.getInventory().setFirewood(false);
                                player.getInventory().setFood(false);
                                break;

                        }
                    }

                }
                if(this.getObstacle().getId()==4){
                    snakeProbility();

                }

            }else {
                return false;
            }

        }

        return false;

    }


    public void afterHit(){
        System.out.println("Canınız : "+this.getPlayer().getHealth());
        System.out.println(this.getObstacle().getName()+ " Canı : "+this.getObstacle().getHealth());
        System.out.println();
    }

    public  void playerStats(){
        System.out.println("Oyuncu değerleri");
        System.out.println("------------------------");
        System.out.println("Sağlık : "+this.getPlayer().getHealth());
        System.out.println("Silah : "+this.getPlayer().getInventory().getWeapon().getName());
        System.out.println("Zırh : "+this.getPlayer().getInventory().getArmor().getName());
        System.out.println("Bloklama : "+this.getPlayer().getInventory().getArmor().getBlock());
        System.out.println("Hasar : "+this.getPlayer().getTotalDamage());
        System.out.println("Para : "+getPlayer().getMoney());
    }
    public void obstacleStats(int i){
        System.out.println(i+". "+this.getObstacle().getName()+ " Değerleri ");
        System.out.println("----------------------");
        System.out.println("Sağlık : "+this.getObstacle().getHealth());
        System.out.println("Hasar : "+this.getObstacle().getDamage());
        System.out.println("Ödül : "+this.getObstacle().getAward());

    }
    public void snakeProbility(){
        Random random = new Random();

        double weaponProbability = random.nextDouble(); // Silah kazanma olasılığı
        double armorProbability = random.nextDouble(); // Zırh kazanma olasılığı
        double moneyProbability = random.nextDouble(); // Para kazanma olasılığı

        if (weaponProbability <= 0.15) {
            double probablyWeapon = random.nextDouble();
            if (probablyWeapon <= 0.2) {

                System.out.println("Tüfek kazandınız.");
                this.getPlayer().getInventory().setWeapon(Weapon.getWeaponObjByID(3));

            } else if (probablyWeapon <= 0.5) {
                System.out.println("Kılıç kazandınız.");
                this.getPlayer().getInventory().setWeapon(Weapon.getWeaponObjByID(2));

            } else {
                System.out.println("Tabanca kazandınız.");
                this.getPlayer().getInventory().setWeapon(Weapon.getWeaponObjByID(1));

            }
        } else if (armorProbability <= 0.15) {
            double probablyArmor= random.nextDouble();
            if (probablyArmor <= 0.2) {
                System.out.println("Ağır zırh kazandınız.");
                this.getPlayer().getInventory().setArmor(Armor.getArmorObjByID(3));

            } else if (probablyArmor <= 0.5) {
                System.out.println("Orta zırh kazandınız.");
                this.getPlayer().getInventory().setArmor(Armor.getArmorObjByID(2));

            } else {
                System.out.println("Hafif zırh kazandınız.");
                this.getPlayer().getInventory().setArmor(Armor.getArmorObjByID(1));

            }
        } else if (moneyProbability <= 0.25) {
            double probablyMoney = random.nextDouble();
            if (probablyMoney <= 0.2) {
                System.out.println("Extra 10 para kazandınız.");
                this.getPlayer().setMoney(this.getPlayer().getMoney()+10);

            } else if (probablyMoney <= 0.5) {
                System.out.println("Extra 5 para kazandınız.");
                this.getPlayer().setMoney(this.getPlayer().getMoney()+5);

            } else {
                System.out.println("Extra 1 para kazandınız.");
                this.getPlayer().setMoney(this.getPlayer().getMoney()+1);

            }
        } else {
            System.out.println("Hiç birşey kazanamadınız.");


        }
        System.out.println("Güncel paranız : "+this.getPlayer().getMoney());
    }
    public Obstacle getObstacle() {
        return obstacle;
    }

    public void setObstacle(Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public int getMaxObstacle() {

        return maxObstacle;
    }

    public void setMaxObstacle(int maxObstacle) {
        this.maxObstacle = maxObstacle;
    }

    public int getObsCount() {
        return obsCount;
    }

    public void setObsCount(int obsCount) {
        this.obsCount = obsCount;
    }
}