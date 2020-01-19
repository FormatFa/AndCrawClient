package formatfa.craw.andcrawclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.connect).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        EditText host = findViewById(R.id.host);
        EditText port = findViewById(R.id.port);
        Intent intent = new Intent(this,ViewActivity.class);
        intent.putExtra("host",host.getText().toString());
        intent.putExtra("port",port.getText().toString());
        startActivity(intent);
    }
}
