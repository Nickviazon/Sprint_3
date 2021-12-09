package com.example;

public class OrderTrack {
    public int track;

    public OrderTrack(int track) {
        this.track = track;
    }

    public static OrderTrack from(int track) {
        return new OrderTrack(track);
    }

}
