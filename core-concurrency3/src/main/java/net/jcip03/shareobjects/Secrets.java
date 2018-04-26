package net.jcip03.shareobjects;
import java.util.*;

/**
 * Secrets
 *
 * Publishing an object ----发布1个对象，最简单的方式： public static
 *
 * @author Brian Goetz and Tim Peierls
 */
class Secrets {
    public static Set<Secret> knownSecrets;

    public void initialize() {
        knownSecrets = new HashSet<Secret>();
    }
}


class Secret {
}