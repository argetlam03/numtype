import java.io.File;
import java.io.IOException;
import java.util.*;

public class prototype {
    //                    a  b  c  d  e  f  g  h  i  j  k  l  m  n  o  p  q  r  s  t  u  v  w  x  y  z
    static int keys [] = {4, 2, 1, 4, 7, 4, 5, 5, 9, 6, 6, 6, 3, 2, 9, 9, 7, 7, 4, 8, 9, 2, 7, 1, 8, 1};
    static String WORDS_FILE_NAME = "words.txt";
    static HashMap<Long, ArrayList<Word>> map;

    public static void main(String[] args) {
        map = new HashMap<>();
        readFile();
        Scanner lineRead = new Scanner(System.in);

        String s = "";
        while (!s.equals("exit")) {
            s = lineRead.nextLine();
            long encoding;
            try {
                encoding = Long.parseLong(s);
            } catch (NumberFormatException e) {
                System.out.println("Input must consist of positive integers.");
                continue;
            }

            String decoding;
            try {
                decoding = map.get(encoding).getFirst().text;
            } catch (NullPointerException e) {
                System.out.println("Word for this encoding does not exist in data.");
                continue;
            }

            System.out.println(decoding);
        }
    }

    static void readFile() {
        try (Scanner fileRead = new Scanner(new File(WORDS_FILE_NAME))) {
            while (fileRead.hasNextLine()) {
                String word = fileRead.nextLine();
                handleWords(word.toLowerCase());
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    static void handleWords(String word) {
        String buildNum = "";
        for (int i = 0; i < word.length(); i++) buildNum += keys[word.charAt(i) - 'a'];

        long value = Long.parseLong(buildNum);
        ArrayList<Word> wordsOfValue = map.get(value);
        if (wordsOfValue == null) {
            wordsOfValue = new ArrayList<>();
        }

        wordsOfValue.add(new Word(word, 0));

        map.put(value, wordsOfValue);
    }

    static class Word implements Comparable<Word> {
        String text;
        long rank;

        Word(String text, long rank) {
            this.text = text;
            this.rank = rank;
        }

        @Override
        public int compareTo(Word o) {
            return Long.compare(this.rank, o.rank);
        }
    }
}

// 7 : qwer
// 8 : ty
// 9 : uiop
// 4 : asdf
// 5 : gh
// 6 : jkl
// 1: zxc
// 2 vbn
// 3 : m

/*
    Notes:
        Typing with same type of finger seems more natural potentially than by location...
 */