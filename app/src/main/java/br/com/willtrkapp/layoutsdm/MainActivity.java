package br.com.willtrkapp.layoutsdm;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final String ESTADO_NOTIFICAO_CHECKBOX = "ESTADO_NOTIFICAO_CHECKBOX";
    private final String NOTIFICACAO_RADIO_BUTTON_SELECIONADO = "NOTIFICACAO_RADIO_BUTTON_SELECIONADO";

    //Chaves para os métodos onCreat e Onsave

    private final String TEL_ARRAY_STRING_KEY = "TEL_ARRAY_STRING_KEY";
    private final String TEL_SPINNER_VALUE_ARRAY_KEY = "TEL_SPINNER_VALUE_ARRAY_KEY";
    private final String EMAIL_ARRAY_KEY = "EMAIL_ARRAY_KEY";


    private EditText nomeEditTex;
    //private EditText emailEditTex;
    //private EditText telefoneEditTex;

    private CheckBox notificacoesCheckbox;

    private RadioGroup notificacoesRadioGroup;

    private LinearLayout telefoneLinearLayout;

    private LinearLayout emailLinearLayout;

    private ArrayList<String> emailArraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll_view_activity_main);

        notificacoesCheckbox = findViewById(R.id.notificacoesCheckBox);
        notificacoesRadioGroup = findViewById(R.id.notificacoesRadioGroup);
        nomeEditTex = findViewById(R.id.nomeEditText);
        //emailEditTex = findViewById(R.id.emailEditText);
        //telefoneEditTex = findViewById(R.id.telefoneEditText);



        telefoneLinearLayout = findViewById(R.id.telefoneLinearLayout);
        emailLinearLayout = findViewById(R.id.emailLinearLayout);
        emailArraylist = new ArrayList<>();



        //Tratando evento de check no checkbox

        /*
        notificacoesCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CheckBox) view).isChecked())
                {
                    notificacoesRadioGroup.setVisibility(View.VISIBLE);
                }
                else
                {
                    notificacoesRadioGroup.setVisibility(View.GONE);
                }
            }
        });*/
        notificacoesCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    notificacoesRadioGroup.setVisibility(View.VISIBLE);
                }
                else{
                    notificacoesRadioGroup.setVisibility(View.GONE);
                }
            }
        });

    }

    private void inflateTelefone(String pTelefone, Integer pSpinnerPosition)
    {
        LayoutInflater layoutInflater = getLayoutInflater();
        View novoTelefoneLayout = layoutInflater.inflate(R.layout.novotelefonelayout, null);
        if(pTelefone != null)
        {
            ((EditText)novoTelefoneLayout.findViewById(R.id.telefoneEditText)).setText(pTelefone);
            ((Spinner)novoTelefoneLayout.findViewById(R.id.tipoTelefoneSpinner)).setSelection(pSpinnerPosition);
        }
        telefoneLinearLayout.addView(novoTelefoneLayout);
    }

    private void inflateEmail(String pEmail)
    {
        LayoutInflater layoutInflater = getLayoutInflater();
        View novoEmailLayout = layoutInflater.inflate(R.layout.novo_email_layout, null, false);
        if(pEmail != null)
            ((EditText)novoEmailLayout.findViewById(R.id.emailEditText)).setText(pEmail);

        emailLinearLayout.addView(novoEmailLayout);
    }

    public void adicionarTelefone(View view){
        if(view.getId() == R.id.adicionarTelefoneButton){
            inflateTelefone(null, null);
        }
    }


    public void adicionarEmail(View view){
        if(view.getId() == R.id.adicionarEmailButton){
            inflateEmail(null);
        }
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //SALVAR OS DADOS DE ESTADO DINÂMICO
        outState.putBoolean(ESTADO_NOTIFICAO_CHECKBOX, notificacoesCheckbox.isChecked());
        outState.putInt(NOTIFICACAO_RADIO_BUTTON_SELECIONADO, notificacoesRadioGroup.getCheckedRadioButtonId());

        ArrayList<String> telefoneArraylist = new ArrayList<>();
        ArrayList<Integer> telefoneSpinnerValueArrayList = new ArrayList<>();

        for (int i = 0; i < telefoneLinearLayout.getChildCount(); i++) {
            View vwTelefone = telefoneLinearLayout.getChildAt(i);
            telefoneArraylist.add(((EditText)vwTelefone.findViewById(R.id.telefoneEditText)).getText().toString());
            telefoneSpinnerValueArrayList.add(((Spinner)vwTelefone.findViewById(R.id.tipoTelefoneSpinner)).getSelectedItemPosition());
        }
        outState.putStringArrayList(TEL_ARRAY_STRING_KEY, telefoneArraylist);
        outState.putIntegerArrayList(TEL_SPINNER_VALUE_ARRAY_KEY, telefoneSpinnerValueArrayList);

        for (int i = 0; i < emailLinearLayout.getChildCount(); i++) {
            View vwEmail = emailLinearLayout.getChildAt(i);
            emailArraylist.add(((EditText)vwEmail.findViewById(R.id.emailEditText)).getText().toString());
        }
        outState.putStringArrayList(EMAIL_ARRAY_KEY, emailArraylist);

    }



    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);


        if(savedInstanceState != null){

            notificacoesCheckbox.setChecked(savedInstanceState.getBoolean(ESTADO_NOTIFICAO_CHECKBOX, false));
            int idRdb = savedInstanceState.getInt(NOTIFICACAO_RADIO_BUTTON_SELECIONADO, -1);
            if(idRdb != -1)
                notificacoesRadioGroup.check(idRdb);

            if(savedInstanceState.getStringArrayList(TEL_ARRAY_STRING_KEY) != null)
            {
                ArrayList<String> telefoneArraylist = savedInstanceState.getStringArrayList(TEL_ARRAY_STRING_KEY);
                ArrayList<Integer> telefoneSpinnerValueArrayList = savedInstanceState.getIntegerArrayList(TEL_SPINNER_VALUE_ARRAY_KEY);
                for(int i = 0; i < telefoneArraylist.size(); i ++)
                    inflateTelefone(telefoneArraylist.get(i), telefoneSpinnerValueArrayList.get(i));

            }

            if(savedInstanceState.getStringArrayList(EMAIL_ARRAY_KEY) != null)
            {
                ArrayList<String> emailArrayList = savedInstanceState.getStringArrayList(EMAIL_ARRAY_KEY);
                for(int i = 0; i < emailArrayList.size(); i ++)
                    inflateEmail(emailArrayList.get(i));
            }

        }

    }

    public void limparFormulario(View botao){
        notificacoesCheckbox.setChecked(false);
        notificacoesRadioGroup.clearCheck();
        nomeEditTex.setText("");
        nomeEditTex.requestFocus();
        telefoneLinearLayout.removeAllViews();
        emailLinearLayout.removeAllViews();
    }

}
