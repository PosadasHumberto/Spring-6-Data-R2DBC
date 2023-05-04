package org.hposadas.spring6datar2dbc.repositories;

import org.hposadas.spring6datar2dbc.config.DatabaseConfig;
import org.hposadas.spring6datar2dbc.domain.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataR2dbcTest
@Import(DatabaseConfig.class)
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveNewBeer() {
        beerRepository.save(getTestBeer()).subscribe(beer -> {
            System.out.println(beer.toString());
        });
    }

    Beer getTestBeer() {
        return Beer.builder()
                .beerName("Test Beer")
                .beerStyle("LAGER")
                .price(BigDecimal.TEN)
                .upc("123456")
                .build();
    }

}