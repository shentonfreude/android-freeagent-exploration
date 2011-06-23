package shenton.chris.HelloFormStuff;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class HelloFormStuffActivity extends Activity {

	private static final String TAG = "FAC";
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		final Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(HelloFormStuffActivity.this, "Starting...", Toast.LENGTH_SHORT).show();
				BufferedReader in = null;
				
				try {
					// manifest.xml needs <uses-permission android:name="android.permission.INTERNET" />
					HttpClient client = new DefaultHttpClient();
					HttpGet request = new HttpGet("https://koansys.freeagentcentral.com/tasks");
					// How the fuck are you supposed to Base64.encode("user:pass")? 
					request.addHeader("Authorization", "Basic [PUT YOUR BASE64 HERE]");
					request.addHeader("Accept", "application/xml");
					HttpResponse response = client.execute(request);
					Log.i(TAG, response.getStatusLine().toString());
					
					int status = response.getStatusLine().getStatusCode();
					if (status != HttpStatus.SC_OK) {
						ByteArrayOutputStream ostream = new ByteArrayOutputStream();
						response.getEntity().writeTo(ostream);
						Log.e(TAG, ostream.toString());
						Toast.makeText(HelloFormStuffActivity.this, ostream.toString(), Toast.LENGTH_LONG);
					} else {
						in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
						Toast.makeText(HelloFormStuffActivity.this, "1:" + in.readLine(), Toast.LENGTH_SHORT).show();
						Toast.makeText(HelloFormStuffActivity.this, "2:" + in.readLine(), Toast.LENGTH_SHORT).show();
						Toast.makeText(HelloFormStuffActivity.this, "3:" + in.readLine(), Toast.LENGTH_SHORT).show();
						Toast.makeText(HelloFormStuffActivity.this, "4:" + in.readLine(), Toast.LENGTH_SHORT).show();
						Toast.makeText(HelloFormStuffActivity.this, "...Done", Toast.LENGTH_SHORT).show();
						String line;
						while ((line = in.readLine()) != null) {
							Log.i(TAG, line);
						}
						in.close();
					}
				}
				catch (Exception e) {
					Log.e(TAG, e.toString());
					Toast.makeText(HelloFormStuffActivity.this, "ERR: " + e.toString(), Toast.LENGTH_LONG).show();
				}
				
				
				
				
			}
		});
		
	}
}
