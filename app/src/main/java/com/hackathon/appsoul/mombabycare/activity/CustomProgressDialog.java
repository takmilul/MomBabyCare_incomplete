package com.hackathon.appsoul.mombabycare.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;

/**
 * Created by A. A. M. Sharif on 27-Apr-16.
 */
public class CustomProgressDialog extends ProgressDialog {
    public CustomProgressDialog(Context context) {
        super(context);
    }

    @Override
    public void setMessage(CharSequence message) {
        SpannableString ss = createProgressDialogMessage(message.toString());
        super.setMessage(ss);
    }

    public static SpannableString createProgressDialogMessage(String msg){
        SpannableString ss = new SpannableString(msg);
        ss.setSpan(new RelativeSizeSpan(1.28f), 0, ss.length(), 0);
        return ss;
    }
}
