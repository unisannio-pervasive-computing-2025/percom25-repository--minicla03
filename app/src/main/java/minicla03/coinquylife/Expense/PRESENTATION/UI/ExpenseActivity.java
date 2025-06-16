package minicla03.coinquylife.Expense.PRESENTATION.UI;

import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import minicla03.coinquylife.Auth.DOMAIN.model.User;
import minicla03.coinquylife.Expense.DOMAIN.model.Expense;
import minicla03.coinquylife.Expense.DOMAIN.model.StatusExpense;
import minicla03.coinquylife.Expense.PRESENTATION.ViewModel.ExpenseViewModel;
import minicla03.coinquylife.R;

public class ExpenseActivity extends AppCompatActivity
{
    private ExpenseViewModel expenseViewModel;
    private RecyclerView rvExpenses;
    private ExpenseAdapter adapter;
    private List<Expense> expenseList;

    private MaterialButton btnAddExpense;
    private MaterialButton btnRiepilogoBilancio;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_layout);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        rvExpenses = findViewById(R.id.rv_expenses);
        btnAddExpense = findViewById(R.id.btn_add_expense);
        btnRiepilogoBilancio = findViewById(R.id.btn_riepilogo_bilancio);
        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        expenseList= expenseViewModel.getExpenses().getValue()==null ? new ArrayList<>() : expenseViewModel.getExpenses().getValue();

        adapter = new ExpenseAdapter(expenseList);

        rvExpenses.setLayoutManager(new LinearLayoutManager(this));
        rvExpenses.setAdapter(adapter);
        adapter = new ExpenseAdapter(expenseList);
        rvExpenses.setLayoutManager(new LinearLayoutManager(this));
        rvExpenses.setAdapter(adapter);
        MaterialButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());


        adapter.setOnPayClickListener(position ->
        {
            Expense expense = expenseList.get(position);

            new AlertDialog.Builder(this)
                    .setTitle("Conferma pagamento")
                    .setMessage("Vuoi segnare questa spesa come saldata?")
                    .setPositiveButton("Sì", (dialog, which) ->
                    {
                        expenseViewModel.updateExpenseStatus(expense.getId());
                        expenseViewModel.getUpdateExpenseStatusLiveData().observe(this, expenseResult ->
                        {
                            if (expenseResult != null)
                            {
                                expense.setStatus(StatusExpense.SETTLED);
                                expenseList.remove(position);
                                adapter.notifyItemRemoved(position);
                                Toast.makeText(this, "Spesa segnata come saldata!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "Errore nell'aggiornamento della spesa", Toast.LENGTH_SHORT).show();
                            }
                        });
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        String houseCode = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                .getString("houseCode", null);
        String json = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                .getString("coinquyList", null);
        List<User> listCoinquy = new Gson().fromJson(json, new TypeToken<List<User>>() {
        }.getType());
        expenseViewModel.setListCoinquy(listCoinquy);
        expenseViewModel.setHouseId(houseCode);
        expenseViewModel.getAllExpenses(houseCode);

        btnAddExpense.setOnClickListener(v -> showAddExpenseDialog());
        btnRiepilogoBilancio.setOnClickListener(v -> showDebtDialog());
        expenseViewModel.getExpenseLiveData().observe(this, expenseResult ->
        {
            Expense addExpense = expenseResult.getExpenses();
            expenseList.add(addExpense);
            adapter.notifyItemInserted(expenseList.size() - 1);
        });

        expenseViewModel.getExpenses().observe(this, expenseResult ->
        {
            expenseList.clear();
            if (expenseResult != null && !expenseResult.isEmpty())
            {
                expenseResult=expenseResult.stream().filter(s->s.getStatus()==StatusExpense.PENDING).collect(Collectors.toList());
                expenseList.addAll(expenseResult);
            }
            Log.d("ExpenseActivity", "Expenses received: " + expenseList.size());
            adapter.notifyDataSetChanged();
        });
    }

    private void showAddExpenseDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_new_expense, null);

        EditText etDescription = dialogView.findViewById(R.id.etDescription);
        EditText etAmount = dialogView.findViewById(R.id.etAmount);
        Spinner spinnerPaidBy = dialogView.findViewById(R.id.spinnerPaidBy);
        Spinner spinnerCategory = dialogView.findViewById(R.id.spinnerCategory);
        LinearLayout participantsContainer = dialogView.findViewById(R.id.participantsContainer);
        Button btnCancel = dialogView.findViewById(R.id.btnMyCancel);
        Button btnOk = dialogView.findViewById(R.id.btnMyOk);

        AlertDialog dialog = builder.setView(dialogView).create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        List<String> participants = expenseViewModel.getListCoinquy().getValue()
                .stream()
                .map(User::getUsername)
                .collect(Collectors.toList());

        participants.add(0, "Chi ha pagato?");
        ArrayAdapter<String> paidByAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, participants);
        paidByAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerPaidBy.setAdapter(paidByAdapter);
        spinnerPaidBy.setSelection(0);


        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this, R.array.expense_categories, R.layout.spinner_item);
        categoryAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerCategory.setAdapter(categoryAdapter);
        spinnerCategory.setSelection(0);

        participantsContainer.removeAllViews();
        for (String participant : participants)
        {
            if (participant.equals("Chi ha pagato?")) continue;
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(participant);
            checkBox.setTextColor(Color.parseColor("#8A2BE2"));
            if (participant.equals(spinnerPaidBy.getSelectedItem())) {
                checkBox.setChecked(true);
            }
            participantsContainer.addView(checkBox);
        }

        btnOk.setOnClickListener(v -> {
            String description = etDescription.getText().toString().trim();
            String amountStr = etAmount.getText().toString().trim();
            String payer = (String) spinnerPaidBy.getSelectedItem();
            String categoryName = (String) spinnerCategory.getSelectedItem();

            if (TextUtils.isEmpty(description)) {
                Toast.makeText(this, "Inserisci una descrizione", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(amountStr)) {
                Toast.makeText(this, "Inserisci un importo", Toast.LENGTH_SHORT).show();
                return;
            }
            if (categoryName.equals("\uD83E\uDDFE Seleziona una categoria"))
            {
                Toast.makeText(this, "Seleziona una categoria", Toast.LENGTH_SHORT).show();
                return;
            }
            if(participantsContainer.getChildCount() == 0) {
                Toast.makeText(this, "Aggiungi almeno un partecipante", Toast.LENGTH_SHORT).show();
                return;
            }
            if (payer.equals("Chi ha pagato?")) {
                Toast.makeText(this, "Seleziona chi ha pagato", Toast.LENGTH_SHORT).show();
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(amountStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Importo non valido", Toast.LENGTH_SHORT).show();
                return;
            }

            List<String> selectedParticipants = new ArrayList<>();
            for (int i = 0; i < participantsContainer.getChildCount(); i++) {
                View child = participantsContainer.getChildAt(i);
                if (child instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) child;
                    if (checkBox.isChecked()) {
                        selectedParticipants.add(checkBox.getText().toString());
                    }
                }
            }

            expenseViewModel.createExpense(description, amount, expenseViewModel.getHouseId().getValue(),
                    payer, categoryName, selectedParticipants);

            showNotification("Nuova spesa", "Hai aggiunto una spesa di " + amount + "€ per " + description);
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());
    }


    private void showDebtDialog() {
        expenseViewModel.calculateDebt(expenseViewModel.getHouseId().getValue());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_debts, null);
        LinearLayout debtsContainer = dialogView.findViewById(R.id.spesaContainer);

        builder.setView(dialogView);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final Observer<List<String>> debtObserver = debts -> {
            debtsContainer.removeAllViews();
            Log.d("ExpenseActivity", "Debts received: " + debts);
            if (debts == null || debts.isEmpty()) {
                TextView noDebtsText = new TextView(this);
                noDebtsText.setText("💸 Nessun debito trovato");
                noDebtsText.setTextColor(Color.parseColor("#8A2BE2"));
                noDebtsText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                noDebtsText.setPadding(8, 8, 8, 8);
                debtsContainer.addView(noDebtsText);
                return;
            }

            for (String msg : debts) {
                TextView tv = new TextView(this);
                tv.setText(msg);
                tv.setTextColor(Color.parseColor("#8A2BE2"));
                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tv.setPadding(8, 8, 8, 8);
                debtsContainer.addView(tv);
            }
        };

        expenseViewModel.getDebtLiveData().observe(this, debtObserver);
        dialog.setOnDismissListener(dialogInterface -> {
            expenseViewModel.getDebtLiveData().removeObserver(debtObserver);
        });
        dialog.show();
    }

    private void showNotification(String title, String message)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean notificationsEnabled = prefs.getBoolean("notifications_enabled", true);
        if (!notificationsEnabled) {
            return;
        }

        String channelId = "spesa_channel";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Notifiche Spese",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("Notifiche per le spese create");
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null)
            {
                manager.createNotificationChannel(channel);
            }
        }

        Intent intent = new Intent(this, ExpenseActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ico_app_background)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }
        notificationManager.notify(1001, builder.build());
    }

}
