package com.library.book.service;

import com.library.book.model.dto.ClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CLIENT-SERVICE")
public interface FeignClientService {

    @GetMapping("/client/get/{clientId}")
    ClientDto getClientById(@PathVariable Long clientId);
}
