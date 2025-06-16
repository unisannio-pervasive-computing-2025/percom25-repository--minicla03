package minicla03.coinquylife.Expense.PRESENTATION.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import minicla03.coinquylife.Expense.DOMAIN.model.Expense;
import minicla03.coinquylife.Expense.DOMAIN.model.StatusExpense;
import minicla03.coinquylife.R;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>
{
    private List<Expense> expenses;
    private OnPayClickListener onPayClickListener;

    public interface OnPayClickListener
    {
        void onPayClicked(int position);
    }

    public void setOnPayClickListener(OnPayClickListener listener) {

        this.onPayClickListener = listener;
    }

    public ExpenseAdapter(List<Expense> expenses)
    {
        this.expenses = expenses;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expense, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position)
    {
        Expense expense = expenses.get(position);

        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.ITALY);
        holder.tvAmount.setText(format.format(expense.getAmount()));

        holder.tvDescription.setText(expense.getDescription());
        holder.payer.setText(expense.getCreatedBy() != null ? expense.getCreatedBy() : "N/A");

        if (expense.getStatus() == StatusExpense.SETTLED)
        {
            holder.tvSaldata.setVisibility(View.VISIBLE);
            holder.btnPay.setVisibility(View.GONE);
        }
        else if (expense.getStatus() == StatusExpense.PENDING)
        {
            holder.tvSaldata.setVisibility(View.GONE);
            holder.btnPay.setVisibility(View.VISIBLE);
        } else
        {
            holder.tvSaldata.setVisibility(View.GONE);
            holder.btnPay.setVisibility(View.VISIBLE);
        }

        if (expense.getCategory() != null) {
            holder.tvCategoria.setText(expense.getCategory().getName());
        } else {
            holder.tvCategoria.setText("N/A");
        }

        holder.btnPay.setOnClickListener(null);

        holder.btnPay.setOnClickListener(v -> {
            if (onPayClickListener != null) {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION &&
                        currentPosition < expenses.size()) {
                    onPayClickListener.onPayClicked(currentPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public static class ExpenseViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvAmount, tvDescription, tvSaldata, tvDaFare, tvCategoria, payer;
        android.widget.Button btnPay;

        public ExpenseViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tvAmount = itemView.findViewById(R.id.tv_amount);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvSaldata = itemView.findViewById(R.id.tv_saldata);
            tvDaFare = itemView.findViewById(R.id.tv_da_fare);
            payer = itemView.findViewById(R.id.tv_payer);
            tvCategoria = itemView.findViewById(R.id.tv_categoria);
            btnPay = itemView.findViewById(R.id.btn_pay);
        }
    }
}
