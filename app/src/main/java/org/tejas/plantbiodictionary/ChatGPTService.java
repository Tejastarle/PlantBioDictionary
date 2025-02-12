package org.tejas.plantbiodictionary;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ChatGPTService {
    @Headers({
            "Authorization: Bearer sk-proj-XXXXX", // Replace with your OpenAI API Key
            "Content-Type: application/json"
    })
    @POST("chat/completions")
    Call<ChatGPTResponse> analyzeImage(@Body ChatGPTRequest request);
}
