import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class ExtractorCPP extends ExtractorC {

	private Set<String> reservedWords;
	
	public ExtractorCPP(File program) throws IOException {
		super(program);
	}
	
	@Override
	protected void prepareReservedWords() {
		this.reservedWords = new HashSet<String>();
		for (String s : ReservedCPP.reservedWords) {
			this.reservedWords.add(s);
		}
	}
	
}