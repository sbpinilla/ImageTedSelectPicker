package com.example.sbpinilla.apptedpiker;

import android.app.Activity;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;

public class MainActivity extends AppCompatActivity {

    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this ;
        FileUtils.folderApp();
        Button btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TedBottomPicker bottomSheetDialogFragment = new TedBottomPicker.Builder(MainActivity.this)
                        .setOnMultiImageSelectedListener(new TedBottomPicker.OnMultiImageSelectedListener() {
                            @Override
                            public void onImagesSelected(ArrayList<Uri> uriList) {
                                // here is selected uri list


                                ArrayList<String>tempUris = new ArrayList<>();

                                for (Uri uri:uriList) {

                                    Log.d("MainActivity","myUri.getPath() : "+uri.getPath());
                                    tempUris.add(uri. getPath());

                                }

                                ImageView img = findViewById(R.id.img);




                              /*  try {

                                    ExifInterface exifInterface = new ExifInterface(uriList.get(0).getPath());

                                    Bitmap bmp= MediaStore
                                            .Images
                                            .Media
                                            .getBitmap(
                                                    activity.getContentResolver(),
                                                    Uri.fromFile(new File(uriList.get(0).getPath())));

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

                                    img.setImageURI(FileUtils.getImageUri(activity,bmp));

                                }catch (Exception e){


                                }*/



                                CallBackCreatePdf callBackCreatePdf = new CallBackCreatePdf() {
                                    @Override
                                    public void OnCallBackCreatePdf(String tPath) {

                                        if(tPath!=""){

                                            Toast.makeText(getBaseContext(),"Pdf Creado",Toast.LENGTH_LONG).show();

                                        }else{
                                            Toast.makeText(getBaseContext(),"Error al crear el Pdf",Toast.LENGTH_LONG).show();
                                        }


                                    }
                                };

                                MaterialDialog.Builder builder = new MaterialDialog.Builder(activity)
                                        .title("Creando Pdf")
                                        .content("un momento ...")
                                        .cancelable(false)
                                        .progress(true, 0);
                                MaterialDialog dialog = builder.build();

                                AsynCreatePdf asynCreatePdf = new AsynCreatePdf(activity,callBackCreatePdf,dialog,tempUris,"1",("pdf"+(new Date()).getSeconds()));

                                asynCreatePdf.execute();


                            }
                        })
                        .setPeekHeight(1600)
                        .showTitle(true)
                        //.setPeekHeight(20)
                        .setCompleteButtonText("Hecho")
                        .setEmptySelectionText("Sin seleccionar")
                        .create();

                bottomSheetDialogFragment.show(getSupportFragmentManager());

            }
        });


    }
}
