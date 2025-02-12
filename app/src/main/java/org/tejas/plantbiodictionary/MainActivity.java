package org.tejas.plantbiodictionary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
import java.io.ByteArrayOutputStream;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView resultTextView;
    private String base64Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        resultTextView = findViewById(R.id.resultTextView);
        Button captureButton = findViewById(R.id.captureButton);
        Button analyzeButton = findViewById(R.id.analyzeButton);

        // Capture Image
        captureButton.setOnClickListener(v ->
                ImagePicker.with(this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
//                       .createIntent(new ImagePicker.IntentCallback() {
//                            @Override
//                            public void onIntentCreated(Intent intent) {
//                                launcher.launch(intent);
//                            }
//                        })

        );


        // Analyze Image (Send to ChatGPT API)
        analyzeButton.setOnClickListener(v -> {
            if (base64Image != null) {
                analyzePlantDisease(base64Image);
            } else {
                resultTextView.setText("Capture an image first!");
            }
        });
    }

    // Activity Result Launcher for ImagePicker
    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri uri = result.getData().getData();
                    imageView.setImageURI(uri);
                    convertImageToBase64(uri);
                }
            });

    // Convert Image to Base64
    private void convertImageToBase64(Uri imageUri) {
        try {
            Bitmap bitmap = android.provider.MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            byte[] byteArray = outputStream.toByteArray();
            base64Image = Base64.encodeToString(byteArray, Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Send Image to ChatGPT API
    private void analyzePlantDisease(String base64Image) {
        String prompt = "Analyze this plant image and detect any disease. Provide possible remedies.";

        ChatGPTService service = RetrofitClient.getInstance().create(ChatGPTService.class);
        ChatGPTRequest request = new ChatGPTRequest("gpt-4-vision-preview", prompt, base64Image);

        Call<ChatGPTResponse> call = service.analyzeImage(request);
        call.enqueue(new Callback<ChatGPTResponse>() {
            @Override
            public void onResponse(Call<ChatGPTResponse> call, Response<ChatGPTResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    resultTextView.setText(response.body().getText());
                } else {
                    resultTextView.setText("Error: Unable to analyze image.");
                }
            }

            @Override
            public void onFailure(Call<ChatGPTResponse> call, Throwable t) {
                resultTextView.setText("Failed: " + t.getMessage());
            }
        });
    }
}
