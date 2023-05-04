package org.hposadas.spring6datar2dbc.bootstrap;

import lombok.RequiredArgsConstructor;
import org.hposadas.spring6datar2dbc.domain.Beer;
import org.hposadas.spring6datar2dbc.repositories.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class BootstrapData implements CommandLineRunner {

    @Autowired
    private final BeerRepository beerRepository;


    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        beerRepository.count().subscribe(c -> System.out.println("The count is: " + c));
    }

    public void loadBeerData() {
        beerRepository.count().subscribe(count -> {
            if (count == 0) {
                Beer beer1 = Beer.builder()
                        .beerName("Victoria")
                        .beerStyle("WHEAT")
                        .upc("161817")
                        .price(BigDecimal.valueOf(15.35))
                        .quantityOnHand(250)
                        .createdDate(LocalDateTime.now())
                        .lastModifiedDate(LocalDateTime.now())
                        .build();

                Beer beer2 = Beer.builder()
                        .beerName("Estrella")
                        .beerStyle("LAGER")
                        .upc("989794")
                        .price(BigDecimal.valueOf(19.99))
                        .quantityOnHand(330)
                        .createdDate(LocalDateTime.now())
                        .lastModifiedDate(LocalDateTime.now())
                        .build();

                Beer beer3 = Beer.builder()
                        .beerName("Bud Ligth")
                        .beerStyle("PILSNER")
                        .upc("868676")
                        .price(BigDecimal.valueOf(12.50))
                        .quantityOnHand(250)
                        .createdDate(LocalDateTime.now())
                        .lastModifiedDate(LocalDateTime.now())
                        .build();

                beerRepository.save(beer1).subscribe();
                beerRepository.save(beer2).subscribe();
                beerRepository.save(beer3).subscribe();

            }
        });
    }
}