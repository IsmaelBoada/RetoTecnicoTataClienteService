package com.pt.clientes.controller;

import com.pt.clientes.rest.model.ModelResponseDto;
import com.pt.clientes.rest.model.PersonaDto;
import com.pt.clientes.services.operators.PersonaService;
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
@RequestMapping("personas")
public class PersonaController {


    private final PersonaService personaService;

    @PostMapping("save")
    public ModelResponseDto saveData(@Valid @RequestBody PersonaDto model) {

        return ModelResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .body(personaService.save(model))
                .build();
    }


    @PutMapping("update")
    public ModelResponseDto updateData(@RequestParam UUID tokenId,
                                       @Valid @RequestBody PersonaDto model) {
        return ModelResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .body(personaService.update(tokenId, model))
                .build();
    }


    @GetMapping("findByTokenId")
    public ModelResponseDto findByTokenId(@RequestParam UUID tokenId) {
        return ModelResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .body(personaService.findByTokenId(tokenId))
                .build();
    }

    @DeleteMapping("delete")
    public ModelResponseDto deleteData(@RequestParam UUID tokenId) {
        personaService.delete(tokenId);
        return ModelResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .build();
    }


}
