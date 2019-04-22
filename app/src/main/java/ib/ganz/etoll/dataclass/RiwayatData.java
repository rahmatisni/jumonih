package ib.ganz.etoll.dataclass;

import com.google.gson.annotations.SerializedName;

public class RiwayatData
{
    public static final int TOP_UP = 0,
                            SHARING = 1,
                            TRANSAKSI = 2;

    @SerializedName("jenis") int jenis;
    @SerializedName("top_up") TopUpData topUpData;
    @SerializedName("sharing") SharingData sharingData;

    public int getJenis()
    {
        return jenis;
    }

    public TopUpData getTopUpData()
    {
        return topUpData;
    }

    public SharingData getSharingData()
    {
        return sharingData;
    }
}
