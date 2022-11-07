package lab2;

import lab2.vk.VkClient;
import lab2.config.VkConfig;
import lab2.vk.VkManager;

import java.util.List;
import java.util.regex.Pattern;

public class Main {
    private static final String CONFIG_PATH = "src/main/resources/application.conf";

    public static void main(String[] args) {
        if (args.length != 2 || !Pattern.compile("[1-9][0-9]*").matcher(args[1]).matches()) {
            System.err.println("Usage: <hashtag without '#'> <hours>");
            return;
        }

        String hashTag = args[0];
        int hours = Integer.parseInt(args[1]);

        if (hours > 24) {
            System.err.println("Hours must be between 1 and 24");
            return;
        }

        VkManager manager = new VkManager(new VkClient(new VkConfig(CONFIG_PATH)));
        List<Integer> posts = manager.getPostsCountHist(hashTag, hours);
        System.out.println("Posts about #" + hashTag + ":");
        for (int i = 0; i < hours; i++) {
            System.out.println("Hours ago: " + (hours - i));
            System.out.println("Posts: " +
                    posts.get(i));
        }
    }
}
