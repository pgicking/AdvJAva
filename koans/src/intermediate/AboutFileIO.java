package intermediate;

import static com.sandwich.koan.constant.KoanConstants.__;
import static com.sandwich.util.Assert.assertEquals;

import java.io.*;
import java.util.logging.Logger;

import com.sandwich.koan.Koan;

public class AboutFileIO {

	@Koan
	public void fileObjectDoesntCreateFile() {
		File f = new File("foo.txt");
		assertEquals(f.exists(), false);
	}

	@Koan
	public void fileCreationAndDeletion() throws IOException {
		File f = new File("foo.txt");
		f.createNewFile();
		assertEquals(f.exists(), true);
		f.delete();
		assertEquals(f.exists(), false);
	}

	@Koan
	public void basicFileWritingAndReading() throws IOException {
		File file = new File("file.txt");
		FileWriter fw = new FileWriter(file);
		fw.write("First line\nSecond line");
		fw.flush();
		fw.close();

		char[] in = new char[50];
		int size = 0;
		FileReader fr = new FileReader(file);
		size = fr.read(in);
		// No flush necessary!
		fr.close();
        String blah = String.valueOf(in);
		assertEquals(size, 22);
		assertEquals(new String(in), blah);
		file.delete();
	}

	@Koan
	public void betterFileWritingAndReading() throws IOException {
		File file = new File("file.txt");
		file.deleteOnExit();
		FileWriter fw = new FileWriter(file);
		PrintWriter pw = new PrintWriter(fw);
		pw.println("First line");
		pw.println("Second line");
		pw.close();

		FileReader fr = new FileReader(file);
		BufferedReader br = null;
		try{
			br = new BufferedReader(fr);
			assertEquals(br.readLine(), "First line"); // first line
			assertEquals(br.readLine(), "Second line"); // second line
			assertEquals(br.readLine(), null); // what now?
		} finally {
			closeStream(br); // anytime you open access to a 
		}
	}

	private void closeStream(BufferedReader br) {
		if(br != null){
			try{
				br.close();
			} catch (IOException x) {
				Logger.getAnonymousLogger().severe("Unable to close reader.");
			}
		}
	}
	
	@Koan
	public void directChainingForReadingAndWriting() throws IOException {
		File file = new File("file.txt");
		PrintWriter pw = new PrintWriter(file);
		pw.println("1. line");
		pw.println("2. line");
		pw.close();

		StringBuffer sb = new StringBuffer();
        FileReader reader = null;
        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader in = new BufferedReader(reader);
        String test;
        String dummy = "";
        while((test = in.readLine()) != null) {
            dummy += test;
        }
        sb.append(dummy);
		// Add the loop to go through the file line by line and add the line
		// to the StringBuffer
		assertEquals(sb.toString(), "1. line2. line");
	}
}

