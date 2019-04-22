package ib.ganz.etoll.response;

import com.google.gson.annotations.SerializedName;

import ib.ganz.etoll.dataclass.MemberData;

public class RegisterResponse
{
    @SerializedName("code")     int code;
    @SerializedName("member")   MemberData member;

    public int getCode()
    {
        return code;
    }

    public MemberData getMember()
    {
        return member;
    }
}
