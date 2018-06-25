package com.hackathon.appsoul.mombabycare.adapters;//

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hackathon.appsoul.mombabycare.R;
import com.hackathon.appsoul.mombabycare.model.ChildModel;
import com.hackathon.appsoul.mombabycare.util.AnimationUtil;
import com.hackathon.appsoul.mombabycare.util.ColorGenerator;
import com.hackathon.appsoul.mombabycare.util.TextDrawable;

import java.util.ArrayList;

public class ChildProfileListAdapter extends RecyclerView.Adapter<ChildProfileListAdapter.ViewHolder> {
   ArrayList<ChildModel> profileList;
   String firstLetter;
   Context context;
   ColorGenerator generator = ColorGenerator.MATERIAL;
   private int previousPosition = 0;
   
   public ChildProfileListAdapter(Context context, ArrayList<ChildModel> object) {
      this.context = context;
      this.profileList = object;
   }

   @Override
   public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_list_template_for_mom, parent, false);
      return new ViewHolder(view);
   }

   @Override
   public void onBindViewHolder(ViewHolder viewHolder, int position) {
      viewHolder.idTv.setText(getItem(position).getId());
      viewHolder.nameTv.setText(getItem(position).getBabyName());
      viewHolder.statusTv.setText(getItem(position).getAge());
      firstLetter = String.valueOf(getItem(position).getBabyName().charAt(0)).toUpperCase();
      TextDrawable drawable = TextDrawable.builder().buildRound(firstLetter, generator.getRandomColor());
      viewHolder.profilePicIv.setImageDrawable(drawable);
   
      if (position > previousPosition){
         AnimationUtil.animate(viewHolder, true);
      }
      else {
         AnimationUtil.animate(viewHolder, false);
      }
      previousPosition = position;
   }

   public ChildModel getItem(int position) {
      return profileList.get(position);
   }

   @Override
   public int getItemCount() {
      return profileList == null ? 0 : profileList.size();
   }

   class ViewHolder extends RecyclerView.ViewHolder {
      TextView idTv;
      TextView nameTv;
      TextView statusTv;
      ImageView profilePicIv;

      public ViewHolder(View convertView) {
         super(convertView);
         profilePicIv = (ImageView) convertView.findViewById(R.id.patientProfilePicIV);
         idTv = (TextView) convertView.findViewById(R.id.lblProfileId);
         nameTv = (TextView) convertView.findViewById(R.id.lblProfileName);
         statusTv = (TextView) convertView.findViewById(R.id.lblMomStatus);
      }
   }
}
