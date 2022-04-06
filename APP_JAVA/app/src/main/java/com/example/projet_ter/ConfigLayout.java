package com.example.projet_ter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CompoundButton;

import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;

import com.argos.utils.FrameAnalyzer;

import java.util.ArrayList;
import java.util.List;

public class ConfigLayout {

    private static final String TAG = "ProjetTER::ConfigLayout";

    private final View layout;
    private final View topLayout;

    private final FrameAnalyzer frameAnalyzer;

    private final ImageButton buttonChangeAlgo;

    private final Group kMeansGroup;
    private final Group hsvGroup;

    private final TextView agloNameField;

    private final SeekBar minAreaSeekBar;
    private final SeekBar nbClusterSeekBar;
    private final SeekBar nbItSeekBar;
    private final SeekBar thresholdSeekBar;

    private final SeekBar hSeekBar;
    private final SeekBar sSeekBar;
    private final SeekBar vSeekBar;

    private boolean isVisible = false;

    /**
     * Constructor
     * @param layout the config layout
     */
    public ConfigLayout(View layout, FrameAnalyzer frameAnalyzer) {
        this.layout = layout;
        this.frameAnalyzer = frameAnalyzer;
        // Getting the top layout
        this.topLayout = this.layout.findViewById(R.id.titleLayout);
        // Getting the group
        this.kMeansGroup = this.layout.findViewById(R.id.kmeansGroup);
        this.hsvGroup = this.layout.findViewById(R.id.hsvGroup);
        // Getting the ImageButton
        this.buttonChangeAlgo = this.layout.findViewById(R.id.swapbutton);
        // Getting the text field
        this.agloNameField = this.layout.findViewById(R.id.textAlgo);
        // Getting the kMeans seekBar
        this.minAreaSeekBar = this.layout.findViewById(R.id.seekBarMinArea);
        this.nbClusterSeekBar = this.layout.findViewById(R.id.seekBarNbCluster);
        this.nbItSeekBar = this.layout.findViewById(R.id.seekBarNbIt);
        this.thresholdSeekBar = this.layout.findViewById(R.id.seekBarThreshold);
        // Getting the hsv seekBar
        this.hSeekBar = this.layout.findViewById(R.id.seekBarH);
        this.sSeekBar = this.layout.findViewById(R.id.seekBarS);
        this.vSeekBar = this.layout.findViewById(R.id.seekBarV);

        // Set a listener for the swapbutton
        this.buttonChangeAlgo.setOnClickListener(new View.OnClickListener() {
            // Set the boolean to check which algo is running
            boolean algoswap = false;
            @Override
            public void onClick(View view) {
                if (algoswap)
                    algoswap = false;
                else
                    algoswap = true;
                if (algoswap) {
                    agloNameField.setText("HSV");
                    hsvGroup.setVisibility(View.VISIBLE);
                    kMeansGroup.setVisibility(View.INVISIBLE);
                    frameAnalyzer.targetZoneFinderMethod = FrameAnalyzer.TargetZoneFinderMethod.HSV;
                } else {
                    agloNameField.setText("K-MEANS");
                    kMeansGroup.setVisibility(View.VISIBLE);
                    hsvGroup.setVisibility(View.INVISIBLE);
                    frameAnalyzer.targetZoneFinderMethod = FrameAnalyzer.TargetZoneFinderMethod.K_MEANS;
                }
            }
        });

        this.topLayout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                setVisible(!is_visible());
                isVisible = !isVisible;
            }
        });

        this.minAreaSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                frameAnalyzer.KmeansTargetZoneFinder.setMin_area_contour(i);
                frameAnalyzer.HSVTargetZoneFinder.setMin_area_contour(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        this.nbClusterSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                frameAnalyzer.KmeansTargetZoneFinder.setClustersNumber(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        this.nbItSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                frameAnalyzer.KmeansTargetZoneFinder.setAttemptNumber(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        this.thresholdSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                frameAnalyzer.KmeansTargetZoneFinder.setThreshold(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        this.hSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                frameAnalyzer.HSVTargetZoneFinder.setHue_value(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        this.sSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                frameAnalyzer.HSVTargetZoneFinder.setSaturation_value(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        this.vSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                frameAnalyzer.HSVTargetZoneFinder.setValue_value(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    /**
     * Set the sliders configuration visibility
     * @param visible true to visible
     */
    @RequiresApi(api = Build.VERSION_CODES.N) // For lambda function
    public void setVisible(boolean visible) {
        // Getting the current param layout
        ConstraintLayout.LayoutParams layoutP = (ConstraintLayout.LayoutParams) layout.getLayoutParams();
        // Set the new value to the element on the screen
        float pixels = 0;
        if (visible) {
            pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 420, layout.getContext().getResources().getDisplayMetrics());
        }
        layoutP.height = (int) pixels;
        // Apply new param to the layout
        this.layout.setLayoutParams(layoutP);
    }

    /**
     * return the visibility of the layout
     * @return true if visible
     */
    public boolean is_visible() {
        return this.isVisible;
    }

}
