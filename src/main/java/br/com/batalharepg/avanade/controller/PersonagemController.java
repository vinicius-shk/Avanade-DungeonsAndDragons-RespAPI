package br.com.batalharepg.avanade.controller;

import br.com.batalharepg.avanade.dto.request.PersonagemRequest;
import br.com.batalharepg.avanade.dto.request.PersonagemUpdateRequest;
import br.com.batalharepg.avanade.dto.response.PersonagemResponse;
import br.com.batalharepg.avanade.exceptions.NotFoundException;
import br.com.batalharepg.avanade.service.PersonagemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/personagem")
@AllArgsConstructor
public class PersonagemController {
    private final PersonagemService personagemService;

    @PostMapping
    public ResponseEntity<PersonagemResponse> criaPersonagem(@RequestBody PersonagemRequest body) {
        try {
            PersonagemResponse response = personagemService.criarPersonagem(body);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<PersonagemResponse>> listaTodosPersonagens() {
        return ResponseEntity.ok(personagemService.listaTodosPersonagens());
    }

    @GetMapping("/buscar/nome/{nome}")
    public ResponseEntity<List<PersonagemResponse>> listaPersonagemPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(personagemService.listaPersonagemPorNome(nome));
    }

    @GetMapping("/buscar/id/{uuid}")
    public ResponseEntity<PersonagemResponse> buscaPersonagemPorUuid(@PathVariable UUID uuid) {
        try {
            return ResponseEntity.ok(personagemService.buscaPersonagemPorUuid(uuid));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/atualizar/{uuid}")
    public ResponseEntity<PersonagemResponse> atualizaPersonagem(@PathVariable UUID uuid,
                                                                 @RequestBody PersonagemUpdateRequest body) {
        try {
            PersonagemResponse response = personagemService.atualizaPersonagem(uuid, body);
            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deletar/{uuid}")
    public ResponseEntity<Void> deletaPersonagem(@PathVariable UUID uuid) {
        try {
            personagemService.deletaPersonagem(uuid);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
