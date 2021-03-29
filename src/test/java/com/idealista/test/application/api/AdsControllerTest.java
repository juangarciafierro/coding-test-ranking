package com.idealista.test.application.api;

import com.idealista.application.api.AdsController;
import org.json.JSONArray;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AdsController.class)
public class AdsControllerTest {

    private static final int MIN_SCORE = 40;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("should return Internal Server Error if GET /ads/public or GET /ads/quality and no score has been generated")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void shouldReturnHTTP5XXifScoreIsNotCalculated() throws Exception {
        mockMvc.perform(get("/ads/public"))
                .andExpect(status().isInternalServerError());
        mockMvc.perform(get("/ads/quality"))
                .andExpect(status().isInternalServerError());
    }


    @Test
    @DisplayName("should return 200 OK if score has been correctly generated in POST /ads/score")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void shouldTriggerScoreCalculation() throws Exception {
        mockMvc.perform(post("/ads/score"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("should return 200 OK if GET /ads/public or GET /ads/quality and score has been generated")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void shouldReturnHTTP2XXifScoreIsCalculated() throws Exception {
        mockMvc.perform(post("/ads/score"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/ads/public"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/ads/quality"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("should return JSON of size 3 when GET /ads/quality")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void shouldReturnJSONArrayOfSize3WhenGetQualityAds() throws Exception {
        mockMvc.perform(post("/ads/score"))
                .andExpect(status().isOk());
        MvcResult result = mockMvc.perform(get("/ads/quality"))
                .andExpect(status().isOk())
        .andReturn();
        JSONArray responseJSON = new JSONArray(result.getResponse().getContentAsString());
        assertEquals(3,responseJSON.length());
    }

    @Test
    @DisplayName("should return only ads with less than MIN_SCORE score when GET /ads/quality")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void shouldReturnOnlyAdsWithLowScoreWhenGetQualityAds() throws Exception {
        mockMvc.perform(post("/ads/score"))
                .andExpect(status().isOk());
        MvcResult result = mockMvc.perform(get("/ads/quality"))
                .andExpect(status().isOk())
                .andReturn();
        JSONArray responseJSON = new JSONArray(result.getResponse().getContentAsString());
        for (int index = 0; index < responseJSON.length(); index++) {
            assert(Integer.parseInt(responseJSON.getJSONObject(index).getString("score")) < MIN_SCORE);
        }
    }

    @Test
    @DisplayName("should return JSON of size 5 when GET /ads/public")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void shouldReturnJSONArrayOfSize5WhenGetPublicAds() throws Exception {
        mockMvc.perform(post("/ads/score"))
                .andExpect(status().isOk());
        MvcResult result = mockMvc.perform(get("/ads/public"))
                .andExpect(status().isOk())
                .andReturn();
        JSONArray responseJSON = new JSONArray(result.getResponse().getContentAsString());
        assertEquals(5,responseJSON.length());
    }
}
