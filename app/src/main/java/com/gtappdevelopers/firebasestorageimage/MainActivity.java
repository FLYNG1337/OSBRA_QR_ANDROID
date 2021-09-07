package com.gtappdevelopers.firebasestorageimage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.print.PrintHelper;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;



public class MainActivity extends AppCompatActivity {

    //vaiables for imageview,edittext,button, bitmap and qrencoder.
    private ImageView qrCodeIV;
    private EditText dataEdt;
    private Button generateQrBtn;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;

    private void print_QR(Bitmap bitmap)
    {
        PrintHelper photoPrinter = new PrintHelper(this);
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        photoPrinter.printBitmap("droids.jpg - test print", bitmap);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initializing all variables.
        qrCodeIV = findViewById(R.id.idIVQrcode);
        //dataEdt = findViewById(R.id.idEdt);
        generateQrBtn = findViewById(R.id.idBtnGenerateQR);

        //intializing onclick listner for button.
        generateQrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (TextUtils.isEmpty(dataEdt.getText().toString())) {
                    //if the edittext inputs are empty then execute this method showing a toast message.
                    Toast.makeText(MainActivity.this, "Enter some text to generate QR Code", Toast.LENGTH_SHORT).show();
                } else {*/

                // Below defines Data to be written into QRCode
                Date date = Calendar.getInstance().getTime();
                DateFormat datef = new SimpleDateFormat("dd.MM.yyyy");
                DateFormat timef = new SimpleDateFormat("hh:mm:ss");
                String Data = "OSBRA\nk-Teil\nKontrolliert am: " + datef.format(date) + " um " + timef.format(date);

                //below line is for getting the windowmanager service.
                WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                //initializing a variable for default display.
                Display display = manager.getDefaultDisplay();
                //creating a variable for point which is to be displayed in QR Code.
                Point point = new Point();
                display.getSize(point);
                //getting width and height of a point
                int width = point.x;
                int height = point.y;
                //generating dimension from width and height.
                int dimen = width < height ? width : height;
                dimen = dimen * 3 / 4;
                //setting this dimensions inside our qr code encoder to generate our qr code.
                qrgEncoder = new QRGEncoder(Data, null, QRGContents.Type.TEXT, dimen);
                try {
                    //getting our qrcode in the form of bitmap.
                    bitmap = qrgEncoder.encodeAsBitmap();
                    // the bitmap is set inside our image view using .setimagebitmap method.
                    qrCodeIV.setImageBitmap(bitmap);
                    print_QR(bitmap);

                } catch (WriterException e) {
                    //this method is called for exception handling.
                    Log.e("Tag", e.toString());
                }


                //}
            }
        });
    }
}