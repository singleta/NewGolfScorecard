package com.example.newgolfscorecard;

import android.content.Context;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.newgolfscorecard.buttons.PuttButton;
import com.example.newgolfscorecard.shots.PuttLength;
import com.example.newgolfscorecard.main.HoleScoreFragment;

//import android.test.mock.MockContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import androidx.constraintlayout.widget.ConstraintLayout;

public class SaveHoleDataTest  {

    SaveHoleData saveHoleData;

    @Mock
    PuttButton puttButton;
    @Mock
    Context context;
    @Mock
    List<HoleScoreFragment> holePages;
    @Mock
    HoleScoreFragment holeScoreFragment;
    @Mock
    HoleDetails holeDetails;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        saveHoleData = new SaveHoleData(holePages);
//        holePages.add(holeScoreFragment);
    }

    @Test
    void testSavePuttButtonValue() {
        doReturn("1").when(puttButton).getText();
        doReturn(PuttLength.HUGE).when(puttButton).getPuttLength();

        HoleScoreFragment holeScoreFragment = new HoleScoreFragment();
        saveHoleData.savePuttButtonValue(puttButton, holeScoreFragment);
        List<PuttLength> puttDistances = holeScoreFragment.getPuttDistances();
//        List<PuttLength> puttDistances = saveHoleData.getPuttDistances();

        assertThat(puttDistances.size(), is(1));
    }

    @Test
    void testSaveFirstPuttDistance() {
        doReturn(PuttLength.HUGE, PuttLength.LONG, PuttLength.SHORT).when(puttButton).getPuttLength();
        List<PuttButton> puttButtons = new ArrayList<>();
        puttButtons.add(puttButton);
        puttButtons.add(puttButton);
        puttButtons.add(puttButton);

        List<PuttLength> puttLengths = new ArrayList<>();
        puttButtons.forEach(pb -> puttLengths.add(pb.getPuttLength()));
        HoleScoreFragment holeScoreFragment = new HoleScoreFragment();
        holeScoreFragment.setPuttDistances(puttLengths);


        saveHoleData.findFirstPuttDistance(holeDetails, holeScoreFragment);
        List<PuttLength> puttDistances = holeScoreFragment.getPuttDistances();

        assertThat(puttDistances.size(), is(3));
        assertThat(puttDistances.get(0).getDistance(), is(PuttLength.HUGE.getDistance()));
    }


    @Test
    void testSaveFirstPuttDistance_TwoHoles() {
        doReturn(PuttLength.HUGE, PuttLength.LONG, PuttLength.SHORT).when(puttButton).getPuttLength();
        List<PuttButton> puttButtons = new ArrayList<>();
        puttButtons.add(puttButton);
        puttButtons.add(puttButton);
        puttButtons.add(puttButton);

        List<PuttLength> puttLengths = new ArrayList<>();
        puttButtons.forEach(pb -> puttLengths.add(pb.getPuttLength()));
        holeScoreFragment.setPuttDistances(puttLengths);

        saveHoleData.findFirstPuttDistance(holeDetails, holeScoreFragment);
        List<PuttLength> puttDistances = holeScoreFragment.getPuttDistances();

        assertThat(puttDistances.size(), is(3));
        assertThat(puttDistances.get(0).getDistance(), is(PuttLength.HUGE.getDistance()));
    }

    @Test
    void saveData_long() {
//        List<HoleScoreFragment> mockIterable = mock(List.class);
//        doReturn(mockIterable).when(holePages).listIterator();
//        MockIterator.mockIterable(mockIterable, holeScoreFragment, holeScoreFragment);
        Bundle arguments = mock(Bundle.class);
        ConstraintLayout layout = mock(ConstraintLayout.class);
        TableLayout tl = mock(TableLayout.class);
        TableRow tr = mock(TableRow.class);

        doReturn(holeScoreFragment, holeScoreFragment).when(holePages).get(anyInt());
        doReturn(arguments).when(holeScoreFragment).getArguments();
        doReturn(1,2).when(arguments).get(anyString());
        doReturn(layout).when(holeScoreFragment).getView();
        doReturn(1).when(layout).getChildCount();
        doReturn(tl).when(layout).getChildAt(anyInt());
        doReturn(2).when(tl).getChildCount();
        doReturn(tr).when(tl).getChildAt(anyInt());
        doReturn(1).when(tr).getChildCount();
        doReturn(puttButton).when(tr).getChildAt(anyInt());
        doReturn("3", "1", "2", "1").when(puttButton).getText();
        doReturn(PuttLength.HUGE, PuttLength.SHORT, PuttLength.MEDIUM, PuttLength.SHORT).when(puttButton).getPuttLength();

        List<HoleDetails> holeDetails = saveHoleData.saveData();

        assertThat(holeDetails.size(), is(2));
    }

    @Test
    void saveData() {
        SaveHoleData shd = spy(SaveHoleData.class);
        doReturn(holeDetails).when(shd).saveSingleHoleData(any(), any());
        doCallRealMethod().when(shd).setHolePages(anyList());

        List<HoleScoreFragment> holePages = new ArrayList<>();
        holePages.add(new HoleScoreFragment());
        shd.setHolePages(holePages);

        List<HoleDetails> holeDetails = shd.saveData();

        assertThat(holeDetails.size(), is(1));
    }

    @Test
    void saveData_Null() {
        SaveHoleData shd = spy(SaveHoleData.class);
        doNothing().when(shd).saveSingleHoleData(any(), any());
        doCallRealMethod().when(shd).setHolePages(anyList());

        List<HoleScoreFragment> holePages = new ArrayList<>();
        holePages.add(null);
        shd.setHolePages(holePages);

        List<HoleDetails> holeDetails = shd.saveData();

        assertThat(holeDetails.size(), is(0));
    }


    @Test
    void saveFirstPuttDistance() {
        HoleDetails hd = new HoleDetails();
        HoleScoreFragment hsf = new HoleScoreFragment();
        List<PuttLength> puttLengths = new ArrayList<>();
        puttLengths.add(PuttLength.SHORT);
        puttLengths.add(PuttLength.HUGE);
        hsf.setPuttDistances(puttLengths);

        saveHoleData.findFirstPuttDistance(hd, hsf);

        assertThat(hd.getFirstPuttDistance(), is(PuttLength.HUGE.getDistance()));
    }

//    protected void savePuttButtonValue(View v) {
//        PuttButton puttButton = (PuttButton)v;
//        if (puttButton.getText().length()==1) {
//            puttDistances.add(puttButton.getPuttLength());
//        }
//    }
//
//    protected void saveFirstPuttDistance() {
//        Optional<PuttLength> puttLength = puttDistances.stream().max((putt1, putt2) -> putt1.getDistance().compareTo(putt2.getDistance()));
//        puttLength.ifPresent(this::accept);
//    }


}
