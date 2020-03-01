package dmdd.dmddjava.common.utils;

import java.util.UUID;

public class UtilUUID 
{
	public static String uuid()
	{
		UUID uuid=UUID.randomUUID();
		return uuid.toString();
	}
}
