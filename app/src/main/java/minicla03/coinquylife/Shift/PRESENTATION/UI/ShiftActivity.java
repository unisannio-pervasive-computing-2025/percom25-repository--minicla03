package minicla03.coinquylife.Shift.PRESENTATION.UI;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import minicla03.coinquylife.Auth.DOMAIN.model.User;
import minicla03.coinquylife.R;
import minicla03.coinquylife.Shift.DOMAIN.model.CleaningAssignment;
import minicla03.coinquylife.Shift.PRESENTATION.ViewModel.ShiftViewModel;
import minicla03.coinquylife.Shift.PRESENTATION.ViewModel.UnavailabilityViewModel;
import minicla03.coinquylife.Shift.PRESENTATION.ViewModel.HouseTaskViewModel;

public class ShiftActivity extends AppCompatActivity
{
    private TextInputEditText etStartIndisponibilita, etEndIndisponibilita, etDataOraCompito, etDescrizioneCompito;
    private Spinner spinnerCoinquilino, spinnerTipoCompito;
    private MaterialButton btnSaveIndisponibilita, btnAddCompito, getPlanning, btnBack;
    private RecyclerView rvShiftCalendar;

    private final Calendar calendar = Calendar.getInstance();

    private HouseTaskViewModel houseTaskViewModel;
    private ShiftViewModel shiftViewModel;
    private UnavailabilityViewModel unavailabilityViewModel;
    private List<ShiftItem> shiftItems;

    private ShiftAdapter shiftAdapter;
    private ProgressBar progressBar;

