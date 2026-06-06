package com.abdinasir.unitconverter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText editInputValue;
    private AutoCompleteTextView spinnerConversion;
    private MaterialButton btnConvert;
    private MaterialButton btnClear;
    private MaterialCardView cardError;
    private TextView txtErrorMessage;
    private TextView txtResultValue;
    private TextView txtResultDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind Views
        editInputValue = findViewById(R.id.edit_input_value);
        spinnerConversion = findViewById(R.id.spinner_conversion);
        btnConvert = findViewById(R.id.btn_convert);
        btnClear = findViewById(R.id.btn_clear);
        cardError = findViewById(R.id.card_error);
        txtErrorMessage = findViewById(R.id.txt_error_message);
        txtResultValue = findViewById(R.id.txt_result_value);
        txtResultDetails = findViewById(R.id.txt_result_details);

        // Populate Dropdown Spinner
        ConversionType[] types = ConversionType.values();
        ArrayAdapter<ConversionType> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                types
        );
        spinnerConversion.setAdapter(adapter);
        
        // Select first conversion type by default
        if (types.length > 0) {
            spinnerConversion.setText(types[0].toString(), false);
        }

        // Set Listeners
        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performConversion();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });
    }

    private void performConversion() {
        hideKeyboard();
        
        // Hide error state initially
        cardError.setVisibility(View.GONE);

        // Get Input
        String inputStr = editInputValue.getText() != null ? editInputValue.getText().toString().trim() : "";

        // Validation - Empty Input
        if (inputStr.isEmpty()) {
            showError("Please enter a value.");
            return;
        }

        // Validation - Invalid Number
        double inputValue;
        try {
            inputValue = Double.parseDouble(inputStr);
        } catch (NumberFormatException e) {
            showError("Please enter a valid number.");
            return;
        }

        // Get Selected Conversion Type
        String selectedText = spinnerConversion.getText().toString();
        ConversionType selectedType = null;
        for (ConversionType type : ConversionType.values()) {
            if (type.toString().equals(selectedText)) {
                selectedType = type;
                break;
            }
        }

        if (selectedType == null) {
            showError("Please select a conversion type.");
            return;
        }

        // Perform Conversion
        try {
            double resultValue = UnitConverter.convert(inputValue, selectedType);
            displayResult(inputValue, resultValue, selectedType);
        } catch (Exception e) {
            showError("An error occurred during conversion.");
        }
    }

    private void displayResult(double inputVal, double resultVal, ConversionType type) {
        String inputUnit = UnitConverter.getInputUnit(type);
        String outputUnit = UnitConverter.getOutputUnit(type);

        String formattedInput = formatNumber(inputVal);
        String formattedResult = formatNumber(resultVal);

        // Set primary result display: e.g. "1 m"
        txtResultValue.setText(formattedResult + " " + outputUnit);

        // Set detailed conversion text: e.g. "100 cm = 1 m"
        txtResultDetails.setVisibility(View.VISIBLE);
        txtResultDetails.setText(formattedInput + " " + inputUnit + " = " + formattedResult + " " + outputUnit);
    }

    private void showError(String message) {
        txtErrorMessage.setText(message);
        cardError.setVisibility(View.VISIBLE);
        
        // Reset result displays
        txtResultValue.setText(getString(R.string.empty_result_text));
        txtResultDetails.setVisibility(View.GONE);
        txtResultDetails.setText("");
    }

    private void clearFields() {
        editInputValue.setText("");
        cardError.setVisibility(View.GONE);
        txtResultValue.setText(getString(R.string.empty_result_text));
        txtResultDetails.setVisibility(View.GONE);
        txtResultDetails.setText("");
        
        // Reset spinner to default item
        ConversionType[] types = ConversionType.values();
        if (types.length > 0) {
            spinnerConversion.setText(types[0].toString(), false);
        }
        
        editInputValue.requestFocus();
    }

    private String formatNumber(double value) {
        if (Double.isInfinite(value) || Double.isNaN(value)) {
            return String.valueOf(value);
        }
        if (value == (long) value) {
            return String.format("%d", (long) value);
        } else {
            String formatted = String.format("%.6f", value);
            if (formatted.indexOf('.') > 0) {
                formatted = formatted.replaceAll("0+$", "").replaceAll("\\.$", "");
            }
            return formatted;
        }
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}
