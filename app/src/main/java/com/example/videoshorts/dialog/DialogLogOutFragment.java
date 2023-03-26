package com.example.videoshorts.dialog;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.videoshorts.R;
import com.example.videoshorts.view.activity.LogInActivity;
import com.example.videoshorts.view.activity.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.Objects;

public class DialogLogOutFragment extends DialogFragment {
    private Button btnCancel;
    private Button btnOk;
    private final MainActivity activity;

    public DialogLogOutFragment(MainActivity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Objects.requireNonNull(getDialog()).getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return inflater.inflate(R.layout.custom_alert_dialog_exit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnOk = view.findViewById(R.id.btnOk);
        handleClick();
    }

    private void handleClick() {
        btnCancel.setOnClickListener(view -> dismiss());
        btnOk.setOnClickListener(view -> {
            GoogleSignInOptions gso = new GoogleSignInOptions
                    .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .build();
            GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
            googleSignInClient.signOut();

            Intent intent = new Intent(activity, LogInActivity.class);
            startActivity(intent);
            activity.finish();
        });
    }
}