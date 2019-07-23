package com.digitify.identityscanner.core.ocr;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.digitify.identityscanner.utils.Constants;
import com.googlecode.tesseract.android.TessBaseAPI;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.Nullable;

import static com.googlecode.tesseract.android.TessBaseAPI.PageSegMode.PSM_AUTO;

public class Tesseract {
    protected static String TAG = Tesseract.class.getSimpleName();
    private final String TESS_DIR = "tessdata";
    private final String TESS_TRAINING_FILE = "eng.traineddata";

    private TessBaseAPI mTess;

    public Tesseract(Context context) {
        File trainingFile = loadTrainingFile(context);
        assert trainingFile != null;
        mTess = new TessBaseAPI();
        mTess.setDebug(Constants.SHOW_DEBUG_VIEWS);
        mTess.init(context.getFilesDir().getAbsolutePath(), "eng");
        mTess.setPageSegMode(PSM_AUTO);
    }

    public void stopRecognition() {
        mTess.stop();
    }

    public String getOCRResult(Bitmap bitmap) {
        String whitelist = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789,.-!?<";
        mTess.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, whitelist);
        mTess.setImage(bitmap);
        String result = mTess.getUTF8Text();
        return result;
    }

    public void onDestroy() {
        if (mTess != null)
            mTess.end();
    }

    /**
     * Load trained data file from assets to a place only if file is not there already
     * @param context
     * @return
     */
    @Nullable
    private File loadTrainingFile(@NotNull Context context) {
        try {
            // load cascade file from application resources
            File tessdir = new File(context.getFilesDir(), TESS_DIR);
            if (!tessdir.exists()) tessdir.mkdirs();

            File mCascadeFile = new File(tessdir, TESS_TRAINING_FILE);
            if (mCascadeFile.exists()) {
                return mCascadeFile;
            } else {
                mCascadeFile.createNewFile();
            }

            InputStream is = context.getAssets().open(TESS_DIR + File.separator + TESS_TRAINING_FILE);
            FileOutputStream os = new FileOutputStream(mCascadeFile);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            is.close();
            os.close();
            return mCascadeFile;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Failed to load ocr file. Exception thrown: " + e);
        }
        return null;
    }

}
