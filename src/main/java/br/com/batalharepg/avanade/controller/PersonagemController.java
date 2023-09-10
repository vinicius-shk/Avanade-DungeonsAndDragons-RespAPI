package br.com.batalharepg.avanade.controller;

import br.com.batalharepg.avanade.dto.request.PersonagemRequest;
import br.com.batalharepg.avanade.dto.request.PersonagemUpdateRequest;
import br.com.batalharepg.avanade.dto.response.PersonagemResponse;
import br.com.batalharepg.avanade.service.PersonagemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/personagem")
@RequiredArgsConstructor
public class PersonagemController {
    private final PersonagemService personagemService;

    @PostMapping
    public ResponseEntity<PersonagemResponse> criaPersonagem(@RequestBody PersonagemRequest body) {
            PersonagemResponse response = personagemService.criarPersonagem(body);
            return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PersonagemResponse>> listaTodosPersonagens() {
        return ResponseEntity.ok(personagemService.listaTodosPersonagens());
    }

    @GetMapping("/buscar/nome/{nome}")
    public ResponseEntity<PersonagemResponse> buscaPersonagemPorNome(@PathVariable String nome) {
            return ResponseEntity.ok(personagemService.buscaPersonagemPorNome(nome));
    }

    @GetMapping("/buscar/id/{uuid}")
    public ResponseEntity<PersonagemResponse> buscaPersonagemPorUuid(@PathVariable UUID uuid) {
            return ResponseEntity.ok(personagemService.buscaPersonagemPorUuid(uuid));
    }

    @PatchMapping("/atualizar/{uuid}")
    public ResponseEntity<PersonagemResponse> atualizaPersonagem(@PathVariable UUID uuid,
                                                                 @RequestBody PersonagemUpdateRequest body) {
            PersonagemResponse response = personagemService.atualizaPersonagem(uuid, body);
            return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deletar/{uuid}")
    public ResponseEntity<Void> deletaPersonagem(@PathVariable UUID uuid) {
            personagemService.deletaPersonagem(uuid);
            return ResponseEntity.ok().build();
    }
}
