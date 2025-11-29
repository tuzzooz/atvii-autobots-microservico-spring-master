package com.autobots.automanager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ResourceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void criarDocumentoEnderecoTelefone_basico() throws Exception {
        // documento
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/documentos")
                .contentType(MediaType.APPLICATION_JSON).content("{\"tipo\":\"RG\",\"numero\":\"12345\"}"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isCreated())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.header().exists("Location"));

        // endereco
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/enderecos")
                .contentType(MediaType.APPLICATION_JSON).content("{\"cidade\":\"Cidade\",\"rua\":\"Rua\",\"numero\":\"1\"}"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isCreated())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.header().exists("Location"));

        // telefone
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/telefones")
                .contentType(MediaType.APPLICATION_JSON).content("{\"ddd\":\"11\",\"numero\":\"999999999\"}"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isCreated())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.header().exists("Location"));
    }
}
