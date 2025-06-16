package minicla03.coinquylife.Dash.PRESENTATION.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

import minicla03.coinquylife.Auth.DOMAIN.model.User;
import minicla03.coinquylife.Dash.PRESENTATION.ViewModel.DashboardViewModel;
import minicla03.coinquylife.Expense.PRESENTATION.UI.ExpenseActivity;
import minicla03.coinquylife.ProfileAndSetting.PRESENTATION.UI.ProfileActivity;
import minicla03.coinquylife.R;
import minicla03.coinquylife.Rank.PRESENTATION.UI.RankingActivity;
import minicla03.coinquylife.Shift.PRESENTATION.UI.ShiftActivity;

public class DashboardActivity extends AppCompatActivity
{
    private ImageView imgProfile;
    private TextView tvHouseName;
    private ImageButton btnExpenses, btnRank, btnBoard, btnShifts;
    private DashboardViewModel dashboardViewModel;
    private final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_layout);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        String json = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                .getString("coinquyList", null);
        Type type = new TypeToken<List<User>>(){}.getType();
        List<User> listCoinquy = gson.fromJson(json, type);

        android.util.Log.d("DashboardActivity", "listCoinquy: " + listCoinquy);

        dashboardViewModel.setListUser(listCoinquy);

        imgProfile = findViewById(R.id.imgProfile);
        tvHouseName = findViewById(R.id.tvHouseName);
        btnExpenses = findViewById(R.id.btnExpenses);
        btnRank = findViewById(R.id.btnRank);
        btnBoard = findViewById(R.id.btnBoard);
        btnShifts = findViewById(R.id.btnShifts);

        tvHouseName.setOnClickListener(v-> {
            Toast.makeText(DashboardActivity.this, "HouseActivity name not implemented yet", Toast.LENGTH_SHORT).show();
            //intent0 = new Intent(DashboardActivity.this, ProfileActivity.class);
            //startActivity(intent1);
        });

        btnExpenses.setOnClickListener(v -> {
            Intent intent1 = new Intent(DashboardActivity.this, ExpenseActivity.class);
            startActivity(intent1);
        });

        btnRank.setOnClickListener(v -> {
            Intent intent2 = new Intent(DashboardActivity.this, RankingActivity.class);
            startActivity(intent2);
        });

        btnBoard.setOnClickListener(v -> {
            Toast.makeText(DashboardActivity.this, "Board not implemented yet", Toast.LENGTH_SHORT).show();
        });

        btnShifts.setOnClickListener(v -> {
            Intent intent4 = new Intent(DashboardActivity.this, ShiftActivity.class);
            startActivity(intent4);
        });

        imgProfile.setOnClickListener(v -> {
            Intent intent5 = new Intent(DashboardActivity.this, ProfileActivity.class);
            startActivity(intent5);
        });
    }
}
