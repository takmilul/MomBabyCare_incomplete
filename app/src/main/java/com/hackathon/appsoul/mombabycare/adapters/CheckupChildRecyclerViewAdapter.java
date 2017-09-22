package com.hackathon.appsoul.mombabycare.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

import com.hackathon.appsoul.mombabycare.R;
import com.hackathon.appsoul.mombabycare.adapters.CheckupChildRecyclerViewAdapter.ViewHolder;
import com.hackathon.appsoul.mombabycare.model.CheckupModel;
import com.hackathon.appsoul.mombabycare.util.AnimationUtil;

import java.util.ArrayList;

public class CheckupChildRecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

   private Context context;
   private ArrayList<CheckupModel> checkupModelArrayList;
   private int previousPosition = 0;
   
   public CheckupChildRecyclerViewAdapter(Context context, ArrayList<CheckupModel> checkupList) {
      this.context = context;
      this.checkupModelArrayList = checkupList;
   }

   @Override
   public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkup_row_list, parent, false);

      return new ViewHolder(itemView);
   }

   @Override
   public void onBindViewHolder(ViewHolder viewHolder, int position) {

      CheckupModel model = getCheckupModel(position);

      final ViewHolder finalViewHolder = viewHolder;
      viewHolder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
               getCheckupModel((Integer) buttonView.getTag()).setChecked(isChecked);
               //Toast.makeText(context, String.valueOf(finalViewHolder.checkBox.isChecked()), Toast.LENGTH_SHORT).show();
            }
            else {
               getCheckupModel((Integer) buttonView.getTag()).setChecked(isChecked);
               //Toast.makeText(context, String.valueOf(finalViewHolder.checkBox.isChecked()), Toast.LENGTH_SHORT).show();
            }
         }
      });

      final ViewHolder finalViewHolder1 = viewHolder;
      viewHolder.valueEt.addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence s, int start, int count, int after) {
         }

         @Override
         public void onTextChanged(CharSequence s, int start, int before, int count) {
         }

         @Override
         public void afterTextChanged(Editable s) {
            //getCheckupModel((Integer)buttonView.getTag()).setChecked(isChecked);
            getCheckupModel((Integer) finalViewHolder1.valueEt.getTag()).setValue(s.toString());
         }
      });
      viewHolder.remarksEt.addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence s, int start, int count, int after) {
         }

         @Override
         public void onTextChanged(CharSequence s, int start, int before, int count) {
         }

         @Override
         public void afterTextChanged(Editable s) {
            getCheckupModel((Integer) finalViewHolder1.remarksEt.getTag()).setRemarks(s.toString());
         }
      });
      viewHolder.checkBox.setTag(position);
      viewHolder.nameTV.setTag(position);
      viewHolder.valueEt.setTag(position);
      viewHolder.remarksEt.setTag(position);


      viewHolder.checkBox.setChecked(model.isChecked());
      viewHolder.nameTV.setText(model.getName());
      viewHolder.valueEt.setText(model.getValue());
      viewHolder.remarksEt.setText(model.getRemarks());
   
      if (position > previousPosition){
         AnimationUtil.animate(viewHolder, true);
      }
      else {
         AnimationUtil.animate(viewHolder, false);
      }
      previousPosition = position;
   }

   @Override
   public int getItemCount() {
      return checkupModelArrayList.size();
   }
   public Object getItem(int position) {
      return checkupModelArrayList.get(position);
   }

   private CheckupModel getCheckupModel(int position) {
      return ((CheckupModel) getItem(position));
   }

   public ArrayList<CheckupModel> getCheckupModelArrayList() {
      ArrayList<CheckupModel> checkupModelArrayList = new ArrayList<>();
      for (CheckupModel c : this.checkupModelArrayList) {
         checkupModelArrayList.add(c);
      }
      return checkupModelArrayList;
   }

   public class ViewHolder extends RecyclerView.ViewHolder {
      private CheckBox checkBox;
      private TextView nameTV;
      private EditText valueEt;
      private EditText remarksEt;

      public ViewHolder(View convertView) {
         super(convertView);
         checkBox = (CheckBox) convertView.findViewById(R.id.checkupCheckBox);
         nameTV = (TextView) convertView.findViewById(R.id.nameTV);
         valueEt = (EditText) convertView.findViewById(R.id.valueEt);
         remarksEt = (EditText) convertView.findViewById(R.id.remarksEt);
      }
   }
}
