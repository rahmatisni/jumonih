package ib.ganz.etoll.helper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ib.ganz.etoll.R;

public class Utilz
{
    public static String harga(int i)
    {
        return harga(i + "");
    }

    public static String harga(String s)
    {
        String rev = new StringBuilder(s).reverse().toString();
        StringBuilder n = new StringBuilder();

        for (int i = 0; i < rev.length(); i++)
        {
            if (i != 0 && i % 3 == 0) n.append(".");
            n.append(rev.substring(i, i + 1));
        }

        return "Rp " + n.reverse().toString();
    }

    // Edittext
    public static void edtFocus(Context c, EditText e, int color) // color nya langsung R.color, gk perlu getresources
    {
        if (!e.getText().toString().trim().isEmpty())
        {
            DrawableCompat.setTint(e.getCompoundDrawables()[0], ContextCompat.getColor(c, color));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                e.setElevation(10);
            }
        }

        e.setOnFocusChangeListener((v, b) ->
        {
            DrawableCompat.setTint(e.getCompoundDrawables()[0], ContextCompat.getColor(c, b || !e.getText().toString().trim().isEmpty() ? color : R.color.textDarkDisabled));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                e.setElevation(b ? 10 : 4);
            }
        });
    }

    public static void edtRupiah(EditText... es)
    {
        for (EditText e : es)
        {
            e.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
                {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
                {
                }

                @Override
                public void afterTextChanged(Editable editable)
                {
                    String input = editable.toString();

                    char[] ya = input.toCharArray();
                    boolean isEmpty = false;
                    if (ya.length == 3 && ya[0] == 'R' && ya[1] == 'p' && ya[2] == ' ')
                    {
                        isEmpty = true;
                    }

                    if (!input.trim().isEmpty() && !isEmpty)
                    {
                        if (input.contains("Rp "))
                        {
                            String temp = input.substring(3);
                            input = temp;
                        }

                        // awal hapus titik
                        List<Character> lc = new ArrayList<>();
                        for (char c : input.toCharArray())
                        {
                            lc.add(c);
                        }
                        for (int i = lc.size() - 1; i >= 0; i--)
                        {
                            if (lc.get(i) == '.')
                            {
                                lc.remove(i);
                            }
                        }
                        char[] nc = new char[lc.size()];
                        for (int i = 0; i < lc.size(); i++)
                        {
                            nc[i] = lc.get(i);
                        }

                        input = new String(nc);
                        // akhir hapus titik

                        // ini adalah pembuka yg sakral
                        e.removeTextChangedListener(this);

                        if (input.length() > 3)
                        {
                            char[] arc = input.toCharArray();

                            List<Character> revChar = new ArrayList<>();
                            for (int i = arc.length - 1; i >= 0; i--)
                            {
                                revChar.add(arc[i]);
                            }

                            List<Character> dottedChar = new ArrayList<>();
                            for (int i = 0; i < revChar.size(); i++)
                            {
                                if (i != 0 && i % 3 == 0)
                                {
                                    dottedChar.add('.');
                                }

                                dottedChar.add(revChar.get(i));
                            }

                            char[] finalChar = new char[dottedChar.size()];
                            int idx = 0;
                            for (int i = dottedChar.size() - 1; i >= 0; i--)
                            {
                                finalChar[idx] = dottedChar.get(i);
                                idx++;
                            }

                            input = new String(finalChar);
                        }

                        String finalStr = "Rp " + input;
                        e.setText(finalStr);
                        e.setSelection(e.getText().length());

                        // ini adalah penutup yg sakral
                        e.addTextChangedListener(this);
                    }
                    else
                    {
                        // ini adalah pembuka yg sakral
                        e.removeTextChangedListener(this);

                        e.setText("");

                        // ini adalah penutup yg sakral
                        e.addTextChangedListener(this);
                    }
                }
            });
        }
    }

    public static String getNum(EditText e)
    {
        String s = e.getText().toString().trim();
        String[] c = s.split("");
        StringBuilder res = new StringBuilder();

        for (String x : c)
        {
            try
            {
                Integer.parseInt(x);
                res.append(x);
            }
            catch (NumberFormatException ignored) {}
        }

        return res.toString();
    }
//    End Edittext

    public static void lineThru(TextView t)
    {
        t.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
        t.getPaint().setFlags(0);
    }

    public static void call(Context c, String num)
    {
        Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + num));
        c.startActivity(i);
    }

    public static Bitmap rotate(Bitmap bitmap, float degrees)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}
