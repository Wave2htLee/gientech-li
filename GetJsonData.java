package org.wave;

public class GetJsonData {


    public static void main(String[] args) {
        System.out.println(getJsonValueStringByKey("{\"age\":1,\"list\":[{\"age\":1,\"list\":[],\"name\":\"zhang0\",\"num\":1,\"sex\":\"man0\"},{\"age\":2,\"list\":[],\"name\":\"zhang1\",\"num\":2,\"sex\":\"man1\"},{\"age\":3,\"list\":[],\"name\":\"zhang2\",\"num\":3,\"sex\":\"man2\"},{\"age\":4,\"list\":[],\"name\":\"zhang3\",\"num\":4,\"sex\":\"man3\"},{\"age\":5,\"list\":[],\"name\":\"zhang4\",\"num\":5,\"sex\":\"man4\"},{\"age\":6,\"list\":[],\"name\":\"zhang5\",\"num\":6,\"sex\":\"man5\"},{\"age\":7,\"list\":[],\"name\":\"zhang6\",\"num\":7,\"sex\":\"man6\"},{\"age\":8,\"list\":[],\"name\":\"zhang7\",\"num\":8,\"sex\":\"man7\"},{\"age\":9,\"list\":[],\"name\":\"zhang8\",\"num\":9,\"sex\":\"man8\"},{\"age\":10,\"list\":[],\"name\":\"zhang9\",\"num\":10,\"sex\":\"man9\"}],\"name\":\"zhang\",\"num\":1,\"sex\":\"man\"}",
                "list"));
    }

    public static int getKeyIndexInJsonString(String json,String key) {
         return getKeyIndexInJsonString(json,key,0);
    }

    public static  String getJsonValueStringByKey(String json, String key){
        String value = HandleGetJsonValueStringByKey(json,key);
        if ( value == null ) {
            throw new RuntimeException("can not get value String by key : '"+ key +"' , check the key or json String which is valid ");
        }
        return value;
    }

    public static String HandleGetJsonValueStringByKey(String json, String key){
        int index = 0;
        char[] symbol = new char[1024];
        int checkIndex = -1;
//        while (true) {
//            index = json.indexOf(word,index);
//            if(index < 0) {
//                System.out.println("did‘nt match key : " + word);
//            }
//            //index = index + word.length();
//           // char ss = json.indexOf(index -1 );
//
//            if (json.charAt(index -1 ) == '"' && json.charAt(index + word.length()) == '"' ) {
//                break;
//            } else {
//                index = index + word.length();
//            }
//        }
        index = getKeyIndexInJsonString(json,key);

        boolean start = false;
        boolean isStringChar = false;
        int endIndex = 0;

        for (int i = index + key.length() + 1 ; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '"' || c == '[' || c == '{' || c == '}' || c == ']' ) {
                if (!start) {
                    if (c == '}' || c == ']')
                        return null;
                    if (c == '"' || c == '[' || c == '{') {
                        isStringChar = true;
                        start = true;
                        checkIndex ++;
                        symbol[checkIndex] = c;
                    }
                } else {
                    if (c == '"') {
                        if (symbol[checkIndex] == '"') {
                            if (!isStringChar){
                                isStringChar = true;
                                symbol[checkIndex] = c;
                            } else {
                                isStringChar = false;
                                if (symbol[i] == '"')
                                    checkIndex --;
                                else return null;
                            }
                            checkIndex --;
                        }
                    } else if ((c == '{' || c == '[') || !isStringChar) {
                        checkIndex ++;
                        symbol[checkIndex] = c;
                    }
                    else if(c == '}' || c == ']') {
                        char needMatch = getMatchChar(c);
                        if (symbol[checkIndex] == needMatch) {
                            checkIndex --;
                        } else
                            return null;
                    }
                    if (start && checkIndex == -1) {
                        endIndex = i + 1;
                        break;
                    }
                }
            }
        }
        return json.substring(index -1 ,endIndex);
    }
    public static char getMatchChar(char c) {
        switch(c) {
            case '"' :
                return '"';
            case '}' :
                return '{';
            case ']' :
                return '[';
            case ')' :
                return ')';

        }
        return  ' ';
    }

    /**
     *
     * @param json
     * @param key
     * @param startIndex
     * @return return the key index from json.
     *
     */
    public static int getKeyIndexInJsonString(String json,String key,int startIndex) {
        //int index = 0;
        while (true) {
            startIndex = json.indexOf(key,startIndex);
            if(startIndex < 0) {
                System.out.println("did‘nt match key : " + key);
            }
            if (json.charAt(startIndex -1 ) == '"' && json.charAt(startIndex + key.length()) == '"' ) {
                break;
            } else {
                startIndex = startIndex + key.length();
            }
        }
        return startIndex;
    }
}
