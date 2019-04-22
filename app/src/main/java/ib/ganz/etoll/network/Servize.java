package ib.ganz.etoll.network;

import java.util.List;

import ib.ganz.etoll.dataclass.MemberData;
import ib.ganz.etoll.dataclass.RiwayatData;
import ib.ganz.etoll.helper.Waktu;
import ib.ganz.etoll.manager.SessionManager;
import ib.ganz.etoll.response.BaseResponse;
import ib.ganz.etoll.response.EditProfilResponse;
import ib.ganz.etoll.response.LoginResponse;
import ib.ganz.etoll.response.RegisterResponse;
import ib.ganz.etoll.response.SaldoResponse;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Servize
{
    private static IServize ig;

    private static IServize getService()
    {
        if (ig == null) ig = ApiClient.getRetrofit().create(IServize.class);
        return ig;
    }

    public static Observable<LoginResponse> login(String email, String password)
    {
        return getService()
                .login(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<RegisterResponse> register(String nama, String password, String email, String noTelp, String alamat)
    {
        return getService()
                .register(nama, password, email, noTelp, alamat)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<BaseResponse> logout()
    {
        return getService()
                .logout(SessionManager.getIdMember())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<EditProfilResponse> editProfil(String nama_user, String alamat, String no_telp, String no_rek, String bank)
    {
        return getService()
                .editProfil(SessionManager.getIdMember(), nama_user, alamat, no_telp, no_rek, bank)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<SaldoResponse> topUp(String token)
    {
        return getService()
                .topUp(SessionManager.getIdMember(), token, Waktu.sekarang())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<BaseResponse> share(String id_tujuan, String nominal_sharing)
    {
        return getService()
                .share(SessionManager.getIdMember(), id_tujuan, Waktu.sekarang(), nominal_sharing)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<List<MemberData>> getMember()
    {
        return getService()
                .getMember(SessionManager.getIdMember())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<String> refreshSaldo()
    {
        return getService()
                .refreshSaldo(SessionManager.getIdMember())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<List<RiwayatData>> getRiwayat()
    {
        return getService()
                .getRiwayat(SessionManager.getIdMember())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
