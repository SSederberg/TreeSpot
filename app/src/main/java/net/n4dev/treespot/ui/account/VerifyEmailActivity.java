package net.n4dev.treespot.ui.account;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.orhanobut.logger.Logger;

import net.n4dev.treespot.databinding.ActivityVerifyEmailBinding;
import net.n4dev.treespot.ui.TreeSpotActivity;
import net.n4dev.treespot.ui.main.MainActivity;
import net.n4dev.treespot.util.ActivityUtil;

public class VerifyEmailActivity extends TreeSpotActivity {

    public static final String ARG_USER_USERNAME = "ARG_USER_USERNAME";
    public static final String ARG_USER_UUID = "ARG_USER_UUID";
    public static final String ARG_USER_EMAIL = "ARG_USER_EMAIL";

    private ActivityVerifyEmailBinding binding;
    private String email;
    private String username;
    private String accountID;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyEmailBinding.inflate(getLayoutInflater());

        if(getIntent().getExtras() != null) {
            setupFromArgs(getIntent().getExtras());
        }else {
            Logger.e("Failed to verify email/account! The required Bundle was null!");
        }
        binding.setEmailAddress(email);
        setContentView(binding.getRoot());

        binding.verifyEmailAuth.setOnClickListener(l -> {
            binding.verifyEmailLoading.setVisibility(View.VISIBLE);
            ActivityUtil.Companion.startActivity(MainActivity.class, this);
        });

    }

    private void setupFromArgs(Bundle extras) {
        accountID = extras.getString(ARG_USER_UUID);
        email = extras.getString(ARG_USER_EMAIL);
        username = extras.getString(ARG_USER_USERNAME);
    }
}
