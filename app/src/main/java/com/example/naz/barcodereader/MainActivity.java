package com.example.naz.barcodereader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    DatabaseHelper myDb;
    private Button scanBtn;
    Button btnAddData;
    private TextView formatTxt, contentTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scanBtn = (Button)findViewById(R.id.scan_button);
        formatTxt = (TextView)findViewById(R.id.scan_format);
        contentTxt = (TextView)findViewById(R.id.scan_content);
        scanBtn.setOnClickListener(this);
        myDb = new DatabaseHelper(this);
        btnAddData = (Button) findViewById(R.id.button_add);
        AddData();
    }

    public void  AddData()
    {
        btnAddData.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInsearted = myDb.insertData(formatTxt.getText().toString(),
                                contentTxt.getText().toString());
                        if (isInsearted= true)
                            Toast.makeText(MainActivity.this,"Data Inserted", Toast.LENGTH_LONG).show();
                        Toast.makeText(MainActivity.this,"Data not Inserted", Toast.LENGTH_LONG).show();
                    }
                }
        );

    };

    }

    public void onClick(View v){
        if(v.getId()==R.id.scan_button){
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            formatTxt.setText("FORMAT: " + scanFormat);
            contentTxt.setText("CONTENT: " + scanContent);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