    private int lastClickedPosition = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shift_layout);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        String houseId=getSharedPreferences("app_prefs", MODE_PRIVATE).getString("houseCode", null);
        String json = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                .getString("coinquyList", null);
        List<User> listCoinquy = new Gson().fromJson(json, new TypeToken<List<User>>() {
        }.getType());

        Log.d( "ShiftActivity", "House ID: " + houseId);
        Log.d( "ShiftActivity", "Coinquy List: " + (listCoinquy != null ? listCoinquy.size() : "null"));

        initViews();

        houseTaskViewModel = new ViewModelProvider(this).get(HouseTaskViewModel.class);
        shiftViewModel = new ViewModelProvider(this).get(ShiftViewModel.class);
        unavailabilityViewModel = new ViewModelProvider(this).get(UnavailabilityViewModel.class);
        shiftViewModel.retrieveAllShift(houseId);
        shiftItems = shiftViewModel.getPlanning().getValue()==null ? new ArrayList<>() : new ArrayList<>(convertToShiftItem(shiftViewModel.getPlanning().getValue()));

        shiftViewModel.getPlanning().observe(this, planning -> {
            progressBar.setVisibility(View.GONE);
            if (planning != null && !planning.isEmpty())
            {
                Log.d("ShiftActivity", "PLANNING size: " + planning.size());
                renderShiftItems(planning);
            } else {
                shiftItems.clear();
                Toast.makeText(this, "Nessun compito trovato", Toast.LENGTH_SHORT).show();
            }
        });

        shiftViewModel.getIsLoading().observe(this, loading -> {
            if (loading != null && loading) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });

        unavailabilityViewModel.initializeRoommates(houseId, listCoinquy);
        shiftViewModel.setListCoinquy(listCoinquy);
        shiftViewModel.setHouseId(houseId);
        //shiftViewModel.fetchPlanning(houseId);

        List<String> usernamesCopy = shiftViewModel.getListCoinquy().getValue().stream().map(User::getUsername).collect(Collectors.toList());
        usernamesCopy.add(0, "Seleziona Coinquilino");
        spinnerCoinquilino.setAdapter(new ArrayAdapter<>(this, R.layout.spinner_item, usernamesCopy));
        spinnerTipoCompito.setAdapter( ArrayAdapter.createFromResource(this, R.array.compito_types, R.layout.spinner_item));
        spinnerTipoCompito.setSelection(0);
        spinnerCoinquilino.setSelection(0);

        etStartIndisponibilita.setOnClickListener(v -> showDateTimePicker(etStartIndisponibilita));
        etEndIndisponibilita.setOnClickListener(v -> showDateTimePicker(etEndIndisponibilita));
        etDataOraCompito.setOnClickListener(v -> showDateTimePicker(etDataOraCompito));

        rvShiftCalendar.setLayoutManager(new LinearLayoutManager(this));
        shiftAdapter = new ShiftAdapter(shiftItems);
        rvShiftCalendar.setAdapter(shiftAdapter);

        btnSaveIndisponibilita.setOnClickListener(v -> {
            try {
                saveIndisponibilita();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
        btnAddCompito.setOnClickListener(v -> {
            try {
                addCompito();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
        btnBack.setOnClickListener(v -> {
            finish();
        });

        getPlanning.setOnClickListener(v ->{
                progressBar.setVisibility(View.VISIBLE);
                shiftViewModel.fetchPlanning(houseId);
        });

        shiftViewModel.getErrorResult().observe(this, error -> {
            progressBar.setVisibility(View.GONE);
            if (error != null)
            {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });

        shiftViewModel.getRankResult().observe(this, rankResult -> {
            if (rankResult != null) {
                Log.d("ShiftActivity", "Punti assegnati: " + rankResult);
                Toast.makeText(ShiftActivity.this, "Punti assegnati: " + rankResult, Toast.LENGTH_SHORT).show();
                ShiftItem item = shiftItems.get(lastClickedPosition);
                item.setCompleted(true);
                shiftAdapter.notifyItemChanged(lastClickedPosition);
            } else {
                Toast.makeText(ShiftActivity.this, "Errore durante il completamento del compito", Toast.LENGTH_SHORT).show();
            }
        });


        shiftAdapter.setOnItemClickListener(position -> {
            lastClickedPosition = position;
            ShiftItem clickedItem = shiftItems.get(position);
            String token = getSharedPreferences("app_prefs", MODE_PRIVATE).getString("token", null);

            shiftViewModel.confirmTaskCompletion(
                    token,
                    clickedItem.getIdShift(),
                    clickedItem.getTipoCompito(),
                    clickedItem.getCoinquilino(),
                    shiftViewModel.getHouseId().getValue(),
                    clickedItem.getOrario().getEnd().toString()
            );
        });
    }

    private List<ShiftItem> convertToShiftItem(List<CleaningAssignment> value)
    {
        for(CleaningAssignment val: value)
        {
            Log.d("ShiftActivity", ", Done: " + val.getTask().isDone());
        }
        return value.stream().map(assignment ->
                new ShiftItem(
                        assignment.getId(),
                        assignment.getAssignedRoommate().getUsernameRoommate(),
                        assignment.getTask().getTask().getTaskName(),
                        assignment.getTask().getDescription(),
                        assignment.getTask().getTimeSlot(),
                        assignment.getTask().isDone())
        ).collect(Collectors.toList());
    }

    private void renderShiftItems(List<CleaningAssignment> planning)
    {
        Set<String> existingIds = shiftItems.stream()
                .map(ShiftItem::getIdShift)
                .collect(Collectors.toSet());
        shiftItems.clear();

        for(CleaningAssignment val: planning)
        {
            Log.d("ShiftActivity", ", Done: " + val.getTask().isDone());
        }

        for (CleaningAssignment assignment : planning)
        {
            String id = assignment.getId();
            if (!existingIds.contains(id)) {
                ShiftItem item = new ShiftItem(
                        id,
                        assignment.getAssignedRoommate().getUsernameRoommate(),
                        assignment.getTask().getTask().getTaskName(),
                        assignment.getTask().getDescription(),
                        assignment.getTask().getTimeSlot(),
                        assignment.getTask().isDone()
                );
                shiftItems.add(item);
            }
        }
        shiftAdapter.notifyDataSetChanged();
    }


    private void saveIndisponibilita() throws ParseException {
        String coinquilino = (String) spinnerCoinquilino.getSelectedItem();
        String start = etStartIndisponibilita.getText() != null ? etStartIndisponibilita.getText().toString() : "";
        String end = etEndIndisponibilita.getText() != null ? etEndIndisponibilita.getText().toString() : "";

        if (coinquilino == null || coinquilino.isEmpty() || start.isEmpty() || end.isEmpty()) {
            Toast.makeText(this, "Compila tutti i campi per indisponibilità", Toast.LENGTH_SHORT).show();
            return;
        }

        clearIndisponibilitaFields();
        unavailabilityViewModel.addUnavailability(coinquilino, shiftViewModel.getHouseId().getValue(), start, end);
        unavailabilityViewModel.getResultAdd().observe( this, result -> {
            if (result != null)
            {
                Toast.makeText(this, "Indisponibilità aggiunta: " + result, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Errore durante il salvataggio dell'indisponibilità", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addCompito() throws ParseException {
        String descrizione = etDescrizioneCompito.getText() != null ? etDescrizioneCompito.getText().toString() : "";
        String tipo = (String) spinnerTipoCompito.getSelectedItem();
        String dataOra = etDataOraCompito.getText() != null ? etDataOraCompito.getText().toString() : "";

        if (descrizione.isEmpty() || tipo == null || tipo.isEmpty() || dataOra.isEmpty()) {
            Toast.makeText(this, "Compila tutti i campi per il compito", Toast.LENGTH_SHORT).show();
            return;
        }

        clearCompitoFields();
        houseTaskViewModel.createTask(descrizione, tipo, dataOra, shiftViewModel.getHouseId().getValue());
        houseTaskViewModel.getTaskCreated().observe(this, result -> {
            if (result) {
                Toast.makeText(this, "Compito aggiunto: " + result, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Errore durante il salvataggio del compito", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void clearIndisponibilitaFields() {
        etStartIndisponibilita.setText("");
        etEndIndisponibilita.setText("");
        spinnerCoinquilino.setSelection(0);
    }

    private void clearCompitoFields() {
        etDescrizioneCompito.setText("");
        spinnerTipoCompito.setSelection(0);
        etDataOraCompito.setText("");
    }

    private void showDateTimePicker(TextInputEditText targetEditText)
    {
        Calendar current = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);
                    TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                            (timeView, hourOfDay, minute) -> {
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                                targetEditText.setText(sdf.format(calendar.getTime()));
                            },
                            current.get(Calendar.HOUR_OF_DAY),
                            current.get(Calendar.MINUTE),
                            true);
                    timePickerDialog.show();
                },
                current.get(Calendar.YEAR),
                current.get(Calendar.MONTH),
                current.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void initViews()
    {
        etStartIndisponibilita = findViewById(R.id.etStartIndisponibilita);
        etEndIndisponibilita = findViewById(R.id.etEndIndisponibilita);
        etDataOraCompito = findViewById(R.id.etDataOraCompito);
        etDescrizioneCompito = findViewById(R.id.etDescrizioneCompito);

        spinnerCoinquilino = findViewById(R.id.spinnerCoinquilino);
        spinnerTipoCompito = findViewById(R.id.spinnerTipoCompito);

        btnSaveIndisponibilita = findViewById(R.id.btnSaveIndisponibilita);
        btnAddCompito = findViewById(R.id.btnAddCompito);
        btnBack=findViewById(R.id.btnBack);

        rvShiftCalendar = findViewById(R.id.rvShiftCalendar);
        getPlanning= findViewById(R.id.getPlanning);

        progressBar = findViewById(R.id.progressBar);

        etStartIndisponibilita.setShowSoftInputOnFocus(false);
        etEndIndisponibilita.setShowSoftInputOnFocus(false);
        etDataOraCompito.setShowSoftInputOnFocus(false);
    }
}
