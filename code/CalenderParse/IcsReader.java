package app;

import java.io.File;
import java.util.Scanner;

public class IcsReader{
	public static void main(String[] args) {
        System.out.println("Hi");
		try{
			Scanner scan = new Scanner(new File("0941736-1845496.ics"));
			System.out.println("pls");
			while(scan.hasNext()){
				String line = scan.nextLine().toString();
				if(line.contains("BEGIN:VEVENT")){
					
					scan.nextLine();//skip "UID"
					scan.nextLine();//skip "DTSTAMP"
					String startDate = scan.nextLine().toString().substring(8);
					System.out.println(startDate);
					String endDate = scan.nextLine().toString().substring(6);	
					System.out.println(endDate);
					String location = scan.nextLine().toString().substring(9);	
					System.out.println(location);
					String courseCode = scan.nextLine().toString().substring(8);
					System.out.println(courseCode);
					scan.nextLine(); //skip "TRANSP"
					scan.nextLine();	//Skip "X-MICROSOFT-CDO-INTENDEDSTATUS"
					String teacher = scan.nextLine().toString().substring(12,18);
					char firstChar = teacher.charAt(0);
					char lastChar = teacher.charAt(5);
					
					
					
					if(firstChar != ' ' && lastChar == ' ')
					{
						System.out.println(teacher);
					}
					System.out.println("----------------------");
					scan.close();
				}
			}
		}
		catch (Exception e){
			System.out.println("Nope");
		}
    }
}