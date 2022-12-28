package guru.springframework.msscbrewery.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbrewery.services.BeerService;
import guru.springframework.msscbrewery.web.model.BeerDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

@RunWith(SpringRunner.class)
@WebMvcTest(BeerController.class)
public class BeerControllerTest {

    @MockBean
    BeerService beerService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    BeerDto validaBeer;

    @Before
    public void setUp() throws Exception {
        validaBeer = BeerDto.builder().id(UUID.randomUUID())
                .beerName("PaleAle1")
                .beerStyle("PaleAle")
                .upc(6868768L)
                .build();
    }

    @Test
    public void getBeer() throws Exception{
        /* given(beerService.getBeerById(any(UUID.class))).willReturn(validaBeer);
        mockMvc.perform((RequestBuilder) get("/api/v1/beer"+validaBeer.getId().toString()))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect((ResultMatcher) jsonPath("$.id",is(validaBeer.getId().toString())))
                .andExpect((ResultMatcher) jsonPath("$.beerName", is(validaBeer.getBeerName())));*/


    }

    @Test
    public void handlePost() throws Exception{

    }

    @Test
    public void handleUpdate() throws Exception{

    }
}