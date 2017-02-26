package hack.pranav.hackersquad.androidmd;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText searchBar;
    Button searchButton;
    JSONSerializer serializer;
    ListView results;
    String[] arr;
    private JSONObject obj;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        serializer = new JSONSerializer(getApplicationContext(), "search_request.json", new String[]{"numberOfResults", "dymSize",
                                                                                                        "page", "filterByPrecedence",
                                                                                                        "searchTerm", "filterByExpression",
                                                                                                        "distinctBy", "showFields", "properties",
                                                                                                        "clientApp", "clientAppVersion", "siteId",
                                                                                                        "userId", "age", "sex", "filterByAge", "filterBySex"});
        searchBar = (EditText) findViewById(R.id.search_bar);
        searchButton = (Button) findViewById(R.id.search_button);
        results = (ListView) findViewById(R.id.resultsList);
        arr = new String[10];
        url = "http://184.73.124.73:80/PortalWebService/api/v2/product/exampleLexicalStream/search";

        //final ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_main, arr);
        //results.setAdapter(adapter);
        //Need to error handle for empty array

        searchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String txt = searchBar.toString();
                Log.d("D", "a");
                if(txt.length() > 0) {
                    Log.d("D", "ay");
                    boolean isSaved = saveData(txt);
                    if( isSaved )
                        ObtainJSONObject();
                    Log.d("D", "oy");
                    /*JsonObjectRequest req = new JsonObjectRequest(url, obj,new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                VolleyLog.v("Response:%n %s", response.toString(4)); //changed from volleylog
                                Log.d("D", "ayy");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d("D", "ayyy");
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.e("Error: ", error.getMessage());
                        }
                    });*/
                    RequestQueue queue = Volley.newRequestQueue(v.getContext());
                    StringRequest strRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>()
                            {
                                @Override
                                public void onResponse(String response)
                                {
                                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                }
                            },
                            new Response.ErrorListener()
                            {
                                @Override
                                public void onErrorResponse(VolleyError error)
                                {
                                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            })
                    {
                        @Override
                        protected Map<String, String> getParams()
                        {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("tag", "test");
                            return params;
                        }
                    };

                    queue.add(strRequest);
                }
            }
        });
    }

    private boolean saveData( String searchTerm ) {
        try{
            serializer.saveFile(new String[] { "10", "5", "1", "1", searchTerm, "", "", "", "", "App", "1.0", "HospitalA", "UserA", "0", "F", "false", "false"});
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean ObtainJSONObject()
    {
        try
        {
            obj = serializer.loadFile();
            Log.d("load_file", "File Successfully loaded");
            return true;
        }
        catch (Exception e)
        {
            Log.e("load_file", "File Not Loaded Successfully");
            return false;
        }
    }
}
