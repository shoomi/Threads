package phaser;

import java.util.*;

public class Test {
    private static Set<String> set = new TreeSet<String>();
    public static void main(String[] args) {
        set.add("one");
        set.add("two");
        set.add("three");
        set.add("/u000a");
        set.add("/u000d");
        List list = new ArrayList();
        list.add(32);
        list.add("dd");
    }
}
