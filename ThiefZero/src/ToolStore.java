public class ToolStore extends NormalLoc{
    public ToolStore(Player player) {
        super(player,  "Mağaza");
    }
    @Override
    public boolean onLocation() {
        System.out.println("----- Mağazaya hoşgeldiniz ! -----");
        boolean showMenu=true;
        while(showMenu){
            System.out.println("1- Silahlar \n" +
                    "2- Zırhlar\n" +
                    "3- Çıkış\n" +
                    "Lütfen bir seçim yapınız.");
            int selectCase= input.nextInt();
            while (selectCase<1||selectCase>3){
                System.out.println("Geçersiz, lütfen yeniden seçim yapınız.");
                selectCase= input.nextInt();
            }
            switch (selectCase){
                case 1:
                    printWeapon();
                    buyWeapon();
                    break;
                case 2:
                    printArmor();
                    buyArmor();
                    break;
                case 3:
                    System.out.println("Tekrar bekleriz.");
                    showMenu=false;
                    break;

            }

        }
        return true;
    }

    public void printWeapon(){
        System.out.println("---- Silahlar ----");
        System.out.println("0 - Çıkış Yap");
        for(Weapon w:Weapon.weapons()){
            System.out.println(w.getId()+"- "+w.getName()+"<Para: "+w.getPrice()+", Hasar : "+w.getDamage());
        }

    }
    public void buyWeapon(){
        System.out.println("Bir silah seçiniz:");
        int selectWeaponID= input.nextInt();
        while (selectWeaponID<0||selectWeaponID>Weapon.weapons().length){
            System.out.println("Geçersiz, tekrar deneyiniz.");
            selectWeaponID= input.nextInt();
        }
        if(selectWeaponID!=0){
            Weapon selectedWeapon=Weapon.getWeaponObjByID(selectWeaponID);
            if ((selectedWeapon!=null)){
                if(selectedWeapon.getPrice()> this.getPlayer().getMoney()){
                    System.out.println("Yeterli paranız yok.");
                }else{
                    System.out.println(selectedWeapon.getName()+" silahını satın aldınız.");
                    int balance=this.getPlayer().getMoney()-selectedWeapon.getPrice();
                    this.getPlayer().setMoney(balance);
                    System.out.println("Kalan bakiye : "+this.getPlayer().getMoney());
                    this.getPlayer().getInventory().setWeapon(selectedWeapon);

                }
            }
        }

    }

    public void printArmor() {
        System.out.println( "----- Zırhlar -----");
        System.out.println("0 - Çıkış Yap");
        for(Armor a:Armor.armors()){
            System.out.println(a.getId()+"- "+
                    a.getName()+" < Para : "+
                    a.getPrice()+", Zırh : "+
                    a.getBlock()+" >");
        }

    }
    public void buyArmor() {
        System.out.println("Bir zırh seçiniz:");
        int selectArmorID = input.nextInt();
        while (selectArmorID < 0 || selectArmorID > Weapon.weapons().length) {
            System.out.println("Geçersiz, tekrar deneyiniz.");
            selectArmorID = input.nextInt();
        }

        if(selectArmorID!=0){
            Armor selectedArmor=Armor.getArmorObjByID(selectArmorID);
            if(selectedArmor.getPrice()>this.getPlayer().getMoney()){
                System.out.println("Yeterli bakiyeniz bulunmamaktadır !");

            }else {
                System.out.println(selectedArmor.getName()+" zırhını satın aldınız !");
                this.getPlayer().setMoney(this.getPlayer().getMoney()-selectedArmor.getPrice());
                this.getPlayer().getInventory().setArmor(selectedArmor);
                System.out.println("Kalan Bakiye : "+this.getPlayer().getMoney());
            }
        }
    }

}