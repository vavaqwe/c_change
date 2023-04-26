package com.example.curva_xd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Activity_internet extends AppCompatActivity {

    MainActivity mainActivity = new MainActivity();
    private String request;
    @SuppressWarnings("deprecation")
    @SuppressLint("StaticFieldLeak")
    private class ConnectTask extends AsyncTask<Void, Void, Void> {
        @Nullable
        @Override
        protected Void doInBackground(Void... voids) {
            Socket socket = null;
            try {
                InetAddress ipAddress = InetAddress.getByName(String.valueOf(get_ip()));
                socket = new Socket(ipAddress, 12345);
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(request.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                mainActivity.res = true;
                socket.close();
            } catch (IOException ignored) {
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    }catch (IOException e) {
                        error.setText(e.toString());
                    }
                }
            }
            return null;
        }
    }
    private void connect_name(String req){
        request = req;
    }
    public void connect(String request) {
        ConnectTask task = new ConnectTask();
        task.execute();
        connect_name(request);
    }
    Button btn_site, btn_yt ,back,btn_kish,btn_browser;
    EditText txt_yt, txt_site;
    TextView txt,error;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comp);
        btn_site = findViewById(R.id.btn_site);
        btn_browser = findViewById(R.id.btn_browser);
        btn_kish = findViewById(R.id.btn_kish);
        btn_yt = findViewById(R.id.btn_yt);
        back = findViewById(R.id.back_to_main2);
        txt_site = findViewById(R.id.site_txt);
        txt_yt = findViewById(R.id.yt_txt);
        txt = findViewById(R.id.txt2);
        error = findViewById(R.id.txt);
        @SuppressLint({"SetTextI18n", "NonConstantResourceId"}) View.OnClickListener onClickListener = view -> {
            switch (view.getId()) {
                case R.id.btn_site:
                    txt.setText("Site"+ txt_site.getText());
                    connect("6" + txt_site.getText().toString());
                    if (mainActivity.res) {
                        Toast.makeText(this, "Открываю сайт", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.btn_yt:
                    txt.setText("youtube"+ txt_yt.getText());
                    connect("7" + txt_yt.getText().toString());
                    if (mainActivity.res) {
                        Toast.makeText(this, "Открываю youtube", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.btn_browser:
                    txt.setText("browser");
                    connect("2");
                    if (mainActivity.res) {
                        Toast.makeText(this, "Включаю браузер", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.btn_kish:
                    txt.setText("КИШ");
                    connect("3");
                    if (mainActivity.res) {
                        Toast.makeText(this, "Кайф музыка", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }};
        View.OnClickListener clickListener1 = view -> {
            Intent int_btn = new Intent(Activity_internet.this, MainActivity.class);
            startActivity(int_btn);
        };
        back.setOnClickListener(clickListener1);
        btn_site.setOnClickListener(onClickListener);
        btn_yt.setOnClickListener(onClickListener);
        btn_kish.setOnClickListener(onClickListener);
        btn_browser.setOnClickListener(onClickListener);
    }
    public String get_ip(){
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
