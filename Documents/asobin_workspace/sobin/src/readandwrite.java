import java.io.*;
import java.util.*;
/**
 * 
 * @author sobin
 * params
 * args[0] = the wimbledon file
 * args[1] = the name of a player that youre looking for
 * args[2] = the tournament
 */
public class readandwrite {
	protected static HashSet <String> Names;
	protected static HashSet <String> Tournys;
	public void getFile (String args[]) {
		File f = new File(args[0]);
		try {
			BufferedReader in = new BufferedReader (new FileReader (f));
			in.readLine();
			String s = in.readLine();
			StringTokenizer toke = new StringTokenizer (s);
			while (toke.hasMoreTokens()) {
				String name = toke.nextToken();
				Names.add(name);
				String tourny = toke.nextToken();
				Tournys.add(tourny);
				s = in.readLine();
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	public static void main(String args[])throws NullPointerException {
		String Player = args[1];
		String Tournament = args[2];
		new readandwrite();
		Hashtable<HashSet, HashSet> winners = new Hashtable<HashSet, HashSet> ();
		winners.put(Names, Tournys);
		System.out.println(winners.entrySet());
			for(HashSet itm : winners.keySet())
			{
				if (winners.containsValue(Player) && winners.containsValue(Tournament)) {
					System.out.println("TRUE");
				}
				else if (winners.containsValue(Player) == false || winners.containsValue(Tournament) == false){
					System.out.println("FALSE");
				}
				System.out.println(itm + " " + winners.get(itm));
			}
		
	}
}