package com.example.curva_xd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
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

public class Activity_comp extends AppCompatActivity {

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
                InetAddress ipAddress = InetAddress.getByName(get_ip());
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
    Button btn_reboot, btn_off, btn_game, btn_ds,back;
    SeekBar volume;
    TextView txt, error,sound_txt;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inet);
        btn_reboot = findViewById(R.id.btn_reboot);
        btn_off = findViewById(R.id.btn_off);
        btn_game = findViewById(R.id.btn_game);
        btn_ds = findViewById(R.id.btn_ds);
        back = findViewById(R.id.back_to_main);
        txt = findViewById(R.id.txt1);
        sound_txt = findViewById(R.id.sound_txt);
        error = findViewById(R.id.txt3);
        volume = findViewById(R.id.seekBar);
        @SuppressLint({"SetTextI18n", "NonConstantResourceId"}) View.OnClickListener onClickListener = view -> {
            switch (view.getId()) {
                case R.id.btn_off:
                    txt.setText("off");
                    connect("0");
                    if (mainActivity.res) {
                        Toast.makeText(this, "Офаю комп", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.btn_ds:
                    txt.setText("discord");
                    connect("1");
                    if (mainActivity.res) {
                        Toast.makeText(this, "Включаю дс", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.btn_reboot:
                    txt.setText("reboot");
                    connect("4");
                    if (mainActivity.res) {
                        Toast.makeText(this, "Перезапускаю комп", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        };
        View.OnClickListener clickListener1 = view -> {
            Intent int_btn = new Intent(Activity_comp.this, MainActivity.class);
            startActivity(int_btn);
        };
        View.OnClickListener clickListener = view -> {
            Intent int_btn = new Intent(Activity_comp.this, Activity_game.class);
            startActivity(int_btn);
        };
        volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress = progressValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                String resultText = String.valueOf(progress);
                sound_txt.setText(resultText);
                String volume = String.valueOf(progress);
                connect("8"+volume);
            }
        });
        back.setOnClickListener(clickListener1);
        btn_game.setOnClickListener(clickListener);
        btn_reboot.setOnClickListener(onClickListener);
        btn_off.setOnClickListener(onClickListener);
        btn_ds.setOnClickListener(onClickListener);

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