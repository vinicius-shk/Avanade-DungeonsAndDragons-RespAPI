package br.com.batalharepg.avanade.controller;

import br.com.batalharepg.avanade.dto.request.PersonagemRequest;
import br.com.batalharepg.avanade.dto.request.PersonagemUpdateRequest;
import br.com.batalharepg.avanade.dto.response.PersonagemResponse;
import br.com.batalharepg.avanade.service.PersonagemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @Operation(summary = "Cria um personagem")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "criado com sucesso",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = PersonagemResponse.class)) }),
        @ApiResponse(responseCode = "400", description = "Personagem com este nome já cadastrado",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "Tipo de personagem não encontrado",
            content = @Content) })
    @PostMapping
    public ResponseEntity<PersonagemResponse> criaPersonagem(@RequestBody PersonagemRequest body) {
            PersonagemResponse response = personagemService.criarPersonagem(body);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Lista todas os personagens")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "listado com sucesso",
            content = { @Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = PersonagemResponse.class))) })})
    @GetMapping
    public ResponseEntity<List<PersonagemResponse>> listaTodosPersonagens() {
        return ResponseEntity.ok(personagemService.listaTodosPersonagens());
    }

    @Operation(summary = "Busca um personagem pelo nome")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "encontrado com sucesso",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = PersonagemResponse.class)) }),
        @ApiResponse(responseCode = "404", description = "Personagem não encontrado",
            content = @Content) })
    @GetMapping("/buscar/nome/{nome}")
    public ResponseEntity<PersonagemResponse> buscaPersonagemPorNome(@PathVariable String nome) {
            return ResponseEntity.ok(personagemService.buscaPersonagemPorNome(nome));
    }

    @Operation(summary = "Busca um personagem pelo id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "encontrado com sucesso",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = PersonagemResponse.class)) }),
        @ApiResponse(responseCode = "404", description = "Personagem não encontrado",
            content = @Content) })
    @GetMapping("/buscar/id/{uuid}")
    public ResponseEntity<PersonagemResponse> buscaPersonagemPorUuid(@PathVariable UUID uuid) {
            return ResponseEntity.ok(personagemService.buscaPersonagemPorUuid(uuid));
    }

    @Operation(summary = "Atualiza um personagem pelo id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "encontrado com sucesso",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = PersonagemResponse.class)) }),
        @ApiResponse(responseCode = "404", description = "Personagem não encontrado",
            content = @Content) })
    @PatchMapping("/atualizar/{uuid}")
    public ResponseEntity<PersonagemResponse> atualizaPersonagem(@PathVariable UUID uuid,
                                                                 @RequestBody PersonagemUpdateRequest body) {
            PersonagemResponse response = personagemService.atualizaPersonagem(uuid, body);
            return ResponseEntity.ok(response);
    }

    @Operation(summary = "Deleta um personagem por id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Personagem deletado com sucesso",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "Personagem não encontrada",
            content = @Content) })
    @DeleteMapping("/deletar/{uuid}")
    public ResponseEntity<Void> deletaPersonagem(@PathVariable UUID uuid) {
            personagemService.deletaPersonagem(uuid);
            return ResponseEntity.ok().build();
    }
}
