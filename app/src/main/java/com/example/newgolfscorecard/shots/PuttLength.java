package com.example.newgolfscorecard.shots;

public enum PuttLength {
    ZERO( "None", 0),
    SHORT("0-10", 1),
    MEDIUM( "10-20", 2),
    LONG("20-30", 3),
    HUGE( "30+", 4);


    private String puttName;
    private Integer distance;

    PuttLength(String puttName, Integer distance) {

        this.puttName = puttName;
        this.distance = distance;
    }



    public String getPuttName() {
        return puttName;
    }
    public Integer getDistance() { return distance; }
}
