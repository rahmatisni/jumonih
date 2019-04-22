package ib.ganz.etoll.network;

import java.util.List;

import ib.ganz.etoll.dataclass.MemberData;
import ib.ganz.etoll.dataclass.RiwayatData;
import ib.ganz.etoll.response.BaseResponse;
import ib.ganz.etoll.response.EditProfilResponse;
import ib.ganz.etoll.response.LoginResponse;
import ib.ganz.etoll.response.RegisterResponse;
import ib.ganz.etoll.response.SaldoResponse;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IServize
{
    @POST("login.php")
    @FormUrlEncoded
    Observable<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @POST("register.php")
    @FormUrlEncoded
    Observable<RegisterResponse> register(
            @Field("nama")      String nama,
            @Field("password")  String password,
            @Field("email")     String email,
            @Field("no_telp")   String no_telp,
            @Field("alamat")    String alamat
    );

    @POST("logout.php")
    @FormUrlEncoded
    Observable<BaseResponse> logout(
            @Field("id_member") String id_sales
    );

    @POST("profil.php")
    @FormUrlEncoded
    Observable<EditProfilResponse> editProfil(
            @Field("id_member") String id_member,
            @Field("nama_member") String nama_member,
            @Field("alamat") String alamat,
            @Field("no_telp") String no_telp,
            @Field("no_rek") String no_rek,
            @Field("bank") String bank
    );

    @POST("topup.php")
    @FormUrlEncoded
    Observable<SaldoResponse> topUp(
            @Field("id_member") String idMember,
            @Field("token") String token,
            @Field("tgl_top_up") String tgl
    );

    @POST("sharing.php")
    @FormUrlEncoded
    Observable<BaseResponse> share(
            @Field("id_member") String idMember,
            @Field("id_tujuan") String id_tujuan,
            @Field("tgl_sharing") String tgl_sharing,
            @Field("nominal_sharing") String nominal_sharing
    );

    @GET("member.php")
    Observable<List<MemberData>> getMember(
            @Query("id_member") String idMember
    );

    @GET("saldo.php")
    Observable<String> refreshSaldo(
            @Query("id_member") String idMember
    );

    @GET("riwayat.php")
    Observable<List<RiwayatData>> getRiwayat(
            @Query("id_member") String idMember
    );
}
