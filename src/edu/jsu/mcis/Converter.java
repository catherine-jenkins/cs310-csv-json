package edu.jsu.mcis;

import java.io.*;
import java.util.*;
import com.opencsv.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Converter {
    
    /*
    
        Consider the following CSV data:
        
        "ID","Total","Assignment 1","Assignment 2","Exam 1"
        "111278","611","146","128","337"
        "111352","867","227","228","412"
        "111373","461","96","90","275"
        "111305","835","220","217","398"
        "111399","898","226","229","443"
        "111160","454","77","125","252"
        "111276","579","130","111","338"
        "111241","973","236","237","500"
        
        The corresponding JSON data would be similar to the following (tabs and
        other whitespace have been added for clarity).  Note the curly braces,
        square brackets, and double-quotes!  These indicate which values should
        be encoded as strings, and which values should be encoded as integers!
        
        {
            "colHeaders":["ID","Total","Assignment 1","Assignment 2","Exam 1"],
            "rowHeaders":["111278","111352","111373","111305","111399","111160",
            "111276","111241"],
            "data":[[611,146,128,337],
                    [867,227,228,412],
                    [461,96,90,275],
                    [835,220,217,398],
                    [898,226,229,443],
                    [454,77,125,252],
                    [579,130,111,338],
                    [973,236,237,500]
            ]
        }
    
        Your task for this program is to complete the two conversion methods in
        this class, "csvToJson()" and "jsonToCsv()", so that the CSV data shown
        above can be converted to JSON format, and vice-versa.  Both methods
        should return the converted data as strings, but the strings do not need
        to include the newlines and whitespace shown in the examples; again,
        this whitespace has been added only for clarity.
    
        NOTE: YOU SHOULD NOT WRITE ANY CODE WHICH MANUALLY COMPOSES THE OUTPUT
        STRINGS!!!  Leave ALL string conversion to the two data conversion
        libraries we have discussed, OpenCSV and json-simple.  See the "Data
        Exchange" lecture notes for more details, including example code.
    
    */
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        
        String results = "";
        
        try {
            
            CSVReader reader = new CSVReader(new StringReader(csvString));
            List<String[]> full = reader.readAll();
            Iterator<String[]> iterator = full.iterator();
            
            // INSERT YOUR CODE HERE
            ArrayList <String> colHeaders = new ArrayList<>();
            ArrayList <String> rowHeaders = new ArrayList<>();
            ArrayList <ArrayList<Integer>> data = new ArrayList<>();
            
            //converting array of strings to arraylist of strings (column headers)
            String [] temp = iterator.next();
            
            for (int i =0; i < temp.length; i++){
                colHeaders.add(temp[i]);
            }
            
            //parsing out row headers and data 
            while (iterator.hasNext()){
                
                temp = iterator.next();
                
                //add first String of each line to rowHeaders
                rowHeaders.add(temp[0]);
                
                //add remaining strings to data
                ArrayList <Integer> temp2 = new ArrayList<>();
                
                for (int i = 1; i < temp.length; i++){
                    
                    temp2.add(Integer.parseInt(temp[i]));
                }
                
                data.add(temp2);
            }
            
            JSONObject jsonObject = new JSONObject();
            
            jsonObject.put("colHeaders", colHeaders);
            jsonObject.put("rowHeaders", rowHeaders);
            jsonObject.put("data", data);
            
            //converting JSON object to JSON string 
            results = jsonObject.toJSONString();
        } 
        
        catch(Exception e) { return e.toString(); }
        
        return results.trim();
        
    }
    
    public static String jsonToCsv(String jsonString) {
        
        String results = "";
        
        try {

            StringWriter writer = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(writer, ',', '"', '\n');
            
            // INSERT YOUR CODE HERE
            
            //parse jsonString to JSONObject
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject)parser.parse(jsonString);
            
            //get column headers and store in an array list of strings
            ArrayList <String> temp = (ArrayList<String>)jsonObject.get("colHeaders");
            
            String[] colHeaders = new String[temp.size()];
            
            for (int i = 0; i < colHeaders.length; i++){
                
                colHeaders[i] = temp.get(i);
            }
            
            //pass array of strings of column headers to csvWriter
            csvWriter.writeNext(colHeaders);
            
            //get row header and data for each line
            ArrayList<ArrayList<Long>> data = (ArrayList<ArrayList<Long>>)jsonObject.get("data");
            
            //creates an array of Strings to feed to csv writer
            String[] temp2 = new String[1 + data.get(0).size()];
            
            ArrayList <String> rowHeaders = (ArrayList<String>)jsonObject.get("rowHeaders");
            
            for (int i = 0; i < rowHeaders.size() ; i++){
                
                temp2[0] = rowHeaders.get(i);
                
                for (int j = 0; j < data.get(0).size(); j++){
                    
                    temp2[j+1] = data.get(i).get(j).toString();
                }
                
                csvWriter.writeNext(temp2);
            }
            results = writer.toString();
        }
        
        catch(Exception e) { return e.toString(); }
        
        return results.trim();
        
    }

}