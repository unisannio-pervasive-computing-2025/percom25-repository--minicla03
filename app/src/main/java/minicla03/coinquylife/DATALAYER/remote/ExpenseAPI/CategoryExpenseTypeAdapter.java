package minicla03.coinquylife.DATALAYER.remote.ExpenseAPI;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.JsonToken;

import java.io.IOException;

import minicla03.coinquylife.Expense.DOMAIN.model.CategoryExpense;

public class CategoryExpenseTypeAdapter extends TypeAdapter<CategoryExpense> {

    @Override
    public void write(JsonWriter out, CategoryExpense value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value.getName());
        }
    }

    @Override
    public CategoryExpense read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        String categoryString = in.nextString();
        for (CategoryExpense category : CategoryExpense.values()) {
            if (category.getName().equalsIgnoreCase(categoryString)) {
                return category;
            }
        }
        return CategoryExpense.OTHER;
    }
}