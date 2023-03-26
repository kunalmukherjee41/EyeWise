package com.technopie.eyewise.api;

import com.technopie.eyewise.model.AnswerResponse;
import com.technopie.eyewise.model.AnswerResponse1;
import com.technopie.eyewise.model.QuestionsResponse;
import com.technopie.eyewise.model.UserResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Api {
    @FormUrlEncoded
    @POST("createuser")
    Call<ResponseBody> createUser(
            @Field("name") String name,
            @Field("phone_no") String phone,
            @Field("email") String username,
            @Field("password") String password,
            @Field("dob") String designation,
            @Field("pin_code") String last_login
    );

    @FormUrlEncoded
    @POST("userlogin")
    Call<UserResponse> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("getallquestions")
    Call<QuestionsResponse> getAllQuestions();

    @FormUrlEncoded
    @POST("saveanswers")
    Call<ResponseBody> saveAnswers(
            @Field("refuser_id")int refUserId,
            @Field("refquestion_id")int refQuestionId,
            @Field("answer")String answer
    );

    @PUT("getanswersbyuserid/{refuser_id}")
    Call<AnswerResponse> getAnswersByUserId(@Path("refuser_id") int refUserId);

    @PUT("isanswered/{refuser_id}")
    Call<AnswerResponse1> isAnswered(@Path("refuser_id") int refUserId);

}
