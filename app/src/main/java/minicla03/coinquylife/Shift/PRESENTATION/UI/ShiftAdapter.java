package minicla03.coinquylife.Shift.PRESENTATION.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import minicla03.coinquylife.R;

public class ShiftAdapter extends RecyclerView.Adapter<ShiftAdapter.ShiftViewHolder> {

    private final List<ShiftItem> items;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ShiftAdapter(List<ShiftItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ShiftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.clean_item, parent, false);
        return new ShiftViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ShiftAdapter.ShiftViewHolder holder, int position) {
        ShiftItem item = items.get(position);
        holder.giornoTv.setText(item.getOrario().getStart().substring(0,10));
        holder.taskDescriptionTv.setText(item.getDescrizione());
        holder.taskTimeTv.setText(item.getOrario().getStart().substring(11, 16) + " - " + item.getOrario().getEnd().substring(11, 16));
        String text = "Assegnato a: ";
        holder.taskCoinquilinoTv.setText(text+" "+item.getCoinquilino());

        if (item.isCompleted())
        {
            holder.btnCompleteTask.setVisibility(View.GONE);
        }
        else
        {
            holder.btnCompleteTask.setVisibility(View.VISIBLE);
            holder.btnCompleteTask.setOnClickListener(v -> {
                if (listener != null) {
                    int currentPosition = holder.getAdapterPosition();
                    if (currentPosition != RecyclerView.NO_POSITION &&
                            currentPosition < items.size()) {
                        listener.onItemClick(currentPosition);
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ShiftViewHolder extends RecyclerView.ViewHolder
    {
        TextView giornoTv, taskDescriptionTv, taskTimeTv, taskCoinquilinoTv;
        Button btnCompleteTask;

        public ShiftViewHolder(@NonNull View itemView)
        {
            super(itemView);
            giornoTv = itemView.findViewById(R.id.GiornoTextView);
            taskDescriptionTv = itemView.findViewById(R.id.taskDescriptionTextView);
            taskTimeTv = itemView.findViewById(R.id.timeSlotTextView);
            taskCoinquilinoTv = itemView.findViewById(R.id.assignedRoommateTextView);
            btnCompleteTask = itemView.findViewById(R.id.btn_complete_task);
        }
    }
}