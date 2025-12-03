package me.elordenador.proyectobackend.libs;

import java.util.Random;

public class StringGenerator {
    public static String generate(int cantidad) {
        Random random = new Random();
        StringBuilder out = new StringBuilder();
        String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-.,_";
        for (int i = 0; i < cantidad; i++) {
            out.append(abc.charAt(random.nextInt(abc.length())));
        }
        return out.toString();

    }
}
