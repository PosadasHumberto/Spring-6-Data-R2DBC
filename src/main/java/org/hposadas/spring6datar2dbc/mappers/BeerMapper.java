package org.hposadas.spring6datar2dbc.mappers;

import org.hposadas.spring6datar2dbc.domain.Beer;
import org.hposadas.spring6datar2dbc.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO beerDTO);
    BeerDTO beerToBeerDto(Beer beer);
}
