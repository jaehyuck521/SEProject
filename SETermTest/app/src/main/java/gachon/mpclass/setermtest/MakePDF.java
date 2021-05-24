package gachon.mpclass.setermtest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfDocument.PageInfo;
import android.graphics.pdf.PdfRenderer;
import android.os.Build;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.tom_roush.pdfbox.pdmodel.PDPage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class MakePDF {
    int pageWidth=1200;
    public  void createPDF() {
        PdfDocument doc = new PdfDocument();
        Paint paint = new Paint();
        Paint titlePaint = new Paint();
        PdfDocument.PageInfo pageInfo=new PdfDocument.PageInfo.Builder(1200,2000,1).create();
        PdfDocument.Page page=doc.startPage(pageInfo);
        Canvas canvas=page.getCanvas();
        titlePaint.setTextSize(50f);
        canvas.drawText("근무표",pageWidth/2,150,titlePaint);
        titlePaint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(35f);
        paint.setColor(Color.BLACK);
        canvas.drawText("월요일: ",20,300,paint);
        canvas.drawText("오전: "+"Kim",20, 400, paint);
        canvas.drawText("오후: "+"Choi",20, 500, paint);
        canvas.drawText("마감: "+"Lee",20, 600, paint);
        canvas.drawText("화요일: ",20,700,paint);
        canvas.drawText("오전: "+"Park",20, 800, paint);
        canvas.drawText("오후: "+"Jason",20, 900, paint);
        canvas.drawText("마감: "+"Howard",20, 1000, paint);
        doc.finishPage(page);
        File file=new File(Environment.getExternalStorageDirectory(),"/workshop.PDF");
        if (file.exists()) file.delete();
        try {
            doc.writeTo(new FileOutputStream(file));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        doc.close();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void getImagesFromPDF(File pdf, File png) throws IOException {
        if(png.exists()){
            png.delete();
        }
        png.mkdirs();
        ParcelFileDescriptor fileDescriptor = ParcelFileDescriptor.open(pdf, ParcelFileDescriptor.MODE_READ_ONLY);
        PdfRenderer renderer = new PdfRenderer(fileDescriptor);
        final int pageCount = renderer.getPageCount();
        //페이지 반복해서, 찾는다.
        //pdf가 여러페이지일 경우, 하지만, 보통 1페이지 이내로 구성할 예정
        for (int i = 0; i < pageCount; i++) {
            // Getting Page object by opening page.
            PdfRenderer.Page page = renderer.openPage(i);
            // Creating empty bitmap. Bitmap.Config can be changed.
            Bitmap bitmap = Bitmap.createBitmap(page.getWidth(), page.getHeight(),Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            // Set White background color.
            canvas.drawColor(Color.WHITE);
            canvas.drawBitmap(bitmap, 0, 0, null);
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
            page.close();
            File file = new File(png.getAbsolutePath(), "image"+i + ".png");
            if (file.exists()) file.delete();
            //존재할 경우, 삭제하고 다시 만든다.
            try {
                FileOutputStream out = new FileOutputStream(file); //이미지 파일로 저장.
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                Log.v("Saved Image - ", file.getAbsolutePath());
                out.flush();
                out.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
