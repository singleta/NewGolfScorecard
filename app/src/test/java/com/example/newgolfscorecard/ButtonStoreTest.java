package com.example.newgolfscorecard;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.View;
import android.widget.ToggleButton;

import com.example.newgolfscorecard.buttons.ButtonStore;
import com.example.newgolfscorecard.shots.ShotOutcome;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ButtonStoreTest {

    public ButtonStoreTest() {
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
//        PowerMockito.mockStatic(Xml.class);
    }

    @Mock
    ToggleButton toggleButton;

    @Mock
    private View view;

    @Mock
    private Resources resources;

    @Mock
    XmlResourceParser parser;

    @Mock
    AttributeSet attributeSet;


    @Test
    public void setupDriveButtons() {

        mockStatic(Xml.class);
        ShotOutcome shotOutcome = ShotOutcome.D_LEFT;
        when(view.getResources()).thenReturn(resources);
        when(view.getResources().getXml(shotOutcome.getId())).thenReturn(parser);
        when(resources.getXml(shotOutcome.getId())).thenReturn(parser);
        when(Xml.asAttributeSet(parser)).thenReturn(attributeSet);

        ButtonStore buttonStore = new ButtonStore(view);

        buttonStore.setupDriveButtons();

        buttonStore.getDriveButtons();

    }
}
