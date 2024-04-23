
import java.util.Random;

public class Main {

    public static boolean bossStunned = false;
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {290, 270, 250, 300, 320, 200, 100,150};
    public static int[] heroesDamage = {10, 15, 20, 0, 5, 0, 0,10};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medical", "Golem", "Lucky", "Witcher","Thor"};
    public static int roundNumber = 0;


    public static int heal = 200;

    public static void healHerouse() {
        int medical = 3;
        if (heroesHealth[3] <= 0) {
            return;
        }
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && heroesHealth[i] < 100 && i != medical) {
                heroesHealth[i] = heroesHealth[i] + heal;
                System.out.println("MEDICHealed" + "---" + heroesAttackType[i] +"  " +  "for" +" " + "MedicHeal"+ " " + heal);
                break;
            }
        }
    }
    public static boolean golemAlive = true;
    public static void isGolem() {
        int golem = 5;
        if (golemAlive) {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] > 0 && i != golem) {
                    heroesHealth[i] = heroesHealth[i] - bossDamage / 5;
                    if (heroesHealth[i] < 0) {
                        heroesHealth[i] = 0;
                    }
                }
            }
        }
    }

    public static void isLucky() {
        boolean luckyAlive = true;
        if (luckyAlive && bossDamage > 0) {
            Random random = new Random();
            boolean isLucky = random.nextBoolean();
            if (isLucky) {
                System.out.println("LUCKY" + "______" + "I evaded the blow");
            }
        }

    }


    public static boolean witchAlive = true;
    public static void isWitcher() {
        int witcher = 6;
        if (witchAlive) {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] == 0 && i != witcher) {
                    heroesHealth[i] = 100;
                    witchAlive = false;
                    System.out.println(" WITCHER" + " ----" + " I give my life");
                    break;
                }
            }
        }
    }

    public static void thorStunner() {
        boolean thorAlive = true;
        if (thorAlive && !bossStunned) {
            Random random = new Random();
            boolean isStunned = random.nextBoolean();
            if (isStunned) {
                bossStunned = true;
                System.out.println("Thor stunned the boss for 1 round!!!");
            }
        }
    }
    public static void main(String[] args) {
        printStatistics();
        while (!isGameOver()) {
            playRound();
        }
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }


        return allHeroesDead;
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        if (!bossStunned) {
            bossHits();
        };
        bossHits();
        thorStunner();
        isWitcher();
        isLucky();
        isGolem();
        healHerouse();
        heroesHit();
        printStatistics();
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomInd = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefence = heroesAttackType[randomInd];
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (bossDefence == heroesAttackType[i]) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    damage = heroesDamage[i] * coeff;
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }


    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber + " ------------");
        /*String defence;
        if (bossDefence == null) {
            defence = "No defence";
        } else {
            defence = bossDefence;
        }*/
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage
                + " defence: " + (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i]
                    + " damage: " + heroesDamage[i]);
        }
    }
}
