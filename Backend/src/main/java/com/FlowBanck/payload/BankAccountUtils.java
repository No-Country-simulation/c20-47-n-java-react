package com.FlowBanck.payload;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BankAccountUtils {

    private static final List<String> animales = Arrays.asList("ELEFANTE","MONO","LEON","PATO","CONEJO","GATO","PERRO","CABALLO","TIGRE","OSO","MORZA","PUMA","GORILLA","LORO","CANARIO","CONDOR","CEBRA","ÑANDU",
            "VACA","GALLINA","SERPIENTE","COBRA","CISNE","AGUILA","TORO","TORTUGA","COCODRILLO","CAIMAN","HIENA","LOBO","CABRA","OVEJA","JIRAFA","LIEBRE","HALCON","TIBURON","BALLENA","CANGURO","DELFIN","FOCA");

    public static String generateAccountNumber(){
        Random random = new Random();

        int bloque1 = random.nextInt(9000000) + 1000000;
        int verificador1 = random.nextInt(10);

        int bloque2 = random.nextInt(900) + 100;
        int verificador2 = random.nextInt(10);

        return String.format("%d-%d %d-%d", bloque1, verificador1, bloque2, verificador2);
    }

    public static String generateCbu(){
        Random random = new Random();
        StringBuilder cbu = new StringBuilder();
        for(int i = 0; i < 22; i++){
            cbu.append(random.nextInt(10));
        }
        return cbu.toString();
    }

    public static String generateAlias(){
        Random random = new Random();

        String animal1 = animales.get(random.nextInt(animales.size()));
        String animal2 = animales.get(random.nextInt(animales.size()));
        String animal3 = animales.get(random.nextInt(animales.size()));

        return  String.format("%s.%s.%s", animal1,animal2,animal3);
    }

}


