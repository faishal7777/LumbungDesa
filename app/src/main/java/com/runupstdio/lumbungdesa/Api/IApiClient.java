package com.runupstdio.lumbungdesa.Api;

import com.runupstdio.lumbungdesa.Model.AddCart;
import com.runupstdio.lumbungdesa.Model.AddProduct;
import com.runupstdio.lumbungdesa.Model.Address;
import com.runupstdio.lumbungdesa.Model.Barangku;
import com.runupstdio.lumbungdesa.Model.Cart;
import com.runupstdio.lumbungdesa.Model.Chatting;
import com.runupstdio.lumbungdesa.Model.Checkout;
import com.runupstdio.lumbungdesa.Model.Conversation;
import com.runupstdio.lumbungdesa.Model.Detail;
import com.runupstdio.lumbungdesa.Model.Done;
import com.runupstdio.lumbungdesa.Model.Feed;
import com.runupstdio.lumbungdesa.Model.History;
import com.runupstdio.lumbungdesa.Model.Penjualan;
import com.runupstdio.lumbungdesa.Model.Product;
import com.runupstdio.lumbungdesa.Model.Profile;
import com.runupstdio.lumbungdesa.Model.Register;
import com.runupstdio.lumbungdesa.Model.RemoveCart;
import com.runupstdio.lumbungdesa.Model.SendChat;
import com.runupstdio.lumbungdesa.Model.UploadImage;
import com.runupstdio.lumbungdesa.Model.UserExist;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
                              @Field("road") String road,
                              @Field("notificationToken") String notifToken);

    @Multipart
    @POST("v1/user-ava")
    Call<UploadImage> set_profile_ava(@Header("Authorization") String token,
                                      @Part MultipartBody.Part file,
                                      @Part("input_image") RequestBody name);

    @GET("v1/user-info")
    Observable<Profile> user_info(@Header("Authorization") String token);

    @GET("v1/user-info-product")
    Observable<Profile> user_info_prod(@Header("Authorization") String token,
                                       @Query("product_id") int prodid);

    @GET("v1/user-address")
    Call<Address> user_address(@Header("Authorization") String token);

    @GET("v1")
    Observable<Feed> feed_index();

    @GET("v1/home")
    Observable<Feed> feed_home(@Header("Authorization") String token);

    @GET("v1")
    Observable<Feed> feed_index_cat(@Query("catID") int catid);

    @GET("v1/home")
    Observable<Feed> feed_home_cat(@Header("Authorization") String token,
                                   @Query("catID") int catid);


    @GET("v1/product")
    Observable<Product> product_detail(@Query ("productId") int prodId,
                                       @Header("Authorization") String token);

    @GET("v1/cart/")
    Observable<Cart> get_cart(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("v1/cart/add")
    Call<AddCart> add_to_cart(@Header("Authorization") String token,
                              @Field("id_product") int id_product,
                              @Field("quantity") int qty);

    @FormUrlEncoded
    @POST("v1/cart/remove")
    Call<RemoveCart> remove_from_cart(@Header("Authorization") String token,
                                      @Field("id_cart") int id_cart);

    @FormUrlEncoded
    @POST("v1/checkout")
    Call<Checkout> checkout(@Header("Authorization") String token,
                            @Field("id_payment") int id_payment);

    @FormUrlEncoded
    @POST("v1/done")
    Call<Done> done(@Header("Authorization") String token,
                    @Field("transaction_id") int trxId);

    @FormUrlEncoded
    @POST("v1/accept")
    Call<Done> accept(@Header("Authorization") String token,
                    @Field("transaction_id") int trxId);

    @FormUrlEncoded
    @POST("v1/cencel")
    Call<Done> cencel(@Header("Authorization") String token,
                      @Field("transaction_id") int trxId);

    @Multipart
    @POST("v1/product")
    Call<AddProduct> add_product(@Header("Authorization") String token,
                                 @Part("product_name") RequestBody product_name,
                                 @Part("product_desc") RequestBody product_desc,
                                 @Part("product_price") int product_price,
                                 @Part("product_stok") int product_stok,
                                 @Part("product_cat") int product_cat,
                                 @Part("expired_at") int expired_at,
                                 @Part MultipartBody.Part[] product_image);

    @GET("v1/history")
    Observable<Barangku> get_barangku(@Header("Authorization") String token,
                                     @Query("status") String status);

    @GET("v1/history")
    Observable<History> get_history(@Header("Authorization") String token,
                                    @Query("status") String status);

    @GET("v1/history")
    Observable<Penjualan> get_history_penjualan(@Header("Authorization") String token,
                                                @Query("status") String status);
    @GET("v1/detail")
    Observable<Detail> get_detail(@Header("Authorization") String token,
                                  @Query("transaction_id") int trxId);

    @GET("v1/chat")
    Observable<Conversation> get_conversation(@Header("Authorization") String token);

    @GET("v1/chat")
    Observable<Chatting> get_conversation_chat(@Header("Authorization") String token,
                                               @Query("chat_id") int chat_id);

    @FormUrlEncoded
    @POST("v1/chat")
    Call<SendChat> send_chat(@Header("Authorization") String token,
                             @Field("destination_id") String destinationId,
                             @Field("message") String message);

    @FormUrlEncoded
    @POST("v1/fcm")
    Call<Done> fcm(@Header("Authorization") String token,
                             @Field("fcmToken") String fcm);
}
