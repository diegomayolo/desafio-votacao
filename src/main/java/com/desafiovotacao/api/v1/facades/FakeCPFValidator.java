package com.desafiovotacao.api.v1.facades;

import com.desafiovotacao.api.v1.client.dtos.FakeCPFDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "fakeCPFValidator", url = "${fake.cpf.url}")
public interface FakeCPFValidator {
    
    @PostMapping("/client/cpf/validate/{cpf}")
    FakeCPFDTO getValidCPF(@PathVariable String cpf);
}
