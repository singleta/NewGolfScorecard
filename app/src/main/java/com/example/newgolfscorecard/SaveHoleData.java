package com.example.newgolfscorecard;

import static com.example.newgolfscorecard.main.HoleScoreFragment.ARG_SECTION_NUMBER;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.newgolfscorecard.buttons.PuttButton;
import com.example.newgolfscorecard.shots.PuttLength;
import com.example.newgolfscorecard.main.HoleScoreFragment;
import com.example.newgolfscorecard.shots.ShotOutcome;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class SaveHoleData {

    private static final String APPROACH = "approach";
    private static final String FAIRWAY = "fairway";
    private static final String PUTTING = "putting";
    List<Integer> driveResultsIds = ShotOutcome.getDriveResultsIds();
    List<Integer> approachResultsStrings = ShotOutcome.getApproachResultsIds();
    List<HoleScoreFragment> holePages;
//    HoleDetails holeDetails = new HoleDetails();


    public SaveHoleData() {
    }

    public SaveHoleData(List<HoleScoreFragment> holePages) {
        this.holePages = holePages;
    }

    public List<HoleDetails> saveData() {
        List<HoleDetails> holeDetailsList = new ArrayList<>(18);
        for (HoleScoreFragment holePage : holePages) {
            HoleDetails holeDetails = new HoleDetails();
//            HoleScoreFragment holePage = holePages.get(i-1);
            if (holePage != null) {
                holeDetails = saveSingleHoleData(holePage, holeDetails);
                System.out.println(holeDetails.toString());
                holeDetailsList.add(holeDetails);
            } else {
                System.out.println("hole page is null");
            }
        }
        return holeDetailsList;
    }

    public HoleDetails saveSingleHoleData(HoleScoreFragment holeScoreFragment, HoleDetails holeDetails) {
        holeDetails.setDate(LocalDate.now());
        Bundle arguments = holeScoreFragment.getArguments();
        if (arguments != null) {
            Object o = arguments.get(ARG_SECTION_NUMBER);
            if (o instanceof Integer) {
                holeDetails.setHole((Integer) o);
            }
        }
        holeDetails.setPar(HoleDetails.parsByHole[holeDetails.getHole() - 1]);
        ConstraintLayout cl = (ConstraintLayout) holeScoreFragment.getView();
        if (cl != null) {
            int childCount = cl.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View v = cl.getChildAt(i);
                if (v instanceof TableLayout) {
                    processTable(v, holeDetails, holeScoreFragment);
                    int firstPuttDistance = findFirstPuttDistance(holeDetails, holeScoreFragment);
                    holeDetails.setFirstPuttDistance(firstPuttDistance);
                } else { // It's the totals
                    saveTotal(v, holeDetails);
                }
            }
        }

        System.out.println("Saving view data");
        return holeDetails;
    }

    public void processTable(View v, HoleDetails holeDetails, HoleScoreFragment holeScoreFragment) {
        TableLayout tl = (TableLayout) v;
        int childCount = tl.getChildCount();
        for (int i = 0; i < childCount; i++) {
            TableRow tr = (TableRow) tl.getChildAt(i);
            for (int j = 0; j < tr.getChildCount(); j++) {
                View child = tr.getChildAt(j);
                saveChildButtonValue(holeDetails, holeScoreFragment, tl, child);
            }
        }
    }

    private void saveChildButtonValue(HoleDetails holeDetails, HoleScoreFragment holeScoreFragment, TableLayout tl, View child) {
        if (child instanceof ToggleButton) {
            if (((ToggleButton) child).isChecked())
                saveToggleButtonValue(child, tl, holeDetails);
        } else if (child instanceof ImageButton) {
            if (child.getVisibility() == View.VISIBLE) {
                saveTeeValue(child, holeDetails);
            }
        } else if (child instanceof PuttButton) {
            savePuttButtonValue(child, holeScoreFragment);
        }
    }

    protected void savePuttButtonValue(View v, HoleScoreFragment holeScoreFragment) {
        PuttButton puttButton = (PuttButton) v;
        if (puttButton.getText().length() == 1) {
            holeScoreFragment.getPuttDistances().add(puttButton.getPuttLength());
        }
    }

    protected int findFirstPuttDistance(HoleDetails holeDetails, HoleScoreFragment holeScoreFragment) {
        List<PuttLength> puttDistances = holeScoreFragment.getPuttDistances();
        Optional<PuttLength> puttLength = puttDistances.stream().max(Comparator.comparing(PuttLength::getDistance));
//        puttLength.ifPresent(pl -> holeDetails.setFirstPuttDistance(pl.getDistance()));
        return puttLength.map(PuttLength::getDistance).orElse(3);
    }

    private void saveTeeValue(View child, HoleDetails holeDetails) {
        ImageButton imageButton = (ImageButton) child;
        holeDetails.setTees(String.valueOf(imageButton.getTooltipText()));
    }

    private void saveToggleButtonValue(View v, View tableLayout, HoleDetails holeDetails) {
        ToggleButton tb = (ToggleButton) v;

//        String string = tb.getContext().getString(R.string.dFairway);
        if (tb.isChecked()) {
            CharSequence tbname = tb.getContext().getString(tb.getId());
            String text = tb.getTextOn().toString();
            Optional<Integer> matchedDrive = driveResultsIds.stream().filter(drs ->
                    tb.getContext().getString(drs).contentEquals(tbname)).findFirst();
            if (matchedDrive.isPresent()) {
//        Map<String, Integer> driveResultsMap = ShotOutcome.getDriveResultsMap();
//        if (driveResultsMap.containsValue(tb.getId())) {
//            case R.id.driveTableLayout:
                holeDetails.setFairway(addToStringValues(text, holeDetails.getFairway()));
            } else {
                Optional<Integer> matchedApproach = approachResultsStrings.stream().filter(drs ->
                        tb.getContext().getString(drs).contentEquals(tbname)).findFirst();
                if (matchedApproach.isPresent()) {

//                if (approachResultsStrings.contains(tbname)) {
//            case R.id.approachTableLayout:
                    // If the button value contains a number it's the approach distance
                    // otherwise it's the shot result
                    if (text.contains("G")) {
                        holeDetails.setGir("G");
                    } else {
                        holeDetails.setGir("");
                    }
                    holeDetails.setApproach(addToStringValues(text, holeDetails.getApproach()));
                }
            }
        }
    }

    private void saveTotal(View view, HoleDetails holeDetails) {
        String text;
        if (view.getId() == R.id.totalsLayout) {
            LinearLayout layout = (LinearLayout) view;
            int children = layout.getChildCount();
            for (int i = 0; i < children; i++) {
                View v = layout.getChildAt(i);
                if (v instanceof TextView) {
                    TextView tv = (TextView) v;
                    if (tv.getHint() != null && tv.getHint().toString().equals(view.getResources().getString(R.string.totalPutts))) {
                        holeDetails.setTotalPutts(tv.getText().toString());
                    }
                } else if (v instanceof Spinner) {
                    text = ((Spinner) v).getSelectedItem().toString();
                    holeDetails.setScore(text);
                }
            }
        } else if (view.getId() == R.id.approachDistanceLayout) {
            LinearLayout layout = (LinearLayout) view;
            EditText et = (EditText) layout.getChildAt(1);
            holeDetails.setApproachDistance(et.getText().toString());
        }
    }

    private String addToStringValues(String text, String values) {
        if (values == null) {
            values = text;
        } else {
            values = values.concat(text);
        }
        return values;
    }

    public void setHolePages(List<HoleScoreFragment> holePages) {
        this.holePages = holePages;
    }
}
