package minicla03.coinquylife.SelectionHouse.PRESENTATION.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import minicla03.coinquylife.DATALAYER.remote.HouseSelectionAPI.SelectHouseStatus;
import minicla03.coinquylife.Dash.PRESENTATION.UI.DashboardInit;
import minicla03.coinquylife.R;
import minicla03.coinquylife.SelectionHouse.PRESENTATION.ViewModel.SelectHouseViewModel;

public class NewCoinquyHouseIDFragment extends Fragment {

    private EditText houseName;
    private SelectHouseViewModel selectHouseViewModel;
    private boolean houseCreated = false;

    public NewCoinquyHouseIDFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_new_coinquy_house, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        TextView textViewID = view.findViewById(R.id.textViewID);
        Button btnProceed = view.findViewById(R.id.btnProceed);
        ImageButton btnCopyID = view.findViewById(R.id.btnCopyID);
        TextView tvAddress = view.findViewById(R.id.etAdress);

        houseName= view.findViewById(R.id.etHouseName);
        selectHouseViewModel = new ViewModelProvider(requireActivity()).get(SelectHouseViewModel.class);

        btnProceed.setOnClickListener(v -> {
            if (!houseCreated) {
                String name_house = houseName.getText().toString().trim();
                String address = tvAddress.getText().toString().trim();
                selectHouseViewModel.createHouse(name_house, address);
            } else {
                Intent intent = new Intent(requireContext(), DashboardInit.class);
                requireActivity().getSharedPreferences("app_prefs", android.content.Context.MODE_PRIVATE)
                        .edit()
                        .putString("houseCode", textViewID.getText().toString().trim())
                        .apply();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        btnCopyID.setOnClickListener(v -> {
            String textToCopy = textViewID.getText().toString();
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) requireContext().getSystemService(android.content.Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("House ID", textToCopy);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(requireContext(), "ID copiato negli appunti", Toast.LENGTH_SHORT).show();
        });

        selectHouseViewModel.getHouseCode().observe(getViewLifecycleOwner(), houseCode -> {
            if (houseCode == null || houseCode.isEmpty())
            {
                Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT).show();
            }
            else
            {
                textViewID.setText(houseCode);
            }
        });

        selectHouseViewModel.getHouseCreationResult().observe(getViewLifecycleOwner(), result ->
        {
            Log.d("NewCoinquyHouseIDFragment", "House creation result: " + result.getStatus());
            Log.d("NewCoinquyHouseIDFragment", "House creation result: " + result.getMessage());
            if (result.getStatus()==SelectHouseStatus.HOUSE_CREATED)
            {
                houseCreated = true;
                btnProceed.setText("Prosegui");
            }
            else if(result.getStatus()==SelectHouseStatus.HOUSE_NAME_EMPTY)
            {
                Toast.makeText(requireContext(), "Insert a valid name", Toast.LENGTH_SHORT).show();
            }
            else if(result.getStatus()==SelectHouseStatus.ALREADY_IN_HOUSE)
            {
                Toast.makeText(requireContext(), "User already in a house", Toast.LENGTH_SHORT).show();
            }
            else if(result.getStatus()==SelectHouseStatus.ERROR)
            {
                Toast.makeText(requireContext(), "Error during the creation of the house", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
