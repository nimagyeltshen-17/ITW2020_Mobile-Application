package gcit.edu.todo22;

import android.os.AsyncTask;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class FetchBook  extends AsyncTask<String,Void,String> {
    private WeakReference<TextView> mTitleText;
    private WeakReference<TextView>mAuthorText;

    public FetchBook(TextView mTitleText, TextView mAuthorText) {
        this.mTitleText =new WeakReference<> (mTitleText);
        this.mAuthorText =new WeakReference<>( mAuthorText);
    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getBookInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        try{
            JSONObject jsonObject=new JSONObject(s);
            JSONArray itemArray=jsonObject.getJSONArray("items");
            int i=0;
            String title=null;
            String author=null;
            while(i< itemArray.length() &&(author==null && title==null)){
                JSONObject book=itemArray.getJSONObject(i);
                JSONObject volumeInfo=book.getJSONObject("volumeInfo");

                try{
                    title=volumeInfo.getString("title");
                    author=volumeInfo.getString("authors");

                }catch (Exception e){
                    e.printStackTrace();
                }
                i++;

            }

            if (title !=null && author !=null){
                mTitleText.get().setText(title);
                mAuthorText.get().setText(author);
            }else {
                mTitleText.get().setText("No Result Found");
                mAuthorText.get().setText("No Result Found");
            }


        }catch(JSONException E) {
//            mTitleText.get().setText("No Result Found");
//            mAuthorText.get().setText("No Result Found");
            E.printStackTrace();

        }
        super.onPostExecute(s);
    }
}
