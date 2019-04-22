package ib.ganz.etoll.activity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import ib.ganz.etoll.R;
import ib.ganz.etoll.helper.Develop;
import ib.ganz.etoll.network.ApiClient;

public class IpActivity extends AppCompatActivity
{
    public static void go(Context c)
    {
        c.startActivity(new Intent(c, IpActivity.class));
    }

    @BindView(R.id.edtLama) EditText edtLama;
    @BindView(R.id.edtBaru) EditText edtBaru;
    @BindView(R.id.btnCopy) Button btnCopy;
    @BindView(R.id.btnPaste)Button btnPaste;
    @BindView(R.id.btnSet)  Button btnSet;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip);
        ButterKnife.bind(this);

        edtLama.setText(ApiClient.IP);
        btnSet.setOnClickListener(x ->
        {
            ApiClient.IP = edtBaru.getText().toString();
            Develop.isIpChange = true;
            finish();
        });

        btnCopy.setOnClickListener(x ->
        {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("label", edtLama.getText().toString());
            clipboard.setPrimaryClip(clip);

            Toast.makeText(getApplicationContext(), "Tercopy IP nya!", Toast.LENGTH_SHORT).show();
        });

        btnPaste.setOnClickListener(x ->
        {
            edtBaru.setText(readFromClipboard());
            edtBaru.requestFocus();
        });
    }

    public String readFromClipboard()
    {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard.hasPrimaryClip())
        {
            ClipDescription description = clipboard.getPrimaryClipDescription();
            ClipData data = clipboard.getPrimaryClip();
            if (data != null && description != null && description.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN))
                return String.valueOf(data.getItemAt(0).getText());
        }
        return "";
    }
}
