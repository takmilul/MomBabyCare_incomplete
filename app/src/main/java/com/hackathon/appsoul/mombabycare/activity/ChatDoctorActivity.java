package com.hackathon.appsoul.mombabycare.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.media.ExifInterface;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hackathon.appsoul.mombabycare.R;
import com.hackathon.appsoul.mombabycare.datastorage.DataBaseHelper;
import com.hackathon.appsoul.mombabycare.datastorage.DataSource;
import com.hackathon.appsoul.mombabycare.model.Event;


import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class ChatDoctorActivity extends AppCompatActivity implements AudioPlayer.PlayerClient {
    // Audio file name format
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH);
    
    private static SimpleDateFormat m_dateFormat = new SimpleDateFormat("MMMMMMMMM d, yyyy", Locale.ENGLISH);
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String DELETED = "deleted";
    private static final String WARNING = "Please enter a note, a picture or a recording";
    
    // Keys for saved state
    private static final String KEY_AUDIO_STATE 	= "audioState";
    private static final String KEY_AUDIO_FILE_NAME = "audioFileName";
    
    private static final String AnimationDrawable = null;
    private String m_mode;
    private String m_warning;
    private Integer m_result = 0;
    
    private static MediaRecorder recorder;
    
    
    private LinearLayout 			layout;
    private Event m_event;
    private Calendar m_date;
    
    private static boolean isAudioRecorded;
    private static boolean isImageCaptured;
    
    
    private DataBaseHelper helper;
    private DataSource dataSource;
    
    
    private AudioPlayer				audioPlayer;
    private File					audioFile;
    private String m_audioFilePath;
    private ViewGroup				areaNone;
    private ViewGroup				areaRecording;
    private ViewGroup				areaFinished;
    
    private static CustomProgressDialog progressDialog = null;
    //private ActionBar m_actionBar;
    
    private enum AudioState {
        NONE,		// No recording or playback underway
        RECORDING,	// Recording
        FINISHED,	// Finished recording
    }
    private AudioState audioState;
    
    private ImageButton				start;
    private ImageButton				delete;
    private Button					done;
    private Button					cancel;
    private TextView                timerText;
    
    EditText m_dateView;
    EditText m_noteView;
    private TextView m_warnView;
    
    ImageButton m_buttonDelete;
    ImageButton m_buttonCreate;
    TextView m_photoText;
    ImageView m_photoView;
    String m_currentPhotoPath;
    
    String currentFile = "";
    static boolean uploadResult = false;
    
    static ChatDoctorActivity activity = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater();
        layout = (LinearLayout)inflater.inflate(R.layout.activity_chat_doctor, null);
        setContentView(layout);
        
        m_dateView = (EditText)findViewById(R.id.diary_date);
        m_noteView = (EditText)findViewById(R.id.diary_notes);
        m_warnView = (TextView) findViewById(R.id.diary_warning);
        m_buttonDelete = (ImageButton)findViewById(R.id.diary_delete);
        m_buttonCreate = (ImageButton)findViewById(R.id.diary_create);
        m_photoView = (ImageView) findViewById(R.id.diary_image);
        m_photoText = (TextView) findViewById(R.id.diary_create_text);
        
        
        activity  = this;
        //isAudioRecorded = false;
        //isImageCaptured = false;
        progressDialog = new CustomProgressDialog(this);
        dataSource=new DataSource(this);
        
        
        
        m_mode = getIntent().getStringExtra(DoctorHelpFragment.REQUEST_MODE);
        m_date = Calendar.getInstance();
