/******************************************************\
| Functions relating to accessing and using resources. |
|                                                      |
| @author David Saxon                                  |
\******************************************************/

package nz.co.withfire.diecubesdie.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.util.Log;

public class ResourceUtil {
    
    /**Reads a resource into a string
    @param context the android context
    @param resourceID the id of the resource
    @return a string containing the data of the resource*/
    static public String resourceToString(
        final Context context, int resourceID) {
        
        //open the resource into an input stream
        final InputStream inputStream = context.getResources().
            openRawResource(resourceID);
        //open the input stream in an input stream reader
        final InputStreamReader inputStreamReader =
            new InputStreamReader(inputStream);
        //open the input stream reader in a buffered reader
        final BufferedReader bufferedReader =
            new BufferedReader(inputStreamReader);
        
        //reading variables
        String nextLine;
        final StringBuilder text = new StringBuilder();
        
        try {
            
            while ((nextLine = bufferedReader.readLine()) != null) {
                
                text.append(nextLine);
                text.append('\n');
            }
        } catch (IOException e) {
            
            return null;
        }
        
        return text.toString();
    }
}
