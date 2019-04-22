package ib.ganz.etoll.dataclass;

import com.google.gson.annotations.SerializedName;

public class SharingData
{
    @SerializedName("id_sharing") String idSharing;
    @SerializedName("id_member") String idMember;
    @SerializedName("tujuan") MemberData tujuan;
    @SerializedName("tgl_sharing") String tgl;
    @SerializedName("nominal_sharing") String nominal;

    public String getIdSharing()
    {
        return idSharing;
    }

    public String getIdMember()
    {
        return idMember;
    }

    public MemberData getTujuan()
    {
        return tujuan;
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
