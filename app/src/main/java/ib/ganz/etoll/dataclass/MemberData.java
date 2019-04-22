package ib.ganz.etoll.dataclass;

import com.google.gson.annotations.SerializedName;

public class MemberData
{
    @SerializedName("id_member") String idMember;
    @SerializedName("nama_member") String namaMember;
    @SerializedName("password_member") String passwordMember;
    @SerializedName("email_member") String emailMember;
    @SerializedName("tlp_member") String tlpMember;
    @SerializedName("alamat_member") String alamatMember;
    @SerializedName("status_member") String statusMember;
    @SerializedName("saldo") String saldo;

    public String getIdMember()
    {
        return idMember;
    }

    public String getNamaMember()
    {
        return namaMember;
    }

    public String getPasswordMember()
    {
        return passwordMember;
    }

    public String getEmailMember()
    {
        return emailMember;
    }

    public String getTlpMember()
    {
        return tlpMember;
    }

    public String getAlamatMember()
    {
        return alamatMember;
    }

    public String getStatusMember()
    {
        return statusMember;
    }

    public String getSaldo()
    {
        return saldo;
    }

    public void setSaldo(String saldo)
    {
        this.saldo = saldo;
    }

    @Override
    public String toString()
    {
        return idMember;
    }
}
