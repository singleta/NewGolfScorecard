package com.example.newgolfscorecard.buttons;

import android.content.Context;

import androidx.appcompat.widget.AppCompatButton;

import com.example.newgolfscorecard.shots.PuttLength;


public class PuttButton extends AppCompatButton {

    PuttLength puttLength;

    public PuttButton(Context context, PuttLength puttLength) {
        super(context);
        this.puttLength = puttLength;
        this.setText(puttLength.getPuttName());
    }

    public PuttLength getPuttLength() {
        return puttLength;
    }
}
