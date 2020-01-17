package borman.onenight;

import java.util.Random;

public class RandomService {

    public static String createUserIdForSession(String userName) {
        Random random = new Random();
        int x = random.nextInt(5000);
        return userName + (x * random.nextInt() * 6);
    }

    public static String createRandomLobbyCode() {
        Random random = new Random();
        int x = random.nextInt(10);
        int y = random.nextInt(10);
        int z = random.nextInt(10);
        int u = random.nextInt(10);
        return (x + "" + y + "" + z + "" + u);
    }

}
