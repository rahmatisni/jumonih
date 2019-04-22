package ib.ganz.etoll.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by limakali on 3/19/2018.
 */

public class BaseResponse
{
    @SerializedName("code") int code;

    public int getCode()
    {
        return code;
    }
}
