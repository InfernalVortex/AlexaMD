package hack.pranav.hackersquad.androidmd;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Created by Mrigank on 7/6/2015.
 */
public class JSONSerializer
{
    private Context mContext;
    private String mFilename;
    private String[] keys;

    public JSONSerializer(Context c, String f, String[] keys)
    {
        this.mContext = c;
        this.mFilename = f;
        this.keys = keys;
    }

    private JSONObject toJSON(String[] objs) throws JSONException
    {
        JSONObject json = new JSONObject();
        for(int i = 0; i < keys.length; i++)
        {
            json.put(keys[i], objs[i].toString());
        }

        return json;
    }

    public void saveFile(String[] objs) throws JSONException, IOException
    {
        JSONObject obj = toJSON(objs);
        Writer writer = null;
        try
        {
            OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(obj.toString());
        }
        finally
        {
            if(writer != null)
                writer.close();
        }

    }

    public JSONObject loadFile() throws JSONException, IOException
    {
        BufferedReader reader = null;
        JSONObject jsonObject;

        try
        {
            InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null)
            {
                jsonString.append(line);
            }

            jsonObject = (JSONObject) new JSONTokener(jsonString.toString()).nextValue();
        }
        catch (Exception e)
        {
            jsonObject = new JSONObject();
        }
        finally {
            if(reader != null)
            {
                reader.close();
            }
        }

        return jsonObject;
    }
}