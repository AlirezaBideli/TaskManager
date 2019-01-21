package com.example.admin.software_1.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.widget.ImageView;

import com.example.admin.software_1.R;
import com.example.admin.software_1.models.Task;
import com.example.admin.software_1.models.TaskLab;

import java.io.File;

/**
 * Created by ADMIN on 1/20/2019.
 */

public class PictureUtils {


    public static Bitmap getScaledBitmap(String path, int desWidth, int desHeigh) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);


        int srcWidth=options.outWidth;
        int srcHeight=options.outHeight;


        int sampleSize=1;
        if (srcWidth>desWidth || srcHeight>desHeigh)
        {

            float heighScale=srcHeight/desHeigh;
            float widthScale=srcWidth/desWidth;
            int sapmleSize=Math.round(heighScale>widthScale?heighScale:widthScale);
        }
        options = new BitmapFactory.Options();
        options.inSampleSize = 16;
        Bitmap bitmap=BitmapFactory.decodeFile(path, options);






        return bitmap;
    }

    public static Bitmap getScaledBitmap(String path, Activity activity)
    {

        Point point=new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(point);

        return getScaledBitmap(path,point.x,point.y);
    }




    public static void   updatePhotoView(Activity activity, Task task, ImageView imageView) {
        File pictureFile=TaskLab.getInstance().getTaskPicture(activity,task);

        if (pictureFile == null || !pictureFile.exists()) {
            imageView.setImageResource(R.mipmap.task);

        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(pictureFile.getPath(), activity);
            imageView.setImageBitmap(bitmap);
        }
    }
}
