package org.icspl.ecdtlp.Interfaces


import org.icspl.ecdtlp.models.GetAllSQModel
import org.icspl.ecdtlp.models.GetAllTblTrUnit
import org.icspl.ecdtlp.models.GetAllTlpMonitoringDetailsModel
import org.icspl.ecdtlp.models.GetQuartersModel
import org.icspl.ecdtlp.models.GetSectionModel
import org.icspl.ecdtlp.models.InsertSectionModel
import org.icspl.ecdtlp.models.InsertTlpModel
import org.icspl.ecdtlp.models.UserDetailModel
import org.icspl.ecdtlp.models.UserLogin
import org.icspl.ecdtlp.models.UserLoginDetail
import io.reactivex.Observable

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    @FormUrlEncoded
    @POST("login.php")
    fun checkLogin(@Field("name") name: String, @Field("pass") password: String): Call<UserLogin>

    @FormUrlEncoded
    @POST("Login")
    fun loginDetails(@Field("username") name: String, @Field("password") password: String): Call<UserDetailModel>

    @FormUrlEncoded
    @POST("userDetails.php")
    fun userDetails(@Field("name") name: String, @Field("pass") password: String): Call<UserLoginDetail>

    @FormUrlEncoded
    @POST("getSection.php")
    fun getSection(@Field("name") name: String): Call<GetSectionModel>

    @FormUrlEncoded
    @POST("getQuarters.php")
    fun getQuarters(@Field("name") name: String): Call<GetQuartersModel>

    @FormUrlEncoded
    @POST("getAllSQDetails.php")
    fun getAllSQDetails(@Field("name") name: String): Call<GetAllSQModel>


    @FormUrlEncoded // get all Tbl_Trunit table by Contractor_id
    @POST("getAlltrunit.php")
    fun getAllTbl_Tr_Unit(@Field("name") name: String): Call<GetAllTblTrUnit>

    @FormUrlEncoded // get all TblMonitoring_TLP table by Contractor_id
    @POST("getAlltlpmonitoring.php")
    fun getAllTblMonitoringDetails(@Field("name") name: String): Call<GetAllTlpMonitoringDetailsModel>

    @FormUrlEncoded
    // @POST("insertSection.php")
    @POST("Insertsec")
    fun insertSection(@Field("contractor_id") contractor_id: String, @Field("section") section: String, @Field("ip_volt") ip_volt: String,
                      @Field("op_volt") op_volt: String, @Field("ext_volt") ext_volt: String, @Field("op_current") op_current: String,
                      @Field("internal_voltage_ref") internal_voltage_ref: String, @Field("ref_selector") ref_selector: String,
                      @Field("coarse_control") coarse_control: String, @Field("fine_ctrl") fine_ctrl: String,
                      @Field("psp_req") psp_req: String, @Field("date") dateofentry: String, @Field("quarters") quarters: String):
    // insert Sections Details
            Call<List<InsertSectionModel>>

    @FormUrlEncoded
    //@POST("insertTLP.php")
    @POST("Monitoring")
    fun insertTlp(@Part("client_id") client_id: String,
                  @Part("contractor_id") contractor_id: String,
                  @Part("section") section: String,
                  @Part("ts_no") ts_no: String,
                  @Part("ts_type") ts_type: String,
                  @Part("ts_km") ts_km: String,
                  @Part("ts_location") ts_location: String,
                  @Part("psp_min") psp_min: Double,
                  @Part("psp_max") psp_max: Double,
                  @Part("casing_min") casing_min: Double,
                  @Part("casing_max") casing_max: Double,
                  @Part("valve") valve: Double,
                  @Part("p1") p1: Double,
                  @Part("p2") p2: Double,
                  @Part("zinc_value") zinc_value: Double,
                  @Part("ac_psp") ac_psp: Double,
                  @Part("s_date") s_date: String,
                  @Part("s_time") s_time: String,
                  @Part("n_cordinate") n_cordinate: String,
                  @Part("e_cordinate") e_cordinate: String,
                  @Part("remark") remark: String,
                  @Part("dateofentry") dateofentry: String,
                  @Part("quarters") quarters: String,
                  @Part("manual_remarks") manual_remarks: String,
                  @Part("tlp_file") TLPFile: MultipartBody.Part,
                  @Part("reading_file") reading_file: MultipartBody.Part,
                  @Part("selfie") selfie: MultipartBody.Part,
                  @Part("type_client") type_client: String,
                  @Part("tsno_client") tsno_client: RequestBody):
    // insert TLP Details
            Call<InsertTlpModel>

    @Multipart
    @POST("Monitoring")
    fun insertTlpNew(@Part("client_id") client_id: RequestBody,
                  @Part("contractor_id") contractor_id: RequestBody,
                  @Part("section") section: RequestBody,
                  @Part("ts_no") ts_no: RequestBody,
                  @Part("ts_type") ts_type: RequestBody,
                  @Part("ts_km") ts_km: RequestBody,
                  @Part("ts_location") RequestBody: RequestBody,
                  @Part("psp_min") psp_min: RequestBody,
                  @Part("psp_max") psp_max: RequestBody,
                  @Part("casing_min") casing_min: RequestBody,
                  @Part("casing_max") casing_max: RequestBody,
                  @Part("valve") valve: RequestBody,
                  @Part("p1") p1: RequestBody,
                  @Part("p2") p2: RequestBody,
                  @Part("zinc_value") zinc_value: RequestBody,
                  @Part("ac_psp") ac_psp: RequestBody,
                  @Part("s_date") s_date: RequestBody,
                  @Part("s_time") s_time: RequestBody,
                  @Part("n_cordinate") n_cordinate: RequestBody,
                  @Part("e_cordinate") e_cordinate: RequestBody,
                  @Part("dateofentry") dateofentry: RequestBody,
                  @Part("quarters") quarters: RequestBody,
                  @Part("manual_remarks") manual_remarks: RequestBody,
                  @Part tlp_file: MultipartBody.Part,
                  @Part reading_file: MultipartBody.Part,
                  @Part selfie: MultipartBody.Part,
                  @Part("type_client") type_client: RequestBody,
                  @Part("tsno_client") tsno_client: RequestBody,
                  @Part("driver_name") driver_name: RequestBody,
                  @Part("driver_mob") driver_mob: RequestBody,
                  @Part("vehicle_number") vehical_number: RequestBody,
                  @Part("start_km") start_km: RequestBody,
                  @Part("end_km") end_km: RequestBody,
                  @Part("crnt_num") crnt_num: RequestBody,
                  @Part("max_num") max_num: RequestBody):
    // insert TLP Details
            Call<List<InsertSectionModel>>
}