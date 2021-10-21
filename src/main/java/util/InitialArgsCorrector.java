package util;

import java.util.ArrayList;
import java.util.List;

public class InitialArgsCorrector {

    public static String[] concatenateArgs(String args[]) {
        List<String> newArgs = new ArrayList<>();
        int i = 0;
        while(i < args.length) {
            if(args[i].contains("'") && args[i].lastIndexOf('\'') != args[i].length()-1) {
                String curr = args[i];
                i++;
                while(i < args.length) {
                    curr += " " + args[i];
                    if(args[i].contains("'")) {
                        i++;
                        break;
                    }
                    i++;
                }
                newArgs.add(curr);
            } else {
                newArgs.add(args[i]);
                i++;
            }
        }
        String[] newArr = new String[newArgs.size()];
        return newArgs.toArray(newArr);
    }

    public static void addInDoubleQuotes(String[] args) {
        for(int i = 0; i < args.length; i++) {
            if(args[i].contains("{")) {
                int leftParanIndex = args[i].indexOf("{");
                int colonIndex = args[i].indexOf(":");

                String argWithQuotes = args[i].substring(0, leftParanIndex+1) + "\"";
                argWithQuotes += args[i].substring(leftParanIndex+1, colonIndex) + "\"";
                argWithQuotes += args[i].substring(colonIndex);

                args[i] = argWithQuotes;
            }
        }
    }
}
