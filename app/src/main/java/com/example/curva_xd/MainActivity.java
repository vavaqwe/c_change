package com.example.curva_xd;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    @SuppressLint("StringFormatInvalid")
    public boolean res = false;
    Button btn_txt, btn_btn, scanButton, btn_sel;
    TextView txt,ip_txt;
    EditText edtxt;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch toggle;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = findViewById(R.id.txt1);
        btn_btn = findViewById(R.id.btn_btn);
        btn_txt = findViewById(R.id.btn_text);
        edtxt = findViewById(R.id.edtxt);
        toggle = findViewById(R.id.switch1);
        scanButton = findViewById(R.id.btnScan);
        btn_sel = findViewById(R.id.button);
        ip_txt = findViewById(R.id.ip_txt);
        ip_txt.setText("Сейчас используется : "+load());
        toggle.setOnClickListener(v -> {
            if (edtxt.getVisibility() == View.VISIBLE) {
                toggle.setText("Write");
                edtxt.setVisibility(View.INVISIBLE);
                btn_sel.setVisibility(View.INVISIBLE);
                scanButton.setVisibility(View.VISIBLE);
            } else {
                toggle.setText("Qr code");
                edtxt.setVisibility(View.VISIBLE);
                btn_sel.setVisibility(View.VISIBLE);
                scanButton.setVisibility(View.INVISIBLE);
            }
        });
        scanButton.setOnClickListener(v ->
                scanCode());
        View.OnClickListener click = view -> save(edtxt.getText().toString());
        View.OnClickListener clickListener1 = view -> {
            Intent intent = new Intent(MainActivity.this, Activity_comp.class);
            startActivity(intent);
        };
        View.OnClickListener clickListener2 = view -> {
            Intent intent = new Intent(MainActivity.this, Activity_internet.class);
            startActivity(intent);
        };
        btn_sel.setOnClickListener(click);
        btn_txt.setOnClickListener(clickListener1);
        btn_btn.setOnClickListener(clickListener2);
    }

    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Отсканируйте qr код");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result ->
    {
        if (result.getContents() != null) {
            Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
            save(result.getContents());
        } else {
            Toast.makeText(this, "Не удалось считать Qr код", Toast.LENGTH_SHORT).show();
        }
    });
    public void save(String text){
        try {
            FileOutputStream fos = openFileOutput("ip.txt", Context.MODE_PRIVATE);
            fos.write(text.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String load(){
        try {
            FileInputStream fis = openFileInput("ip.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            fis.close();
            isr.close();
            br.close();

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}