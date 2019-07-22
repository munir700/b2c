package com.digitify.identityscanner.core.recognition.recognisers;

import android.content.Context;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognitionClient;
import com.amazonaws.services.rekognition.model.CompareFacesMatch;
import com.amazonaws.services.rekognition.model.CompareFacesRequest;
import com.amazonaws.services.rekognition.model.CompareFacesResult;
import com.amazonaws.services.rekognition.model.Image;
import com.digitify.identityscanner.core.detection.models.Face;
import com.digitify.identityscanner.core.recognition.interfaces.Matcher;
import com.digitify.identityscanner.core.recognition.interfaces.Recogniser;
import com.digitify.identityscanner.core.recognition.models.FaceMatchResult;
import com.digitify.identityscanner.core.recognition.models.FaceRecognitionResult;
import com.digitify.identityscanner.utils.ImageUtils;

import java.nio.ByteBuffer;
import java.util.List;

public class FaceRecogniser implements Matcher<Face, FaceMatchResult>, Recogniser<Face, FaceRecognitionResult> {
    public static String TAG = FaceRecogniser.class.getSimpleName();
    private AmazonRekognitionClient recogniser;

    public FaceRecogniser(Context context) {
        ClientConfiguration configuration = new ClientConfiguration();
        configuration.setMaxErrorRetry(3);
        configuration.setConnectionTimeout(30 * 1000);
        configuration.setSocketTimeout(30 * 1000);

        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                context,
                "eu-west-1:b9ef69b0-31a4-42ff-b53f-128e1871e646",
                Regions.EU_WEST_1
        );

        recogniser = new AmazonRekognitionClient(credentialsProvider, configuration);
    }

    @Override
    public FaceMatchResult match(Face source, Face target) {
        byte[] sba = ImageUtils.convertBitmapToByteArray(source.getBitmap());
        byte[] tba = ImageUtils.convertBitmapToByteArray(target.getBitmap());
        Image sourceImg = new Image().withBytes(ByteBuffer.wrap(sba));
        Image targetImg = new Image().withBytes(ByteBuffer.wrap(tba));

        CompareFacesRequest request = new CompareFacesRequest()
                .withSourceImage(sourceImg)
                .withTargetImage(targetImg)
                .withSimilarityThreshold(70F);


        FaceMatchResult result = new FaceMatchResult(false, 50); // unpredicted
        result.setSource(source);
        result.setTarget(target);
        try {
            // Call operation
            CompareFacesResult compareFacesResult = getRecogniser().compareFaces(request);
            // Display results
            List<CompareFacesMatch> faceDetails = compareFacesResult.getFaceMatches();
            if (faceDetails.size() > 0) {
                // we found a few matches, get the first one.
                CompareFacesMatch match = faceDetails.get(0);
                result.setMatched(true);
                result.setConfidence(match.getSimilarity());
                return result;
            }

            if (compareFacesResult.getUnmatchedFaces().size() > 0) {
                // the face was not matched
                result.setMatched(false);
                result.setConfidence(100);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setMatched(false);
            result.setConfidence(100);
        }
        return result;
    }

    @Override
    public FaceRecognitionResult recognise(Face source) {
        throw new UnsupportedOperationException("Recognising is not yet supported");
    }

    private AmazonRekognitionClient getRecogniser() {
        return recogniser;
    }
}
