package com.example.newgolfscorecard.main;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
//import androidx.lifecycle.ViewModelProviders;

import com.example.newgolfscorecard.MainActivity;
import com.example.newgolfscorecard.R;
import com.example.newgolfscorecard.buttons.ButtonStore;
import com.example.newgolfscorecard.shots.PuttLength;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class HoleScoreFragment extends Fragment {

    public static final int TEE = 1;
    public static final int DRIVE = 2;
    public static final int APPROACH = 3;
    public static final int PUTTING = 4;
    Spinner scoreSelector;
    TextView roundScore;
    private List<PuttLength> puttDistances = new ArrayList<>();
    ButtonStore buttonStore;

    private static final String TAG = HoleScoreFragment.class.getSimpleName();

    public static final String ARG_SECTION_NUMBER = "section_number";

    public HoleScoreFragment newInstance(int index) {
        Log.d(TAG, "newInstance");
        HoleScoreFragment fragment = new HoleScoreFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        PageViewModel pageViewModel = ViewModelProvider.NewInstanceFactory.getInstance().create(PageViewModel.class);
//        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_hole_template, container, false);
//        View root = FragmentHoleTemplateBinding.inflate(inflater).constraintLayout;
        buttonStore = new ButtonStore(root);
        placeButtons(root);
        return root;
    }



    private void placeButtons(View view) {
        Log.d(TAG, "placeButtons");
        placeButtons(view, buttonStore.getTeeButtons(), TEE);
        placeButtons(view, buttonStore.getDriveButtons(), DRIVE);
        placeButtons(view, buttonStore.getApproachButtons(), APPROACH);
        addApproachDistance(view);
        placeButtons(view, buttonStore.getPuttButtons(), PUTTING);
        addTotalFields(view);
    }

    private void placeButtons(View view, Map<String, View> buttons, int section) {
        int id = 0;
        int label = 0;
        switch (section) {
            case DRIVE:
                id = R.id.driveTableLayout;
                label = R.string.drive;
                break;
            case APPROACH:
                id = R.id.approachTableLayout;
                label = R.string.approach;
                break;
            case PUTTING:
                id = R.id.puttsTableLayout;
                label = R.string.putting;
//                label = R.string.tees;
                break;
            case TEE:
                id = R.id.teeLayout;
                label = R.string.tees;
                break;
        }
        layoutTable(view, buttons, section, id, label);
    }

    private void layoutTable(View view, Map<String, View> buttons, int section, int id, int label) {
        TableLayout layout = view.findViewById(id);
        TableRow tr = new TableRow(layout.getContext());
        if (section == TEE) {
            tr.setGravity(Gravity.END);
        }
        layout.addView(tr);
        TextView rowLabel = new TextView(layout.getContext());
        rowLabel.setText(label);
//        rowLabel.setTextAppearance(R.style.TextAppearance_AppCompat_Button);
        tr.setLayoutParams(new TableRow.LayoutParams(0));
        tr.addView(rowLabel);
        for (Map.Entry<String, View> es : buttons.entrySet()) {
            if (tr.getChildCount() == 5 ||
//                    es.getKey().equals(ShotOutcome.A_FORTY.name()) ||
//                    es.getKey().equals(ShotOutcome.A_TWO_HUNDRED.name()) ||
                    es.getKey().equals(getString(R.string.puttZero))) {
                tr = new TableRow(layout.getContext());
                layout.addView(tr);
            }
            tr.addView(es.getValue());
        }
    }

    private void addApproachDistance(View v) {
        Log.d(TAG, "addApproachDistanceView");
        TextView approachDistanceLabel = new TextView(v.getContext());
        approachDistanceLabel.setText("Approach Distance");
        EditText approachDistance = buttonStore.getApproachDistanceView();
        approachDistance.setMinWidth(200);
        approachDistance.setGravity(Gravity.CENTER);
        approachDistance.setTextSize(22);
        approachDistance.setText("100");
        approachDistance.setInputType(InputType.TYPE_CLASS_NUMBER);
        approachDistance.setSelectAllOnFocus(true);
        approachDistance.clearFocus();

        LinearLayout layout = v.findViewById(R.id.approachDistanceLayout);
        layout.addView(approachDistanceLabel);
        layout.addView(approachDistance);

    }

    private void addTotalFields(View v) {
        Log.d(TAG, "addTotalFields");
        Integer[] score = new Integer[] {0,1,2,3,4,5,6,7,8,9,10};
        TextView totalPuttsLabel = new TextView(v.getContext());
        totalPuttsLabel.setText(getString(R.string.totalPutts));
        TextView totalPutts = buttonStore.getTotalPuttsView();
        totalPutts.setMinWidth(50);
        totalPutts.setGravity(Gravity.CENTER);
        totalPutts.setTextSize(22);
        totalPutts.setText("0");
        totalPutts.setHint(R.string.totalPutts);
        TextView holeScore = new TextView(v.getContext());
        holeScore.setText(getString(R.string.holeScore));
        holeScore.setGravity(Gravity.END);
        setupScoreSelector(v, score);


        roundScore = new TextView(v.getContext());

        roundScore.setGravity(Gravity.END);
        roundScore.setText(R.string.zero);
        layoutTotalItems(v, totalPuttsLabel, totalPutts, holeScore);
//        v.add
    }

    private void setupScoreSelector(View v, Integer[] score) {
        scoreSelector = new Spinner(v.getContext());
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(v.getContext(), R.layout.spinner_custom_layout, score);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        scoreSelector.setAdapter(adapter);
        scoreSelector.setSelection(4, false);
        scoreSelector.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateRoundScore(adapterView.getSelectedItemPosition());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //

            }
        });
    }

    private void layoutTotalItems(View v, TextView totalPuttsLabel, TextView totalPutts, TextView holeScore) {
        LinearLayout layout = v.findViewById(R.id.totalsLayout);
        Space space = new Space(getContext());
        space.setMinimumWidth(200);
        layout.addView(space);
        layout.addView(totalPuttsLabel);
        layout.addView(totalPutts);
        layout.addView(holeScore);
        layout.addView(scoreSelector);
        layout.addView(roundScore);
    }


    public void updateRoundScore(int score) {
//        int score = Integer.parseInt(scoreSelector.getSelectedItem().toString());
//        roundScore.setText(String.valueOf(MainActivity.getOverallScore()+ score));
//        MainActivity.setOverallScore(MainActivity.getOverallScore() + score);
//        return MainActivity.getOverallScore()+ score;
        roundScore.setText(String.valueOf(((MainActivity)getActivity()).getOverallScore()));
    }

    public int getHoleScore() {
        return scoreSelector.getSelectedItemPosition();
    }

    public List<PuttLength> getPuttDistances() {
        return puttDistances;
    }

    public void setPuttDistances(List<PuttLength> puttDistances) {
        this.puttDistances = puttDistances;
    }
}