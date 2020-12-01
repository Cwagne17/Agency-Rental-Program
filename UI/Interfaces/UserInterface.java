package UI.Interfaces;
import java.util.Scanner;

public interface UserInterface {
	//the start method simply begins the command loop
	//of the corresponding user interface type (employee or manager)
	
	public void start(Scanner input);
}