/*
 * Copyright 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.mrgabriel.tic_tac_toe;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class splash extends AppCompatActivity {
    private ImageView appLogo;
    private Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Find appLogo & playButton on xml
        appLogo = (ImageView) findViewById(R.id.logo);
        playButton = (Button) findViewById(R.id.play_button);

        // Animate appLogo & Button onScreenLoad
        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.transition);
        appLogo.startAnimation(myanim);
        playButton.startAnimation(myanim);

        //set onclickListener on the play button
        playButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Create a new intent to open MainActivity
                Intent mainActivityIntent = new Intent(splash.this, MainActivity.class);
                startActivity(mainActivityIntent);
            }
        });
    }

    // create an exit dialog
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.exit);
        builder.setMessage(R.string.exit_message);

        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Do nothing
            }
        });

        AlertDialog dialog = builder.show();


    }


}
