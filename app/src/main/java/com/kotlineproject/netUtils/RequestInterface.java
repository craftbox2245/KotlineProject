package com.kotlineproject.netUtils;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RequestInterface {

    String secure_field = "key";
    String secure_value = "1226";

    // todo Get Customer Detail
    @POST("service_user.php?" + secure_field + "=" + secure_value + "&s=5&type=Android")
    Call<ResponseBody> getcustomerDetail();

    String secure_field1 = "r_p";
    String secure_value1 = "1224";

    // todo Get  Product
    @POST("service_general.php?" + secure_field1 + "=" + secure_value1 + "&service=get_product&type=Android")
    Call<ResponseBody> getProduct();




}
