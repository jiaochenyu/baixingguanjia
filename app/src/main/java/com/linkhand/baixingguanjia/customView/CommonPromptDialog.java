/*
 * Copyright 2015 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.linkhand.baixingguanjia.customView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;


/**
 * Created in Oct 23, 2015 1:19:04 PM.
 *
 * @author Yan Zhenjie.
 */
public class CommonPromptDialog extends Dialog {
    private Context mContext;
    TextView oneTV;
    TextView twoTV;
    TextView messageTV;
    private OnClickListener optionOneClickListener;
    private OnClickListener optionTwoClickListener;


    public CommonPromptDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        initView();
        initListener();
    }


    private void initView() {
        setContentView(R.layout.dialog_prompt_common);
        messageTV = (TextView)findViewById(R.id.dialog_message);
        oneTV = (TextView) findViewById(R.id.notextview);
        twoTV = (TextView) findViewById(R.id.yestextview);
    }
    private void initListener() {
        oneTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionOneClickListener.onClick(CommonPromptDialog.this, DialogInterface.BUTTON_POSITIVE);
            }
        });
        twoTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionTwoClickListener.onClick(CommonPromptDialog.this, DialogInterface.BUTTON_POSITIVE);
            }
        });
    }

    public void setMessage(String message) {
        messageTV.setText(message);
    }
    public void setOptionOneClickListener(String optionOne, OnClickListener listener) {
        this.optionOneClickListener = listener;
        oneTV.setText(optionOne);
    }

    public void setOptionTwoClickListener(String optionTwo, OnClickListener listener) {
        this.optionTwoClickListener = listener;
        twoTV.setText(optionTwo);
    }


}
