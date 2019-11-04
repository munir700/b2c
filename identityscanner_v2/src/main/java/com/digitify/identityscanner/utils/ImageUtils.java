package com.digitify.identityscanner.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.exifinterface.media.ExifInterface;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static android.graphics.BitmapFactory.decodeFile;
import static android.graphics.BitmapFactory.decodeStream;

public class ImageUtils extends FileUtils {

    public static Bitmap getBitmapFromAsset(Context context, String filePath) {
        AssetManager assetManager = context.getAssets();

        InputStream is;
        Bitmap bitmap = null;
        try {
            is = assetManager.open(filePath);
            bitmap = decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static Bitmap getBitmapFromStorage(String filePath) {
        return decodeFile(filePath);
    }

    public static String getPathFromContentUri(Context context, Uri uri) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = context.getContentResolver().query(uri,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }

    public static Bitmap byteArrayToBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    @Nullable
    public static String savePicture(Context context, byte[] frame) {
        try {
            File savedPhoto = getFilePrivately(context);
            FileOutputStream outputStream = new FileOutputStream(savedPhoto.getPath());
            outputStream.write(frame);
            outputStream.close();
            return savedPhoto.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public static String savePicture(Context context, Bitmap bitmap) {
        try {
            File savedPhoto = getFilePrivately(context);
            FileOutputStream outputStream = new FileOutputStream(savedPhoto);
            bitmap.compress(Bitmap.CompressFormat.PNG, 85, outputStream);
            outputStream.flush();
            outputStream.close();
            return savedPhoto.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public static String savePicture(Context context, Bitmap bitmap, String filename) {
        try {
            File savedPhoto = getFilePrivately(context, filename);
            FileOutputStream outputStream = new FileOutputStream(savedPhoto);
            bitmap.compress(Bitmap.CompressFormat.PNG, 85, outputStream);
            outputStream.flush();
            outputStream.close();
            return savedPhoto.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public static String savePicture(File file, Bitmap bitmap) {
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 85, outputStream);
            outputStream.flush();
            outputStream.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public static File getFilePrivately(Context context) {
        return getFilePrivately(context, "photo" + System.currentTimeMillis() + ".jpg");
    }

    @Nullable
    public static File getFilePrivately(Context context, String name) {
        File savedPhoto = new File(context.getFilesDir(), name);
        try {
            if (savedPhoto.exists()) {
                savedPhoto.delete();
            }
            if (!savedPhoto.exists()) {
                savedPhoto.createNewFile();
            }
            return savedPhoto;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public static File getFilePublically(String name) {
        String baseFolder = Environment.getExternalStorageDirectory().getAbsolutePath();
        File savedPhoto = new File(baseFolder + File.separator + name);

        try {
            if (savedPhoto.exists()) {
                savedPhoto.delete();
            }
            if (!savedPhoto.exists()) {
                savedPhoto.createNewFile();
            }
            return savedPhoto;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public static File getFilePublically(String folder, String file) {
        File dirs = new File(folder);
        if (!dirs.exists()) {
            dirs.mkdirs();
        }

        File savedPhoto = new File(dirs, File.separator + file);
        try {
            if (savedPhoto.exists()) {
                savedPhoto.delete();
            }
            if (!savedPhoto.exists()) {
                savedPhoto.createNewFile();
            }
            return savedPhoto;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Bitmap downScaleBitmap(Bitmap original, float scaleFactor) {
        Bitmap resizedBitmap =
                Bitmap.createScaledBitmap(
                        original,
                        (int) (original.getWidth() / scaleFactor),
                        (int) (original.getHeight() / scaleFactor),
                        true);
        return resizedBitmap;
    }

    // Null: got OOM
    // TODO ignores flipping. but it should be super rare.
    @Nullable
    public static Bitmap decodeBitmap(@NonNull byte[] source, int maxWidth, int maxHeight, @NonNull BitmapFactory.Options options, int rotation) {
        if (maxWidth <= 0) maxWidth = Integer.MAX_VALUE;
        if (maxHeight <= 0) maxHeight = Integer.MAX_VALUE;
        int orientation;
        boolean flip;
        if (rotation == -1) {
            InputStream stream = null;
            try {
                // http://sylvana.net/jpegcrop/exif_orientation.html
                stream = new ByteArrayInputStream(source);
                ExifInterface exif = new ExifInterface(stream);
                int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                orientation = readExifOrientation(exifOrientation);
                flip = exifOrientation == ExifInterface.ORIENTATION_FLIP_HORIZONTAL ||
                        exifOrientation == ExifInterface.ORIENTATION_FLIP_VERTICAL ||
                        exifOrientation == ExifInterface.ORIENTATION_TRANSPOSE ||
                        exifOrientation == ExifInterface.ORIENTATION_TRANSVERSE;

            } catch (IOException e) {
                e.printStackTrace();
                orientation = 0;
                flip = false;
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (Exception ignored) {
                    }
                }
            }
        } else {
            orientation = rotation;
            flip = false;
        }

        Bitmap bitmap;
        try {
            if (maxWidth < Integer.MAX_VALUE || maxHeight < Integer.MAX_VALUE) {
                options.inJustDecodeBounds = true;
                // BitmapFactory.decodeByteArray(source, 0, source.length, options);

                int outHeight = options.outHeight;
                int outWidth = options.outWidth;
                if (orientation % 180 != 0) {
                    //noinspection SuspiciousNameCombination
                    outHeight = options.outWidth;
                    //noinspection SuspiciousNameCombination
                    outWidth = options.outHeight;
                }

                options.inSampleSize = computeSampleSize(outWidth, outHeight, maxWidth, maxHeight);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeByteArray(source, 0, source.length, options);
            } else {
                bitmap = BitmapFactory.decodeByteArray(source, 0, source.length);
            }

            if (orientation != 0 || flip) {
                Matrix matrix = new Matrix();
                matrix.setRotate(orientation);
                Bitmap temp = bitmap;
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                temp.recycle();
            }
        } catch (OutOfMemoryError e) {
            bitmap = null;
        }
        return bitmap;
    }

    static int readExifOrientation(int exifOrientation) {
        int orientation;
        switch (exifOrientation) {
            case ExifInterface.ORIENTATION_NORMAL:
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                orientation = 0;
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                orientation = 180;
                break;

            case ExifInterface.ORIENTATION_ROTATE_90:
            case ExifInterface.ORIENTATION_TRANSPOSE:
                orientation = 90;
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
            case ExifInterface.ORIENTATION_TRANSVERSE:
                orientation = 270;
                break;

            default:
                orientation = 0;
        }
        return orientation;
    }


    private static int computeSampleSize(int width, int height, int maxWidth, int maxHeight) {
        // https://developer.android.com/topic/performance/graphics/load-bitmap.html
        int inSampleSize = 1;
        if (height > maxHeight || width > maxWidth) {
            while ((height / inSampleSize) >= maxHeight
                    || (width / inSampleSize) >= maxWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static String getPath(Context context, Uri uri) {
        String path = "";
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        int column_index;
        if (cursor != null) {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index);
            cursor.close();
        }
        return path;
    }


    public static Bitmap resizePhoto(File imageFile, Context context, Uri uri, ImageView view) {
        BitmapFactory.Options newOptions = new BitmapFactory.Options();
        try {
            decodeStream(context.getContentResolver().openInputStream(uri), null, newOptions);
            int photoHeight = newOptions.outHeight;
            int photoWidth = newOptions.outWidth;

            newOptions.inSampleSize = Math.min(photoWidth / view.getWidth(), photoHeight / view.getHeight());
            return compressPhoto(imageFile, decodeStream(context.getContentResolver().openInputStream(uri), null, newOptions));
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static Bitmap resizePhoto(File imageFile, String path, ImageView view) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        decodeFile(path, options);
        int photoHeight = options.outHeight;
        int photoWidth = options.outWidth;
        options.inSampleSize = Math.min(photoWidth / view.getWidth(), photoHeight / view.getHeight());
        return compressPhoto(imageFile, decodeFile(path, options));
    }

    private static Bitmap compressPhoto(File photoFile, Bitmap bitmap) {
        try {
            FileOutputStream fOutput = new FileOutputStream(photoFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, fOutput);
            fOutput.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return bitmap;
    }

    /**
     * Converts bitmap to byte array in PNG format
     *
     * @param bitmap source bitmap
     * @return result byte array
     */
    public static byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            return baos.toByteArray();
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Converts bitmap to the byte array without compression
     *
     * @param bitmap source bitmap
     * @return result byte array
     */
    public static byte[] convertBitmapToByteArrayUncompressed(Bitmap bitmap) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(bitmap.getByteCount());
        bitmap.copyPixelsToBuffer(byteBuffer);
        byteBuffer.rewind();
        return byteBuffer.array();
    }

    /**
     * Converts compressed byte array to bitmap
     *
     * @param src source array
     * @return result bitmap
     */
    public static Bitmap convertCompressedByteArrayToBitmap(byte[] src) {
        return BitmapFactory.decodeByteArray(src, 0, src.length);
    }
}