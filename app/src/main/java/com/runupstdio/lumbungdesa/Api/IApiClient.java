package com.runupstdio.lumbungdesa.Api;

import com.runupstdio.lumbungdesa.Model.AddCart;
import com.runupstdio.lumbungdesa.Model.Feed;
import com.runupstdio.lumbungdesa.Model.Product;
import com.runupstdio.lumbungdesa.Model.Profile;
import com.runupstdio.lumbungdesa.Model.Register;
import com.runupstdio.lumbungdesa.Model.UserExist;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IApiClient {
    @FormUrlEncoded
    @POST("check")
    Call<UserExist> isUserExist(@Field("msisdn") String msisdn);

    @FormUrlEncoded
    @POST("register")
    Call<Register> doRegister(@Header("Authorization") String token,
                              @Field("msisdn") String msisdn,
                              @Field("name") String name,
                              @Field("country") String country,
                              @Field("state") String state,
                              @Field("city") String city,
                              @Field("kecamatan") String kecamatan,
                              @Field("desa") String desa,
                              @Field("road") String road);

    @GET("v1/user-info")
    Observable<Profile> user_info(@Header("Authorization") String token);

    @GET("v1")
    Observable<Feed> feed_index();

    @GET("v1/home")
    Observable<Feed> feed_home(@Header("Authorization") String token);

    @GET("v1/product")
    Observable<Product> product_detail(@Query ("productId") int prodId,
                                       @Header("Authorization") String token);

    @FormUrlEncoded
    @POST("v1/cart/add")
    Call<AddCart> add_to_cart(@Header("Authorization") String token,
                              @Field("id_product") int id_product,
                              @Field("quantity") int qty);
}
