package com.example.newgolfscorecard;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.newgolfscorecard.shots.ShotOutcome;


import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class ShotOutcomeTest  {

    @Test
    public void testGetDriveResults() {
        Map<String, Integer> driveResultsMap = ShotOutcome.getDriveResultsMap();

        assertNotNull(driveResultsMap);
        assertThat(driveResultsMap.size(), Is.is(8));

    }
    
    @Test
    public void testGetApproachResults() {
        Map<String, Integer> approachResultsMap = ShotOutcome.getApproachResultsMap();

        assertNotNull(approachResultsMap);
        assertThat(approachResultsMap.size(), Is.is(15));

    }
}