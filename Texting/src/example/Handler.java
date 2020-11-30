
package example;

import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class Handler implements RequestHandler<Object, String>
{
    Gson gson;
    
    public Handler() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }
    @Override
    public String handleRequest(Object obj1, final Context context) {
        final String response = new String("200 OK");
        try {
        	HashMap<String, String> hm = new HashMap<String, String>();
        	hm.put("16479956601", "Junaid Ikram");
        	hm.put("14372466992", "Ali Usmani");
        	hm.put("14164567666", "Ian Cheung");
        	LambdaLogger logger = context.getLogger();
            String wholeObjectAsString = gson.toJson(obj1);
            if(wholeObjectAsString.indexOf("Parameters") > 0)
            {
	            wholeObjectAsString = wholeObjectAsString.substring(wholeObjectAsString.indexOf("Parameters"));
	            wholeObjectAsString = wholeObjectAsString.substring(wholeObjectAsString.indexOf("phone"));
	            wholeObjectAsString = wholeObjectAsString.substring(wholeObjectAsString.indexOf("+"));
	            String phoneNumber = wholeObjectAsString.substring(wholeObjectAsString.indexOf("+") + 1,wholeObjectAsString.indexOf("\""));
            }
            wholeObjectAsString = gson.toJson(obj1);
            if(wholeObjectAsString.indexOf("Parameters") > 0)
            {
	            wholeObjectAsString = wholeObjectAsString.substring(wholeObjectAsString.indexOf("Parameters"));
	            wholeObjectAsString = wholeObjectAsString.substring(wholeObjectAsString.indexOf("option"));
	            wholeObjectAsString = wholeObjectAsString.substring(wholeObjectAsString.indexOf("\"") + 1);
	            wholeObjectAsString = wholeObjectAsString.substring(wholeObjectAsString.indexOf("\"") + 1);
	            String option = wholeObjectAsString.substring(0,wholeObjectAsString.indexOf("\""));
            }
            wholeObjectAsString = gson.toJson(obj1);
            String phoneNumber = "";
            String option = "";
            String name = "";
            if(wholeObjectAsString.indexOf("Parameters") > 0)
            {
	            wholeObjectAsString = wholeObjectAsString.substring(wholeObjectAsString.indexOf("Parameters"));
	            name = "";
	            if(wholeObjectAsString.indexOf("name") >= 0)
	            {
	            	wholeObjectAsString = wholeObjectAsString.substring(wholeObjectAsString.indexOf("name"));
	            	wholeObjectAsString = wholeObjectAsString.substring(wholeObjectAsString.indexOf("\"") + 1);
	            	wholeObjectAsString = wholeObjectAsString.substring(wholeObjectAsString.indexOf("\"") + 1);
	            	name = wholeObjectAsString.substring(0,wholeObjectAsString.indexOf("\""));
	            }
	            else
	            {
	            	name = hm.get(phoneNumber);
	            	if(name == null)
	            		name = "Customer";
	            }
            }
            TextBody tb1 = new TextBody();
            tb1.setPhone(phoneNumber);
            tb1.setOption(option);
            tb1.setName(name);
            final URL url = new URL("https://hooks-us.imiconnect.io/events/FM1Q1OHFCM");
            final HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Key", "M2I1Y2U1YmRiYjYxNDRiYWIyNTUxMzkxN2JjOTQ3");
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonString = mapper.writeValueAsString((Object)tb1);
            final DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(jsonString);
            wr.close();
            final int status = con.getResponseCode();
            final BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            final StringBuffer content = new StringBuffer();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


}