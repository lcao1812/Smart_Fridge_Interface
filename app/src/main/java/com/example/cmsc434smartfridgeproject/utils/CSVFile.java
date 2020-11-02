package com.example.cmsc434smartfridgeproject.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CSVFile {
    InputStream inputStream;

    public CSVFile(InputStream inputStream){
        this.inputStream = inputStream;
    }

    public List read() throws ParseException {
        List <String>resultList = new ArrayList<String>();
        List foodList = new ArrayList<FoodItem>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
//                String[] row = csvLine.split(",");
                resultList.add(csvLine);
            }
        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: "+e);
            }
        }
        for(int i = 0; i < resultList.size() / 5 ; i++){
            int j = 5*i;
            HashSet<String> allergens = new HashSet<String>();
            allergens.add(resultList.get(j+3));
            foodList.add(new FoodItem(
                    resultList.get(j),
                    Integer.parseInt(resultList.get(j+1)),
                    new SimpleDateFormat("MM/dd/yyyy").parse(resultList.get(j+2)),
                    allergens,
                    resultList.get(j+4)
            ));
        }
        return foodList;
    }
//     foodName.getText().toString().trim(),
//                                    Integer.parseInt(foodAmount.getText().toString().trim()),
//            new SimpleDateFormat("MM/dd/yyyy").parse(foodDate.getText().toString().trim()),
//    foodAllergies,
//            foodOwner.getText().toString().trim())
}
