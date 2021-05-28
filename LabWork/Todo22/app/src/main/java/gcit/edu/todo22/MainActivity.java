 package gcit.edu.todo22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

 public class MainActivity extends AppCompatActivity {
    public EditText searchInput;
    private TextView title,author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchInput=findViewById(R.id.bookInput);
        title=findViewById(R.id.tittleText);
        author=findViewById(R.id.authorText);
    }

     public void searchBook(View view) {
        String queryString=searchInput.getText().toString();
        new FetchBook(title,author).execute(queryString);
         ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
         NetworkInfo networkInfo=null;
         if(connectivityManager!=null){
             networkInfo=connectivityManager.getActiveNetworkInfo();
         }
         if(networkInfo !=null && networkInfo.isConnected()&& queryString.length()!=0){
             new FetchBook(title,author).execute(queryString);
             title.setText("");
             author.setText("loading");

         }
         else {
             if (queryString.length()==0){
                 title.setText("");
                 author.setText("No search term");

             }else {
                 title.setText("");
                 author.setText("No network");
             }
         }
     }
 }