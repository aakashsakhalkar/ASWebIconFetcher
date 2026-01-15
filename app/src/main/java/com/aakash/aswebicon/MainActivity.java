package com.aakash.aswebicon;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private EditText etUrl;
    private ImageView ivIcon;
    private TextView tvDomain, tvSource, tvError;
    private ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUrl = findViewById(R.id.etUrl);
        ivIcon = findViewById(R.id.ivIcon);
        tvDomain = findViewById(R.id.tvDomain);
        tvSource = findViewById(R.id.tvSource);
        tvError = findViewById(R.id.tvError);
        progressbar = findViewById(R.id.progressbar);
        Button btnFetch = findViewById(R.id.btnFetch);

        ASWebIconFetcher fetcher = new ASWebIconFetcher();

        btnFetch.setOnClickListener(v -> {
            progressbar.setVisibility(View.VISIBLE);

            String url = etUrl.getText().toString().trim();

            if (url.isEmpty()) {
                Toast.makeText(this, "Please enter a website URL", Toast.LENGTH_SHORT).show();
                return;
            }

            tvError.setVisibility(View.GONE);

            fetcher.fetch(url, new ASWebIconFetcher.WebIconCallback() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onSuccess(String iconUrl, String domain, String source) {
                    runOnUiThread(() -> {
                        progressbar.setVisibility(View.GONE);
                        tvDomain.setText(domain);
                        tvSource.setText("Source: " + source);

                        Glide.with(MainActivity.this)
                                .load(iconUrl)
                                .into(ivIcon);
                    });
                }

                @SuppressLint("SetTextI18n")
                @Override
                public void onError(String errorMessage, String iconUrl, String domain) {
                    runOnUiThread(() -> {
                        progressbar.setVisibility(View.GONE);
                        tvDomain.setText(domain);
                        tvSource.setText("Source: placeholder");
                        tvError.setText(errorMessage);
                        tvError.setVisibility(View.VISIBLE);

                        if (iconUrl != null) {
                            Glide.with(MainActivity.this)
                                    .load(iconUrl)
                                    .into(ivIcon);
                        }
                    });
                }
            });
        });
    }
}
