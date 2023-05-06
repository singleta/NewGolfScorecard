package com.example.newgolfscorecard.buttons;

import static com.example.newgolfscorecard.shots.ShotOutcome.A_BUNKER;
import static com.example.newgolfscorecard.shots.ShotOutcome.A_FRINGE;
import static com.example.newgolfscorecard.shots.ShotOutcome.A_FROM_FAIRWAY;
import static com.example.newgolfscorecard.shots.ShotOutcome.A_FROM_LROUGH;
import static com.example.newgolfscorecard.shots.ShotOutcome.A_FROM_RROUGH;
import static com.example.newgolfscorecard.shots.ShotOutcome.A_GIR;
import static com.example.newgolfscorecard.shots.ShotOutcome.A_GREEN;
import static com.example.newgolfscorecard.shots.ShotOutcome.A_LEFT;
import static com.example.newgolfscorecard.shots.ShotOutcome.A_LONG;
import static com.example.newgolfscorecard.shots.ShotOutcome.A_MIDDLE;
import static com.example.newgolfscorecard.shots.ShotOutcome.A_OB;
import static com.example.newgolfscorecard.shots.ShotOutcome.A_PENALTY;
import static com.example.newgolfscorecard.shots.ShotOutcome.A_RIGHT;
import static com.example.newgolfscorecard.shots.ShotOutcome.A_SHORT;
import static com.example.newgolfscorecard.shots.ShotOutcome.A_TREE;
import static com.example.newgolfscorecard.shots.ShotOutcome.D_BUNKER;
import static com.example.newgolfscorecard.shots.ShotOutcome.D_FAIRWAY;
import static com.example.newgolfscorecard.shots.ShotOutcome.D_LEFT;
import static com.example.newgolfscorecard.shots.ShotOutcome.D_OB;
import static com.example.newgolfscorecard.shots.ShotOutcome.D_PENALTY;
import static com.example.newgolfscorecard.shots.ShotOutcome.D_RIGHT;
import static com.example.newgolfscorecard.shots.ShotOutcome.D_TREE;

import android.view.View;
import android.widget.ToggleButton;

import com.example.newgolfscorecard.shots.ShotOutcome;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ButtonActions {

    private View view;
    static View staticView;
    ButtonActions(View view) {
        this.view = view;
    }

    void toggleButtonStates(ToggleButton tb, Map<String, View> driveButtons, Map<String, View> approachButtons) {
        Map<Integer, ToggleButton> buttons = new HashMap<>();

        buttons.clear();

        driveButtons.forEach((k, v) -> {
            buttons.put(v.getId(), (ToggleButton) v);
        });

        int id = tb.getId();
        if (id == D_LEFT.getId() || id == D_RIGHT.getId()) {
            removeButtons(buttons, D_TREE, D_OB, D_PENALTY, D_BUNKER);
        } else if (id == D_BUNKER.getId() || id == D_OB.getId() || id == D_PENALTY.getId() || id == D_TREE.getId()) {
            removeButtons(buttons, D_LEFT, D_RIGHT);
        } else if (id == D_FAIRWAY.getId()) {
            removeButtons(buttons);
        } else {
            buttons.clear();
        }

        approachButtons.forEach((k, v) -> {
            buttons.put(v.getId(), (ToggleButton)v);
        });

        // Remove the ones that can also be selected at the same time is the selected button
        if (id == A_LEFT.getId() || id == A_RIGHT.getId()) {
            removeButtons(buttons, A_TREE, A_OB, A_PENALTY, A_BUNKER, A_GIR);
        } else if (id == A_BUNKER.getId() || id == A_OB.getId() || id == A_PENALTY.getId() || id == A_TREE.getId()) {
            removeButtons(buttons, A_LEFT, A_RIGHT, A_SHORT, A_GIR);
        } else if (id == A_GIR.getId()) {
            removeButtons(buttons, A_LEFT, A_RIGHT, A_PENALTY, A_BUNKER, A_TREE, A_MIDDLE, A_LONG, A_SHORT, A_FRINGE);
        } else if (id == A_SHORT.getId() || id == A_MIDDLE.getId() || id == A_LONG.getId()) {
            removeButtons(buttons, A_LEFT, A_RIGHT, A_BUNKER, A_PENALTY, A_GIR);
        } else if (id == A_FRINGE.getId()) {
            removeButtons(buttons, A_LEFT, A_RIGHT, A_PENALTY, A_GIR, A_SHORT, A_LONG);
//        } else if (id == A_FORTY.getId() || id == A_EIGHTY.getId() || id == A_ONE_TWENTY.getId() ||
//                id == A_ONE_SIXTY.getId() || id == A_TWO_HUNDRED.getId() || id == A_TWO_HUNDRED_PLUS.getId()) {
//            removeButtons(buttons, approachOutcomes);
        } else if (id == A_FROM_FAIRWAY.getId() || id == A_FROM_RROUGH.getId() || id == A_FROM_LROUGH.getId()) {
            removeButtons(buttons, A_LEFT, A_RIGHT, A_PENALTY, A_BUNKER, A_TREE, A_MIDDLE, A_LONG, A_SHORT, A_FRINGE, A_GIR);
        } else if (id == A_GREEN.getId()) {
            removeButtons(buttons);
        } else {
            buttons.clear();
        }

//            case SHORT:
//                buttons.remove(text);
//                break;

        buttons.remove(id);

        turnOffToggleButtons(buttons);

    }


    private void removeButtons(Map<Integer, ToggleButton> buttons, ShotOutcome... names) {
        System.out.println("remove the buttons");
        Arrays.stream(names).forEach(so -> {
            buttons.remove(so.getId());
        });
    }

    private void removeButtons(Map<Integer, ToggleButton> buttons, ShotOutcome[] shotOutcomes, ShotOutcome... names) {
        ShotOutcome[] newArray = Stream.concat(Arrays.stream(shotOutcomes), Arrays.stream(names))
                .toArray(ShotOutcome[]::new);
        removeButtons(buttons, newArray);
    }


    private void turnOffToggleButtons(Map<Integer, ToggleButton> buttons) {
        buttons.forEach((k, v) -> v.setChecked(false));
    }

     void resetPuttButtons(Map<String, View> puttButtons) {
        puttButtons.forEach(this::setPuttsToZero);
    }



    void setPuttsToZero(String k, View v) {
        PuttButton pb = ((PuttButton) v);
        pb.setText("0");
        pb.performLongClick();

    }



}
