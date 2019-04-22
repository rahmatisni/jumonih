package ib.ganz.etoll.helper;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by limakali on 11/28/2017.
 */

public class Imaging
{
    public static Bitmap getScaled(Bitmap b, double maxSize)
    {
        double ratio = 1;

        double w = b.getWidth();
        double h = b.getHeight();

        if (w < maxSize || h < maxSize)
            return b;

        if (w < h)
        {
            if (w > maxSize)
            {
                w = maxSize;

                ratio = maxSize / b.getWidth();
                h = h * ratio;
            }
        }
        else if (w > h)
        {
            if (h > maxSize)
            {
                h = maxSize;
                ratio = maxSize / b.getHeight();
                w = w * ratio;
            }
        }
        else
        {
            if (h > maxSize)
            {
                h = maxSize;
                w = maxSize;
            }
        }

        if (w != b.getWidth() || h != b.getHeight())
            return Bitmap.createScaledBitmap(b, (int)w, (int)h, false);
        else
            return null;
    }

    public static String convertBitmapToString(Bitmap bitmap)
    {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);
        byte[] arB = b.toByteArray();
        return Base64.encodeToString(arB, Base64.DEFAULT);
    }
}
