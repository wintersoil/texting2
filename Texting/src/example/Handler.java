
package example;


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
    @Override
    public String handleRequest(Object obj1, final Context context) {
    	this.gson = new GsonBuilder().setPrettyPrinting().create();
    	LambdaLogger logger = context.getLogger();
        TextBody tb1 = new TextBody();
        try {
        	HashMap<String, String> hm = new HashMap<String, String>();
        	hm.put("16479956601", "Jay Ikram");
        	hm.put("14372466992", "Ali Usmani");
        	
            String wholeObjectAsString = gson.toJson(obj1);
            String phoneNumber = "";
            String option = "";
            String name = "";
            logger.log("Hey this is a clown");
            if(wholeObjectAsString.indexOf("Parameters") > 0)
            {
	            wholeObjectAsString = wholeObjectAsString.substring(wholeObjectAsString.indexOf("Parameters"));
	            wholeObjectAsString = wholeObjectAsString.substring(wholeObjectAsString.indexOf("phone"));
	            wholeObjectAsString = wholeObjectAsString.substring(wholeObjectAsString.indexOf("+"));
	            phoneNumber = wholeObjectAsString.substring(wholeObjectAsString.indexOf("+") + 1,wholeObjectAsString.indexOf("\""));
	            logger.log(phoneNumber);
            }
            logger.log("Hey parsing to gson string");
            wholeObjectAsString = gson.toJson(obj1);
            logger.log("Hey parsing to gson string done");
            if(wholeObjectAsString.indexOf("Parameters") > 0)
            {
	            wholeObjectAsString = wholeObjectAsString.substring(wholeObjectAsString.indexOf("Parameters"));
	            wholeObjectAsString = wholeObjectAsString.substring(wholeObjectAsString.indexOf("option"));
	            wholeObjectAsString = wholeObjectAsString.substring(wholeObjectAsString.indexOf("\"") + 1);
	            wholeObjectAsString = wholeObjectAsString.substring(wholeObjectAsString.indexOf("\"") + 1);
	            option = wholeObjectAsString.substring(0,wholeObjectAsString.indexOf("\""));
	            logger.log(option);
            }
            wholeObjectAsString = gson.toJson(obj1);
            logger.log("Hey parsing to gson string done more");

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
	            	logger.log(name);
	            }
	            else
	            {
	            	name = hm.get(phoneNumber);
	            	logger.log(name + " 7 From Dict");
	            	if(name == null)
	            		name = "Customer";
	            }
            }
            logger.log("Hey there it is 1");

            tb1.setPhone(phoneNumber);
            tb1.setOption(option);
            tb1.setName(name);
            tb1.setStatus("success");
            logger.log("Hey there it is 2");
            final URL url = new URL("https://hooks-sandbox.imiconnect.io/events/2QZXI2QXQG");
            final HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Key", "M2I1Y2U1YmRiYjYxNDRiYWIyNTUxMzkxN2JjOTQ3");
            logger.log("Hey there it is 3");
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonString = mapper.writeValueAsString((Object)tb1);
            logger.log("Hey there it is 4");
            final DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            logger.log("Hey there it is 5");
            wr.writeBytes(jsonString);
            wr.close();
            logger.log("Hey there it is 6");
            final int status = con.getResponseCode();
            logger.log("Hey there it is 7");
//            final BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//            final StringBuffer content = new StringBuffer();
//            String inputLine;
//            while ((inputLine = in.readLine()) != null) {
//                content.append(inputLine);
//            }
//            in.close();
            con.disconnect();
            logger.log("Hey there it is 8");
        }
        catch (Exception e) {
        	logger.log("In exception");
        	logger.log(e.getMessage());
            e.printStackTrace();
        }
        return gson.toJson(tb1);
    }


}
