package com.digitify.identityscanner.modules.docscanner.utils;

import android.content.Context;
import android.graphics.Bitmap;
import com.digitify.identityscanner.core.arch.MainHandler;
import com.digitify.identityscanner.core.arch.WorkerHandler;
import com.digitify.identityscanner.core.ocr.Tesseract;

import java.util.Arrays;

public class OrcUtils {
    public interface OnOCRCompleteListener {
        void onTextRecognised(String text);

        void onTextRecognitionFailed(String error);
    }

    public static void runTextRecognition(Context context, Bitmap bitmap, final OnOCRCompleteListener onOCRCompleteListener) {
        try {
            WorkerHandler.get("ocr").post(() -> {
                Tesseract tess = new Tesseract(context);
                String ocrText = tess.getOCRResult(bitmap);
                MainHandler.get().post(()-> onOCRCompleteListener.onTextRecognised(ocrText));
            });
        } catch (Exception e) {
            onOCRCompleteListener.onTextRecognitionFailed(e.getMessage());
        }
    }

//    public static void runTextRecognitionFirebase(Bitmap bitmap, final OnOCRCompleteListener onOCRCompleteListener) {
//
//        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
//        FirebaseVisionTextRecognizer recognizer = FirebaseVision.getInstance().getOnDeviceTextRecognizer();
//        recognizer.processImage(image)
//                .addOnSuccessListener(
//                        texts -> {
//                            String text = processTextRecognitionResult(texts);
//                            onOCRCompleteListener.onTextRecognised(text);
//                        })
//                .addOnFailureListener(
//                        e -> {
//                            // Task failed with an exception
//                            e.printStackTrace();
//                            onOCRCompleteListener.onTextRecognitionFailed(e.getLocalizedMessage());
//                        });
//    }

//    private static String processTextRecognitionResult(FirebaseVisionText texts) {
//        List<FirebaseVisionText.TextBlock> blocks = texts.getTextBlocks();
//        if (blocks.size() == 0) {
//            return "";
//        }
//
//        StringBuilder builder = new StringBuilder();
//        for (int i = 0; i < blocks.size(); i++) {
//            List<FirebaseVisionText.Line> lines = blocks.get(i).getLines();
//            for (int j = 0; j < lines.size(); j++) {
//                List<FirebaseVisionText.Element> elements = lines.get(j).getElements();
//                for (int k = 0; k < elements.size(); k++) {
//                    FirebaseVisionText.Element element = elements.get(k);
//                    builder.append(element.getText());
//                    builder.append(" ");
//                }
//                builder.append("\n");
//            }
//        }
//
//        return builder.toString();
//    }

    public static String formatForMrz(String text) {
        final int EID_CHAR_LENGTH = 30;
        final int PASSPORT_CHAR_LENGTH = 44;
        int minCol = EID_CHAR_LENGTH;

        StringBuilder builder = new StringBuilder();
        String[] lines = text.split("\n");
        // Remove spaces and adjust lines
        for (String line : lines) {
            builder.append(line.trim().replaceAll("\\s+", ""));
            builder.append("\n");
        }
        if (builder.toString().endsWith("\n")) {
            builder.deleteCharAt(builder.length() - 1);
        }
        text = builder.toString();

        // Remove spaces and adjust lines
        String[] refinedLines = text.split("\n");
        builder = new StringBuilder();

        if (refinedLines.length == 2) {
            minCol = PASSPORT_CHAR_LENGTH;
        }

        for (String line : refinedLines) {
            int cols = line.length();
            if (cols < minCol) {
                // find filler <<< symbol
                int fillerInd = line.lastIndexOf('<');
                if (fillerInd < 0) fillerInd = line.indexOf('\n') - 1;
                if (fillerInd < 0) fillerInd = line.length();
                int extraFillers = minCol - cols;
                char[] fillers = getFillers(extraFillers);
                StringBuilder b = new StringBuilder(line);
                b.insert(fillerInd, fillers, 0, fillers.length);
                builder.append(b.toString());
            } else {
                builder.append(line);
            }
            builder.append("\n");
        }
        if (builder.toString().endsWith("\n")) {
            builder.deleteCharAt(builder.length() - 1);
        }
        text = builder.toString();

        return text;
    }

    private static char[] getFillers(int size) {
        char[] fillers = new char[size];
        Arrays.fill(fillers, '<');
        return fillers;
    }
}
