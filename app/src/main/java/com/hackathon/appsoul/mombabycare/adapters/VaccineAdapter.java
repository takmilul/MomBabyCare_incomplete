package com.hackathon.appsoul.mombabycare.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.hackathon.appsoul.mombabycare.R;
import com.hackathon.appsoul.mombabycare.adapters.VaccineAdapter.ViewHolder;
import com.hackathon.appsoul.mombabycare.model.VaccineModel;
import com.hackathon.appsoul.mombabycare.util.AnimationUtil;

import java.util.ArrayList;

public class VaccineAdapter extends RecyclerView.Adapter<ViewHolder> {

   private Context context;
   private ArrayList<VaccineModel> vaccineModelArrayList;
   private int previousPosition = 0;
   
   public VaccineAdapter(Context context, ArrayList<VaccineModel> checkupList) {
      this.context = context;
      this.vaccineModelArrayList = checkupList;
   }

   @Override
   public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vaccine_row_list, parent, false);
      return new ViewHolder(itemView);
   }

   @Override
   public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

      final AlertDialog.Builder builder = new AlertDialog.Builder(context);
      builder.setTitle("Warning");
      builder.setIcon(android.R.drawable.sym_def_app_icon);
      builder.setMessage("Did Your Child Take This Vaccine?");
      builder.setCancelable(true);

      final VaccineModel model = getVaccineModel(position);
      viewHolder.checkBox.setOnCheckedChangeListener(null);

      viewHolder.checkBox.setTag(position);
      viewHolder.vaccineNameTV.setTag(position);
      viewHolder.vaccineDate.setTag(position);

      viewHolder.checkBox.setChecked(model.isChecked());
      viewHolder.vaccineNameTV.setText(model.getVaccine());
      viewHolder.vaccineDate.setText(model.getDate());
   
      if (position > previousPosition){
         AnimationUtil.animate(viewHolder, true);
      }
      else {
         AnimationUtil.animate(viewHolder, false);
      }
      previousPosition = position;
      
      final boolean[] exitListener = {false};

      viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
            if (exitListener[0]) {
               exitListener[0] = false;
               return;
            }
            builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                  if (isChecked) {
                     exitListener[0] = true;
                  }
                  viewHolder.checkBox.setChecked(false);
                  getVaccineModel((Integer) buttonView.getTag()).setChecked(false);
                  dialog.cancel();
               }
            });
            builder.setNegativeButton("Yes",
                  new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int id) {
                        if (!isChecked) {
                           exitListener[0] = true;
                        }
                        viewHolder.checkBox.setChecked(true);
                        getVaccineModel((Integer) buttonView.getTag()).setChecked(true);
                        dialog.cancel();
                     }
                  });
            builder.create().show();
         }
      });

      viewHolder.vaccineNameTV.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(final View v) {

            builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                  if (viewHolder.checkBox.isChecked()) {
                     exitListener[0] = true;
                  }
                  viewHolder.checkBox.setChecked(false);
                  getVaccineModel((Integer) v.getTag()).setChecked(false);
                  dialog.cancel();
               }
            });
            builder.setNegativeButton("Yes",
                  new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int id) {
                        if (!viewHolder.checkBox.isChecked()) {
                           exitListener[0] = true;
                        }
                        viewHolder.checkBox.setChecked(true);
                        getVaccineModel((Integer) v.getTag()).setChecked(true);
                        dialog.cancel();
                     }
                  });
            builder.create().show();
         }
      });
      
      
   }

   @Override
   public int getItemCount() {
      return vaccineModelArrayList.size();
   }

   public Object getItem(int position) {
      return vaccineModelArrayList.get(position);
   }

   private VaccineModel getVaccineModel(int position) {
      return ((VaccineModel) getItem(position));
   }

   public ArrayList<VaccineModel> getVaccineModelArrayList() {
      ArrayList<VaccineModel> vaccineModelArrayList = new ArrayList<>();
      for (VaccineModel c : this.vaccineModelArrayList) {
         vaccineModelArrayList.add(c);
      }
      return vaccineModelArrayList;
   }

   public class ViewHolder extends RecyclerView.ViewHolder {
      private CheckBox checkBox;
      private TextView vaccineNameTV;
      private TextView vaccineDate;

      public ViewHolder(View convertView) {
         super(convertView);
         checkBox = (CheckBox) convertView.findViewById(R.id.vaccineCheckBox);
         vaccineNameTV = (TextView) convertView.findViewById(R.id.vaccineNameTV);
         vaccineDate = (TextView) convertView.findViewById(R.id.vaccineDateTV);
      }
   }
}
