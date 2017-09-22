package com.hackathon.appsoul.mombabycare.activity;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hackathon.appsoul.mombabycare.R;
import com.hackathon.appsoul.mombabycare.datastorage.DataBaseHelper;
import com.hackathon.appsoul.mombabycare.datastorage.DataSource;
import com.hackathon.appsoul.mombabycare.model.Event;
import com.hackathon.appsoul.mombabycare.server.SQLConnection;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;


public class DoctorHelpFragment extends Fragment implements AudioPlayer.PlayerClient{
    
    private static SimpleDateFormat formatOfEventDate = new SimpleDateFormat("MMMMMMMMM d", Locale.ENGLISH);
    
    private DataBaseHelper helper;
    private DataSource dataSource;
    
    private DashBoardMomActivity dashBoardMomActivity;
    private ListView 			    listView;
    private ImageButton chatDoctorBtn;
    private View contentView;
    private DoctorHelpFragment fragment;
    private ChatHistoryListViewAdapter adapter;
    private static AudioPlayer activeAudioPlayer;
    
    private SQLConnection connectionClass;
    
    public static final String	REQUEST_MODE 		= "mode";
    public static final String	REQUEST_MODE_NEW 	= "new";
    public static final String	REQUEST_MODE_EDIT 	= "edit";
    public static final int REQUEST_CODE_CHAT_DOCTOR = 1;
    
    //SharedPreference
    public static final String PREFERENCES = "preferences";
    
    
    public enum RefreshType {ON_NEW_OR_EDIT, ON_DELETE, ON_CHAT_HISTORY_CHANGE}
    
    
    
    /**
     * Scroll speed
     */
    public static final Integer FRICTION_SCALE_FACTOR = 5;
    /**
     * Preference index
     */
    public static final String PREFERENCE_INDEX = "index";
    /**
     * Preference top
     */
    public static final String PREFERENCE_TOP = "top";
    
    public DoctorHelpFragment() {
        // Required empty public constructor
        super();
        fragment = this;
    }
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dashBoardMomActivity = (DashBoardMomActivity) getActivity();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        contentView = inflater.inflate(R.layout.fragment_doctor_help, container, false);
        return contentView;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dataSource=new DataSource(getActivity());
        
        
        adapter = new ChatHistoryListViewAdapter(contentView.getContext());
        
        dashBoardMomActivity.setChatHistoryListViewAdapter(adapter);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        
        listView = (ListView) contentView.findViewById(R.id.lstTimeline);
        
        listView = (ListView) contentView.findViewById(R.id.lstTimeline);
        listView.setFriction(ViewConfiguration.getScrollFriction() * FRICTION_SCALE_FACTOR);
        
        listView.setAdapter(adapter);
        //adapter.refresh(RefreshType.ON_NEW_OR_EDIT);
        adapter.scrollToLastItem(listView);
        
        
        chatDoctorBtn = (ImageButton) contentView.findViewById(R.id.btnHelp);
        //TODO set visibility=gone if an event is already added and unset OnClickListener
        chatDoctorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashBoardMomActivity.getApplicationContext(), ChatDoctorActivity.class);
                intent.putExtra(REQUEST_MODE, REQUEST_MODE_NEW);
                fragment.startActivityForResult(intent, REQUEST_CODE_CHAT_DOCTOR);
            }
        });
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        if (intent == null){
            // Return from called activity by pressing back button
            
            return;
        } else { // if (requestCode=REQUEST_CODE_CHAT_DOCTOR)
            
            adapter.refresh(RefreshType.ON_NEW_OR_EDIT);
            
        }
    }
    
    @Override
    public void onPause() {
        super.onPause();
        saveCurrentListPosition();
    }
    
    /**
     * saves current list position
     */
    public void saveCurrentListPosition() {
        // save list current position
        int index = listView.getFirstVisiblePosition();
        View view = listView.getChildAt(0);
        int top = (view == null) ? 0 : view.getTop();
        
        SharedPreferences preferences = getActivity().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        
        editor.putInt(PREFERENCE_INDEX, index);
        editor.putInt(PREFERENCE_TOP, top);
        editor.apply();
    }
    
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        saveCurrentListPosition();
    }

