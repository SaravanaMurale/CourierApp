package com.example.courierapp.activity;

import android.Manifest;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.courierapp.R;
import com.example.courierapp.utils.PermissionUtils;
import com.example.courierapp.utils.ToastUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.example.courierapp.utils.AppConstant.LOCATION_PERMISSION_REQUEST_CODE;


public class PDFGenerationActivity extends AppCompatActivity {

    Button generatePDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfgeneration);

        generatePDF = (Button) findViewById(R.id.generatePDF);

        if (!PermissionUtils.hasPermission(PDFGenerationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                ) {
            PermissionUtils.requestPermissions(PDFGenerationActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, LOCATION_PERMISSION_REQUEST_CODE);


        } else {

            if (PermissionUtils.hasPermission(PDFGenerationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    && PermissionUtils.hasPermission(PDFGenerationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {


            }

        }

        generatePDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPDF();
            }
        });


    }

    private void createPDF() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {

            PdfDocument pdfDocument = new PdfDocument();
            Paint myPaint = new Paint();
            PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(900, 1200, 1).create();
            PdfDocument.Page myPage = pdfDocument.startPage(myPageInfo);
            Canvas canvas = myPage.getCanvas();

            int startXPosition = 10;
            int endXPosition = myPageInfo.getPageWidth() - 10;
            int startYPosition = 130;

//PDF generation commit

            myPaint.setTextAlign(Paint.Align.CENTER);
            myPaint.setTextSize(15.0f);
            canvas.drawText("SUN ATLANTIC COURIER EXPRESS", myPageInfo.getPageWidth() / 2, 30, myPaint);

            //myPaint.setTextAlign(Paint.Align.CENTER);
            myPaint.setTextSize(8.0f);
            canvas.drawText("www.sunatlantics.com", myPageInfo.getPageWidth() / 2, 40, myPaint);

            //Rectangle
            myPaint.setStyle(Paint.Style.STROKE);
            myPaint.setStrokeWidth(5);
            canvas.drawRect(10, 80, myPageInfo.getPageWidth() - 10, 1200, myPaint);

           /* //LINE
            myPaint.setStrokeWidth(5);
            myPaint.setColor(getResources().getColor(R.color.sun_atlantic_color));
            canvas.drawLine(startXPosition, startYPosition, endXPosition, startYPosition, myPaint);
*/
            //First Row

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            myPaint.setStyle(Paint.Style.FILL);
            myPaint.setColor(getResources().getColor(R.color.sun_atlantic_color));
            canvas.drawText("NAME OF SENDER", 80, 110, myPaint);

            //BOX VERTICAL LINE
            myPaint.setStrokeWidth(2);
            //myPaint.setColor(getResources().getColor(R.color.black));
            canvas.drawLine(160, 80, 160, 178, myPaint);

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("TELEPHONE NO", 210, 110, myPaint);

            //BOX VERTICAL LINE
            myPaint.setStrokeWidth(2);
            canvas.drawLine(270, 80, 270, 1200, myPaint);


            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("CONTACT PERSON", 330, 110, myPaint);

            //BOX VERTICAL LINE
            myPaint.setStrokeWidth(2);
            canvas.drawLine(410, 80, 410, 178, myPaint);

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("TELEPHONE NO", 460, 110, myPaint);

            //BOX VERTICAL LINE LONG
            myPaint.setStrokeWidth(2);
            canvas.drawLine(550, 80, 550, 1200, myPaint);

            //LINE
            myPaint.setStrokeWidth(2);
            myPaint.setColor(getResources().getColor(R.color.sun_atlantic_color));
            canvas.drawLine(startXPosition, startYPosition, 550, startYPosition, myPaint);

            //End of first row

            //Second Row

            //LONG HORIZONTAL LINE
            myPaint.setStrokeWidth(2);
            myPaint.setColor(getResources().getColor(R.color.sun_atlantic_color));
            canvas.drawLine(startXPosition, startYPosition + 50, 550, startYPosition + 50, myPaint);

            //End Second Row


            //Third Row
            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("YOUR ACCOUNT #", 80, 210, myPaint);

            //BOX VERTICAL LINE
            myPaint.setStrokeWidth(2);
            canvas.drawLine(160, startYPosition + 50, 160, startYPosition + 100, myPaint);

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("RECEIVER ADDRESS", 340, 210, myPaint);

            //LONG HORIZONTAL LINE
            myPaint.setStrokeWidth(2);
            myPaint.setColor(getResources().getColor(R.color.sun_atlantic_color));
            canvas.drawLine(startXPosition, startYPosition + 100, 550, startYPosition + 100, myPaint);

            //End of Thrid Row

            //Fourth Row
            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("SENDER ADDRESS", 80, 260, myPaint);

            //Horizontal LINE
            myPaint.setStrokeWidth(2);
            canvas.drawLine(startXPosition, startYPosition + 150, 270, startYPosition + 150, myPaint);

            //End of Fourth Row

            //Fifth Fow
            //HORIZONTAL LINE
            myPaint.setStrokeWidth(2);
            canvas.drawLine(startXPosition, startYPosition + 330, 270, startYPosition + 330, myPaint);
            //End of Fifth Fow

            //Sixth Row
            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("PRODUCT TYPE", 65, 490, myPaint);

            //BOX VERTICAL LINE
            myPaint.setStrokeWidth(2);
            canvas.drawLine(125, startYPosition + 330, 125, startYPosition + 380, myPaint);

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            //String s="TYPE OF SERVICE";
            canvas.drawText("TYPE OF SERVICE", 180, 490, myPaint);

            //HORIZONTAL LINE
            myPaint.setStrokeWidth(2);
            canvas.drawLine(startXPosition, startYPosition + 380, 270, startYPosition + 380, myPaint);

            //End Sixth Row

            //Seventh Row
            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("DOCUMENT", 55, 530, myPaint);


            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("DELIVERY", 140, 530, myPaint);

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("EXPRESS", 230, 530, myPaint);


            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("PARCEL", 40, 580, myPaint);


            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("COLLECTION", 120, 580, myPaint);

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("PAYMENT", 230, 580, myPaint);

            //HORIZONTAL LINE
            myPaint.setStrokeWidth(2);
            canvas.drawLine(startXPosition, startYPosition + 480, 270, startYPosition + 480, myPaint);
            //End Seventh Row

            //8th Row
            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("JOB CODE", 145, 645, myPaint);

            //BOX VERTICAL LINE
            myPaint.setStrokeWidth(2);
            canvas.drawLine(100, startYPosition + 480, 100, startYPosition + 530, myPaint);

            //BOX VERTICAL LINE
            myPaint.setStrokeWidth(2);
            canvas.drawLine(200, startYPosition + 480, 200, startYPosition + 530, myPaint);

            //HORIZONTAL LINE
            myPaint.setStrokeWidth(2);
            canvas.drawLine(startXPosition, startYPosition + 530, 270, startYPosition + 530, myPaint);
            //End of 8th Row

            //9th Row
            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("SENDER ", 50, 690, myPaint);

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("DECLARATION", 60, 710, myPaint);


            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("PICK-UP BY (SUN", 200, 690, myPaint);

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("ATLANTIC)", 200, 710, myPaint);

            //HORIZONTAL LINE
            myPaint.setStrokeWidth(2);
            canvas.drawLine(startXPosition, startYPosition + 600, 270, startYPosition + 600, myPaint);
            //End of 9th Row

            //10th Row
            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("NAME ", 40, 760, myPaint);

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("DATE ", 160, 760, myPaint);

            //BOX VERTICAL LINE
            myPaint.setStrokeWidth(2);
            canvas.drawLine(135, startYPosition + 600, 135, startYPosition + 700, myPaint);

            //HORIZONTAL LINE
            myPaint.setStrokeWidth(2);
            canvas.drawLine(startXPosition, startYPosition + 650, 270, startYPosition + 650, myPaint);

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("NAME ", 40, 810, myPaint);

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("DATE ", 160, 810, myPaint);

            //HORIZONTAL LINE
            myPaint.setStrokeWidth(2);
            canvas.drawLine(startXPosition, startYPosition + 700, 270, startYPosition + 700, myPaint);
            //End of 10th Row

            //11th Row
            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("SHIPMENT INFORMATION ", 100, 860, myPaint);

            //HORIZONTAL LINE
            myPaint.setStrokeWidth(2);
            canvas.drawLine(startXPosition, startYPosition + 750, 270, startYPosition + 750, myPaint);
            //End of 11th Row

            //12th Row
            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("NUMBER OF PIECES:", 80, 900, myPaint);

            //HORIZONTAL LINE
            myPaint.setStrokeWidth(2);
            canvas.drawLine(startXPosition, startYPosition + 800, 270, startYPosition + 800, myPaint);


            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("WEIGHT:", 55, 950, myPaint);

            //HORIZONTAL LINE
            myPaint.setStrokeWidth(2);
            canvas.drawLine(startXPosition, startYPosition + 850, 270, startYPosition + 850, myPaint);

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("VALUE:", 55, 1000, myPaint);

            //BOX VERTICAL LINE
            myPaint.setStrokeWidth(2);
            canvas.drawLine(150, startYPosition + 750, 150, startYPosition + 890, myPaint);

            //HORIZONTAL LINE
            myPaint.setStrokeWidth(2);
            canvas.drawLine(startXPosition, startYPosition + 890, 270, startYPosition + 890, myPaint);

            //End of 12th Row

            //Second Part

            //HORIZONTAL LINE
            myPaint.setStrokeWidth(2);
            canvas.drawLine(270, startYPosition + 290, 550, startYPosition + 290, myPaint);

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("ORDER DESCRIPTION:", 340, 450, myPaint);


            //HORIZONTAL LINE
            myPaint.setStrokeWidth(2);
            canvas.drawLine(270, startYPosition + 330, 550, startYPosition + 330, myPaint);

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("Pls include one column cust to key in", 400, 500, myPaint);

            //HORIZONTAL LINE
            myPaint.setStrokeWidth(2);
            canvas.drawLine(270, startYPosition + 400, 550, startYPosition + 400, myPaint);

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("RECEIVED IN GOOD ORDER", 360, 550, myPaint);

            //HORIZONTAL LINE
            myPaint.setStrokeWidth(2);
            canvas.drawLine(270, startYPosition + 450, 550, startYPosition + 450, myPaint);

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("NAME:", 300, 610, myPaint);

            //HORIZONTAL LINE
            myPaint.setStrokeWidth(2);
            canvas.drawLine(270, startYPosition + 500, 550, startYPosition + 500, myPaint);

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("IC NO:", 300, 660, myPaint);

            //HORIZONTAL LINE
            myPaint.setStrokeWidth(2);
            canvas.drawLine(270, startYPosition + 550, 550, startYPosition + 550, myPaint);

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("DATE:", 300, 720, myPaint);

            //HORIZONTAL LINE
            myPaint.setStrokeWidth(2);
            canvas.drawLine(270, startYPosition + 610, 550, startYPosition + 610, myPaint);

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("CONDITION CARRIAG:", 350, 770, myPaint);

            //HORIZONTAL LINE
            myPaint.setStrokeWidth(2);
            canvas.drawLine(270, startYPosition + 650, 550, startYPosition + 650, myPaint);

            //THIRD PART

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("CONSIGNMENT NUMBER:", 630, 110, myPaint);

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(10.0f);
            canvas.drawText("Non negotiable and at owner's Risk", 650, 130, myPaint);

            //HORIZONTAL LINE
            myPaint.setStrokeWidth(2);
            canvas.drawLine(550, startYPosition + 10, 890, startYPosition + 10, myPaint);

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(10.0f);
            canvas.drawText("92452", 585, 160, myPaint);

            //HORIZONTAL LINE
            myPaint.setStrokeWidth(2);
            canvas.drawLine(550, startYPosition + 50, 890, startYPosition + 50, myPaint);

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("SHIPPER's AGREEMENT(Signature Required)", 690, 215, myPaint);

            //HORIZONTAL LINE
            myPaint.setStrokeWidth(2);
            canvas.drawLine(550, startYPosition + 100, 890, startYPosition + 100, myPaint);

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(11.0f);
            canvas.drawText("Unless otherwise agreed in writing.We agree that's", 690, 260, myPaint);

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(11.0f);
            canvas.drawText("terms and conditions of carriage are all the terms", 690, 280, myPaint);

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(11.0f);
            canvas.drawText("of the contract between me/us and Sun Atlantic", 690, 300, myPaint);

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(11.0f);
            canvas.drawText("and (1) such terms and conditions and / or", 690, 320, myPaint);

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(11.0f);
            canvas.drawText("excludes Sun Atlantic's liability for loss damage or", 690, 340, myPaint);

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(11.0f);
            canvas.drawText("delay and (2) this shipment does not contain cash", 690, 360, myPaint);

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(11.0f);
            canvas.drawText("or dangerous good (see reverse).", 690, 380, myPaint);

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(11.0f);
            canvas.drawText("Signature:", 585, 440, myPaint);

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(11.0f);
            canvas.drawText("Date:", 570, 470, myPaint);

            //HORIZONTAL LINE
            myPaint.setStrokeWidth(2);
            canvas.drawLine(550, startYPosition + 370, 890, startYPosition + 370, myPaint);

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(11.0f);
            canvas.drawText("Content for Class charges Goes Here:", 660, 530, myPaint);

            //HORIZONTAL LINE
            myPaint.setStrokeWidth(2);
            canvas.drawLine(550, startYPosition + 420, 890, startYPosition + 420, myPaint);

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("Services:", 590, 620, myPaint);

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("Others:", 580, 670, myPaint);

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("Insurance:", 590, 720, myPaint);

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("Currency:", 590, 770, myPaint);

            myPaint.setStrokeWidth(0);
            myPaint.setTextSize(13.0f);
            canvas.drawText("Total:", 580, 820, myPaint);


            pdfDocument.finishPage(myPage);


            Toast.makeText(this, "PDF Generated", Toast.LENGTH_LONG).show();

            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "FirstPdf.pdf");

            try {
                pdfDocument.writeTo(new FileOutputStream(file));
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else {
            ToastUtils.getInstance(PDFGenerationActivity.this).showLongToast(getString(R.string.lower_vers));
        }


    }
}
