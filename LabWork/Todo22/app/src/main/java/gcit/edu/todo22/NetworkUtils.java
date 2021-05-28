package gcit.edu.todo22;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.Buffer;

public class NetworkUtils {
    private static String BOOK_BASE_URL="https://www.googleapis.com/books/v1/volumes?";
    private static final  String QUERY_PARAM="q";
    private static final  String MAX_RESULT="maxResult";
    private static final  String print_type="printType";

    private static final String LOG_TAG=NetworkUtils.class.getSimpleName();

    static String getBookInfo(String queryString){
        HttpURLConnection urlConnection=null;
        BufferedReader reader=null;
        String bookJASONString=null;

        try {
            Uri builtUri=Uri.parse(BOOK_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM,queryString)
                    .appendQueryParameter(MAX_RESULT,"10")
                    .appendQueryParameter(print_type,"books")
                    .build();
            //establishing the connection
            URL requestUrl=new URL(builtUri.toString());
            urlConnection=(HttpURLConnection)requestUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            //get the   input stream
            InputStream inputStream=urlConnection.getInputStream();
            reader=new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder=new StringBuilder();
            String line;
            while((line=reader.readLine())!=null){
                builder.append(line);
                builder.append("\n");

            }
            if (builder.length()==0){
                return null;
            }
            bookJASONString=builder.toString();


        }catch (IOException E){
            E.printStackTrace();
        }
        finally {
            if (urlConnection !=null){
                urlConnection.disconnect();
            }
            if (reader !=null){
                try{
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

        }
        Log.d(LOG_TAG,bookJASONString);
        return bookJASONString;


    }
}
