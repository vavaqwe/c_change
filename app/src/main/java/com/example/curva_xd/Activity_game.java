package com.example.curva_xd;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Activity_game extends AppCompatActivity {
    MainActivity mainActivity = new MainActivity();
    private String request;
    private String name_btn = "Dota";
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
                    }catch (IOException ignored) {
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
    Button ret_btn, btn_game1, btn_game2, btn_game3, btn_game4, btn_game5,btn_name;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ret_btn = findViewById(R.id.ret_btn);
        btn_game1 = findViewById(R.id.btn_game1);
        btn_game2 = findViewById(R.id.btn_game2);
        btn_game3 = findViewById(R.id.btn_game3);
        btn_game4 = findViewById(R.id.btn_game4);
        btn_game5 = findViewById(R.id.btn_game5);
        btn_name = findViewById(R.id.btn_name);
        View.OnClickListener clickListener = view -> {
            createInputDialog();
        };
        btn_name.setOnClickListener(clickListener);
        @SuppressLint({"SetTextI18n", "NonConstantResourceId"}) View.OnClickListener onClickListener = view -> {
            switch (view.getId()) {
                case R.id.btn_game1:
                    connect("5");
                    if (mainActivity.res) {
                        Toast.makeText(this, "Офаю комп", Toast.LENGTH_SHORT).show();
                    }
                    btn_game1.setText(name_btn);
                    break;
                case R.id.btn_game2:
                    connect("9");
                    if (mainActivity.res) {
                        Toast.makeText(this, "Офаю комп", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.btn_game3:
                    connect("10");
                    if (mainActivity.res) {
                        Toast.makeText(this, "Офаю комп", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.btn_game4:
                    connect("11");
                    if (mainActivity.res) {
                        Toast.makeText(this, "Офаю комп", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.btn_game5:
                    connect("12");
                    if (mainActivity.res) {
                        Toast.makeText(this, "Офаю комп", Toast.LENGTH_SHORT).show();
                    }
                    break;

            }
        };
        View.OnClickListener clickListener1 = view -> {
            Intent int_btn = new Intent(Activity_game.this, Activity_comp.class);
            startActivity(int_btn);
        };
        btn_game1.setOnClickListener(onClickListener);
        ret_btn.setOnClickListener(clickListener1);

    }
    private void createInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_game.this);
        builder.setTitle("");
        builder.setMessage("");

        final EditText input = new EditText(Activity_game.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String text = input.getText().toString();
                name_btn = text;
            }
        });

        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
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