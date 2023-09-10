package br.com.batalharepg.avanade.controller;

import br.com.batalharepg.avanade.service.CombateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/defesa")
@RequiredArgsConstructor
public class DefesaController {
    private final CombateService combateService;

}
