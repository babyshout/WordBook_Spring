package kopo.data.wordbook.app.word.problem.service.impl;

import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.Random;

class DifferencesBetweenRandomAndSecureRandomTest {


    /**
     * {@link Random}, {@linkplain SecureRandom} 차이점 비교용..
     */
    @Test
    void randomTest() {
        ;
        System.out.println(new Random(10).nextInt(100));
        System.out.println(new Random(10).nextInt());
        System.out.println(new Random(10).nextInt(100));
        SecureRandom secureRandom = new SecureRandom();
//        secureRandom.setSeed(10);

        System.out.println(secureRandom.nextInt());
        System.out.println(secureRandom.nextInt());
        System.out.println(secureRandom.nextInt());
        System.out.println(secureRandom.nextInt(10));
        System.out.println(secureRandom.nextInt(10));
        System.out.println(secureRandom.nextInt(10));
    }
}