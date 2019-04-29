package ib.ganz.etoll.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import ib.ganz.etoll.helper.Develop;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by limakali on 1/5/2018.
 */

public class ApiClient
{
    //    public static String IP = "http://192.168.0.141/";
    public static String IP = "http://192.168.1.13/";
    private static String BASE_URL = IP + "etoll/api/";
    public static String IMG_DIR = IP + "etoll/admin/images/";

    private static Retrofit retrofit;

    public static Retrofit getRetrofit()
    {
        if (Develop.isIpChange) setUrl();

        if (retrofit == null || Develop.isIpChange)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getOkHttpClient())
                    .addConverterFactory(GxonConverterFaxtory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }

        return retrofit;
    }

    private static OkHttpClient getOkHttpClient()
    {
        try
        {
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager(){
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException
                {}
                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException
                {}
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }}}, new SecureRandom());

            TrustManagerFactory trustManagerFactory;
            trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            }
            X509TrustManager trustManager = (X509TrustManager) trustManagers[0];

            return new OkHttpClient.Builder()
                    .sslSocketFactory(context.getSocketFactory(), trustManager)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build();
        }
        catch (KeyManagementException |NoSuchAlgorithmException |KeyStoreException e)
        {
            e.printStackTrace();
            return new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build();
        }
    }

    private static void setUrl()
    {
        BASE_URL = IP + "etoll/api/";
        IMG_DIR = IP + "etoll/admin/images/";
    }
}
