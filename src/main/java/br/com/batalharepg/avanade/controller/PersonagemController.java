package br.com.batalharepg.avanade.controller;

import br.com.batalharepg.avanade.dto.request.PersonagemRequest;
import br.com.batalharepg.avanade.dto.response.PersonagemResponse;
import br.com.batalharepg.avanade.service.PersonagemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/personagem")
@AllArgsConstructor
public class PersonagemController {
    private final PersonagemService personagemService;

    @PostMapping
    public ResponseEntity<PersonagemResponse> createCharacter(@RequestBody PersonagemRequest body) {
        try {
            PersonagemResponse response = personagemService.criarPersonagem(body);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
