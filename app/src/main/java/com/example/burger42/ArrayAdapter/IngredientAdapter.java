package com.example.burger42.ArrayAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.burger42.Ingredients.Ingredient;
import com.example.burger42.R;

public class IngredientAdapter extends ArrayAdapter<Ingredient> {
    private Context context;
    public IngredientAdapter(@NonNull  Context context){
        super(context,0);
        this.context = context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        Ingredient currentItem = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.ingredient_item, parent, false);
        }
        TextView ingredientName = (TextView) convertView.findViewById(R.id.ingredient_item_text);
        ingredientName.setText("* " + currentItem.name());
        return convertView;
    }
}
