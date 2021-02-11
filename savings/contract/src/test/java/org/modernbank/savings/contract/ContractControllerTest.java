package org.modernbank.savings.contract;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.modernbank.savings.contract.controller.SavingsContractController;

@WebMvcTest(SavingsContractController.class)
public class ContractControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
	public void getContractListShouldReturnOK() throws Exception {
        this.mockMvc.perform(get("/savings/")).andExpect(status().isOk());
    }

    @Test
	public void getContractShouldReturnOK() throws Exception {
        this.mockMvc.perform(get("/savings/123456")).andExpect(status().isOk());
    }

    @Test
	public void postNewContractShouldReturnCREATED() throws Exception {
        this.mockMvc.perform(post("/savings/")).andExpect(status().isCreated());
    }

    @Test
    public void updateContractShouldReturnOK() throws Exception {
        this.mockMvc.perform(put("/savings/123456")).andExpect(status().isOk());
    }

    @Test
	public void deleteContractShouldReturnOK() throws Exception {
        this.mockMvc.perform(delete("/savings/123456")).andExpect(status().isOk());
    }

}
