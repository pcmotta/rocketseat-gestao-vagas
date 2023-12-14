package br.com.attomtech.gestaovagas.modules.company.controllers;

import br.com.attomtech.gestaovagas.modules.company.dto.CreateJobDTO;
import br.com.attomtech.gestaovagas.modules.company.entities.CompanyEntity;
import br.com.attomtech.gestaovagas.modules.company.repositories.CompanyRepository;
import br.com.attomtech.gestaovagas.utils.TestUtils;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FlywayTest
@ActiveProfiles("test")
public class CreateJobControllerTest {

    MockMvc mvc;

    @Autowired
    WebApplicationContext context;

    @Autowired
    CompanyRepository companyRepository;

    @Before
    public void setup() {
        mvc = webAppContextSetup(context)
            .apply(springSecurity())
            .build();
    }

    @Test
    @DisplayName("Should be able to create a new job")
    public void shoulBeAbleToCreateANewJob() throws Exception {
        var company = CompanyEntity.builder()
            .description("Company Description")
            .email("company@company.com")
            .password("1234567890")
            .username("company")
            .name("Company")
            .build();

        var companySaved = companyRepository.saveAndFlush(company);

        var createJobDto = CreateJobDTO.builder()
            .benefits("BENEFITS_TEST")
            .description("DESCRIPTION_TEST")
            .level("LEVEL_TEST")
            .build();

        mvc.perform(
            post("/company/job")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", TestUtils.generateToken(companySaved.getId(), "JAVAGAS_@123#"))
                .content(TestUtils.objectToJson(createJobDto))
        ).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Should no be able to creaate a new job if company not found")
    public void shouldNotBeAbleToCreateNewJobIfCompanyNotFound() throws Exception {
        var createJobDto = CreateJobDTO.builder()
            .benefits("BENEFITS_TEST")
            .description("DESCRIPTION_TEST")
            .level("LEVEL_TEST")
            .build();

        mvc.perform(
            post("/company/job")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", TestUtils.generateToken(UUID.randomUUID().toString(), "JAVAGAS_@123#"))
                .content(TestUtils.objectToJson(createJobDto))
        ).andExpect(status().isBadRequest());
    }
}
