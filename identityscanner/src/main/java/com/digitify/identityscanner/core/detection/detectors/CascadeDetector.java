package com.digitify.identityscanner.core.detection.detectors;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.digitify.identityscanner.R;
import com.digitify.identityscanner.core.detection.models.Entity;
import com.digitify.identityscanner.core.detection.utils.OpenCVUtils;

import org.jetbrains.annotations.NotNull;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Size;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.Nullable;

public abstract class CascadeDetector<T extends Entity> extends EntityDetector<T> {
    protected static String TAG = CascadeDetector.class.getSimpleName();

    /**
     * Returns the training model (an .xml file in raw folder)
     *
     * @return
     */
    protected abstract int getTrainingRawModel();


    protected final String CASCADE_DIR = "cascade";
    private CascadeClassifier mCascadeDetector;
    private int absoluteSize = 0;
    private float relativeSize = 0.15f;

    public CascadeDetector(Context context) {
        load(context);
    }

    private void applyCascadeDetectionFilters(Mat src) {
        OpenCVUtils.grayScale(src);
        OpenCVUtils.gaussianBlur(src, new Size(21, 21));
    }

    protected final synchronized MatOfRect findCascade(@NotNull Mat mGray) {
        applyCascadeDetectionFilters(mGray);
        updateAbsoluteFaceSize(mGray.rows());
        MatOfRect eyes = new MatOfRect();
        if (getCascadeDetector() != null) {
            getCascadeDetector().detectMultiScale(mGray, eyes, 1.1, 3, 2, new Size(getAbsoluteSize(), getAbsoluteSize()), new Size());
//             getCascadeDetector().detectMultiScale(mGray, eyes, 1.1, 3, 2);
        }


        return eyes;
    }

    private void load(Context context) {
        File file = loadTrainingFile(context);
        assert file != null;
        loadCascadeDetector(file);
        file.delete();
    }

    @Nullable
    private File loadTrainingFile(@NotNull Context context) {
        try {
            // load cascade file from application resources
            InputStream is = context.getResources().openRawResource(getTrainingRawModel());
            File cascadeDir = context.getDir(CASCADE_DIR, Context.MODE_PRIVATE);
            File mCascadeFile = new File(cascadeDir, generateTrainingModelName());
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
            Log.e(TAG, "Failed to load cascade. Exception thrown: " + e);
        }
        return null;
    }

    private void loadCascadeDetector(@NotNull File mCascadeFile) {
        mCascadeDetector = new CascadeClassifier(mCascadeFile.getAbsolutePath());
        // TODO: Do error handling.. pass the errors down to children
        if (mCascadeDetector.empty()) {
            Log.e(TAG, "Failed to load cascade classifier");
            mCascadeDetector = null;
        } else
            Log.i(TAG, "Loaded cascade classifier from " + mCascadeFile.getAbsolutePath());
    }

    protected final CascadeClassifier getCascadeDetector() {
        return mCascadeDetector;
    }

    private int getAbsoluteSize() {
        return absoluteSize;
    }

    private float getRelativeSize() {
        return relativeSize;
    }

    private void setAbsoluteSize(int absoluteSize) {
        this.absoluteSize = absoluteSize;
    }

    public void setMinSize(float relativeSize) {
        this.relativeSize = relativeSize;
        setAbsoluteSize(0);
    }

    private void updateAbsoluteFaceSize(int height) {
        if (getAbsoluteSize() == 0) {
            if (Math.round(height * getRelativeSize()) > 0) {
                setAbsoluteSize(Math.round(height * getRelativeSize()));
            }
        }
    }

    protected String generateTrainingModelName() {
        return "detector_" + System.currentTimeMillis() + "_" + getTrainingRawModel();
    }


}
