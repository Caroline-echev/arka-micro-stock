package com.arka.micro_stock.adapters.driving.reactive.mapper;

import com.arka.micro_stock.adapters.driving.reactive.dto.request.AddressRequest;
import com.arka.micro_stock.adapters.driving.reactive.dto.request.SupplierRequest;
import com.arka.micro_stock.adapters.driving.reactive.dto.response.SupplierResponse;
import com.arka.micro_stock.domain.model.SupplierModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(componentModel = "spring")
public interface ISupplierDtoMapper {

    SupplierResponse toResponse(SupplierModel model);

    @Mapping(target = "address", source = "address", qualifiedByName = "concatAddress")
    SupplierModel toModel(SupplierRequest request);

    @Named("concatAddress")
    default String concatAddress(AddressRequest address) {
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
