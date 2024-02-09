package it.aleph.omegamonolith.service.resource.impl;

import it.aleph.omegamonolith.callnumber.calculator.CallNumberCalculator;
import it.aleph.omegamonolith.callnumber.calculator.CallNumberResult;
import it.aleph.omegamonolith.dto.catalog.book.BookDto;
import it.aleph.omegamonolith.dto.resource.request.RequestedResourceOperationDto;
import it.aleph.omegamonolith.dto.resource.request.RequestedTypeOperationDto;
import it.aleph.omegamonolith.mapper.catalog.BookDtoMapper;
import it.aleph.omegamonolith.service.resource.RequestedResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class RequestedResourceServiceImpl implements RequestedResourceService {

    private final CallNumberCalculator calculator;
    private final BookDtoMapper bookDtoMapper;
    private final KafkaTemplate<String, RequestedResourceOperationDto> kafkaTemplate;

    @Override
    public void insertOperation(BookDto bookDto) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        RequestedResourceOperationDto request = new RequestedResourceOperationDto();
        request.setRequestedTypeOperationDto(RequestedTypeOperationDto.INSERTION);
        request.setResourceType("book");
        request.setAssociatedUser(jwt.getClaim("preferred_username"));
        request.setValid(true);
        request.setExecuted(false);

        CallNumberResult callNumberResult = calculator.calculate(bookDto);

        request.setAddress(callNumberResult.getCallNumber());
        bookDto.setCutterNumber(callNumberResult.getCutterNumber());

        request.setResource(bookDtoMapper.toMap(bookDto));
        kafkaTemplate.send("report-book-events", request);
    }
}
