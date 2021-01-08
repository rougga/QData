package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Tools {

    public static List<List<ArrayList>> splitAgences(List<ArrayList> table, int totaleIndex) {
        List<List<ArrayList>> st = new ArrayList<>();
        List<ArrayList> filler = new ArrayList<>();
        for (int i = 0; i < table.size(); i++) {
            if (Objects.equals(table.get(i).get(totaleIndex), "Sous-Totale")) {
                filler.add(table.get(i));
                st.add(filler);
                filler = new ArrayList<>();
            } else if (Objects.equals(table.get(i).get(totaleIndex), "Totale")) {
                filler = new ArrayList<>();
                filler.add(table.get(i));
                st.add(filler);
                filler = new ArrayList<>();
            } else {
                filler.add(table.get(i));
            }
        }
        return st;
    }

}
