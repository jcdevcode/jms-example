package mx.ninja.dev.cl;

/**
 * Commands of application.
 * 
 * @author Julio Bolaños
 *
 */
public enum Command {
	CONSUMER, PRODUCER;

	public static Command getEnum(String cmd) {
		for (Command cm : Command.values()) {
			if (cm.name().equalsIgnoreCase(cmd)) {
				return cm;
			}
		}
		return null;
	}
	
	
	public static String valuesList(){
		StringBuilder str = new StringBuilder();
		int length = Command.values().length;
		int limit = length -1;
		for(int i = 0; i < length ; i++){			
			if( i != limit){
				str.append(Command.values()[i]);
				str.append(" / ");
			}else{
				str.append(Command.values()[i]);
			}
		}		
		return str.toString();
	}
}
