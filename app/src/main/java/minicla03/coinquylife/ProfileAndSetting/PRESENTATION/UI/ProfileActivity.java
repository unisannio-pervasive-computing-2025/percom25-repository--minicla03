package minicla03.coinquylife.ProfileAndSetting.PRESENTATION.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.Objects;

import minicla03.coinquylife.ProfileAndSetting.PRESENTATION.ViewModel.ProfileViewModel;
import minicla03.coinquylife.R;

public class ProfileActivity extends AppCompatActivity
{
    private ImageView imgProfile;
    private TextView tvName, tvRole;
    private EditText etBio;
    private MaterialToolbar toolbar;
    private ProfileViewModel profileViewModel;

    private static final int REQUEST_GALLERY = 1;
    private static final int REQUEST_CAMERA = 2;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        profileViewModel = new ProfileViewModel(getApplication());
        String token = getSharedPreferences("app_prefs", MODE_PRIVATE)
                .getString("token", null);
        String houseId = getSharedPreferences("app_prefs", MODE_PRIVATE)
                .getString("houseId", null);
        profileViewModel.loadUserProfile(token,houseId);

        profileViewModel.getUserLiveData().observe(this, user ->
        {
            if (user != null)
            {
                tvName.setText(user.getName());
                if (user.getProfileImage() != null)
                {
                    //imgProfile.setImageDrawable(Converters.toDrawable(user.getProfileImage()));
                }
            }
        });
        initializeUI();
        setupListeners();
    }

    private void initializeUI()
    {
        imgProfile = findViewById(R.id.imgProfile);
        tvName = findViewById(R.id.tvName);
        etBio = findViewById(R.id.etBio);
        toolbar = findViewById(R.id.topAppBar);
    }

    private void showBioEditDialog() {
        EditText input = new EditText(this);
        input.setText(etBio.getText().toString());

        new AlertDialog.Builder(this)
                .setTitle("Modifica Bio")
                .setView(input)
                .setPositiveButton("Salva", (dialog, which) -> {
                    etBio.setText(input.getText().toString());
                })
                .setNegativeButton("Annulla", null)
                .show();
    }

    private void showImagePickerDialog()
    {
        String[] options = {"Galleria", "Fotocamera"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scegli immagine profilo")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, REQUEST_GALLERY);
                    } else {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, REQUEST_CAMERA);
                    }
                }).show();
    }

    private void setupListeners()
    {
        imgProfile.setOnClickListener(v -> {
            showImagePickerDialog();
        });

        etBio.setOnClickListener(v->{
            showBioEditDialog();
        });

        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });

        toolbar.setOnMenuItemClickListener(item ->
        {
            if (item.getItemId() == R.id.action_settings) {
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
            }
            return false;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK || data == null) return;

        if (requestCode == REQUEST_GALLERY)
        {
            Uri selectedImage = data.getData();
            if (selectedImage != null)
            {
                imgProfile.setImageURI(selectedImage);

            }
        }
        else if (requestCode == REQUEST_CAMERA)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            if (photo != null)
            {
                imgProfile.setImageBitmap(photo);

            }
        }
    }
}