package co.yap.modules.kyc.fragments

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface UploadIdCardRetroService {

    @Multipart
    @POST("digi_ocr/detect/")
    fun uploadIdCard(@Part file: MultipartBody.Part):Call<CardScanResponse>


}