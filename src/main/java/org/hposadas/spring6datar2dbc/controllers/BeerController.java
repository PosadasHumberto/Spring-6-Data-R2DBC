package org.hposadas.spring6datar2dbc.controllers;

import lombok.RequiredArgsConstructor;
import org.hposadas.spring6datar2dbc.model.BeerDTO;
import org.hposadas.spring6datar2dbc.services.BeerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class BeerController {

    private final BeerService beerService;
    public static final String BEER_PATH = "/api/v2/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

    @GetMapping(path = BEER_PATH)
    Flux<BeerDTO> listBeers(){
        return beerService.listBeers();
    }

    @GetMapping(BEER_PATH_ID)
    Mono<BeerDTO> getBeerById(@PathVariable Integer beerId) {
        return beerService.getBeerById(beerId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @PostMapping(BEER_PATH)
    Mono<ResponseEntity<Void>> createNewBeer(
            @Validated @RequestBody BeerDTO beerDTO) {
        return beerService.saveNewBeer(beerDTO)
                .map(savedDto -> ResponseEntity.created(UriComponentsBuilder
                        .fromHttpUrl("http://localhost:8080/" + BEER_PATH + "/"
                        + savedDto.getId()).build().toUri())
                        .build());
    }

    @PutMapping(BEER_PATH_ID)
    Mono<ResponseEntity<Void>> updateBeerById(
            @PathVariable("beerId") Integer beerId,
            @Validated @RequestBody BeerDTO beerDTO){

        return beerService.updateBeer(beerId, beerDTO)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(beer -> ResponseEntity.noContent().build());
    }

    @PatchMapping(BEER_PATH_ID)
    Mono<ResponseEntity<Void>> patchExistingBeer(
            @PathVariable("beerId") Integer beerId,
            @Validated @RequestBody BeerDTO beerDTO
    ) {
        return beerService.patchBeer(beerId, beerDTO)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(patchedDto -> ResponseEntity.noContent().build());
    }

    @DeleteMapping(BEER_PATH_ID)
    Mono<ResponseEntity<Void>> deleteBeer(@PathVariable("beerId") Integer beerId) {
        return beerService.getBeerById(beerId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(beerDTO -> beerService.deleteBeer(beerDTO.getId()))
                .thenReturn(ResponseEntity.noContent().build());
    }
}
