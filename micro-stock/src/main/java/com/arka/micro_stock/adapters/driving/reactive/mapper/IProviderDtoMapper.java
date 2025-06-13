package com.arka.micro_stock.adapters.driving.reactive.mapper;

import com.arka.micro_stock.adapters.driving.reactive.dto.request.ProviderRequest;
import com.arka.micro_stock.adapters.driving.reactive.dto.response.ProviderResponse;
import com.arka.micro_stock.domain.model.ProviderModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(componentModel = "spring")
public interface IProviderDtoMapper {

    ProviderResponse toResponse(ProviderModel model);

    @Mapping(target = "address", source = "address", qualifiedByName = "concatAddress")
    ProviderModel toModel(ProviderRequest request);

    @Named("concatAddress")
    default String concatAddress(com.arka.micro_stock.adapters.driving.reactive.dto.request.AddressRequest address) {
        if (address == null) return null;

        return Stream.of(
                        address.getCountry(),
                        address.getState(),
                        address.getCity(),
                        address.getStreet(),
                        address.getNomenclature(),
                        address.getObservation()
                )
                .filter(part -> part != null && !part.isBlank())
                .collect(Collectors.joining(", "));
    }
}
