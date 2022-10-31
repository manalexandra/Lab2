import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static int integer_check(String s) {
        try {
            Integer.parseInt(s);
            return 1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    public static boolean identifier_check(String str)
    {
        // If first character is invalid
        if (!((str.charAt(0) >= 'a' && str.charAt(0) <= 'z')
                || (str.charAt(0)>= 'A' && str.charAt(0) <= 'Z')
                || str.charAt(0) == '_'))
            return false;
        // Traverse the string for the rest of the characters
        for (int i = 1; i < str.length(); i++)
        {
            if (!((str.charAt(i) >= 'a' && str.charAt(i) <= 'z')
                    || (str.charAt(i) >= 'A' && str.charAt(i) <= 'Z')
                    || (str.charAt(i) >= '0' && str.charAt(i) <= '9')
                    || str.charAt(i) == '_'))
                return false;
        }

        // String is a valid identifier
        return true;
    }

    public static boolean reservedWord_check(String s) {
        return s.equals("number") || s.equals("letter") || s.equals("letters") || s.equals("list") || s.equals("if") || s.equals("for") || s.equals("while") || s.equals("dodo") || s.equals("read") || s.equals("write") || s.equals("otherwise");
    }

    public static boolean separator_check(String s) {
        return s.equals("::") || s.equals(";;") || s.equals("(") || s.equals(")") || s.equals("{") || s.equals("}");
    }

    public static boolean operator_check(String s) {
        return s.equals("==") || s.equals(">>") || s.equals("<<") || s.equals(">=") || s.equals("<=") || s.equals("++") || s.equals("--") || s.equals("**") || s.equals("//") || s.equals("!=") || s.equals("=");
    }

    public static void main(String[] args) {

        Map<String, Integer> symbolMap = new HashMap<String, Integer>(); //only identifiers and constants
        int n = 0;

        Map<String, List<Integer>> PIFMap = new HashMap<String, List<Integer>>();
        int m = 0;

        Map<String, Integer> map = new HashMap<String, Integer>();

        map.put("identifier", 0);
        map.put("constant", 1);
        map.put("number", 2);
        map.put("letter", 3);
        map.put("letters", 4);
        map.put("list", 5);
        map.put("if", 6);
        map.put("for", 7);
        map.put("while", 8);
        map.put("dodo", 9);
        map.put("read", 10);
        map.put("write", 11);
        map.put("otherwise", 12);
        map.put("::", 13);
        map.put(";;", 14);
        map.put("(", 15);
        map.put(")", 16);
        map.put("{", 17);
        map.put("}", 18);
        map.put("==", 19);
        map.put(">>", 20);
        map.put("<<", 21);
        map.put(">=", 22);
        map.put("<=", 23);
        map.put("++", 24);
        map.put("--", 25);
        map.put("**", 26);
        map.put("//", 27);
        map.put("!=", 28);
        map.put("=", 29);

        try {
            File file = new File("C:\\Users\\Alexandra\\OneDrive\\Desktop\\3rd year\\Semester 1\\Formal Languages and Compiler Design\\fisier2.txt");    //creates a new file instance
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            StringBuffer sb = new StringBuffer();
            String line;

            while ((line = br.readLine()) != null) {

                String[] splited = line.split(" ");

                for (String s : splited) {
                    if (integer_check(s) == 1) {
                        if (!(map.containsKey(s))) {
                            if(!(symbolMap.containsKey(s))) {
                                symbolMap.put(s, n);
                                n++;
                            }
                        }
                    } else if (identifier_check(s)) {
                        if (!(map.containsKey(s))) {
                            if (!(symbolMap.containsKey(s))) {
                                symbolMap.put(s, n);
                                n++;
                            }
                        }
                    }
                    if ((identifier_check(s)) || (integer_check(s) == 1) ||reservedWord_check(s) || separator_check(s) || operator_check(s)) {
                        List<Integer> maps = new ArrayList<>();
                        maps.add(symbolMap.get(s) == null ? -1 : symbolMap.get(s));
                        if((identifier_check(s))){
                            maps.add(0);
                        } else if ((integer_check(s) == 1)){
                            maps.add(1);
                        } else {
                            maps.add(map.get(s));
                        }
                        PIFMap.put(s, maps);
                    } else {
                        PIFMap.put(s, Collections.singletonList(0));
                    }
                }
            }
            fr.close();
            System.out.println("Symbol Table: ");
            for(Map.Entry<String, Integer> entry : symbolMap.entrySet())
                System.out.println("Key = " + entry.getKey() +
                        ", Value = " + entry.getValue());
            System.out.println(sb.toString());

            System.out.println("Program Internal Form: ");
            for(Map.Entry<String, List<Integer>> entry : PIFMap.entrySet())
                System.out.println("Key = " + entry.getKey() +
                        ", [SymbolTable value, TokenTable value] = " + entry.getValue());
            System.out.println(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
