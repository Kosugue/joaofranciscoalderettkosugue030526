package com.seplagseletivo.projeto_backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    // Requisito (l) - Documentação Swagger
    @Test
    void swaggerUiShouldBePublic() throws Exception {
        mockMvc.perform(get("/swagger-ui.html"))
                .andExpect(result -> {
                    // Substitui o .or() por uma verificação manual do status no MvcResult
                    int status = result.getResponse().getStatus();
                    // Aceita 200 (OK) ou 302 (Found/Redirect)
                    if (status != 200 && status != 302) {
                        throw new AssertionError("Status não esperado: " + status);
                    }
                });

        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk());
    }

    // Requisito (j) - Endpoint Versionado
    // Requisito (b) - Bloqueio sem token
    @Test
    void shouldForbidAccessToProtectedEndpointWithoutToken() throws Exception {
        mockMvc.perform(get("/api/v1/albums"))
                .andExpect(status().isForbidden());
    }

    // Requisito (a) - CORS (Simulação)
    @Test
    void shouldApplyCorsRules() throws Exception {
        mockMvc.perform(options("/api/v1/albums")
                        .header("Access-Control-Request-Method", "GET")
                        .header("Origin", "http://site-malicioso.com"))
                .andExpect(status().isForbidden());
    }

}