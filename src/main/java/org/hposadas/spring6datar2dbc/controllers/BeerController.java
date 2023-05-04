package org.hposadas.spring6datar2dbc.controllers;

import org.hposadas.spring6datar2dbc.model.BeerDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class BeerController {

    public static final String BEER_PATH = "/api/v2/beer";

    @GetMapping(path = BEER_PATH)
    Flux<BeerDTO> listBeers(){
        return Flux.just(BeerDTO.builder().id(1).build(),
                BeerDTO.builder().id(2).build());
    }
}