//        m_actionBar = getActionBar();
//        String activityTitle;
        if(m_mode.equals(DoctorHelpFragment.REQUEST_MODE_EDIT)) {
//            activityTitle = getString(R.string.Diary_Edit);
//
//            m_actionBar.setTitle(activityTitle);
//            m_actionBar.setHomeButtonEnabled(true);
            
            m_event = getIntent().getParcelableExtra("event");
            m_date.setTime(m_event.getDate());
            
            
        } else if (m_mode.equals(DoctorHelpFragment.REQUEST_MODE_NEW)){
//            activityTitle = getString(R.string.Diary_New);

//            m_actionBar.setTitle(activityTitle);
//            m_actionBar.setHomeButtonEnabled(true);
            
            m_event = new Event();
            m_event.setDate(m_date.getTime());
            m_event.setType(Event.Type.USER);
        }
        
        
        
        
        if (m_dateView != null) {
            String strDate = m_dateFormat.format(m_event.getDate());
            m_dateView.setText(strDate);
        }
        
        if (m_noteView != null) {
            if (m_event.getText() != null && (m_event.getText().length() > 0)) {
                m_noteView.setText(m_event.getText());
            }
        }
        
        if (m_buttonCreate != null) {
            m_buttonCreate.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    DispatchTakePictureIntent();
                }
            });
        }
        
        if (m_photoText != null) {
            m_photoText.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    DispatchTakePictureIntent();
                }
            });
        }
        
        if (m_buttonDelete != null) {
            m_buttonDelete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //TODO deletes previous Image File
                    if(m_event.getPhotoFile() != null)
                        deleteMediaFile(m_event.getPhotoFile());
                    
                    m_event.setPhotoFile(null);
                    m_currentPhotoPath = null;
                    
                    if (setPhoto()) { //refreshes view
                        setContentView(layout);
                    }
                }
            });
        }
        
        if (setPhoto()) {
            setContentView(layout);
        }
        
        audioPlayer = (AudioPlayer)getLastCustomNonConfigurationInstance();
        
        setUpAudio(savedInstanceState);
        
        
        
    }
    
    public void datePicker(View view) {
        DatePickerFragment datePickerFragment = new DatePickerFragment(m_dateView);
        datePickerFragment.show(getFragmentManager(), "date");
    }
    
    
    /**
     * Dispatches picture intent
     */
    private void DispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = CreateImageFile();
            } catch (IOException e) {
                // Error occurred while creating the File
                Log.d("ChatDoctorActivity", "error creating photo file", e);
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }
    
    /**
     * Creates Image File and Sets Path
     */
    private File CreateImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageFileName = "image_" + timeStamp + ".jpg";
        
        File image = new File(this.getExternalFilesDir(null), imageFileName);
        m_currentPhotoPath = image.getAbsolutePath();
        
        return image;
    }
    
    /**
     * Take picture result
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (m_currentPhotoPath != null && (m_currentPhotoPath.length() > 0)) {
                m_event.setPhotoFile(m_currentPhotoPath);
                isImageCaptured = true;
            }
            
            if (setPhoto()) {
                setContentView(layout);
            }
            
        } else if (resultCode == RESULT_CANCELED) {
        }
    }
    
    /**
     * Sets photo or add photo icon
     */
    private boolean setPhoto() {
        if (m_event.getPhotoFile() != null && (m_event.getPhotoFile().length() > 0)) {
            String photoFile = m_event.getPhotoFile();
            
            m_photoView.setVisibility(View.VISIBLE);
            m_buttonDelete.setVisibility(View.VISIBLE);
            
            m_buttonCreate.setVisibility(View.GONE);
            m_photoText.setVisibility(View.GONE);
            
            if (photoFile != null && (photoFile.length()>0)) {
                File file = new File(photoFile);
                Bitmap bitmap = decodeSampledBitmapFromPath(file.getAbsolutePath(),1000,1000);
                
                m_photoView.setImageBitmap(bitmap);
                
                return true;
            } else {
                Toast.makeText(getBaseContext(),"stored photo file does not exist", Toast.LENGTH_LONG).show();
            }
        } else {
            m_photoView.setVisibility(View.GONE);
            m_buttonDelete.setVisibility(View.GONE);
            
            m_buttonCreate.setVisibility(View.VISIBLE);
            m_photoText.setVisibility(View.VISIBLE);
            
            return true;
        }
        
        return false;
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
    
    private void setUpAudio(Bundle savedInstanceState){
        // The three mutually exclusive views corresponding to the recording state
        areaNone = (ViewGroup)findViewById(R.id.audio_area_none);
        areaRecording = (ViewGroup)findViewById(R.id.audio_area_recording);
        areaFinished = (ViewGroup)findViewById(R.id.audio_area_finished);
        
        if (savedInstanceState == null){
            m_audioFilePath = m_event.getAudioFile();
            if (m_audioFilePath != null && m_audioFilePath.length() > 0){
                setState(AudioState.FINISHED);
            }
            else {
                setState(AudioState.NONE);
            }
        }
        else {
            setState(AudioState.valueOf(savedInstanceState.getString(KEY_AUDIO_STATE)));
            if (audioState.equals(AudioState.FINISHED)){
                if (audioPlayer != null){
                    audioPlayer.restore(this, areaFinished);
                }
            }
            if (savedInstanceState.containsKey(KEY_AUDIO_FILE_NAME)){
                m_audioFilePath = savedInstanceState.getString(KEY_AUDIO_FILE_NAME);
            }
        }
        setupAudioButtons();
    }
    
    // Set recording state and show corresponding view. Disable other UI elements when recording
    private void setState(AudioState newState){
        audioState = newState;
        
        areaNone.setVisibility(audioState.equals(AudioState.NONE) ? View.VISIBLE : View.GONE);
        areaRecording.setVisibility(audioState.equals(AudioState.RECORDING) ? View.VISIBLE : View.GONE);
        areaFinished.setVisibility(audioState.equals(AudioState.FINISHED) ? View.VISIBLE : View.GONE);
        
        if (audioState.equals(AudioState.RECORDING)){
            findViewById(R.id.diary_date).setEnabled(false);
            findViewById(R.id.diary_notes).setEnabled(false);
            findViewById(R.id.diary_delete).setEnabled(false);
            findViewById(R.id.diary_create).setEnabled(false);
            findViewById(R.id.diary_create_text).setEnabled(false);
            ImageView recordingInProgress = (ImageView) findViewById(R.id.recording_in_progress);
            ((AnimationDrawable)recordingInProgress.getDrawable()).start();
        }
        else {
            findViewById(R.id.diary_date).setEnabled(true);
            findViewById(R.id.diary_notes).setEnabled(true);
            findViewById(R.id.diary_delete).setEnabled(true);
            findViewById(R.id.diary_create).setEnabled(true);
            findViewById(R.id.diary_create_text).setEnabled(true);
            if (audioState.equals(AudioState.FINISHED)){
                if (audioPlayer == null){
                    audioPlayer = new AudioPlayer(this, areaFinished, m_audioFilePath, this);
                }
            }
            else if (audioState.equals(AudioState.NONE)){
                m_audioFilePath = null;
            }
            ImageView recordingInProgress = (ImageView) findViewById(R.id.recording_in_progress);
            ((AnimationDrawable)recordingInProgress.getDrawable()).stop();
        }
    }
    
    private void setupAudioButtons(){
        start 			= (ImageButton)findViewById(R.id.audio_record_start);
        delete 			= (ImageButton)findViewById(R.id.audio_delete);
        done 			= (Button)findViewById(R.id.audio_record_done);
        cancel 			= (Button)findViewById(R.id.audio_record_cancel);
        timerText       = (TextView) findViewById(R.id.timer);
        
        // Start recording
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setState(AudioState.RECORDING);
                timerText.setText("00:00:00"); // Resets timer of the TextView
                startRecording();
            }
        });
        // Finish recording
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doneRecording();
            }
        });
        // Cancel recording
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recorder.stop();
                recorder.release();
                
                //cancels timer
                timer.cancel();
                minute = 0;
                hour = 0;
                second = 0;
                
                File temporaryFile = getTemporaryAudioFile();
                if (temporaryFile.exists()){
                    if (!temporaryFile.delete()){
                        Toast.makeText(getBaseContext(), "File deletion failed", Toast.LENGTH_LONG).show();
                        //Log.e(MainActivity.DEBUG_TAG, "********** Can't delete " + temporaryFile.getAbsolutePath());
                    }
                }
                setState(AudioState.NONE);
            }
        });
        // Delete recording
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Deletion can occur when player is playing or stopped
                audioPlayer.stop();
                audioPlayer = null;
                //TODO Deletes previous audio File
                if(m_event.getAudioFile() != null)
                    deleteMediaFile(m_event.getAudioFile());
                
                m_event.setAudioFile(null);
                
                m_audioFilePath = null;
                setState(AudioState.NONE);
            }
        });
    }
    
    //stop and saves audio recording
    private void doneRecording(){
        
        recorder.stop();
        recorder.release();
        
        //cancels timer
        timer.cancel();
        minute = 0;
        hour = 0;
        second = 0;
        
        audioFile = new File(getExternalFilesDir(null), "audio_" + formatter.format(new Date()) + ".amr");
        File temporaryFile = getTemporaryAudioFile();
        if (temporaryFile.exists()){
            if (temporaryFile.renameTo(audioFile)){
                m_audioFilePath = audioFile.getAbsolutePath();
            }
        }
        setState(AudioState.FINISHED);
        isAudioRecorded = true;
    }
    
    private void startRecording(){
        getTemporaryAudioFile();
        if (recorder != null){
            recorder.release();
        }
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        recorder.setOutputFile(getTemporaryAudioFile().getAbsolutePath());
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        
        
        try {
            recorder.prepare();
            //starts timer
            recordTime();
            
            recorder.start();
        } catch (IOException e){
            //Log.e(MainActivity.DEBUG_TAG, "Can't record audio", e);
        }
    }
    
    private int second = 0;
    private int minute = 0;
    private int hour = 0;
    private int maxTime = 5*60*1000; // maximum 5 minutes audio will be recorded
    
    private Timer timer;
    
    
    
    //checks whether recording exceeds maxTime or not
    boolean checkDuration(){
        int duration = hour*60*60 + minute*60 + second;
        return (duration>=maxTime);
    }
    
    private void recordTime() {
        TimerTask timerTask = new TimerTask() {
            
            Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    timerText.setText(String.format("%1$02d:%2$02d:%3$02d", hour, minute, second));
                    super.handleMessage(msg);
                }
            };
            
            @Override
            public void run() {
                second++;
                if (second >= 60) {
                    second = 0;
                    minute++;
                    if (minute >= 60) {
                        minute = 0;
                        hour++;
                    }
                }
                handler.sendEmptyMessage(1);
                
                if(checkDuration()){
                    doneRecording();
                }
            }
            
        };
        timer = new Timer();
        timer.schedule(timerTask, 1000, 1000);
    }
    
    private File getTemporaryAudioFile(){
        return new File(getExternalFilesDir(null), "$$$.mpg");
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.checkup_menu, menu);
        return true;
    }
    
    /**
     * Called when the Save menu item is pressed
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean warning = true;
        if (item.getItemId() == R.id.save) {
            String notes = m_noteView.getText().toString();
            
            try {
                
                m_date.setTime(m_dateFormat.parse(m_dateView.getText().toString()));
                
            } catch (ParseException e) {
                Toast.makeText(getApplicationContext(), "Date ParseException", Toast.LENGTH_LONG).show();
                m_date = null;
                //Log.e(BaseActivity.class.getName(), "Unable to parse date", e);
            }
            
            m_warning = WARNING;
            
            if (m_date != null) {
                if ((notes != null && (notes.length() != 0))){
                    warning = false;
                } else {
                    if (m_currentPhotoPath != null && (!m_currentPhotoPath.equals(DELETED))
                              && (m_currentPhotoPath.length() != 0)
                              || (m_audioFilePath != null)) {
                        notes = null; // if user does not input any text/note, show nothing
                        warning = false;
                    }
                }
            }
            
            if (warning) {
                // Remind user to enter information
                m_warnView.setText(m_warning);
            } else {
                //if (notes != null && (notes.length() != 0))
                m_event.setText(notes);
                if (m_date != null) m_event.setDate(m_date.getTime());
                
                if (saveEvent()) {
                    m_result = Activity.RESULT_OK;
                    closeActivity();
                }
            }
            
            
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    /**
     * Saves Event Object to database
     */
    private boolean saveEvent() {
        
        if(m_currentPhotoPath != null)
            m_event.setPhotoFile(m_currentPhotoPath);
        if(m_audioFilePath != null)
            m_event.setAudioFile(m_audioFilePath);
        
        if (m_mode.equals(DoctorHelpFragment.REQUEST_MODE_EDIT)) {
            
            //Updates Database
            boolean updated=dataSource.update(m_event.getId(), m_event);
            if (updated){
                Toast.makeText(getApplicationContext(),"Updated DB",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "DB Update Failed", Toast.LENGTH_LONG).show();
            }
        }  else if (m_mode.equals(DoctorHelpFragment.REQUEST_MODE_NEW)) {
            
            //Inserts into Database
            boolean inserted=dataSource.insert(m_event);
            if (inserted){
                Toast.makeText(getApplicationContext(),"Inserted into DB",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "Insertion into DB Failed", Toast.LENGTH_LONG).show();
            }
        }
        //TODO Uploads image and audio to the server
        if(m_currentPhotoPath != null){
            sendFile(m_currentPhotoPath);
        }
        
        if(m_audioFilePath != null){
            sendFile(m_audioFilePath);
        }
        
        
        //update cache
        if (m_event.getPhotoFile() != null && (m_event.getPhotoFile().length()>0)) {
            String path;
            path = m_event.getPhotoFile();
            File file = new File(path);
            Bitmap bitmap = decodeSampledBitmapFromPath(file.getAbsolutePath(),200,200);
            
            BitmapCache.InitBitmapCache();
            BitmapCache.addBitmapToMemoryCache(m_event.getId(), bitmap);
            
            return true;
        } else {
            Toast.makeText(getBaseContext(), "stored photo file does not exist", Toast.LENGTH_LONG).show();
        }
        return true;
    }
    
    static public boolean isNetworkConnected () {
        ConnectivityManager connMgr = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
    
    void sendFile(String path){
        currentFile = path;
        showProgressDialog(progressDialog,"Uploading...",true);
        Intent serviceIntent = new Intent(getBaseContext(), FTPService.class);
        serviceIntent.putExtra("path", path);
        serviceIntent.putExtra("task", "upload");
        startService(serviceIntent);

//        Intent serviceIntent = new Intent(getBaseContext(), FTPService.class);
//        serviceIntent.putExtra("path", path);
//        serviceIntent.putExtra("task", "download");
//        serviceIntent.putExtra("remote_file", );
//        startService(serviceIntent);
    }
    
    // Method to be executed after upload
    public static void onPostExecute(String name) {
        
        if(uploadResult){
            if(!progressDialog.isShowing()){
                
                showFileUploaded(name);
            }else{
                stopProgressDialog(progressDialog,"Record uploaded.",1000);
            }
        }else{
            stopProgressDialog(progressDialog, "Failed to upload the record, we will try again later.",1500);
        }
    }
    
    private static void stopProgressDialog(final CustomProgressDialog dialog, final String message, final long delay){
        if(dialog.isShowing()){
            if(message.length()>0){
                dialog.setMessage(message);
            }
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            }, delay);
        }
    }
    
    //TODO
    private boolean showProgressDialog(final CustomProgressDialog dialog, final String message, boolean cancelable){
        
        if(dialog.isShowing()){
            return false;
        }
        
        showDialog(dialog, message, cancelable);
        return true;
    }
    
    //TODO
    private void showDialog(CustomProgressDialog dialog, String message, boolean cancelable){
        
        if(dialog.isShowing()){
            dialog.setMessage(message);
            return;
        }
        
        dialog.setCancelable(cancelable);
        dialog.setMessage(message);
        
        //dialog.show();
    }
    
    public static void setUploadResult(boolean d){
        uploadResult = d;
    }
    
    public static void showFileUploaded(String file){
        String msg = "Uploaded file " + file;
        showToast(msg);
    }
    
    public static void showToast(final String msg){
        if(null == activity){
            return;
        }
        
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(activity==null){
                    return;
                }
                Toast myToast = Toast.makeText(activity, msg, Toast.LENGTH_LONG);
                myToast.setGravity(Gravity.CENTER_VERTICAL, 0, -80);
                myToast.show();
            }
        });
        
    }
    
    
    private void deleteMediaFile(String fileName){
        if (fileName != null && fileName.length() > 0){
            File file = new File(fileName);
            if (!file.delete()){
                Toast.makeText(getBaseContext(), "can't delete " + fileName, Toast.LENGTH_LONG).show();
            }
        }
    }
    
    private void closeActivity() {
        // When finished, create an Intent, adding extra:
        // - MainActivity.MainActivity.REQUEST_MODE with the mode
        // - MainActivity.RESULT_KEY_DIARY_ENTRY containing the primary key
        // Then call setResult(code, Intent) and finish();
        // For now let's just use Activity.RESULT_OK for the return code
        // Just doing it here for temporary testing
        
        // return to the main timeline
        setResult(m_result, getIntent());
        finish();
    }
    
    /** Methods of the AudioPlayer.PlayerClient interface ***********/
    @Override
    public AudioPlayer getActiveAudioPlayer() {
        return null;
    }
    
    @Override
    public void setActiveAudioPlayer(AudioPlayer player) {
        
    }
    
    @Override
    public void playerStarted() {
        findViewById(R.id.diary_date).setEnabled(false);
        findViewById(R.id.diary_notes).setEnabled(false);
        findViewById(R.id.diary_delete).setEnabled(false);
        findViewById(R.id.diary_create).setEnabled(false);
        findViewById(R.id.diary_create_text).setEnabled(false);
    }
    
    @Override
    public void playerStopped() {
        findViewById(R.id.diary_date).setEnabled(true);
        findViewById(R.id.diary_notes).setEnabled(true);
        findViewById(R.id.diary_delete).setEnabled(true);
        findViewById(R.id.diary_create).setEnabled(true);
        findViewById(R.id.diary_create_text).setEnabled(true);
        InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(layout, InputMethodManager.SHOW_IMPLICIT);
    }
    /****************************************************************/
}
