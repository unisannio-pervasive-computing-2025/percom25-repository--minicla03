package minicla03.coinquylife.Rank.PRESENTATION.UI;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Map;
import java.util.Objects;
import java.util.List;
import java.util.ArrayList;

import android.widget.TextView;
import minicla03.coinquylife.Rank.DOMAIN.model.Classifica;
import minicla03.coinquylife.R;
import minicla03.coinquylife.Rank.PRESENTATION.ViewModel.RankViewModel;

public class RankingActivity extends AppCompatActivity
{
    private LinearLayout linearLayoutRanking;
    private Button btnBackToHome;
    private RankViewModel rankViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rank_layout);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        linearLayoutRanking = findViewById(R.id.cardViewRanking).findViewById(R.id.rankingLayout);
        btnBackToHome = findViewById(R.id.btnBack);
        rankViewModel= new ViewModelProvider(this).get(RankViewModel.class);
        rankViewModel.getClassificaLiveData().observe(this, classificaMap ->
        {
            if (classificaMap != null && !classificaMap.isEmpty()) {
                populateRanking(classificaMap);
            }
        });
        rankViewModel.fetchClassifica();
        btnBackToHome.setOnClickListener(v -> finish());
    }

    private void populateRanking(Map<String, Classifica> classificaMap)
    {
        linearLayoutRanking.removeAllViews();
        if (classificaMap != null && !classificaMap.isEmpty())
        {
            List<Map.Entry<String, Classifica>> sortedList = new ArrayList<>(classificaMap.entrySet());
            sortedList.sort((a, b) -> Integer.compare(b.getValue().getTotalPoint(), a.getValue().getTotalPoint()));

            for (int i = 0; i < sortedList.size(); i++)
            {
                Map.Entry<String, Classifica> entry = sortedList.get(i);
                String username = entry.getKey();
                Classifica classifica = entry.getValue();

                View rankingItem = getLayoutInflater().inflate(R.layout.ranking_item, linearLayoutRanking, false);

                TextView tvPosition = rankingItem.findViewById(R.id.tvPosition);
                TextView tvUsername = rankingItem.findViewById(R.id.tvUsername);
                TextView tvScore = rankingItem.findViewById(R.id.tvScore);

                int posizione = i + 1;
                String medaglia = "";
                if (posizione == 1) medaglia = "🥇";
                else if (posizione == 2) medaglia = "🥈";
                else if (posizione == 3) medaglia = "🥉";

                tvPosition.setText(medaglia.isEmpty() ? posizione + "." : medaglia);
                tvUsername.setText(username);
                tvScore.setText(classifica.getTotalPoint() + " pt");

                linearLayoutRanking.addView(rankingItem);
            }
        }
        else
        {
            View errorView = getLayoutInflater().inflate(R.layout.ranking_error_item, linearLayoutRanking, false);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            errorView.setLayoutParams(layoutParams);
            linearLayoutRanking.addView(errorView);
        }
    }
}
