package minicla03.coinquylife.SelectionHouse.PRESENTATION.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import minicla03.coinquylife.DATALAYER.remote.HouseSelectionAPI.SelectHouseStatus;
import minicla03.coinquylife.Dash.PRESENTATION.UI.DashboardInit;
import minicla03.coinquylife.R;
import minicla03.coinquylife.SelectionHouse.PRESENTATION.ViewModel.SelectHouseViewModel;

public class JoinCoinquyHouseFragment extends Fragment
{
    private EditText etHouseID;
    private Button btnConfirm;
    private SelectHouseViewModel selectHouseViewModel;

    public JoinCoinquyHouseFragment() {  }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_existing_house, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        etHouseID = view.findViewById(R.id.etHouseID);
        btnConfirm = view.findViewById(R.id.btnConfirmHouseID);
        selectHouseViewModel = new ViewModelProvider(requireActivity()).get(SelectHouseViewModel.class);

        btnConfirm.setOnClickListener(v -> {
            String houseId = etHouseID.getText().toString().trim();

            if (houseId.isEmpty())
            {
                Toast.makeText(requireContext(), "Insert a valid id", Toast.LENGTH_SHORT).show();
            }
            else
            {
                selectHouseViewModel.joinHouse(etHouseID.getText().toString().trim());
            }
        });

        selectHouseViewModel.getJoinHouseResult().observe(getViewLifecycleOwner(), result->
        {
            Log.d("JOIN_HOUSE", "Result: " + result.getStatus() + ", Message: " + result.getMessage());
            if (result.getStatus() == SelectHouseStatus.LINKED_SUCCES)
            {

                Intent intent = new Intent(requireActivity(), DashboardInit.class);
                requireActivity().getSharedPreferences("app_prefs", android.content.Context.MODE_PRIVATE)
                        .edit()
                        .putString("houseCode", etHouseID.getText().toString().trim())
                        .apply();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            else if (result.getStatus() == SelectHouseStatus.HOUSE_NOT_FOUND)
            {
                Toast.makeText(requireContext(), "House not found", Toast.LENGTH_SHORT).show();
            }
            else if (result.getStatus() == SelectHouseStatus.ERROR)
            {
                Toast.makeText(requireContext(), "User is not in a house", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(requireContext(), "Error joining house", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