//    void refreshOnTimelineChange(){
//        adapter.refresh(RefreshType.ON_CHAT_HISTORY_CHANGE);
//    }
    
    public static class ViewHolder {
        public ImageView picture;
        public TextView text;
        public int position;
    }
    
    public class ChatHistoryListViewAdapter extends BaseAdapter {
        private final Context context;
        private List<Event> events;
        
        public ChatHistoryListViewAdapter(Context context) {
            
            this.context = context;
            //TODO Selects all from Database
            events = dataSource.getAllEvents();
            Collections.sort(events, new Comparator<Event>() {
                @Override
                public int compare(Event lhs, Event rhs) {
                    return lhs.getDate().compareTo(rhs.getDate());
                }
            });
        }
        
        
        // Regenerate the event list, refresh the display
        void refresh(RefreshType refreshType){
            //TODO Selects all from Database
            
            events = dataSource.getAllEvents();
            
            Collections.sort(events, new Comparator<Event>() {
                @Override
                public int compare(Event lhs, Event rhs) {
                    return lhs.getDate().compareTo(rhs.getDate());
                }
            });
            
            notifyDataSetChanged();
            if (refreshType.equals(RefreshType.ON_NEW_OR_EDIT)){
                scrollToLastItem(listView);
            }
        }
        
        // Scroll to the Last item in the ListView
        public void scrollToLastItem(ListView listView) {
            int index = 0;
            int top = 0;
            
            SharedPreferences preferences = dashBoardMomActivity.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
            
            index = preferences.getInt(PREFERENCE_INDEX, 0); // first visible list item = 0 at cold start
            top = preferences.getInt(PREFERENCE_TOP, 0); // = 1 at cold start
            
            if (index ==0 && top == 0) {
                listView.setSelection(events.size() - 1);
            } else {
                listView.setSelectionFromTop(index, top);
            }
        }
        
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            
            ImageView photoView = null;
            ViewHolder holder = null;
            final Event event = events.get(position);
            String photoFile = event.getPhotoFile();
            Event.Type eventType = event.getType();
            TextView date;
            TextView notes;
            
            
            View audioView;
            ViewGroup audioPlayerGroup;
            final String audioFile;
            
            
            switch (eventType){
                
                case DOCTOR: // for all ListView item representing DOCTOR
                    
                    if(view == null || !view.getTag().equals(Event.Type.DOCTOR.name())) {
                        view = LayoutInflater.from(context).inflate(R.layout.list_item_tip, parent, false);
                        holder = new ViewHolder();
                        holder.position = position;
                        view.setTag(holder);
                    } else {
                        holder = (ViewHolder) view.getTag();
                    }
                    
                    notes = (TextView)view.findViewById(R.id.list_item_tip_notes);
                    
                    date = (TextView)view.findViewById(R.id.list_item_tip_date);
                    audioFile = event.getAudioFile();
                    audioView = view.findViewById(R.id.list_item_tip_audio);
                    if (audioFile != null && audioFile.length() > 0){
                        audioPlayerGroup = (ViewGroup)audioView.findViewById(R.id.list_item_tip_audio_player);
                        boolean isActivelyPlaying = false;
                        if (activeAudioPlayer != null){
                            if (activeAudioPlayer.isPlaying(audioFile)){
                                activeAudioPlayer.restore(dashBoardMomActivity, audioPlayerGroup);
                                isActivelyPlaying = true;
                            }
                        }
                        if (!isActivelyPlaying){
                            new AudioPlayer(dashBoardMomActivity, audioPlayerGroup, event.getAudioFile(), fragment);
                        }
                        audioView.setVisibility(View.VISIBLE);
                    }
                    //orphaned recording fix
                    if (audioFile == null || audioFile.length()==0) {
                        audioView.setVisibility(View.GONE);
                    }
                    photoView = (ImageView)view.findViewById(R.id.list_item_tip_photo);
                    photoView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(v.getContext(), PictureActivity.class);
                            Bundle parentBundle = new Bundle();
                            String imagePath = event.getPhotoFile();
                            String activityTitle = "Diary Photo";
                            Integer eventId = event.getId();
                            parentBundle.putInt("eventid", eventId);
                            parentBundle.putString("imagepath", imagePath);
                            parentBundle.putString("imagetitle", activityTitle);
                            intent.putExtras(parentBundle);
                            startActivity(intent);
                        }
                    });
                    notes.setText(event.getText());
                    if (event.getText() == null || event.getText().length()==0) {
                        
                        notes.setVisibility(View.GONE);
                        
                    }
                    date.setText(formatOfEventDate.format(event.getDate()));
                    break;
                
                
                case USER:
                    
                    if(view == null  || !view.getTag().equals(Event.Type.USER.name())) {
                        if(position != events.size()-1)
                            view = LayoutInflater.from(context).inflate(R.layout.list_item_entry, parent, false);
                        else
                            view = LayoutInflater.from(context).inflate(R.layout.list_item_last, parent, false);
                        holder = new ViewHolder();
                        holder.position = position;
                        view.setTag(holder);
                    } else {
                        holder = (ViewHolder) view.getTag();
                    }
                    
                    if(position != events.size()-1) {//for all ListView item representing USER except the last
                        
                        notes = (TextView) view.findViewById(R.id.list_item_entry_notes);
                        
                        
                        date = (TextView) view.findViewById(R.id.list_item_entry_date);
                        audioFile = event.getAudioFile();
                        audioView = (View) view.findViewById(R.id.list_item_entry_audio);
                        if (audioFile != null && audioFile.length() > 0) {
                            audioPlayerGroup = (ViewGroup) audioView.findViewById(R.id.list_item_entry_audio_player);
                            boolean isActivelyPlaying = false;
                            if (activeAudioPlayer != null) {
                                if (activeAudioPlayer.isPlaying(audioFile)) {
                                    activeAudioPlayer.restore(dashBoardMomActivity, audioPlayerGroup);
                                    isActivelyPlaying = true;
                                }
                            }
                            if (!isActivelyPlaying) {
                                new AudioPlayer(dashBoardMomActivity, audioPlayerGroup, event.getAudioFile(), fragment);
                            }
                            audioView.setVisibility(View.VISIBLE);
                        }
                        //orphaned recording fix
                        if (audioFile == null || audioFile.length() == 0) {
                            audioView.setVisibility(View.GONE);
                        }
                        photoView = (ImageView) view.findViewById(R.id.list_item_entry_photo);
                        photoView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(v.getContext(), PictureActivity.class);
                                Bundle parentBundle = new Bundle();
                                String imagePath = event.getPhotoFile();
                                String activityTitle = "Photo";
                                Integer eventId = event.getId();
                                parentBundle.putInt("eventid", eventId);
                                parentBundle.putString("imagepath", imagePath);
                                parentBundle.putString("imagetitle", activityTitle);
                                intent.putExtras(parentBundle);
                                startActivity(intent);
                            }
                        });
                        notes.setText(event.getText());
                        if (event.getText() == null || event.getText().length() == 0) {
                            
                            notes.setVisibility(View.GONE);
                            
                        }
                        date.setText(formatOfEventDate.format(event.getDate()));
                        
                    } else {//for last ListView item representing USER
                        
                        notes = (TextView) view.findViewById(R.id.list_item_last_notes);
                        
                        
                        date = (TextView) view.findViewById(R.id.list_item_last_date);
                        ImageButton editButton = (ImageButton) view.findViewById(R.id.list_item_last_edit);
                        editButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Stop any active audio playback
                                if (activeAudioPlayer != null) {
                                    activeAudioPlayer.stop();
                                }
                                Intent intent = new Intent(dashBoardMomActivity.getApplicationContext(), ChatDoctorActivity.class);
                                intent.putExtra(REQUEST_MODE, REQUEST_MODE_EDIT);
                                //intent.putExtra(DashBoardMomActivity.REQUEST_PRIMARY_KEY, event.getId());
                                intent.putExtra("event", event);
                                
                                fragment.startActivityForResult(intent, REQUEST_CODE_CHAT_DOCTOR);
                            }
                        });
                        ImageButton deleteEntry = (ImageButton) view.findViewById(R.id.list_item_last_delete);
                        deleteEntry.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dashBoardMomActivity.showConfirmationDialog(R.string.dlg_delete_diary_entry, event);
                            }
                        });
                        audioFile = event.getAudioFile();
                        audioView = (View) view.findViewById(R.id.list_item_last_audio);
                        if (audioFile != null && audioFile.length() > 0) {
                            audioPlayerGroup = (ViewGroup) audioView.findViewById(R.id.list_item_last_audio_player);
                            boolean isActivelyPlaying = false;
                            if (activeAudioPlayer != null) {
                                if (activeAudioPlayer.isPlaying(audioFile)) {
                                    activeAudioPlayer.restore(dashBoardMomActivity, audioPlayerGroup);
                                    isActivelyPlaying = true;
                                }
                            }
                            if (!isActivelyPlaying) {
                                new AudioPlayer(dashBoardMomActivity, audioPlayerGroup, event.getAudioFile(), fragment);
                            }
                            audioView.setVisibility(View.VISIBLE);
                        }
                        //orphaned recording fix
                        if (audioFile == null || audioFile.length() == 0) {
                            audioView.setVisibility(View.GONE);
                        }
                        photoView = (ImageView) view.findViewById(R.id.list_item_last_photo);
                        photoView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(v.getContext(), PictureActivity.class);
                                Bundle parentBundle = new Bundle();
                                String imagePath = event.getPhotoFile();
                                String activityTitle = "Photo";
                                Integer eventId = event.getId();
                                parentBundle.putInt("eventid", eventId);
                                parentBundle.putString("imagepath", imagePath);
                                parentBundle.putString("imagetitle", activityTitle);
                                intent.putExtras(parentBundle);
                                startActivity(intent);
                            }
                        });
                        notes.setText(event.getText());
                        if (event.getText() == null || event.getText().length() == 0) {
                            
                            notes.setVisibility(View.GONE);
                        }
                        date.setText(formatOfEventDate.format(event.getDate()));
                    }
                    break;
            }
            
            
            holder.picture = photoView;
            
            if (photoFile != null && photoFile.length() > 0){
                
                ImageLoader imageloader = new ImageLoader(photoFile,photoView,getActivity(),event.getType());
                imageloader.loadBitmap(event.getId(),position, holder);
                
            }
            
            return view;
        }
        
        
        
        //***************************************** From BaseAdapter
        // Get the type of View that will be created by getView(int, View, ViewGroup) for the specified item. (0...
        @Override
        public int getItemViewType (int position){
            Event event = events.get(position);
            switch (event.getType()){
                case DOCTOR: 	    return 0;
                case USER:	        return 1;
                default:			return -1;
            }
            
        }
        
        
        //Returns the number of types of Views that will be created by getView(int, View, ViewGroup)
        @Override
        public int getViewTypeCount (){
            return 2;
        }
        //Indicates whether the item ids are stable across changes to the underlying data.
        @Override public boolean hasStableIds (){
            return true;
        }
        //***************************************** From adapter
        // How many items are in the data set represented by this Adapter
        @Override
        public int getCount() {
            return events.size();
        }
        // Get the data item associated with the specified position in the data set.
        @Override
        public Object getItem(int position) {
            return null;
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }
        //*****************************************
    }
    
    @Override
    public AudioPlayer getActiveAudioPlayer() {
        return activeAudioPlayer;
    }
    
    @Override
    public void setActiveAudioPlayer(AudioPlayer audioPlayer) {
        activeAudioPlayer = audioPlayer;
    }
    
    @Override
    public void playerStarted() {
        
    }
    
    @Override
    public void playerStopped() {
        
    }
}
