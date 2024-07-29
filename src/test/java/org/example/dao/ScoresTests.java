package org.example.dao;

import lombok.extern.log4j.Log4j2;
import org.example.w2.bmi.Scores;
import org.junit.jupiter.api.Test;

@Log4j2
public class ScoresTests {


    @Test public void testBuilder() {

        Scores data1 = Scores.builder()
                .subject1(89)
                .build();
        log.info(data1);


    }
}
