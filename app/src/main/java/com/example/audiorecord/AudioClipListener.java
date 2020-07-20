package com.example.audiorecord;

import android.annotation.SuppressLint;
import android.util.Log;

import java.util.LinkedList;

public class AudioClipListener {

    private static final String TAG = "ConsistentFrequencyDetector";
    private LinkedList<Integer> frequencyHistory;
    private int rangeThreshold;
    private int silenceThreshold;
    public static final int DEFAULT_SILENCE_THRESHOLD = 2000;



    @SuppressLint("LongLogTag")
    public boolean heard(short[] audioData, int sampleRate)
    {
        int frequency = ZeroCrossing.calculate(sampleRate, audioData);
        frequencyHistory.addFirst(frequency);
        // since history is always full, just remove the last
        frequencyHistory.removeLast();
        int range = calculateRange();
        boolean heard = false;
        if (range < rangeThreshold)
        {
            // only trigger it isn't silence

        }
        return heard;
    }
    private int calculateRange()
    {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (Integer val : frequencyHistory)
        {
            if (val >= max)
            {
                max = val;
            }
            if (val < min)
            {
                min = val;
            }
        }
        return max - min;
    }

    private double rootMeanSquared(short[] nums)
    {
        double ms = 0;
        for (int i = 0; i < nums.length; i++)
        {
            ms += nums[i] * nums[i];
        }
        ms /= nums.length;
        return Math.sqrt(ms);
    }
}
