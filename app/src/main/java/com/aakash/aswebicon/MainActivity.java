package com.aakash.aswebicon;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    EditText etUrl, etInclude, etExclude;
    ImageView ivIcon;
    TextView tvDomain, tvSource, tvError, tvFavicon;
    ProgressBar progressbar;
    Spinner spinnerMode;

    ASWebIconFetcher fetcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fetcher = new ASWebIconFetcher();

        etUrl = findViewById(R.id.etUrl);
        etInclude = findViewById(R.id.etInclude);
        etExclude = findViewById(R.id.etExclude);
        ivIcon = findViewById(R.id.ivIcon);
        tvDomain = findViewById(R.id.tvDomain);
        tvSource = findViewById(R.id.tvSource);
        tvError = findViewById(R.id.tvError);
        tvFavicon = findViewById(R.id.tvFavicon);
        progressbar = findViewById(R.id.progressbar);
        spinnerMode = findViewById(R.id.spinnerMode);
        Button btnFetch = findViewById(R.id.btnFetch);

        String[] modes = {"Basic", "Include Only", "Exclude Only", "Include + Exclude"};

        spinnerMode.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                modes));

        spinnerMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                etInclude.setVisibility(View.GONE);
                etExclude.setVisibility(View.GONE);

                if (position == 1) etInclude.setVisibility(View.VISIBLE);
                else if (position == 2) etExclude.setVisibility(View.VISIBLE);
                else if (position == 3) {
                    etInclude.setVisibility(View.VISIBLE);
                    etExclude.setVisibility(View.VISIBLE);
                }
            }

            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        btnFetch.setOnClickListener(v -> startFetch());
    }

    private void startFetch() {
        String url = etUrl.getText().toString().trim();
        String include = etInclude.getText().toString().trim();
        String exclude = etExclude.getText().toString().trim();

        if (url.isEmpty()) {
            Toast.makeText(this, "Enter URL", Toast.LENGTH_SHORT).show();
            return;
        }

        progressbar.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.GONE);

        ASWebIconFetcher.WebIconCallback callback = new ASWebIconFetcher.WebIconCallback() {
            @Override
            public void onSuccess(String iconUrl, String domain, String source) {
                runOnUiThread(() -> showResult(iconUrl, domain, source, null));
            }

            @Override
            public void onError(String error, String iconUrl, String domain) {
                runOnUiThread(() -> showResult(iconUrl, domain, "placeholder", error));
            }
        };

        switch (spinnerMode.getSelectedItemPosition()) {
            case 0: fetcher.fetch(url, callback); break;
            case 1: fetcher.fetchWithInclude(url, include, callback); break;
            case 2: fetcher.fetchWithExclude(url, exclude, callback); break;
            case 3: fetcher.fetchWithFilters(url, include, exclude, callback); break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void showResult(String iconUrl, String domain, String source, String error) {
        progressbar.setVisibility(View.GONE);

        tvDomain.setText("Domain: " + domain);
        tvSource.setText("Source: " + source);
        tvFavicon.setText("Favicon: " + iconUrl);

        Glide.with(this).load(iconUrl).into(ivIcon);

        if (error != null) {
            tvError.setVisibility(View.VISIBLE);
            tvError.setText("Error: " + error);
            tvError.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        } else {
            tvError.setVisibility(View.GONE);
        }
    }
}



//package com.aakash.aswebicon;
//
//import android.annotation.SuppressLint;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.aakash.ascryptorlib.AESCryptor;
//import com.aakash.aswebiconfetcher.ASWebIconFetcher;
//import com.bumptech.glide.Glide;
//
//public class MainActivity extends AppCompatActivity {
//    private static final String TAG = "MainActivity";
//
//    private EditText etUrl;
//    private ImageView ivIcon;
//    private TextView tvDomain, tvSource, tvError;
//    private ProgressBar progressbar;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        etUrl = findViewById(R.id.etUrl);
//        ivIcon = findViewById(R.id.ivIcon);
//        tvDomain = findViewById(R.id.tvDomain);
//        tvSource = findViewById(R.id.tvSource);
//        tvError = findViewById(R.id.tvError);
//        progressbar = findViewById(R.id.progressbar);
//        Button btnFetch = findViewById(R.id.btnFetch);
//
//        ASWebIconFetcher fetcher = new ASWebIconFetcher();
//
//        btnFetch.setOnClickListener(v -> {
//            progressbar.setVisibility(View.VISIBLE);
//
//            String url = etUrl.getText().toString().trim();
//
//            if (url.isEmpty()) {
//                Toast.makeText(this, "Please enter a website URL", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            tvError.setVisibility(View.GONE);
//
//            fetcher.fetch(url, new ASWebIconFetcher.WebIconCallback() {
//                @SuppressLint("SetTextI18n")
//                @Override
//                public void onSuccess(String iconUrl, String domain, String source) {
//                    runOnUiThread(() -> {
//                        progressbar.setVisibility(View.GONE);
//                        tvDomain.setText(domain);
//                        tvSource.setText("Source: " + source);
//
//                        Glide.with(MainActivity.this)
//                                .load(iconUrl)
//                                .into(ivIcon);
//                        try {
//                            String password = "secret123";
//                            String message =domain;
//
//                            String encrypted = AESCryptor.encrypt(password, message);
//
//
//                            String decrypted = AESCryptor.decrypt(password, encrypted);
//
//                            Log.d(TAG, "onSuccess: " +"Encrypted: " + encrypted);
//                            Log.d(TAG, "onSuccess: "+"Decrypted: " + decrypted);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    });
//                }
//
//                @SuppressLint("SetTextI18n")
//                @Override
//                public void onError(String errorMessage, String iconUrl, String domain) {
//                    runOnUiThread(() -> {
//                        progressbar.setVisibility(View.GONE);
//                        tvDomain.setText(domain);
//                        tvSource.setText("Source: placeholder");
//                        tvError.setText(errorMessage);
//                        tvError.setVisibility(View.VISIBLE);
//
//                        if (iconUrl != null) {
//                            Glide.with(MainActivity.this)
//                                    .load(iconUrl)
//                                    .into(ivIcon);
//                        }
//                    });
//                }
//            });
//        });
//    }
//}
