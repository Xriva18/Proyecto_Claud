package com.claud.analyticore.presentation;

import com.claud.analyticore.application.ProcesarAnalisisUseCase;
import com.claud.analyticore.infrastructure.analysis.AnalysisResult;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// kguanoluisa, Controlador REST POST /analysis invocado por el servicio Python, clase AnalysisController, 2026-07-17
// kguanoluisa, Funcionamiento: recibe jobId/texto, ejecuta ProcesarAnalisisUseCase y devuelve sentimiento y palabras clave, sin nuevas variables, 2026-07-17
@RestController
public class AnalysisController {

    private final ProcesarAnalisisUseCase procesarAnalisisUseCase;

    public AnalysisController(ProcesarAnalisisUseCase procesarAnalisisUseCase) {
        this.procesarAnalisisUseCase = procesarAnalisisUseCase;
    }

    @PostMapping("/analysis")
    public ResponseEntity<AnalysisResponse> analizar(@RequestBody AnalysisRequest request) {
        AnalysisResult result = procesarAnalisisUseCase.ejecutar(request.getJobId(), request.getTexto());
        AnalysisResponse response = new AnalysisResponse(result.sentimiento(), result.palabrasClave());
        return ResponseEntity.ok(response);
    }
}
