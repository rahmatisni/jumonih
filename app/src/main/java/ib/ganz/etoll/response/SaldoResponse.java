package ib.ganz.etoll.response;

import com.google.gson.annotations.SerializedName;

public class SaldoResponse
{
    @SerializedName("code") int code;
    @SerializedName("saldo") String saldo;

    public int getCode()
    {
        return code;
    }

    public String getSaldo()
    {
        return saldo;
    }
}
