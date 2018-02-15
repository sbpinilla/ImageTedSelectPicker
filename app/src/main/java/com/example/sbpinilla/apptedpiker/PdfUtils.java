package com.example.sbpinilla.apptedpiker;

/**
 * Created by sbpinilla on 15/02/2018.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by Sergio on 10/12/17.
 */

public class PdfUtils {

    public static final String LOG_ACTIVITY = "PdfUtils";

    public static String ImgPdf(Activity activity, ArrayList<String> listPathImg, String folder, String pdfName) {

        String result = "";

        Image image;
        String path = FileUtils.createFolderApp(folder);
        path = path + pdfName + ".pdf";

        Document document = new Document(PageSize.A4, 38, 38, 50, 38);

        Log.v(LOG_ACTIVITY, "Document Created");

        Rectangle documentRect = document.getPageSize();

        try {

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));

            Log.v(LOG_ACTIVITY, "Pdf writer");

            document.open();

            Log.v(LOG_ACTIVITY, "Document opened");

            for (int i = 0; i < listPathImg.size(); i++) {


                try {

                    ExifInterface exifInterface = new ExifInterface(listPathImg.get(i));

                    Bitmap bmp= MediaStore
                            .Images
                            .Media
                            .getBitmap(
                                    activity.getContentResolver(),
                                    Uri.fromFile(new File(listPathImg.get(i))));

                    int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_UNDEFINED);

                    switch(orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            bmp=FileUtils.rotateImage(bmp, 90);
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            bmp= FileUtils.rotateImage(bmp, 180);
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            bmp=FileUtils.rotateImage(bmp, 270);
                            break;
                        case ExifInterface.ORIENTATION_NORMAL:
                        default:
                            break;
                    }


                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 10, stream);

                    image = Image.getInstance(stream.toByteArray());

                    if (bmp.getWidth() > documentRect.getWidth() || bmp.getHeight() > documentRect.getHeight()) {

                        //bitmap is larger than page,so set bitmap's size similar to the whole page
                        image.scaleAbsolute(documentRect.getWidth(), documentRect.getHeight());

                    } else {
                        //bitmap is smaller than page, so add bitmap simply.
                        //[note: if you want to fill page by stretching image,
                        // you may set size similar to page as above]

                        image.scaleAbsolute(bmp.getWidth(), bmp.getHeight());
                    }

                    Log.v(LOG_ACTIVITY, "Image path adding");

                    image.setAbsolutePosition(
                            (documentRect.getWidth() - image.getScaledWidth()) / 2,
                            (documentRect.getHeight() - image.getScaledHeight()) / 2);
                    Log.v(LOG_ACTIVITY, "Image Alignments");

                    image.setBorder(Image.BOX);

                    image.setBorderWidth(15);

                    document.add(image);

                    document.newPage();

                  //  img.setImageURI(FileUtils.getImageUri(activity,bmp));

                }catch (Exception e){


                }


             /*   Bitmap bmp = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                activity.getContentResolver(),
                                Uri.fromFile(new File(listPathImg.get(i))));

              //  Bitmap bmp = FileUtils.rotateBitmapOrientation(listPathImg.get(i));

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 70, stream);

                //FileUtils.rotateBitmapOrientation(listPathImg.get(i));



                image = Image.getInstance(listPathImg.get(i));
                //image = Image.getInstance(stream.toByteArray());


                if (bmp.getWidth() > documentRect.getWidth() || bmp.getHeight() > documentRect.getHeight()) {

                    //bitmap is larger than page,so set bitmap's size similar to the whole page
                    image.scaleAbsolute(documentRect.getWidth(), documentRect.getHeight());

                } else {
                    //bitmap is smaller than page, so add bitmap simply.
                    //[note: if you want to fill page by stretching image,
                    // you may set size similar to page as above]

                    image.scaleAbsolute(bmp.getWidth(), bmp.getHeight());
                }

                Log.v(LOG_ACTIVITY, "Image path adding");

                image.setAbsolutePosition(
                        (documentRect.getWidth() - image.getScaledWidth()) / 2,
                        (documentRect.getHeight() - image.getScaledHeight()) / 2);
                Log.v(LOG_ACTIVITY, "Image Alignments");

                image.setBorder(Image.BOX);

                image.setBorderWidth(15);

                document.add(image);

                document.newPage();*/
            }

            Log.v(LOG_ACTIVITY, "Image adding");

            result = path;

        } catch (Exception err) {

            Log.v(LOG_ACTIVITY, "Error ");
            err.printStackTrace();
            result = "";
        } finally {
            document.close();
            Log.v(LOG_ACTIVITY, "Document Closed" + path);
        }


        return result;

    }





}