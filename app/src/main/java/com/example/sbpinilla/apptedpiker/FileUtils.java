package com.example.sbpinilla.apptedpiker;

/**
 * Created by sbpinilla on 15/02/2018.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by Sergio on 10/12/17.
 */

public class FileUtils {

    public static final String LOG_ACTIVITY="FileUtils";

    public static final String FOLDER_NAME="DependenciaJudicial";


    public static String folderApp() {

        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+ FOLDER_NAME + "/";

        Log.d(LOG_ACTIVITY,"path:"+path);


        File folder = new File(path);
        if (!folder.exists()) {
            Log.d(LOG_ACTIVITY,"folder.exists():"+folder.exists());
            folder.mkdir();
        }

        return path;

    }


    public static String createFolderApp(String newFolder ) {

        String path = Environment.getExternalStorageDirectory().getAbsolutePath() +"/"+ FOLDER_NAME +"/"+newFolder+ "/";

        Log.d(LOG_ACTIVITY,"path:"+path);


        File folder = new File(path);
        if (!folder.exists()) {
            Log.d(LOG_ACTIVITY,"folder.exists():"+folder.exists());
            folder.mkdir();
        }

        return path;

    }

    public static Boolean checkExistFolder(String nameFolder) {

        File folder = new File(nameFolder);

        return folder.exists();
    }


    public static Bitmap rotateImage(Bitmap source, float angle) {

        Matrix mat = new Matrix();
        mat.postRotate(angle);

        Bitmap bitmap= Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), mat,false);

        return bitmap;
    }

    public static Bitmap rotateBitmapOrientation(String photoFilePath) {
        // Create and configure BitmapFactory
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoFilePath, bounds);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap bm = BitmapFactory.decodeFile(photoFilePath, opts);
        // Read EXIF Data
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(photoFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;
        int rotationAngle = 0;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;
        // Rotate Bitmap
        Matrix matrix = new Matrix();
        matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);
        // Return result
        return rotatedBitmap;
    }

    public  static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

}
