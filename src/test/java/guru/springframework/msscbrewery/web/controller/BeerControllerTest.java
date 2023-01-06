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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BeerController.class)
public class BeerControllerTest {

    @MockBean
    BeerService beerService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    BeerDto validBeer;

    @Before
    public void setUp() throws Exception {
        validBeer = BeerDto.builder().id(UUID.randomUUID())
                .beerName("PaleAle1")
                .beerStyle("PaleAle")
                .upc(6868768L)
                .build();
    }

    @Test
    public void getBeer() throws Exception{

        //given
        given(beerService.getBeerById(any(UUID.class))).willReturn(validBeer);

        //when
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/beer/"+validBeer.getId().toString());

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id",is(validBeer.getId().toString())))
                 .andExpect(MockMvcResultMatchers.jsonPath("$.beerName", is(validBeer.getBeerName())));


    }

    @Test
    public void handlePost() throws Exception{

        //given
        BeerDto beerDto=validBeer;
        beerDto.setId(null);
        BeerDto savedDto = BeerDto.builder().beerName("BeerName1").beerStyle("bierStyle").build();
        String beerDtoToJson = objectMapper.writeValueAsString(beerDto);

        given(beerService.saveNewBeer(any())).willReturn(savedDto);

        //when
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/beer/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoToJson);
        mockMvc.perform(requestBuilder).andExpect(status().isCreated());
    }

    @Test
    public void handleUpdate() throws Exception{

        //given
        BeerDto beerDto=validBeer;
        beerDto.setId(null);
        String beerDtoToJson = objectMapper.writeValueAsString(beerDto);

        //when
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/beer/"+UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoToJson);
        mockMvc.perform(requestBuilder).andExpect(status().isNoContent());

        //then
        then(beerService).should().updateBeer(any(),any());

    }
}