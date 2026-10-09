package com.pt.clientes.controller;

import com.pt.clientes.rest.model.ClienteDto;
import com.pt.clientes.rest.model.ModelResponseDto;
import com.pt.clientes.services.operators.ClienteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("clientes")
public class ClienteController {


    private final ClienteService clienteService;

    @PostMapping("save")
    public ModelResponseDto saveData(@Valid @RequestBody ClienteDto model) {

        return ModelResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .body(clienteService.save(model))
                .build();
    }


    @PutMapping("update")
    public ModelResponseDto updateData(@RequestParam UUID tokenId,
                                       @Valid @RequestBody ClienteDto model) {
        return ModelResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .body(clienteService.update(tokenId, model))
                .build();
    }


    @GetMapping("findByTokenId")
    public ModelResponseDto findByTokenId(@RequestParam UUID tokenId) {
        return ModelResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .body(clienteService.findByTokenId(tokenId))
                .build();
    }

    @DeleteMapping("delete")
    public ModelResponseDto deleteData(@RequestParam UUID tokenId) {
        clienteService.delete(tokenId);
        return ModelResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .build();
    }


}
