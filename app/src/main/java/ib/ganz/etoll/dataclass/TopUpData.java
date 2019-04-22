package ib.ganz.etoll.dataclass;

import com.google.gson.annotations.SerializedName;

public class TopUpData
{
    @SerializedName("id_top_up") String idTopUp;
    @SerializedName("id_member") String idMember;
    @SerializedName("token") String token;
    @SerializedName("tgl_top_up") String tgl;
    @SerializedName("nominal") String nominal;

    public String getIdTopUp()
    {
        return idTopUp;
    }

    public String getIdMember()
    {
        return idMember;
    }

    public String getToken()
    {
        return token;
    }

    public String getTgl()
    {
        return tgl;
    }

    public String getNominal()
    {
        return nominal;
    }
}
