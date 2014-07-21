import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
// Taken from http://whyjava.wordpress.com/2010/05/04/finding-all-the-indexes-of-a-whole-word-in-a-given-string-using-java/
public class WholeWordIndexFinder {
 
    private String searchString;
 
    public WholeWordIndexFinder(String searchString) {
        this.searchString = searchString;
    }
 
    public List<IndexWrapper> findIndexesForKeyword(String keyword) {
        String regex = "\\b"+keyword+"\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(searchString);
 
        List<IndexWrapper> wrappers = new ArrayList<IndexWrapper>();
 
        while(matcher.find() == true){
            int end = matcher.end();
            int start = matcher.start();
            IndexWrapper wrapper = new IndexWrapper(start, end);
            wrappers.add(wrapper);
        }
        return wrappers;
    }
 
    public static void main(String[] args) {
        WholeWordIndexFinder finder = new WholeWordIndexFinder("don?t be evil.being evil is bad");
        List<IndexWrapper> indexes = finder.findIndexesForKeyword("be");
        System.out.println("Indexes found "+indexes.size() +" keyword found at index : "+indexes.get(0).getStart());
    }
 
}