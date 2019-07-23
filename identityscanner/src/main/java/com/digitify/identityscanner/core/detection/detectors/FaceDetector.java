package com.digitify.identityscanner.core.detection.detectors;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.digitify.identityscanner.R;
import com.digitify.identityscanner.core.detection.enums.FaceExpression;
import com.digitify.identityscanner.core.detection.interfaces.IExpressionsAnalyser;
import com.digitify.identityscanner.core.detection.models.Face;
import com.digitify.identityscanner.core.detection.utils.OpenCVUtils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.face.Facemark;
import org.opencv.face.FacemarkLBF;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Detects face using a pre trained model using OpenCV's CascadeClassifier
 */
public class FaceDetector extends CascadeDetector<Face> implements IExpressionsAnalyser<Face> {
    private Facemark facemark;
    private boolean classificationEnabled = false;

    public FaceDetector(Context context) {
        this(context, false);
    }

    public FaceDetector(Context context, boolean classificationEnabled) {
        super(context);
        this.classificationEnabled = classificationEnabled;
        if (classificationEnabled) loadAnalyser(context);
    }

    @Override
    protected int getTrainingRawModel() {
        return R.raw.lbpcascade_frontalface_improved;
    }

    @Nullable
    @Override
    public Face detect(Bitmap src) {
        List<Face> faces = detectAll(src);
        if (faces.size() > 0) return faces.get(0);
        return null;
    }

    @Override
    public List<Face> detectAll(Bitmap src) {
        return detectAll(OpenCVUtils.convertToMat(src));
    }

    @Nullable
    @Override
    public Face detect(@NonNull Mat src) {
        List<Face> faces = detectAll(src);
        if (faces.size() > 0) return faces.get(0);
        return null;
    }

    @Override
    public List<Face> detectAll(Mat src) {
        List<Face> faces = new ArrayList<>();
        Mat tmp = src.clone();
        MatOfRect faceMats = findCascade(tmp);
        List<Rect> faceRects = faceMats.toList();

        for (Rect rect : faceRects) {
            Rect improvedRect = adjustPad(src, rect);
            Mat output = new Mat();
            try {
                Mat croppedFace = OpenCVUtils.cropAdjusted(src, improvedRect);
                if (croppedFace != null) {
                    croppedFace.copyTo(output);
                    croppedFace.release();
                }
                faces.add(new Face(output, improvedRect));
            } catch (Exception e) {
                Log.e("Exception", e.getMessage());
            }
        }

        if (isClassificationEnabled() && faces.size() > 0) {
            // analyse only 1 face for better performance
            analyse(faces.get(0));
        }

        tmp.release();
        return faces;
    }

    private Rect adjustPad(Mat src, Rect rect) {
        int pad = 40;
        int x = rect.x - pad;
        int y = rect.y - pad;
        int w = rect.width + (pad * 2);
        int h = rect.height + (pad * 2);
        double[] vals = {x, y, w, h};
        rect.set(vals);
        return OpenCVUtils.adjustRectInImg(src, rect);
    }

    @Override
    public void analyse(Face face) {
        if (validate(face)) {
            analyseUsingLandmarks(face);
        }
    }

    private void analyseUsingLandmarks(Face face) {
        ArrayList<MatOfPoint2f> landmarks = findFaceMarks(face);
        if (landmarks.size() > 0) {
            // probably there should be only 1 object in the list as 1 face was given as input.
            MatOfPoint2f lm = landmarks.get(0);
            face.setLandmarks(lm);
        }
    }

    private ArrayList<MatOfPoint2f> findFaceMarks(Face face) {
        // fit landmarks for each found face
        ArrayList<MatOfPoint2f> landmarks = new ArrayList<>();
        Rect rect = new Rect(0, 0, face.getDetection().width(), face.getDetection().height());
        facemark.fit(face.getDetection(), OpenCVUtils.toMatOfRect(rect), landmarks);

        return landmarks;
    }

    protected void loadAnalyser(Context context) {
        File file = loadAnalysingDataFile(context, "lbfmodel.yaml", R.raw.lbfmodel);
        facemark = org.opencv.face.Face.createFacemarkLBF();
        facemark.loadModel(file.getAbsolutePath());
    }

    @Nullable
    protected final File loadAnalysingDataFile(@NotNull Context context, String name, int raw) {
        try {
            // load cascade file from application resources
            File cascadeDir = context.getDir(CASCADE_DIR, Context.MODE_PRIVATE);
            File mCascadeFile = new File(cascadeDir, name);
            if (!mCascadeFile.exists()) {

                FileOutputStream os = new FileOutputStream(mCascadeFile);
                InputStream is = context.getResources().openRawResource(raw);

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                is.close();
                os.close();
            }
            return mCascadeFile;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Failed to load cascade. Exception thrown: " + e);
        }
        return null;
    }

    @Contract(pure = true)
    private boolean isClassificationEnabled() {
        return classificationEnabled;
    }

    public void allowClassification(boolean allowed) {
        this.classificationEnabled = allowed;
    }

    @Override
    public boolean isRightEyeOpen(Face face) {
        return FaceExpression.EYE_OPEN.qualify(face.getRightEyeOpenProbability());
    }

    @Override
    public boolean isLeftEyeOpen(Face face) {
        return FaceExpression.EYE_OPEN.qualify(face.getLeftEyeOpenProbability());
    }

    @Override
    public boolean isSmiling(Face face) {
        float smileProb = face.getSmileProbability();
        float eyeProb = face.getRightEyeOpenProbability();
        return ((FaceExpression.SMILE.qualify(smileProb) || FaceExpression.LAUGH.qualify(smileProb))
                && (FaceExpression.EYE_CLOSE.qualify(eyeProb) || FaceExpression.EYE_HALF_OPEN.qualify(eyeProb))
        );
    }

}
