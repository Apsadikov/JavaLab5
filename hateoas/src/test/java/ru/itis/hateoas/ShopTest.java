package ru.itis.hateoas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.itis.hateoas.model.Shop;
import ru.itis.hateoas.repository.ShopRepository;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class ShopTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShopRepository shopRepository;

    @BeforeEach
    public void setUp() {
        when(shopRepository.findByName("shop")).thenReturn(java.util.Optional.ofNullable(getShop()));
    }

    @Test
    public void getShopByName() throws Exception {
        mockMvc.perform(get("/shops/search/byName?name=shop")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(getShop().getName()))
                .andExpect(jsonPath("$.logo").value(getShop().getLogo()))
                .andExpect(jsonPath("$.about").value(getShop().getAbout()))
                .andDo(document("getUserById", responseFields(subsectionWithPath("_links").ignored(),
                        fieldWithPath("name").description("Название"),
                        fieldWithPath("logo").description("Логотип"),
                        fieldWithPath("about").description("Описание")
                )));
    }

    private Shop getShop() {
        return Shop.builder()
                .name("shop")
                .about("about")
                .logo("http://image.com/logo")
                .build();
    }
}
