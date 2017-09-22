package com.hackathon.appsoul.mombabycare.activity;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hackathon.appsoul.mombabycare.activity.DoctorHelpFragment.ViewHolder;
import com.hackathon.appsoul.mombabycare.model.Event;

import java.io.File;
import java.io.InputStream;

/**
 * Methods for setting the content of an ImageView. The image is pre-scaled before
 * loading it. This prevents exceeding available heap size when trying to load large
 * photo images.
 */

public class ImageLoader {
    private ImageView m_photoView;
    private String m_photo;
    private Integer m_id;
    private Boolean m_quality = false;
    private Event.Type m_type;
    private Context m_context;
    private TextView m_divider = null;

    public ImageLoader(String photo, ImageView photoView, Context context, Event.Type type) {
        m_photoView = photoView;
        m_photo = photo;
        m_type = type;
        m_context = context;

        BitmapCache.InitBitmapCache();
    }

    public ImageLoader(Event[] events) {
        BitmapCache.InitBitmapCache();
    }

    public void setDivider(TextView divider) {
        m_divider = divider;
    }

    public void setQuality(Boolean quality) {
        m_quality = quality;
    }

    public void loadBitmap(Integer id, Integer position, ViewHolder holder) {
        m_id = id;

        Bitmap bitmap = null;

        if (m_id != null) bitmap = BitmapCache.getBitmapFromMemCache(m_id);

        if (bitmap != null) {
            //Log.d("bitmap retrieved from cache: ", Integer.toString(m_id));
            m_photoView.setVisibility(View.VISIBLE);
            m_photoView.setImageBitmap(bitmap);
            if (m_divider!=null) m_divider.setVisibility(View.VISIBLE);
        } else {
            if (m_photo != null && !m_photo.equals("")) {
                new ImageLoaderTask(position, holder).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
            }
        }
    }



    private class ImageLoaderTask extends AsyncTask<Integer, String, Bitmap> {
        Context context;
        String photo;
        Event.Type type;
        Integer id;
        Boolean quality;

        Integer t_position;
        ViewHolder t_holder;

        public ImageLoaderTask(int position, ViewHolder holder) {
            t_position = position;
            t_holder = holder;
        }

        private Bitmap decodeSampledBitmapFromAssets(int reqWidth, int reqHeight) {
            Bitmap bitmap;
            AssetManager assetManager = context.getAssets();
            InputStream in;

            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;

                in = assetManager.open(photo);

                BitmapFactory.decodeStream(in, null, options);

                in.close();

                BitmapFactory.Options options1 = new BitmapFactory.Options();
                options1.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

                in = assetManager.open(photo);

                bitmap = BitmapFactory.decodeStream(in, null, options1);

                in.close();

                return bitmap;
            } catch (Exception e) {
                return null;
            }
        }
        /**
         * Scales image to fix out of memory crash
         */
        private Bitmap decodeSampledBitmapFromPath(String path, int reqWidth, int reqHeight) {

            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);

            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            Bitmap bm = BitmapFactory.decodeFile(path, options);
            Bitmap bitmap;
            try {
                Matrix m = new Matrix();
                ExifInterface exif = new ExifInterface(path);

                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);

                if ((orientation == ExifInterface.ORIENTATION_ROTATE_180)) {
                    m.postRotate(180);
                } else if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                    m.postRotate(90);
                }
                else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                    m.postRotate(270);
                }

                Log.d("in orientation", "" + orientation);
                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),bm.getHeight(), m, true);

                return bitmap;
            } catch (Exception e) {
                return null;
            }
        }

        private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;

            if (height > reqHeight || width > reqWidth) {
                if (width > height) {
                    inSampleSize = Math.round((float) height / (float) reqHeight);
                } else {
                    inSampleSize = Math.round((float) width / (float) reqWidth);
                }
            }
            return inSampleSize;
        }

        @Override
        protected void onPreExecute()
        {
            context = m_context;
            photo = m_photo;
            type = m_type;
            id = m_id;
            quality = m_quality;
        }

        @Override
        protected Bitmap doInBackground(Integer... param) {
            Bitmap bitmap = null;

            Integer a,b;

            if (quality==true) {
                a = 1000;
                b = 1000;
            } else {
                a = 200;
                b = 200;
            }

            try {
                if (type.equals(Event.Type.DOCTOR)){
                    bitmap = decodeSampledBitmapFromAssets(a,b);
                } else {
                    File file = new File(photo);
                    Log.d("file: ",file.getAbsolutePath());
                    bitmap = decodeSampledBitmapFromPath(file.getAbsolutePath(),a,b);
                }

                if (id != null) BitmapCache.addBitmapToMemoryCache(id, bitmap);

                return bitmap;
            } catch (Exception e) {
                Log.e(DashBoardMomActivity.DEBUG_TAG,e.toString());

                return null;
            }
        }

        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                if (t_holder == null) {
                    m_photoView.setVisibility(View.VISIBLE);
                    m_photoView.setImageBitmap(bitmap);
                    if (m_divider!=null) m_divider.setVisibility(View.VISIBLE);
                } else {
                    if (t_holder.position == t_position) {
                        if (t_holder.picture!=null) {
                            t_holder.picture.setVisibility(View.VISIBLE);
                            t_holder.picture.setImageBitmap(bitmap);
                        }
                        if (t_holder.text!=null) t_holder.text.setVisibility(View.VISIBLE);
                    }
                }
            } else {
                Log.e("ImageLoaderTask", "failed to load image");
            }
        }
    }
}
