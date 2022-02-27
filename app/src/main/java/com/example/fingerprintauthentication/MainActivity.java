package com.example.fingerprintauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {
    BiometricManager biometricManager;
    BiometricPrompt biometricPrompt;
    TextView msgText;
    Button letsgo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        biometricManager= androidx.biometric.BiometricManager.from(this);
        msgText=findViewById(R.id.msg_txt);
        letsgo=findViewById(R.id.click);
        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                msgText.setText("You can use fingerprint to login");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                msgText.setText("This device has no fingerprint");
                letsgo.setVisibility(View.GONE);

                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                msgText.setText("Sensor is currently unavailable");
                letsgo.setVisibility(View.GONE);
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                msgText.setText("No previous fingerprint, Please set a fingerprint in your security option");
                letsgo.setVisibility(View.GONE);
                break;
        }
        Executor executor= ContextCompat.getMainExecutor(this);
        final BiometricPrompt biometricPrompt=new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });
        final BiometricPrompt.PromptInfo promptInfo= new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Login")
                .setDescription("Use your fingerprint to use the app")
                .setNegativeButtonText("Cancel")
                .build();
        letsgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate(promptInfo);
                Intent intent =new Intent(getApplicationContext(),Next.class);
                startActivity( intent);
            }
        });

    }
}
