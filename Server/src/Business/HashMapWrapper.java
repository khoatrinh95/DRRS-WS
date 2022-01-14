package Business;

import java.util.HashMap;
import java.util.List;

public class HashMapWrapper {
    private HashMap<String, HashMap<String, List<TimeSlot>>> hashMapHashMap;

    public HashMapWrapper(HashMap<String, HashMap<String, List<TimeSlot>>> hashMapHashMap1) {
        hashMapHashMap = hashMapHashMap1;
    }

    public HashMap<String, List<TimeSlot>> get(String date){
        return hashMapHashMap.get(date);
    }
}
