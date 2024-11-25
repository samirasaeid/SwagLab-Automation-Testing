package helpers.Transformer;

import Config.Config;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;
import java.util.List;


import java.util.*;

public class GenericHelper {
    public static List<Integer> getRandomUniqueIndices(int totalProducts, int count){
        Set<Integer> set = new HashSet<Integer>();
        Random random = new Random();

        while (set.size()<count){
            int randomIndex = random.nextInt(totalProducts);
            set.add(randomIndex);
        }
        return new ArrayList<>(set);
    }




}
